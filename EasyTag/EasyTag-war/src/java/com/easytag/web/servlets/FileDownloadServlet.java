package com.easytag.web.servlets;

import com.easytag.core.CoreConstants;
import com.easytag.core.ejb.DocumentManagerLocal;
import com.easytag.core.jpa.entity.Document;
import com.easytag.web.utils.JSFHelper;
import com.easytag.web.utils.SessionUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.ejb.EJB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The File servlet for serving from database.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2007/07/fileservlet.html
 */
public class FileDownloadServlet extends HttpServlet {

    @EJB
    private DocumentManagerLocal fm;
    
    // Constants ----------------------------------------------------------------------------------
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    // Actions ------------------------------------------------------------------------------------
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get ID from request.
        String fileId = request.getParameter("id");

        // Check if ID is supplied to the request.
        if (fileId == null) {
            // Do your thing if the ID is not supplied to the request.
            // Throw an exception, or send 404, or show default/warning page, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Lookup File by FileId in database.
        // Do your "SELECT * FROM File WHERE FileID" thing.
        Document file_db = fm.getFileById(SessionUtils.getUserId(request.getSession(true)), Long.valueOf(fileId));

        // Check if file is actually retrieved from database.
        if (file_db == null) {
            // Do your thing if the file does not exist in database.
            // Throw an exception, or send 404, or show default/warning page, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Init servlet response.
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(file_db.getContentType());
        response.setHeader("Content-Length", String.valueOf(file_db.getSize()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file_db.getName() + "\"");

        // Prepare streams.
        File file = new File(CoreConstants.DEFAULT_PATH+"/"+file_db.getUrl());
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        
        try {
            // Open streams.
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }
    }

    // Helpers (can be refactored to public utility class) ----------------------------------------
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }
}
