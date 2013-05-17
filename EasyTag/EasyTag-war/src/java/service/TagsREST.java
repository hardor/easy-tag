package service;

import com.easytag.core.ejb.DocumentManagerLocal;
import com.easytag.core.jpa.entity.Fragment;
import com.easytag.core.util.SessionHelper;
import com.google.gson.Gson;
import java.util.List;
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
    @Path("delete")
    public void removeFragment(
            @Context HttpServletRequest request, 
            @QueryParam("tag_id") Long fragmentId
    ) {
        Long userId = SessionHelper.getUserId(request.getSession());
        dm.deleteFragment(userId, fragmentId);
    }
    
    @GET
    @Produces("application/json")
    @Path("all")
    public String getAllFragments(
            @Context HttpServletRequest request, 
            @QueryParam("doc_id") Long id
    ) {
        Long userId = SessionHelper.getUserId(request.getSession());
        List<Fragment> fragments = dm.getAllFragments(userId, id);
        return "{\"fragments\":" + new Gson().toJson(fragments) + "}";
    }
    
    @GET
    @Produces("application/json")
    @Path("add")
    public String addTag(
            @Context HttpServletRequest request, 
            @QueryParam("tag") String tag,
            @QueryParam("doc_id") Long documentId,
            @QueryParam("x") Double x,
            @QueryParam("y") Double y,
            @QueryParam("width") Double width,
            @QueryParam("height") Double height
    ) {
        Long userId = SessionHelper.getUserId(request.getSession());
        Fragment t = dm.addFragment(userId, documentId, tag, x.longValue(), y.longValue(), width.longValue(), height.longValue());
        if (t != null) {
            return "{\"fragmentId\": " + t.getId().toString() + "}";
        } else return "{\"fragmentId\": -1}";
        
    }
}
