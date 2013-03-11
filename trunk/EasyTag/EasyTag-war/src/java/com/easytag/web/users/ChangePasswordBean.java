package com.easytag.web.users;

import com.easytag.core.ejb.PasswordManagerLocal;
import com.easytag.core.util.EncryptionTools;
import com.easytag.core.util.StringUtils;
import com.easytag.web.utils.JSFHelper;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 * This view scoped managed bean is used 
 * for changing password of current user.
 * 
 * @author danon
 */
@ManagedBean
@ViewScoped
public class ChangePasswordBean implements Serializable {

    @EJB
    private PasswordManagerLocal pm;
    
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    
    /**
     * Creates a new instance of ChangePasswordBean
     */
    public ChangePasswordBean() {
        
    }
    
    public void resetInput() {
        
    }
    
    private boolean validateUserInput() {
        if(!StringUtils.isNotEmpty(oldPassword, newPassword, confirmPassword)) {
            //JSFHelper.addMessage(FacesMessage.SEVERITY_ERROR, "Validation Error:", "All fields are required.");
            return false;
        }
        if(!confirmPassword.equals(newPassword)) {
          //  JSFHelper.addMessage(FacesMessage.SEVERITY_ERROR, "Validation Error:", "Confirmation password doesn't match new password.");
            return false;
        }
        
        // TODO: regexp check of password
        return true;
    }
    
    private boolean tryChangePassword() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        
        Long userId = (Long) session.getAttribute("user_id");
        if(userId == null) {
          //  JSFHelper.addMessage(FacesMessage.SEVERITY_ERROR, "Authentication Error:", "You should log in to change your password.");
            return false;
        }
        String oldPassword = EncryptionTools.SHA256(this.oldPassword);
        if(pm.checkUserPassword(userId, oldPassword) == null) {
           // JSFHelper.addMessage(FacesMessage.SEVERITY_ERROR, "Password Validation Error:", "Invalid old password.");
            return false;
        }
        oldPassword = null;
        String newPassword = EncryptionTools.SHA256(this.newPassword);
        resetInput();
        try {
            pm.changeUserPassword(userId, newPassword);
        } catch (Exception ex) {
           // JSFHelper.addMessage(FacesMessage.SEVERITY_ERROR, "Server Error:", "Unexpected server error occured.");
            return false;
        }
        return true;
    }
    
    public void changePasswordAction(ActionEvent evt) {
        if(!validateUserInput()) {
            resetInput();
            return;
        }
        if(!tryChangePassword()) {
            resetInput();
            return;
        }
        //JSFHelper.addMessage(FacesMessage.SEVERITY_INFO, "Info:", "Your password has been changed successfully.");
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
}
