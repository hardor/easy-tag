/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytag.core.ejb;

import com.easytag.core.jpa.entity.Fragment;
import java.util.Set;
import javax.ejb.Local;

/**
 *
 * @author 7
 */
@Local
public interface SearchManagerLocal {
    Set<Fragment> findFragmentsByQuery(String query, Long userId);
}
