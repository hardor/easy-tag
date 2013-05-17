package com.easytag.core.ejb;

import com.easytag.core.helpers.email.EmailHelper;
import com.easytag.core.helpers.email.EmailObject;
import com.easytag.core.jpa.entity.User;
import com.easytag.core.jpa.entity.UserPassword;
import com.easytag.core.util.CommonTools;
import com.easytag.core.util.EncryptionTools;
import com.easytag.core.util.StringUtils;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    @EJB
    EmailManagerLocal emailMan;

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
        return up == null ? null : up.getUser().getUser_id();
    }

    private <T> T getFirstElement(Collection<T> c) {
        if (c == null || c.isEmpty()) {
            return null;
        }
        return c.iterator().next();
    }

    @Override
    public boolean recoverPassword(Long userId) {
       
        if(userId == null)
            return false;
        UserPassword up = em.find(UserPassword.class, userId);
        User u = um.getUserById(userId);
        if(up == null)
            return false;
        try {
            String newPassword = generatePassword();
            EmailObject email = EmailHelper.createPasswordRecoveryEmail(u.getFirstName()+" "+u.getLastName(), newPassword);
            emailMan.sendEmail(email.getSubject(), email.getText(), u.getEmail());
            up.setPassword(EncryptionTools.SHA256(newPassword));
            newPassword = null;
            em.persist(up);
            return true;
        } catch (Exception ex) {
            log.error("recoverPassword(): failed to recover password for: userId="+userId, ex);
        }
        return false;
    }

    private String generatePassword() {
        return StringUtils.generateRandomString(10);
    }

    @Override
    public UserPassword getByPassword(String password) {
        Query q = em.createNamedQuery("UserPassword.findByPassword", UserPassword.class);
        q.setParameter("password", password);
        List results = q.getResultList();

        if (!results.isEmpty()) {
            UserPassword up = (UserPassword) results.get(0);
            if (up == null) {
                return null;
            }
            return up;
        } else {
            return null;
        }
    }

    @Override
    public UserPassword getByUserId(Long user_id) {
        User u = um.getUserById(user_id);
        Query q = em.createNamedQuery("UserPassword.findByUser", UserPassword.class);
        q.setParameter("user", u);

        List results = q.getResultList();
        if (!results.isEmpty()) {
            UserPassword up = (UserPassword) results.get(0);
            if (up == null) {
                return null;
            }
            return up;
        } else {
            return null;
        }
    }

    @Override
    public UserPassword changeUserPassword(Long userId, String newPassword) {
        if (userId == null || newPassword == null) {
            return null;
        } else {

            UserPassword up = getByUserId(userId);
            if (up != null) {
                up.setPassword(EncryptionTools.SHA256(newPassword));
                em.merge(up);
                log.info("UserPassword modyfyed: id= " + up.getId());
            }

            return up;
        }
    }

    @Override
    public Long checkUserPassword(Long userId, String password) {

        if (userId == null || password == null) {
            throw new NullPointerException();
        } else {
            UserPassword up = getByPassword(password);
            if (up != null && up.getUser().getUser_id() == userId) {
                return up.getId();
            } else {
                return null;
            }
        }

    }

    @Override
    public void setUser(Long old_user_id, Long new_user_id) {       
        UserPassword up = getByUserId(old_user_id);
        up.setUser(um.getUserById(new_user_id));
        em.merge(up);
        log.info("UserPassword modyfyed: id= " + up.getId());
    }
}
