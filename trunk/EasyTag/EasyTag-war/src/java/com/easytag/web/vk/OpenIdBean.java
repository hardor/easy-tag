package com.easytag.web.vk;

import com.easytag.core.ejb.PasswordManager;
import com.easytag.core.ejb.UserManagerLocal;
import com.easytag.core.jpa.entity.User;
import com.easytag.core.util.EncryptionTools;
import com.easytag.web.utils.JSFHelper;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Vildanov Ruslan
 */
@ManagedBean
@SessionScoped
public class OpenIdBean implements Serializable {

    public OpenIdBean() {
    }
    @EJB
    private UserManagerLocal um;
    @EJB
    private PasswordManager pm;
    private String login;
    private String password;
    private boolean openIdStatus;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOpenIdStatus() {
        return openIdStatus;
    }

    public void setOpenIdStatus(boolean openIdStatus) {
        this.openIdStatus = openIdStatus;
    }

    public void bindAction(final ActionEvent evt) {
        JSFHelper helper = getJSFHelper();
        if (getLogin() == null || getPassword() == null) {
            return;
        }

        User ue = um.getUserById(helper.getUserId());
        User vk_ue = um.getUserByLogin(getLogin());



        if (ue != null) {
            if (EncryptionTools.SHA256(getPassword()).equals(um.getPasswordByLogin(getLogin()))) {

                pm.setUser(ue.getUser_id(), vk_ue.getUser_id());
                // TODO: logging
            } else {
                helper.addMessage("openID_messages", FacesMessage.SEVERITY_WARN, "WRONG", "Incorrect password");
                // TODO: logging
            }
        } else {
            helper.addMessage("openID_messages", FacesMessage.SEVERITY_WARN, "WRONG", "Incorrect login.");
            // TODO: logging
        }
    }
    
    protected JSFHelper getJSFHelper() {
        return new JSFHelper();
    }
}
