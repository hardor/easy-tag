package com.easytag.core.ejb;

import com.easytag.core.jpa.entity.User;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author Vildanov Ruslan
 */
@Local
public interface UserManagerLocal {
   
    User createUser( String email, String firstName, String lastName, String password, String login,String information);

    boolean removeUserById(Long userId);

    User getUserById(Long userId);

    public Long tryLogin(String login, String password);
    
    public User getUserByLogin(String login);  
    
    public List<User> getAllUsers();

    public User getUserByFirstName (String firstName);

    public User getUserByLoginAndPassword(String login, String password); 
    
    public String getPasswordByLogin (String login);
    
    public String getPasswordById (Long Id);

    public User modifyUserInfo(Long userId, String name, String surname, String information,String email,String phone);

}