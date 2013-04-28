package com.easytag.web.users;

import com.easytag.core.ejb.DocumentManagerLocal;
import com.easytag.core.ejb.PasswordManagerLocal;
import com.easytag.core.ejb.UserManagerLocal;
import com.easytag.core.jpa.entity.User;
import com.easytag.web.utils.JSFHelper;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author 7
 */
@ManagedBean
@ViewScoped
public class UserInfoBean implements Serializable {

    private static final Logger log = Logger.getLogger(UserInfoBean.class);
    @EJB
    private UserManagerLocal userManager;
    @EJB
    private PasswordManagerLocal pm;
    @EJB
    DocumentManagerLocal fm;
    private Long userId;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String information;
    private String phone;
    private User user;
    private String password;
    private String avatar;

    /**
     * Creates a new instance of UserInfoBean
     */
    public UserInfoBean() {
        userId = JSFHelper.getUserId();
    }

    @PostConstruct
    private void init() {
        if (userId != null && user == null) {
            user = userManager.getUserById(userId);

        } else {
            log.warn("User Id is " + userId);
        }
    }

    public void refreshAction() {
        userId = JSFHelper.getUserId();
        if (userId != null && user == null) {
            user = userManager.getUserById(userId);

        } else {
            log.warn("User Id is " + userId);
        }
    }

    public void saveParametersAction(ActionEvent evt) {

        final JSFHelper helper = getJSFHelper();
        log.info("Save parametr Action");
        log.info(firstName);
        log.info(lastName);
        log.info(email);
        log.info(phone);
        log.info(information);
        log.info(userId); 
        user = userManager.modifyUserInfo(userId, firstName, lastName, information, email, phone);
        JSFHelper.addMessage(FacesMessage.SEVERITY_INFO, "Succes:", "Info eddite");
        helper.redirect("/user/profile/index");
    }

    public String getLogin() {
        login = pm.getByUserId(getUserId()).getLogin();
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        email = user.getEmail();
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInformation() {
        information = user.getInformation();
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Long getUserId() {
        userId = user.getUser_id();
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        password = userManager.getPasswordById(getUserId());
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        firstName = user.getFirstName();
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        lastName = user.getLastName();
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        phone = user.getPhone();
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        avatar = user.getAvatar();       
        if (avatar == null) {
            avatar = "/img/avatar/default_avatar.png";
        } else {
            avatar = "faces/file?id=" + fm.getFileByUrl(getUserId(), avatar).getId();
        }
        return avatar; 
    }

    public void setAvatar(String avatar) {       
        this.avatar = avatar;
        userManager.setAvatar(getUserId(), avatar);
    }

    protected JSFHelper getJSFHelper() {
        return new JSFHelper();
    }
}
