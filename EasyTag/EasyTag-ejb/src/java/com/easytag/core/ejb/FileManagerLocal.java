package com.easytag.core.ejb;

import com.easytag.core.jpa.entity.Files;
import javax.ejb.Local;

/**
 *
 * @author Vildanov Ruslan
 */
@Local
public interface FileManagerLocal {

    Files createFile(String name, String Url, String contentType, Long size, Long user_id,String extraInfo);

    boolean fileExists(Long fileId);

    Files getFileById(Long userId, Long fileId);
    
    Files getFileByExtraInfo(Long userId, String extraInfo);

    Files getFileByUrl(Long userId, String Url);

    void test();
}
