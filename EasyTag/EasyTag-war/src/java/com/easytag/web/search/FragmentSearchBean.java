/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytag.web.search;


import com.easytag.core.ejb.SearchManagerLocal;
import com.easytag.core.jpa.entity.Fragment;
import com.easytag.web.utils.JSFHelper;
import java.io.Serializable;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author 7
 */
@ManagedBean
@ViewScoped
public class FragmentSearchBean implements Serializable{
    @EJB
    private SearchManagerLocal searchManager;
    
    private JSFHelper helper;
    private String searchQuery;
    /**
     * Creates a new instance of FragmentSearchBean
     */
    public FragmentSearchBean() {
        helper = new JSFHelper();
        
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
    
    public void startSearch(){
        System.out.println("Search started, search query: "+searchQuery);
        System.out.println(helper.getUserId());
        Set<Fragment> fragments = searchManager.findFragmentsByQuery(searchQuery, helper.getUserId());
        System.out.println(fragments);
    }
    
    public String goToSearch(){
       return  "search.xhtml?q="+searchQuery+"&faces-redirect=true";      
       
    }
    
    @PostConstruct
    private void init() {
        JSFHelper helper = getJSFHelper();
        String query = helper.getRequest().getParameter("q");   
        setSearchQuery(query);
        System.out.println("query: "+query);
    }
     
     protected JSFHelper getJSFHelper() {
        return new JSFHelper();
    }
    
}
