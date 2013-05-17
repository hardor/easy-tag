package com.easytag.core.ejb;

import com.easytag.core.jpa.entity.Document;
import com.easytag.core.jpa.entity.Fragment;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Vildanov Ruslan
 */
@Local
public interface DocumentManagerLocal {

    Document createFile(String name, String Url, String contentType, Long size, Long user_id,String extraInfo,Long album_id);

    boolean fileExists(Long fileId);

    Document getFileById(Long userId, Long fileId);
    
    Document getFileById(Long fileId);
    
    Document getFileByExtraInfo(Long userId, String extraInfo);

    Document getFileByUrl(Long userId, String Url);
    
    List<Document> getAllDocuments(); 
    
    List<Document> getAllAlbumDocuments(Long album_id);    
    
    List<Document> getAllAlbumUsersDocuments(Long user_id, Long album_id); 
    
    Fragment addFragment(Long userId, Long documentId, String tag, long x, long y, long width, long height);

    public List<Fragment> getAllFragments(Long userId, Long documentId);

    public void deleteFragment(Long userId, Long fragmentId);
    
    void deleteDocument(Long doc_id);
}
