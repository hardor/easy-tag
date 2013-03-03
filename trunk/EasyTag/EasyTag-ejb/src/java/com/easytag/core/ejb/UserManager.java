package com.easytag.core.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implements some routines for working with registered users.
 * @author danon
 */
@Stateless
public class UserManager implements UserManagerLocal { 
    @PersistenceContext
    private EntityManager em;
    
    

}
