package com.easytag.core.ejb;

import com.easytag.core.jpa.entity.Album;
import com.easytag.core.jpa.entity.Document;
import com.easytag.core.jpa.entity.Fragment;
import com.easytag.core.jpa.entity.Tag;
import com.easytag.core.jpa.entity.User;
import com.easytag.core.util.CollectionUtils;
import com.easytag.core.util.StringUtils;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
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
    public Document getFileById(Long fileId) {
       
        Document f = em.find(Document.class, fileId);
        if (f == null) {
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
       
        Query q = em.createNamedQuery("Document.findByAlbum");
        q.setParameter("album_id", album_id);
        List<Document> documents = q.getResultList();
        if (!documents.isEmpty()) {
            return documents;
        } else {
            return null;
        }
    }
    
  @Override
    public List<Document> getAllAlbumUsersDocuments(Long user_id, Long album_id) {
       
        Query q = em.createNamedQuery("Document.findByAlbumAndUser");
        q.setParameter("album_id", album_id);
        q.setParameter("user_id", user_id);
        List<Document> documents = q.getResultList();
        if (!documents.isEmpty()) {
            return documents;
        } else {
            return null;
        }
    }
  
    @Override
    public Fragment addFragment(Long userId, Long documentId, String tag, long x, long y, long width, long height) {
        // check user
        if (documentId == null || userId == null) {
            return null;
        }
        
        Document doc = em.find(Document.class, documentId);
        if (doc == null || !userId.equals(doc.getCreatedBy().getUser_id())) {
            return null;
        }
        
        Set<Tag> tags = createTags(tag);
        
        // create Fragment
        Fragment f = new Fragment();
        f.setAlbum(doc.getAlbum());
        f.setDocument(doc);
        f.setName(tag);
        f.setTags(tags);
        f.setFirstCoordinateX(x);
        f.setFirstCoordinateY(y);
        f.setSecondCoordinateX(x+width);
        f.setSecondCoordinateY(y+height);
        em.persist(f);
        
        return f;
    }
    
    private Set<Tag> createTags(String tagsStr) {
        if (StringUtils.isEmpty(tagsStr)) {
            return Collections.EMPTY_SET;
        }
        Set<Tag> tags = new HashSet<Tag>();
        StringTokenizer st = new StringTokenizer(tagsStr, ";,");
        while (st.hasMoreTokens()) {
            String tagStr = st.nextToken();
            Query q = em.createQuery("select t from Tag t where t.name = :name", Tag.class);
            q.setParameter("name", tagStr);
            q.setMaxResults(1);
            Tag tag = CollectionUtils.getFirstElement((List<Tag>) q.getResultList());
            if (tag == null) {
                // create new tag
                tag = new Tag();
                tag.setName(tagStr);
                em.persist(tag);
            }
            tags.add(tag);
        }
        return tags;
    }

    @Override
    public List<Fragment> getAllFragments(Long userId, Long documentId) {
        // check user
        if (documentId == null || userId == null) {
            return null;
        }
        
        Document doc = em.find(Document.class, documentId);
        if (doc == null || !userId.equals(doc.getCreatedBy().getUser_id())) {
            return null;
        }
        
        Query q = em.createQuery("select f from Fragment f where f.document.id = :doc_id", Fragment.class);
        q.setParameter("doc_id", documentId);
        return q.getResultList();
    }

    @Override
    public void deleteFragment(Long userId, Long fragmentId) {
        // check user
        if (userId == null || fragmentId == null) {
            return;
        }
        Fragment f = em.find(Fragment.class, fragmentId);
        Document doc = f.getDocument();
        if (doc != null && !userId.equals(doc.getCreatedBy().getUser_id())) {
            return;
        }
        em.remove(f);
    }
    
    
    @Override
    public void deleteDocument(Long doc_id) {
        Document de = this.getFileById(doc_id);
        if (de != null) {
            em.remove(de);
        }
    }

    @Override
    public List<Document> getUserDocuments(Long user_id) {
    Query q = em.createNamedQuery("Document.findByUser");
        q.setParameter("user_id", user_id);
        List<Document> documents = q.getResultList();
        if (!documents.isEmpty()) {
            return documents;
        } else {
            return null;
        }
    }
    
}
