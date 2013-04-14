/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytag.core.ejb;

import com.easytag.core.jpa.entity.Album;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Vildanov Ruslan
 */
@Local
public interface AlbumManagerLocal {
    
    Album createAlbum(Long user_id, String albumName,String additional_info);

    boolean removeAlbumById(Long albumId);

    Album getAlbumById(Long albumId);   

    Album getAlbumByName(String albumName);

    List<Album> getAllAlbums(); 

    Album modifyAlbum(Long userId, String name, String surname, String information, String email, String phone);
}
