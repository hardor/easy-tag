package com.easytag.core.ejb;

import com.easytag.core.jpa.entity.Files;
import com.easytag.core.jpa.entity.User;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



/**
 *
 * @author Vildanov Ruslan
 */
@Stateless
public class FileManager implements FileManagerLocal {

    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private UserManagerLocal um;

    @Override
    public Files createFile(String name, String Url, String contentType, Long size, Long user_id,String extraInfo) {
        Files files = new Files();
        files.setCreatedBy(um.getUserById(user_id));
        files.setName(name);
        files.setExtraInfo(extraInfo);
        files.setSize(size);
        files.setDateCreation(new java.sql.Date(new Date().getTime()));
        files.setUrl(Url);
        em.persist(files);
        return files;

    }

    @Override
    public boolean fileExists(Long fileId) {
        Files f = em.find(Files.class, fileId);
        return f != null;
    }

    @Override
    public Files getFileById(Long userId, Long fileId) {
        if (userId == null) {
            return null;
        }
        Files f = em.find(Files.class, fileId);
        if (f == null) {
            return null;
        }
        User u = em.find(User.class, userId);
        if (u == null) {
            // illegal user
            return null;
        }
        return em.find(Files.class, fileId);
    }

    @Override
    public void test() {
         Files files = new Files();       
        files.setName("testname");       
        files.setSize((long)100);
        files.setDateCreation(new java.sql.Date(new Date().getTime()));
        files.setUrl("testUrl");
        em.persist(files);       
    }

    @Override
    public Files getFileByUrl(Long userId, String Url) {
       if (userId == null) {
            return null;
        }
        User u = em.find(User.class, userId);
        if (u == null) {
            // illegal user
            return null;
        }
        
        Query q = em.createNamedQuery("Files.findByUrl", Files.class);
        q.setParameter("url", Url);
         List results = q.getResultList();
         if (!results.isEmpty()) {            
            return (Files) results.get(0);
        } else {
            return null;
        }       
    }
    
    @Override
    public Files getFileByExtraInfo(Long userId, String extraInfo){
         if (userId == null) {
            return null;
        }
        User u = em.find(User.class, userId);
        if (u == null) {
            // illegal user
            return null;
        }
        
        Query q = em.createNamedQuery("Files.findByExtraInfo", Files.class);
        q.setParameter("extraInfo", extraInfo);
         List results = q.getResultList();
         if (!results.isEmpty()) {            
            return (Files) results.get(0);
        } else {
            return null;
        }       
     }
}
