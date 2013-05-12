package com.easytag.web.utils;

import com.easytag.web.users.LoginBean;
import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ComponentSystemEvent;

/**
 * Contains all event listeners.
 * @author danon
 */
@ManagedBean
@ApplicationScoped
public class EventListenersBean implements Serializable {
    
    /**
     * Creates a new instance of EventListenersBean
     */
    public EventListenersBean() {
    }
    
    public void loginRequired(ComponentSystemEvent event) {
        JSFHelper helper = getJSFHelper();
        if (helper.getUserId() == null) {
            // not logged in
            helper.redirect("/login.xhtml");
        }
    }
    
    protected JSFHelper getJSFHelper() {
        return new JSFHelper();
    }
}
