package com.easytag.web.users;

import com.easytag.core.ejb.UserManagerLocal;
import com.easytag.core.jpa.entity.User;
import com.easytag.core.util.EncryptionTools;
import com.easytag.web.utils.JSFHelper;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vildanov Ruslan
 */
@ManagedBean
@ViewScoped
public class RegistrationBean implements Serializable {

    @EJB
    private UserManagerLocal um;
    
    private String login;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String email;
     private String information;
    
    public RegistrationBean() {
        //
    }

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

    public void registerAction(ActionEvent evt) throws IOException {
        if (getLogin() == null || getPassword() == null) {
            return;            
        }
        firstName = "Unspecified";
        lastName = "Unspecified";
        information="Tell to the world about yourself!";
        final JSFHelper helper = getJSFHelper();
        if(getPassword().equals(getConfirmPassword())) {
            User ue = um.getUserByLogin(getLogin());

            if (ue != null) {
                helper.addMessage("register_messages", FacesMessage.SEVERITY_WARN, "Failed ", "user " + getLogin() + " is registred already. Plese change name!");
            } else {
                um.createUser(getEmail(), getFirstName(), getLastName(), EncryptionTools.SHA256(getPassword()), getLogin(),getInformation());              
                helper.addMessage("register_messages", FacesMessage.SEVERITY_INFO, "Success ", "user " + getLogin() + " is register");
                helper.redirect("login");
            }
        } else {
             helper.addMessage("register_messages", FacesMessage.SEVERITY_WARN, "Failed ", "Password and Confirm password is different");
        }

    }

    public void deleteUser(Long id) {
        if (id == null) {
            return;
        }
        um.removeUserById(id);
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }  

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    protected JSFHelper getJSFHelper() {
        return new JSFHelper();
    }
}
