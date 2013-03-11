package com.easytag.core.ejb;

import com.easytag.core.jpa.entity.User;
import com.easytag.core.jpa.entity.UserPassword;
import com.easytag.core.util.CommonTools;
import com.easytag.core.util.EncryptionTools;
import com.easytag.core.util.StringUtils;
import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 * Contains methods for checking user credentials when login.
 *
 * @author 7
 */
@Stateless
public class PasswordManager implements PasswordManagerLocal {

    private static Logger log = Logger.getLogger(PasswordManager.class);
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    UserManagerLocal um;

    @Override
    public Long checkUserPassword(Long userId, String password) {

        UserPassword up = CommonTools.getSingleElement(
                em.createNamedQuery("UserPassword.findByUserIdAndPassword", UserPassword.class)
                .setParameter("id", userId)
                .setParameter("password", password)
                .getResultList());
        if (log.isTraceEnabled()) {
            if (up == null) {
                log.trace("checkUserPassword: incorrect credentials for userId = " + userId);
            } else {
                log.trace("checkUserPassword: access granted for userId = " + userId);
            }
        }
        return up == null ? null : up.getUser().getId();
    }

    @Override
    public Long checkUserPassword(String login, String password) {
        UserPassword up = CommonTools.getSingleElement(
                em.createNamedQuery("UserPassword.findByLoginAndPassword", UserPassword.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .getResultList());
        if (log.isTraceEnabled()) {
            if (up == null) {
                log.trace("checkUserPassword: incorrect credentials for login = " + login);
            } else {
                log.trace("checkUserPassword: access granted for login = " + login);
            }
        }
        return up == null ? null : up.getUser().getId();
    }

    private <T> T getFirstElement(Collection<T> c) {
        if (c == null || c.isEmpty()) {
            return null;
        }
        return c.iterator().next();
    }

    @Override
    public boolean recoverPassword(Long userId) {
        System.err.println("recoverPassword(): userId = " + userId);
        if (userId == null) {
            return false;
        }
        UserPassword up = em.find(UserPassword.class, userId);
        User u = um.getUserById(userId);
        if (up == null) {
            return false;
        }
        try {
            String newPassword = generatePassword();
//            TODO : for future send email
//            EmailObject email = EmailHelper.createPasswordRecoveryEmail(u.getName() + " " + u.getSurname(), newPassword);
//            emailMan.sendEmail(email.getSubject(), email.getText(), u.getEmail());
            up.setPassword(EncryptionTools.SHA256(newPassword));
            newPassword = null;
            em.persist(up);
            return true;
        } catch (Exception ex) {
            log.error("recoverPassword(): failed to recover password for: userId=" + userId, ex);
        }
        return false;
    }

    private String generatePassword() {
        return StringUtils.generateRandomString(10);
    }

    @Override
    public void changeUserPassword(Long userId, String newPassword) {
        if (userId == null || newPassword == null) {
            throw new NullPointerException();
        }
        em.createQuery("update UserPassword up set up.password = :newPassword where up.id = :id")
                .setParameter("id", userId)
                .setParameter("newPassword", newPassword)
                .executeUpdate();
        // TODO: add log entry about this operation
    }
}
