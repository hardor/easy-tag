package com.easytag.web.jsf.docs;

import com.easytag.core.ejb.AlbumManagerLocal;
import com.easytag.core.ejb.DocumentManagerLocal;
import com.easytag.core.jpa.entity.Album;
import com.easytag.core.jpa.entity.Document;
import com.easytag.web.utils.JSFHelper;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vildanov Ruslan
 */
@ManagedBean
@ViewScoped
public class EditDocumentBean implements Serializable {

    @EJB
    private DocumentManagerLocal dm;   
    private String name;
    private String additional_info;
    private Integer rating;
    

    public EditDocumentBean() {
    }    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }    

    public void editDocument(ActionEvent evt) {
        JSFHelper helper = getJSFHelper();
        String docId = helper.getRequest().getParameter("doc_id");
        String albId = helper.getRequest().getParameter("alb_id");
        Document doc = dm.modifyDocument(Long.valueOf(docId), name, additional_info,rating);
        if (doc != null) {
            helper.addMessage(FacesMessage.SEVERITY_INFO, "Succes:", "Info edite");
            helper.redirect("/user/docs/document.xhtml", "doc_id", docId,"alb_id",albId);
        } else {
            helper.addMessage(FacesMessage.SEVERITY_ERROR, "Fail:", "Info not change");
        }
    }

    protected JSFHelper getJSFHelper() {
        return new JSFHelper();
    }
}
