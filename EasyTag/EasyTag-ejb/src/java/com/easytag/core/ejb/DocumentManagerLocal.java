package com.easytag.core.ejb;

import com.easytag.core.jpa.entity.Album;
import com.easytag.core.jpa.entity.Document;
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
    
    Document getFileByExtraInfo(Long userId, String extraInfo);

    Document getFileByUrl(Long userId, String Url);
    
    List<Document> getAllDocuments(); 
    
    List<Document> getAllAlbumDocuments(Long album_id);    
    
}
