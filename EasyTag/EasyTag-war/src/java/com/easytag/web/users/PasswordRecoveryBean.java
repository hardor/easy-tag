package com.easytag.web.users;

import com.easytag.core.ejb.PasswordManagerLocal;
import com.easytag.core.ejb.UserManagerLocal;
import com.easytag.core.jpa.entity.User;
import com.easytag.core.util.StringUtils;
import com.easytag.web.utils.JSFHelper;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.apache.log4j.Logger;

/**
 * View scoped managed bean for password recovery
 *
 * @author danon
 */
@ManagedBean
@ViewScoped
public class PasswordRecoveryBean implements Serializable {
    
    private final Logger log = Logger.getLogger(RegistrationBean.class);
    
    @EJB
    private UserManagerLocal um;
    
    @EJB
    private PasswordManagerLocal pm;
    
    private String login;
    private String recoveryStatus;

    /**
     * Creates a new instance of LoginBean
     */
    public PasswordRecoveryBean() {
    }
    
    private boolean validateUserInput() {
        if(StringUtils.isEmpty(login))
            return false;
        // TODO: validate login (regexp)
        return true;
    }
    
    private boolean tryRecover(String login) {
       User u = um.getUserByLogin(login);
       if(u == null)
           return false;
       return pm.recoverPassword(u.getUser_id());
    }
    
    public void recoverAction(ActionEvent evt) {       
        JSFHelper helper = getJSFHelper();
        if(!validateUserInput()) {
            helper.addMessage(FacesMessage.SEVERITY_ERROR, "Validation:", "Check your input and try again.");
            recoveryStatus = "fail";
            return;
        }
        
        if(!tryRecover(login)) {
            helper.addMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Password cannot be recovered.");
            recoveryStatus = "fail";
            return;
        }
        
        helper.addMessage(FacesMessage.SEVERITY_INFO, "Information:", "New password has been sent to your e-mail.");    
        recoveryStatus = "ok";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if(login == null)
            this.login = null;
        else
            this.login = login.trim();
    }

    public String getRecoveryStatus() {
        return recoveryStatus;
    }

    public void setRecoveryStatus(String recoveryStatus) {
        this.recoveryStatus = recoveryStatus;
    }
    
    protected JSFHelper getJSFHelper() {
        return new JSFHelper();
    }
}
