package com.easytag.web.utils;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Provides some handy routines for working with JSF context and session
 *
 * @author danon
 */
public abstract class JSFHelper {

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static ExternalContext getExternalContext() {
        final FacesContext fc = getFacesContext();
        if (fc == null) {
            return null;
        }
        return fc.getExternalContext();
    }

    public static HttpServletRequest getRequest() {
        final ExternalContext ec = getExternalContext();
        if (ec == null) {
            return null;
        }
        return (HttpServletRequest) ec.getRequest();
    }

    public static Application getApplication() {
        final FacesContext fc = getFacesContext();
        if (fc == null) {
            return null;
        }
        return fc.getApplication();
    }

    public static HttpSession getSession(boolean create) {
        final HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return request.getSession(create);
    }

    public static FacesMessage addMessage(FacesContext fc, FacesMessage.Severity severity, String summary, String details) {
        final FacesMessage msg = new FacesMessage(severity, summary, details);
        if (fc == null) {
            return msg;
        }
        fc.addMessage(null, msg);
        return msg;
    }

    public static FacesMessage addMessage(FacesMessage.Severity severity, String summary, String details) {
        return addMessage(getFacesContext(), severity, summary, details);
    }
}
