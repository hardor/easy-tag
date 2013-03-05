package com.easytag.core.ejb;

import com.easytag.core.jpa.entity.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Vildanov Ruslan
 */
@Local
public interface UserManagerLocal {
   
    User createUser( String email, String firstName, String lastName, String password, String login);

    boolean removeUserById(Long userId);

    User getUserById(Long userId);

    public Long tryLogin(String login, String password);
    
    public User getUserByLogin(String login);  
    
    public List<User> getAllUsers();

    public User getUserByFirstName (String firstName);

    public User getUserByLoginAndPassword(String login, String password); 
    
    public String getPasswordByLogin (String login);

}