package com.easytag.core.ejb;

import com.easytag.core.jpa.entity.User;
import com.easytag.core.jpa.entity.UserPassword;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Vildanov Ruslan
 */
@Stateless
@LocalBean
public class UserManager implements UserManagerLocal {

    private final Logger log = Logger.getLogger(UserManager.class);
    @PersistenceContext
    private EntityManager em;

    @Override
    public User createUser(String email, String firstName, String lastName, String password, String login) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        
        user.setEmail(email);        
        em.persist(user);

        UserPassword up = new UserPassword();
        up.setUser(user);  
        up.setLogin(login);
        up.setPassword(password);
        em.persist(up);

        log.info("createUser: User created: " + user);

        return user;

    }

    @Override
    public boolean removeUserById(Long userId) {
        User u = getUserById(userId);
        if (u == null) {
            return false;
        }
        em.remove(u);
        return false;
    }

    @Override
    public User getUserById(Long userId) {
        Query q = em.createNamedQuery("User.findByUserId", User.class);
        q.setParameter("id", userId);
        return (User) q.getSingleResult();
    }

    @Override
    public Long tryLogin(String login, String password) {
        Query q = em.createNamedQuery("UserPassword.findByLoginAndPassword", UserPassword.class);
        q.setMaxResults(1);
        q.setParameter("login", login);
        q.setParameter("password", password);
        List<UserPassword> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        }
        return l.get(0).getId();
    }

    @Override
    public User getUserByLogin(String login) {
        Query q = em.createNamedQuery("UserPassword.findByLogin", UserPassword.class);
        q.setParameter("login", login);
        List results = q.getResultList();

        if (!results.isEmpty()) {
            UserPassword up = (UserPassword) results.get(0);
            return up.getUser();
        } else {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = em.createNamedQuery("User.findAll").getResultList();
        if (!users.isEmpty()) {
            return users;
        } else {
            return null;
        }
    }

    @Override
    public User getUserByFirstName(String firstName) {
        Query q = em.createNamedQuery("User.findByFirstName", User.class);
        q.setParameter("firstName", firstName);
        List results = q.getResultList();

        if (!results.isEmpty()) {
            return (User) results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        Query q = em.createNamedQuery("UserPassword.findByLoginAndPassword", UserPassword.class);
        q.setParameter("login", login);
        q.setParameter("password", password);
        
        List<UserPassword> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        }

        Query q2 = em.createNamedQuery("User.findByUserId", User.class);
        q.setParameter("id", l.get(0).getId()); 
        return (User) q2.getResultList().get(0);
    }

    @Override
    public String getPasswordByLogin(String login) {
        Query q = em.createNamedQuery("UserPassword.findByLogin", UserPassword.class);
        q.setParameter("login", login);
        List results = q.getResultList();

        if (!results.isEmpty()) {
            UserPassword up = (UserPassword) results.get(0);
            return up.getPassword();
        } else {
            return null;
        }
    }
}
