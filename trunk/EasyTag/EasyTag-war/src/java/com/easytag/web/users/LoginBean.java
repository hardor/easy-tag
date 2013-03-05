package com.easytag.web.users;

import com.easytag.core.ejb.UserManagerLocal;
import com.easytag.core.jpa.entity.User;
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

    public void loginAction(ActionEvent evt) throws IOException {
        if (getLogin() == null || getPassword() == null) {
            return;
        }
        User ue = um.getUserByLogin(getLogin());

        if (ue != null) {
            if (getPassword().equals(um.getPasswordByLogin(getLogin()))) {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute("login", getLogin());
                FacesContext.getCurrentInstance().getExternalContext().redirect("profit.xhtml");
                // fc.addMessage("login_messages", new FacesMessage(FacesMessage.SEVERITY_WARN, "WRONG", "Incorrect password"));
                System.err.println("Success");
            } else {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("login_messages", new FacesMessage(FacesMessage.SEVERITY_WARN, "WRONG", "Incorrect password"));
                System.err.println("Incorrect password");
            }
        } else {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("login_messages", new FacesMessage(FacesMessage.SEVERITY_WARN, "WRONG", "Incorrect password and login."));
            System.err.println("Incorrect password and login");
        }
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

    public void logoutAction(ActionEvent evt) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("login", null);

    }
}
