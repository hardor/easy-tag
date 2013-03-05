package com.easytag.core.ejb;

import com.easytag.core.CoreConstants;
import com.easytag.core.jpa.entity.User;
import com.easytag.core.jpa.entity.UserGroup;
import com.easytag.core.jpa.entity.UserPassword;
import com.easytag.core.util.CollectionUtils;
import com.easytag.core.util.EncryptionTools;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 * A start-up EJB.
 * Initializes application before start.
 * @author danon
 */
@Singleton @Startup
public class AppInit {
    
    private static final Logger log = Logger.getLogger(AppInit.class);
    
    @PersistenceContext
    private EntityManager em;

    /**
     * Executed when after the bean is created, 
     * i.e. when application is starting.
     */
    @PostConstruct
    public void postConstruct() {
        // init Database and stuff
        createUserGroups();
        createUsers();
    }
    
    /**
     * Executed when this bean is being destroyed, 
     * i.e. when application is being undeployed.
     */
    @PreDestroy
    public void preDestroy() {
        // safely interrupt threadads, release resources
    }
    
    protected void createUserGroups() {
        EntityManager em = getEntityManager();
        try {
            // Admin Group
            if (em.find(UserGroup.class, CoreConstants.ADMIN_GROUP_ID) == null) {
                UserGroup ug = new UserGroup();
                ug.setId(CoreConstants.ADMIN_GROUP_ID);
                ug.setName("Administrators");
                ug.setInformation("Administrative users. Can do whatever they want.");
                em.persist(ug);
            }
            
            // Simple user group
            if (em.find(UserGroup.class, CoreConstants.USER_GROUP_ID) == null) {
                UserGroup ug = new UserGroup();
                ug.setId(CoreConstants.USER_GROUP_ID);
                ug.setName("Users");
                ug.setInformation("Ordinary users. Limited rights.");
                em.persist(ug);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create user groups.", ex);
        }
    }
    
    protected void createUsers() {
        EntityManager em = getEntityManager();
        try {
            // Admin User
            if (em.find(User.class, CoreConstants.ADMIN_USER_ID) == null) {
                User u = new User();
                u.setId(CoreConstants.ADMIN_USER_ID);
                u.setFirstName("EasyTag");
                u.setLastName("Admin");                
                u.setUserGroup(em.find(UserGroup.class, CoreConstants.ADMIN_GROUP_ID));
                em.persist(u);
                
                // Password
                Query q = em.createQuery("select up from UserPassword up where up.user = :user", UserPassword.class);
                q.setParameter("user", u);
                if (CollectionUtils.getFirstElement(q.getResultList()) == null) {
                    UserPassword up = new UserPassword();
                    up.setLogin("admin");
                    up.setUser(u);
                    up.setPassword(EncryptionTools.SHA256("admin"));
                    em.persist(up);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create users.", ex);
        }
    }
    
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
