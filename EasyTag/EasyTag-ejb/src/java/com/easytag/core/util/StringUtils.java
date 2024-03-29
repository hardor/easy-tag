package com.easytag.core.util;

import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.w3c.dom.Document;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;

/**
 *
 * @author rogvold, danon last conflicts resolved 18.11.2012 by rogvold
 */
public class StringUtils {

    public static final String EMPTY_STRING = "";
    public static final String RANDOM_STRING = "G12HIJdefgPQRSTUVWXYZabc56hijklmnopqAB78CDEF0KLMNO3rstu4vwxyz9";

    public static String getStringFromList(List<String> list, String delimiter) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        String s1 = "";
        for (int i = 0; i < list.size() - 1; i++) {
            s1 += list.get(i) + delimiter;
        }
        s1 += list.get(list.size() - 1);
        return s1;
    }
    
    public static int getValidInt(String s) {
        if (!isValidInteger(s)) {
            return 0;
        } else {
            return Integer.parseInt(s);
        }
    } 

    public static List<String> getListOfStrings(String delimeter, String s) {
        try {
            StringTokenizer st = new StringTokenizer(s, delimeter);
            List<String> list = new ArrayList();
            while (st.hasMoreElements()) {
                list.add(st.nextToken().toString());
            }

            return list;
        } catch (Exception e) {
            return null;
        }


    }

    public static boolean isEmpty(String s) {
        return getValidString(s).isEmpty();
    }

    public static boolean isNotEmpty(String... values) {
        for (String s : values) {
            if (isEmpty(s)) {
                return false;
            }
        }
        return true;
    }

    public static String getValidString(String s) {
        return s == null ? EMPTY_STRING : s;
    }

    /**
     * Converts jdom Element to String
     *
     * @param elem an element to be converted.
     * @return a String representation of the element
     */
    public static String elementToString(Element elem) {
        if (elem == null) {
            return EMPTY_STRING;
        }
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(elem);
            transformer.transform(source, result);
            return result.getWriter().toString();
        } catch (Exception ex) {
            return EMPTY_STRING;
        }
    }

    /**
     * Creates empty XML Document.
     *
     * @return new instance of Document object.
     */
    public static Document createDocument() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            return null;
        }
    }

    public static boolean isTrue(String s, String... accept) {
        final String v = getValidString(s);
        for (String t : accept) {
            if (v.equalsIgnoreCase(t)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFalse(String s) {
        return !isTrue(s);
    }

    public static String decode(String value, String charsetName) {
        Charset charset = Charset.forName(charsetName);
        if (value == null) {
            return null;
        }
        return new String(value.getBytes(charset));
    }

    public static String convertEncodings(String value, String srcCharset, String dstCharset) {
        return decode(decode(value, srcCharset), dstCharset);
    }

    public static String concat(String[] values, String delim) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length - 1; i++) {
            sb.append(values[i]);
            sb.append(delim);
        }
        if (values.length > 0) {
            sb.append(values[values.length - 1]);
        }
        return sb.toString();
    }

    public static String generateRandomString(int length) {
        final StringBuilder ar = new StringBuilder();
        final int l = RANDOM_STRING.length();
        Random r = new Random(System.currentTimeMillis());
        for (int i = 1; i <= length; i++) {
            ar.append(RANDOM_STRING.charAt(r.nextInt(l)));
        }
        return ar.toString();
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Pattern p = Pattern.compile("[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isValidInteger(String s) {
        if (s == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

      
}
