package com.easytag.web.vk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author rogvold
 */
public class VkontakteUtils {

    public static final String getFirstVkontakteOAuthUrl() {
        String s = "https://oauth.vk.com/authorize?client_id=" + Constants.APP_ID + "&scope=notify&redirect_uri=/EasyTag-War/vk2&display=popup&response_type=code";
        return s;
    }

    public static final String getSecondVkontakteOauthUrl(String code) {
        String s = "https://oauth.vk.com/access_token?client_id=" + Constants.APP_ID + "&client_secret=" + Constants.APP_SECRET + "&code=" + code + "&redirect_uri=/EasyTag-War/vk2";
        return s;
    }

    public static String getHash(String str) {

        MessageDigest md5;
        StringBuffer hexString = new StringBuffer();
        try {
            md5 = MessageDigest.getInstance("md5");
            md5.reset();
            md5.update(str.getBytes());
            byte messageDigest[] = md5.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }

        } catch (NoSuchAlgorithmException e) {
            return e.toString();
        }
        return hexString.toString();
    }

    public static String getClientResponseQuery(String vkId, String userSecret) {
        return Constants.CLIENT_RESPONSE_URI_VK_ID+"="+vkId+"&"+Constants.CLIENT_HASHED_STRING+"="+getHash(vkId+userSecret);
    }

}
