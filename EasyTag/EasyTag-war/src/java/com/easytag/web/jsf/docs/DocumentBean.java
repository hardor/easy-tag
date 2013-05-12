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
public class DocumentBean implements Serializable {

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
        JSFHelper helper = getJSFHelper();
        String albumId = helper.getRequest().getParameter("alb_id");           
        return dm.getAllAlbumDocuments(Long.valueOf(albumId));
    }
    
    public List<Document> getDocumentsByAlbumAndUser() {
        JSFHelper helper = getJSFHelper();
        String albumId = helper.getRequest().getParameter("alb_id");          
        return dm.getAllAlbumUsersDocuments(helper.getUserId(),Long.valueOf(albumId));
    }
    

    public Document getDocumentById() {
        JSFHelper helper = getJSFHelper();
        String doc_id = helper.getRequest().getParameter("doc_id");
        return dm.getFileById(helper.getUserId(), Long.valueOf(doc_id));
    }

    public Document getDocumentById(Long id) {
        return dm.getFileById(getJSFHelper().getUserId(), id);
    }

    public Document getNext() {
        Long id = Long.valueOf(getJSFHelper().getRequest().getParameter("doc_id"));
        System.out.println("getNext: id="+id);
        Document doc = getDocumentById(id);
        System.out.println("getNext: doc="+doc);
        List<Document> docList = getDocumentsByAlbum();
        System.out.println("getNext: docList="+docList);
        int idx = docList.indexOf(doc);
        System.out.println("getNext: idx="+idx+ " id="+id);
        if (idx < 0 || idx + 1 == docList.size()) {
            return doc;
        }
        return docList.get(idx + 1);
    }

    public Document getPrevious() {
        Long id = Long.valueOf(getJSFHelper().getRequest().getParameter("doc_id"));
        Document doc = getDocumentById(id);
        List<Document> docList = getDocumentsByAlbum();
        int idx = docList.indexOf(doc);
         System.out.println("getPrevious: idx="+idx+ " id="+id);
        if (idx <= 0) {
            return doc;
        }
        return docList.get(idx - 1);
    }
    
    protected JSFHelper getJSFHelper() {
        return new JSFHelper();
    }
}
