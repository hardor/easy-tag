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
        
        doNothing().when(spyBean).logoutAction(mockEvt);
        when(spyBean.getJSFHelper()).thenReturn(mockHelper);
        when(spyBean.getUm()).thenReturn(mockUserManager);
    }
    
    @Test
    public void testLoginAction_AssertLoggedIn() {
        User mockUser = mock(User.class);
        HttpSession mockSession = mock(HttpSession.class);
        
        when(mockUserManager.getUserByLogin("test")).thenReturn(mockUser);
        when(mockUser.getId()).thenReturn(1L);
        when(mockUserManager.getPasswordByLogin("test")).thenReturn(EncryptionTools.SHA256("123"));  
        when(mockHelper.getSession(true)).thenReturn(mockSession);
        
        when(spyBean.getLogin()).thenReturn("test");
        when(spyBean.getPassword()).thenReturn("123");
        
        spyBean.loginAction(mockEvt);
        verify(mockSession).setAttribute("user_id", mockUser.getId());
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
        
        spyBean.loginAction(mockEvt);
        verify(mockHelper).addMessage("login_messages", FacesMessage.SEVERITY_WARN, "WRONG", "Incorrect password and login.");
        assertFalse(spyBean.isLoggedIn());
    }
}