package com.easytag.web.vk;

import com.easytag.core.ejb.UserManagerLocal;
import com.easytag.web.users.LoginBean;
import com.easytag.web.users.UserInfoBean;
import com.easytag.web.utils.JSFHelper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rogvold
 */
public class VkontakteOpenIdServlet extends HttpServlet {

    @EJB
    private UserManagerLocal um;
    
    @ManagedProperty(value = "#{loginBean}")
    private LoginBean loginB;

    public void setLoginB(LoginBean loginB) {
        this.loginB = loginB;
    }
    
     
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {   
             HttpSession session = request.getSession();  
             String first_name = request.getParameter("first_name");
             String last_name = request.getParameter("last_name");
             
             //Some bags in code 
             String photo = request.getParameter("photo");
             
             //String photo ="/img/avatar/default_avatar.png";
             
             String uid = request.getParameter("uid");
             String hash = request.getParameter("hash");
             
             //TODO: Check if the data from vk
            // if(hash.equals(VkontakteUtils.getHash(Constants.APP_ID+Constants.APP_SECRET)));
             final JSFHelper helper = new JSFHelper();
           
            if (um.getUserByLogin(uid)==null){
                um.createUser("", first_name, last_name, hash, uid,"", photo);
                
                session.setAttribute("user_id", um.getUserByLogin(uid).getUser_id());
             //   loginB.setLoggedIn(true);
                response.sendRedirect("faces/user/index.xhtml");                
            }else{
                session.setAttribute("user_id", um.getUserByLogin(uid).getUser_id());
               // loginB.setLoggedIn(true);
                response.sendRedirect("faces/user/index.xhtml");                
            }       
         

        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
