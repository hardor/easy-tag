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
        Album ab = am.createAlbum(helper.getUserId(), name, additional_info);
        helper.addMessage("album_messages", FacesMessage.SEVERITY_INFO, "Success ", "album " + getName() + " is create");
        helper.getSession(true).setAttribute("alb_id", ab.getId());
        helper.redirect("/user/docs/library.xhtml", "alb_id", ab.getId() + "");
    }

    public Album getAlbumById(Long alb_id) {
        JSFHelper helper = getJSFHelper();
        return am.getAlbumById(alb_id);
    }

    public List<Album> getAlbums() {
        return am.getAllAlbums();
    }

    public void deleteAlbum(Long album_id) {
        am.deleteAlbum(album_id);
    }

    public void editAlbum() {
    }

    public String getAlbumName() {
        JSFHelper helper = getJSFHelper();
        String albumId = helper.getRequest().getParameter("alb_id");
        Album ab = getAlbumById(Long.valueOf(albumId));
        return ab.getName();
    }

    public void goAlbumPage() {
        JSFHelper helper = getJSFHelper();
        String albumId = helper.getRequest().getParameter("alb_id");
        System.out.println(albumId);
        helper.redirect("/user/docs/library.xhtml", "alb_id", albumId);
    }

    protected JSFHelper getJSFHelper() {
        return new JSFHelper();
    }
}
