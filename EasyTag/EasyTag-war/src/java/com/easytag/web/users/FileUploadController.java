package com.easytag.web.users;

import com.easytag.core.CoreConstants;
import com.easytag.core.ejb.AlbumManagerLocal;
import com.easytag.core.ejb.DocumentManagerLocal;
import com.easytag.core.ejb.UserManagerLocal;
import com.easytag.core.jpa.entity.Document;
import com.easytag.web.utils.JSFHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class FileUploadController implements Serializable {

    @ManagedProperty(value = "#{userInfoBean}")
    private UserInfoBean userInfo;

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }
    
    @EJB
    DocumentManagerLocal fm;
    @EJB
    UserManagerLocal um;
    @EJB
    AlbumManagerLocal am;
    
    private String destination = CoreConstants.DEFAULT_PATH;
    private UploadedFile file;

    
    public UploadedFile getFile() {
        return file;
    }
    
    private String albumId;

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
  
    public void setFile(UploadedFile file) {
              
        this.file = file;
    }
        
    public void upload() {      
     
        if (file != null) {
            try {
                copyFile(getFile().getFileName(), getFile().getInputstream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void copyFile(String fileName, InputStream in) {
        JSFHelper helper = getJSFHelper();
        try {
            File uploadDirectory = new File(destination);
            uploadDirectory.mkdirs();
            File tempFile = File.createTempFile("easytag_", "_upload", uploadDirectory);
            OutputStream out = new FileOutputStream(tempFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            
            String name = getFile().getFileName();
            String Url = tempFile.getName();
            String contentType = getFile().getContentType();
            Long size = tempFile.length();
            Long user_id = helper.getUserId();
            String extraInfo = "general";   
            
             //avatar or simple document
            if ("/user/profile/edit.xhtml".equals(helper.getRequest().getServletPath())) {
                setAlbumId(am.getAlbumByName("Avatars").getId().toString());
                Document newDocument = fm.createFile(name, Url, contentType, size, user_id, extraInfo, Long.valueOf(this.getAlbumId()));
                userInfo.setAvatar(Url);
            } else {
                setAlbumId(helper.getRequest().getParameter("alb_id"));
              
                Document newDocument = fm.createFile(name, Url, contentType, size, user_id, extraInfo, Long.valueOf(this.getAlbumId()));
            }
            helper.addMessage(FacesMessage.SEVERITY_INFO, "Success ", "document " + name + " is create!");
        } catch (IOException e) {           
            e.printStackTrace();
        }
    }
    
    protected JSFHelper getJSFHelper() {
        return new JSFHelper();
    }
}
