package com.easytag.web.jsf.docs;

import com.easytag.core.ejb.AlbumManagerLocal;
import com.easytag.core.jpa.entity.Album;
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
public class EditAlbumBean implements Serializable {

    @EJB
    private AlbumManagerLocal am;   
    private String name;
    private String additional_info;

    public EditAlbumBean() {
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

    public void editAlbum(ActionEvent evt) {
        JSFHelper helper = getJSFHelper();
        String albumId = helper.getRequest().getParameter("alb_id");
        Album album = am.modifyAlbum(Long.valueOf(albumId), name, additional_info);
        if (album != null) {
            helper.addMessage(FacesMessage.SEVERITY_INFO, "Succes:", "Info edite");
            helper.redirect("/user/docs/library.xhtml", "alb_id", albumId);
        } else {
            helper.addMessage(FacesMessage.SEVERITY_ERROR, "Fail:", "Info not change");
        }
    }

    protected JSFHelper getJSFHelper() {
        return new JSFHelper();
    }
}
