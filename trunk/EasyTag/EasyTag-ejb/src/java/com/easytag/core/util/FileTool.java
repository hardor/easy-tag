package com.easytag.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * The class contains several useful methods 
 * for working with files and streams.
 * 
 * @author danon
 */
public abstract class FileTool {
    
    public static final int DEFAULT_STREAM_BUFFER_SIZE = 1024*16;
    
    /**
     * Copy all data from one stream to another with default buffer size.
     * @param is input stream
     * @param os output stream
     * @throws IOException 
     */
    public static void copyStream(InputStream is, OutputStream os) throws IOException {
        copyStream(is, os, DEFAULT_STREAM_BUFFER_SIZE);
    }
    
    /**
     * Copy all data from one stream to another with specified buffer size.
     * @param is input stream
     * @param os output stream
     * @param bufferSize stream buffer size
     * @throws IOException 
     */
    public static void copyStream(InputStream is, OutputStream os, int bufferSize) throws IOException {
        if(is != null && os != null) {
            final byte[] buffer = new byte[bufferSize];
            int read;
            while((read = is.read(buffer)) >= 0) {
                os.write(buffer, 0, read);
            }
        }
    }
    
}