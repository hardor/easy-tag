package com.easytag.core.util;

import java.security.MessageDigest;
import org.apache.log4j.Logger;

/**
 * Contains useful methods for encryption and decryption of data.
 * @author danon
 */
public class EncryptionTools {

    private static final Logger log = Logger.getLogger(EncryptionTools.class);

    public static String encryptString(String s, String algo) {
        if (s == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance(algo);
            md.update(s.getBytes());
            s = null;
            return toHexString(md.digest());
        } catch (Exception ex) {
            log.error("Couldn't calculate MD5 of string", ex);
            throw new RuntimeException("Couldn't calculate MD5 of string", ex);
        }
    }

    public static String toHexString(byte[] byteData) {
        if (byteData == null) {
            return StringUtils.EMPTY_STRING;
        }
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    public static byte[] fromHexString(CharSequence s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String SHA256(String s) {
        return encryptString(s, "SHA-256");
    }

    public static String MD5(String s) {
        return encryptString(s, "MD5");
    }
}