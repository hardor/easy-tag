package com.easytag.web.users;

import com.easytag.core.ejb.UserManagerLocal;
import com.easytag.core.jpa.entity.User;
import com.easytag.core.util.EncryptionTools;
import com.easytag.web.utils.JSFHelper;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 *
 * @author danon
 */
public class LoginBeanTest {
    
    @Mock
    private UserManagerLocal mockUserManager;
    @Mock
    private ActionEvent mockEvt;
    @Mock
    private JSFHelper mockHelper;
    
    @Spy
    private LoginBean spyBean;
    
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        
        when(spyBean.getJSFHelper()).thenReturn(mockHelper);
        when(spyBean.getUm()).thenReturn(mockUserManager);
    }
    
    @Test
    public void testLoginAction_AssertLoggedIn() {
        User mockUser = mock(User.class);
        HttpSession mockSession = mock(HttpSession.class);
        
        when(mockUserManager.getUserByLogin("test")).thenReturn(mockUser);
        when(mockUser.getUser_id()).thenReturn(1L);
        when(mockUserManager.getPasswordByLogin("test")).thenReturn(EncryptionTools.SHA256("123"));  
        when(mockHelper.getSession(true)).thenReturn(mockSession);
        
        when(spyBean.getLogin()).thenReturn("test");
        when(spyBean.getPassword()).thenReturn("123");
        doNothing().when(spyBean).logoutAction(mockEvt);
        
        spyBean.loginAction(mockEvt);
        verify(mockSession).setAttribute("user_id", mockUser.getUser_id());
        verify(mockHelper).redirect("user/index.xhtml");
        assertTrue(spyBean.isLoggedIn());
    }
    
    @Test
    public void testLoginAction_NotExistingUser() {
        HttpSession mockSession = mock(HttpSession.class);
        
        when(mockUserManager.getUserByLogin("test")).thenReturn(null);
        when(mockUserManager.getPasswordByLogin("test")).thenReturn(null);  
        when(mockHelper.getSession(true)).thenReturn(mockSession);
        
        when(spyBean.getLogin()).thenReturn("test");
        when(spyBean.getPassword()).thenReturn("123");
        doNothing().when(spyBean).logoutAction(mockEvt);
        
        spyBean.loginAction(mockEvt);
        verify(mockHelper).addMessage("login_messages", FacesMessage.SEVERITY_WARN, "WRONG", "Incorrect password and login.");
        assertFalse(spyBean.isLoggedIn());
    }
    
    @Test
    public void testLogoutAction() {
        HttpSession mockSession = mock(HttpSession.class);
        
        when(mockHelper.getSession(true)).thenReturn(mockSession);
        
        spyBean.setLoggedIn(true);
        spyBean.logoutAction(mockEvt);
        verify(mockSession).setAttribute("user_id", null);
        verify(mockHelper).redirect("/login");
        assertFalse(spyBean.isLoggedIn());
    }
    
}