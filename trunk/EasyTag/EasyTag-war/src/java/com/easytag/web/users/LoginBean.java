package com.easytag.web.users;

import com.easytag.core.ejb.UserManagerLocal;
import com.easytag.core.jpa.entity.User;
import com.easytag.core.util.EncryptionTools;
import com.easytag.web.utils.JSFHelper;
import com.easytag.web.utils.SessionUtils;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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

    public void loginAction(final ActionEvent evt) {
        if (getLogin() == null || getPassword() == null) {
            return;
        }

        logoutAction(evt);

        final JSFHelper helper = getJSFHelper();
        final UserManagerLocal um = getUm();
        final User ue = um.getUserByLogin(getLogin());

        if (ue != null) {
            if (EncryptionTools.SHA256(getPassword()).equals(um.getPasswordByLogin(getLogin()))) {
                HttpSession session = helper.getSession(true);
                SessionUtils.setUserId(session, ue.getUser_id());
                helper.redirect("user/index.xhtml");
                // TODO: logging
            } else {
                helper.addMessage("login_message", FacesMessage.SEVERITY_WARN, "WRONG", "Incorrect password");
                // TODO: logging
            }
        } else {
            helper.addMessage("login_messages", FacesMessage.SEVERITY_WARN, "WRONG", "Incorrect login.");
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

    protected UserManagerLocal getUm() {
        return um;
    }

    public boolean isLoggedIn() {
        JSFHelper helper = getJSFHelper();
        return SessionUtils.isLoggedIn(helper.getRequest());
    }

    public void logoutAction(ActionEvent evt) {
        JSFHelper helper = getJSFHelper();
        if (SessionUtils.isLoggedIn(helper.getRequest())) {
            HttpSession session = helper.getSession(true);
            SessionUtils.setUserId(session, null);
            helper.redirect("/login");
        }
    }
}
