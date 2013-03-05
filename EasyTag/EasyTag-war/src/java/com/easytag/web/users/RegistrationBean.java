package com.easytag.web.users;

import com.easytag.core.ejb.UserManagerLocal;
import com.easytag.core.jpa.entity.User;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vildanov Ruslan
 */
@ManagedBean
@ViewScoped
public class RegistrationBean implements Serializable {

    public RegistrationBean() {
    }
    @EJB
    UserManagerLocal um;

    public List<User> getUsers() {
        return um.getAllUsers();
    }

    public User getCurrentUser() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        return um.getUserByLogin((String) session.getAttribute("login"));
    }
    public String getCurrentFirstName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);       
        return um.getUserByLogin((String) session.getAttribute("login")).getFirstName();
    }

    public void registerAction(UserBean ub) {
      System.err.println(ub.toString());
        if (ub.getLogin() == null || ub.getPassword() == null) {
            return;            
        }

      //  if (ub.getPassword().equals(ub.getPasswordConfirm())) {
            User ue = um.getUserByLogin(ub.getLogin());

            if (ue != null) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("register_messages", new FacesMessage(FacesMessage.SEVERITY_WARN, "Failed ", "user " + ub.getLogin() + " is registred already. Plese change name!"));

            } else {
                um.createUser(ub.getEmail(), ub.getFirstName(), null, ub.getPassword(), ub.getLogin());              
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("register_messages", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success ", "user " + ub.getLogin() + " is register"));

            }
//        } else {
//            FacesContext fc = FacesContext.getCurrentInstance();
//            fc.addMessage("register_messages", new FacesMessage(FacesMessage.SEVERITY_WARN, "Failed ", "Password and Confirm password is different"));
//        }

    }

    public void deleteUser(Long id) {
        if (id == null) {
            return;
        }
        um.removeUserById(id);
    }

           
}
