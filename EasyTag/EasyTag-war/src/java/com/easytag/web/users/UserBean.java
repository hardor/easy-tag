package com.easytag.web.users;


import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author hardor
 */
@ManagedBean
@ViewScoped
public class UserBean implements Serializable {

    public UserBean() {
    }
    private String login;
    private String password;
    private String email;
    private String passwordConfirm;
    private String firstName;
    private String lastName;
 
 

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
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

    
    @Override
    public String toString() {
        return "UserBean{" + "login=" + login + ", password=" + password + ", email=" + email + ", passwordConfirm=" + passwordConfirm  + '}';
    }
}
