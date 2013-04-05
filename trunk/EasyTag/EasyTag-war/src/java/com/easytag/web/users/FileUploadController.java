package com.easytag.web.users;

import com.easytag.core.CoreConstants;
import com.easytag.core.ejb.FileManagerLocal;
import com.easytag.core.ejb.UserManagerLocal;
import com.easytag.core.jpa.entity.Files;
import com.easytag.web.utils.JSFHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.activation.MimetypesFileTypeMap;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class FileUploadController implements Serializable {

    @EJB
    FileManagerLocal fm;
    @EJB
    UserManagerLocal um;
    private String destination = CoreConstants.DEFAULT_PATH;
    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        if (file != null) {
            try {
                copyFile(getFile().getFileName(), getFile().getInputstream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void copyFile(String fileName, InputStream in) {
        try {
            // write the inputStream to a FileOutputStream   
            File tempFile = File.createTempFile("easytag_", "_upload", new File(destination));
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
            String contentType = new MimetypesFileTypeMap().getContentType(tempFile);
            Long size = tempFile.length();
            Long user_id = JSFHelper.getUserId();
            String extraInfo = "general";
            Files newFiles = fm.createFile(name, Url, contentType, size, user_id, extraInfo);           
            if ("/user/profile/edit.xhtml".equals(JSFHelper.getRequest().getPathInfo())) {               
                um.setAvatar(JSFHelper.getUserId(), Url);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
