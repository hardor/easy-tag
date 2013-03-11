package com.easytag.core.ejb;

import javax.ejb.Local;

/**
 *
 * @author 7
 */
@Local
public interface PasswordManagerLocal {

    Long checkUserPassword(Long userId, String password);

    Long checkUserPassword(String login, String password);

    public boolean recoverPassword(Long userId);

    public void changeUserPassword(Long userId, String newPassword);
}
