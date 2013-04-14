package com.easytag.core.ejb;

import com.easytag.core.jpa.entity.Album;
import com.easytag.core.jpa.entity.Document;
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
public class DocumentManager implements DocumentManagerLocal {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private UserManagerLocal um;
    @EJB
    private AlbumManagerLocal am;

    @Override
    public Document createFile(String name, String Url, String contentType, Long size, Long user_id, String extraInfo ,Long album_id) {
        Document Document = new Document();
        Document.setCreatedBy(um.getUserById(user_id));
        Document.setName(name);
        Document.setExtraInfo(extraInfo);
        Document.setSize(size);
        Document.setDateCreation(new java.util.Date(new Date().getTime()));
        Document.setUrl(Url);
        Document.setAlbum(am.getAlbumById(album_id));
        em.persist(Document);
        return Document;
    }

    @Override
    public boolean fileExists(Long fileId) {
        Document f = em.find(Document.class, fileId);
        return f != null;
    }

    @Override
    public Document getFileById(Long userId, Long fileId) {
        if (userId == null) {
            return null;
        }
        Document f = em.find(Document.class, fileId);
        if (f == null) {
            return null;
        }
        User u = em.find(User.class, userId);
        if (u == null) {
            // illegal user
            return null;
        }
        return em.find(Document.class, fileId);
    }

    @Override
    public Document getFileByUrl(Long userId, String Url) {
        if (userId == null) {
            return null;
        }
        User u = em.find(User.class, userId);
        if (u == null) {
            // illegal user
            return null;
        }

        Query q = em.createNamedQuery("Document.findByUrl", Document.class);
        q.setParameter("url", Url);
        List results = q.getResultList();
        if (!results.isEmpty()) {
            return (Document) results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Document getFileByExtraInfo(Long userId, String extraInfo) {
        if (userId == null) {
            return null;
        }
        User u = em.find(User.class, userId);
        if (u == null) {
            // illegal user
            return null;
        }

        Query q = em.createNamedQuery("Document.findByExtraInfo", Document.class);
        q.setParameter("extraInfo", extraInfo);
        List results = q.getResultList();
        if (!results.isEmpty()) {
            return (Document) results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Document> getAllDocuments() {
        List<Document> documents = em.createNamedQuery("Document.findAll").getResultList();
        if (!documents.isEmpty()) {
            return documents;
        } else {
            return null;
        }
    }

    @Override
    public List<Document> getAllAlbumDocuments(Long album_id) {
        System.out.println("alb_id22=" + album_id);
        Query q = em.createNamedQuery("Document.findByAlbum");
        q.setParameter("album_id", album_id);
        List<Document> documents = q.getResultList();
        if (!documents.isEmpty()) {
            return documents;
        } else {
            return null;
        }
    }

}
