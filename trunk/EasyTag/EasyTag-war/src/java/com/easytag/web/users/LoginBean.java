package com.easytag.web.users;

import com.easytag.core.ejb.UserManagerLocal;
import com.easytag.core.jpa.entity.User;
import com.easytag.core.jpa.entity.UserPassword;
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

    public LoginBean() {
    }
    @EJB
    UserManagerLocal um;

    public void loginAction(UserBean nb) throws IOException {
         System.err.println(nb.toString());
        if (nb.getLogin() == null || nb.getPassword() == null) {
            return;
        }
        User ue = um.getUserByLogin(nb.getLogin());
        
        if (ue != null) {
            if (nb.getPassword().equals(um.getPasswordByLogin(nb.getLogin()))) {
                HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session.setAttribute("login", nb.getLogin());
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

    public void logoutAction(ActionEvent evt) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("login", null);
        
    }
}
