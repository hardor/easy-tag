package service;

import com.easytag.core.ejb.DocumentManagerLocal;
import com.easytag.core.jpa.entity.Fragment;
import com.easytag.core.util.SessionHelper;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.xml.ws.WebServiceContext;

/**
 *
 * @author danon
 */
@Stateless
@Path("tags")
public class TagsREST {
    
    @Resource  
    private WebServiceContext wsContext;  
    
    @EJB
    private DocumentManagerLocal dm;
    
    @GET
    @Produces("application/json")
    @Path("add")
    public String addTag(
            @Context HttpServletRequest request, 
            @QueryParam("tag") String tag,
            @QueryParam("doc_id") Long documentId,
            @QueryParam("x") Long x,
            @QueryParam("y") Long y,
            @QueryParam("width") Long width,
            @QueryParam("height") Long height
    ) {
        Long userId = SessionHelper.getUserId(request.getSession());
        Fragment t = dm.addFragment(userId, documentId, tag, x, y, width, height);
        if (t != null) {
            return "{fragmentId: " + t.getId().toString() + "}";
        } else return "{fragmentId: -1}";
        
    }
}
