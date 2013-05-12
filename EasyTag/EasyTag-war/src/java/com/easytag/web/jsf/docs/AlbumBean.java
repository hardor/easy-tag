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

/**
 *
 * @author Vildanov Ruslan
 */
@ManagedBean
@ViewScoped
public class AlbumBean implements Serializable {

    @EJB
    private AlbumManagerLocal am;
    private String name;
    private String additional_info;

    public AlbumBean() {
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

    public void createAlbum(ActionEvent evt) throws IOException {
        JSFHelper helper = getJSFHelper();
        if (getName() == null || getAdditional_info() == null) {
            helper.addMessage("album_messages", FacesMessage.SEVERITY_WARN, "Failed ", "Not valid information");
            return;
        }
        //UserInfoBean ub = new UserInfoBean();      
        am.createAlbum(helper.getUserId(), name, additional_info);
        helper.addMessage("album_messages", FacesMessage.SEVERITY_INFO, "Success ", "album " + getName() + " is create");
    }

    public List<Album> getAlbums() {
        return am.getAllAlbums();
    }

    protected JSFHelper getJSFHelper() {
        return new JSFHelper();
    }
}
