package com.easytag.core.util;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Helps to load EJBs.
 * @author danon
 */
public abstract class EJBTool {
    public static <T> T resolve(String jndiName, Class<T> clazz) {
        Context context;
        T t = null;
        try {
            context = new InitialContext();
            t = (T) context.lookup(jndiName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return t;
    }
    
    public static Object resolve(String jndiName) {
        Context context;
        Object o = null;
        try {
            context = new InitialContext();
            o = context.lookup(jndiName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return o;
    }
    
}