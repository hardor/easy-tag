/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytag.core.ejb;

import com.easytag.core.jpa.entity.Album;
import com.easytag.core.jpa.entity.Document;
import com.easytag.core.jpa.entity.Fragment;
import com.easytag.core.jpa.entity.Tag;
import com.easytag.core.jpa.entity.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 7
 */
@Stateless
public class SearchManager implements SearchManagerLocal {  
    @PersistenceContext
    private EntityManager em;
    @EJB
    private UserManagerLocal um;
    @EJB
    private AlbumManagerLocal am;
    @EJB
    private DocumentManagerLocal dm;
    
    
    @Override
    public Set<Fragment> findFragmentsByQuery(String query, Long userId) {
       
       Set<Fragment> result = new HashSet();
       User user = um.getUserById(userId);
       if(user == null) return null;
       List<Fragment> allUserFragments = getAllFragmentsByUser(user);
       System.out.println("Fragment count: "+allUserFragments.size());
        
        
       if(query!= null){
           String[] parts = query.split(" ");
           for(String part : parts){
               System.out.println(" "+part);
               for(Fragment frag:allUserFragments){
                  Set<Tag> tags =  frag.getTags();
                  if(isTagsContainQueryPart(tags, part)){
                        result.add(frag); //Can be modifyed an give us information about what tags, etc.
                  }
               }
           }
       }
       return result;
    }
    
    private boolean isTagsContainQueryPart(Set<Tag> tags, String part){
        for(Tag tag:tags){
            //System.out.println("part: "+part+" tag: "+tag.getName()+" contain: "+tag.getName().indexOf(part, 0));
            if(tag.getName().indexOf(part, 0) != -1) return true; 
        }
        return false;
    }
    
    private List<Fragment> getAllFragmentsByUser(User user){
     List<Fragment> possibleFragments = new ArrayList();
      if(user == null) return possibleFragments;
      
     List<Album> userAlbums = am.getAlbumsByUser(user);
     for(Album al:userAlbums){
       List<Document> documentsByAlbum =  dm.getAllAlbumUsersDocuments(user.getUser_id(), al.getId());
       for(Document doc:documentsByAlbum){
          possibleFragments.addAll(dm.getAllFragments(user.getUser_id(), doc.getId()));
       }
     }
     return possibleFragments;    
    }


}
