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

    User createUser(String email, String firstName, String lastName, String password, String login, String information, String avatar);

    boolean removeUserById(Long userId);

    User getUserById(Long userId);

    Long tryLogin(String login, String password);

    User getUserByLogin(String login);

    List<User> getAllUsers();

    User getUserByFirstName(String firstName);

    User getUserByLoginAndPassword(String login, String password);

    String getPasswordByLogin(String login);

    String getPasswordById(Long Id);
    
    void setAvatar(Long userId,String Avatar);

    User modifyUserInfo(Long userId, String name, String surname, String information, String email, String phone);
}