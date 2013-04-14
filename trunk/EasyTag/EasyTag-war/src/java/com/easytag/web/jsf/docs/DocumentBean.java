package com.easytag.web.jsf.docs;

import com.easytag.core.ejb.DocumentManagerLocal;
import com.easytag.core.jpa.entity.Document;
import com.easytag.web.utils.JSFHelper;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Vildanov Ruslan
 */
@ManagedBean
@ViewScoped
public class DocumentBean implements Serializable{

    @EJB
    private DocumentManagerLocal dm;   

    public DocumentBean() {
    }

       public void createDocument(ActionEvent evt) {
            //look to the FileUploadController
        }
    
    public List<Document> getAllDocuments() {
        return dm.getAllDocuments();
    }
     
    public List<Document> getDocumentsByAlbum() {
        String albumId = JSFHelper.getRequest().getParameter("alb_id");   
        System.out.println("alb_id="+albumId);
        return dm.getAllAlbumDocuments(Long.valueOf(albumId));  
    }
     
      public Document getDocumentById() {   
        String doc_id = JSFHelper.getRequest().getParameter("doc_id");   
        return dm.getFileById(JSFHelper.getUserId(), Long.valueOf(doc_id));
    }
   
    
}
