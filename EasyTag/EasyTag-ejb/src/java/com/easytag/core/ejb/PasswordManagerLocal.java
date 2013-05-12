package com.easytag.core.ejb;


import com.easytag.core.jpa.entity.UserPassword;
import javax.ejb.Local;

/**
 *
 * @author 7
 */
@Local
public interface PasswordManagerLocal {

    Long checkUserPassword(String login, String password);
    
    Long checkUserPassword(Long userId, String password);

    boolean recoverPassword(Long userId);

    UserPassword getByPassword(String password);

    UserPassword getByUserId(Long user_id);
    
    UserPassword changeUserPassword(Long userId, String newPassword);
    
    void setUser(Long old_user_id, Long new_user_id);
  
}
