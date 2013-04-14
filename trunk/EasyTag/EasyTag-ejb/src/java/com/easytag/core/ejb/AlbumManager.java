package com.easytag.core.ejb;

import com.easytag.core.jpa.entity.Album;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
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
public class AlbumManager implements AlbumManagerLocal {

    private final Logger log = Logger.getLogger(UserManager.class);
    @PersistenceContext
    private EntityManager em;
    @EJB
    private UserManagerLocal um;

    @Override
    public Album createAlbum(Long user_id, String albumName, String additional_info) {
        Album album = new Album();
        album.setName(albumName);
        album.setDateCreation(new java.sql.Date(new Date().getTime()));
        album.setAdditionalInfo(additional_info);
        
        album.setUser(um.getUserById(user_id));
        em.persist(album);
      
        return album;
    }

    @Override
    public boolean removeAlbumById(Long albumId) {
        Album a = getAlbumById(albumId);
        if (a == null) {
            return false;
        }
        em.remove(a);
        return false;
    }

    @Override
    public Album getAlbumById(Long albumId) {
        Query q = em.createNamedQuery("Album.findByAlbumId", Album.class);
        q.setParameter("album_id", albumId);
        List results = q.getResultList();
        if (!results.isEmpty()) {
            return (Album) results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Album getAlbumByName(String albumName) {
        Query q = em.createNamedQuery("Album.findByAlbumName", Album.class);
        q.setParameter("albumName", albumName);
        List results = q.getResultList();

        if (!results.isEmpty()) {
            return (Album) results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albums = em.createNamedQuery("Album.findAll").getResultList();
        if (!albums.isEmpty()) {
            return albums;
        } else {
            return null;
        }
    }

    @Override
    public Album modifyAlbum(Long userId, String name, String surname, String information, String email, String phone) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
