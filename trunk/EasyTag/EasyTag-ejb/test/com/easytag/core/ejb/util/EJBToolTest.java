package com.easytag.core.ejb.util;

import com.easytag.core.util.EJBTool;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

/**
 * Test suite for class EJBTool
 * @author danon
 */
public class EJBToolTest {
    @Test
    public void testResolve_shouldReturnNull_ifJndiNameDoesNotExist() {
        Object o = EJBTool.resolve("test");
        assertNull(o);
    }
    
    @Test
    public void testResolveAndCast_shouldCallBasicResolveMethod() {
        TestableEJBTool spyTool = spy(new TestableEJBTool());
        Object o = spyTool.callStaticResolve("test", Object.class);
        assertNull(o);
        verify(spyTool).resolve(eq("test"));
    }
    
    private class TestableEJBTool extends EJBTool {
        protected <T> T callStaticResolve(String jndiName, Class<T> clazz) {
            return resolve(jndiName, clazz);
        }
    }
}