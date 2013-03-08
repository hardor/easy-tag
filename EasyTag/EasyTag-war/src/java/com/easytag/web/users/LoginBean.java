package com.easytag.web.users;

import com.easytag.core.ejb.UserManagerLocal;
import com.easytag.core.jpa.entity.User;
import com.easytag.core.util.EncryptionTools;
import com.easytag.web.utils.JSFHelper;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vildanov Ruslan
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    @EJB
    private UserManagerLocal um;
    
    private String login;
    private String password;
    private boolean loggedIn;

    public void loginAction(final ActionEvent evt) throws IOException {
        if (getLogin() == null || getPassword() == null) {
            return;
        }
        
        logoutAction(evt);

        final JSFHelper helper = getJSFHelper();
        final User ue = um.getUserByLogin(getLogin());

        if (ue != null) {
            if (EncryptionTools.SHA256(getPassword()).equals(um.getPasswordByLogin(getLogin()))) {
                HttpSession session = helper.getSession(true);
                session.setAttribute("user_id", ue.getId());
                helper.redirect("user/index.xhtml");
                loggedIn = true;
                // TODO: logging
            } else {
                FacesContext fc = FacesContext.getCurrentInstance();
                helper.addMessage("login_messages", FacesMessage.SEVERITY_WARN, "WRONG", "Incorrect password");
                // TODO: logging
            }
        } else {
            helper.addMessage("login_messages", FacesMessage.SEVERITY_WARN, "WRONG", "Incorrect password and login.");
            // TODO: logging
        }
    }
    
    protected JSFHelper getJSFHelper() {
        return new JSFHelper();
    }

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

    public UserManagerLocal getUm() {
        return um;
    }

    public void setUm(UserManagerLocal um) {
        this.um = um;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void logoutAction(ActionEvent evt) throws IOException {
        loggedIn = false;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("user_id", null);
        getJSFHelper().redirect("/login");
    }
}
