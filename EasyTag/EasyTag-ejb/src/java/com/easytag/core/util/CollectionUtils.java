package com.easytag.core.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Tools for working with collections
 * @author danon
 */
public abstract class CollectionUtils {
    
    public static <T> T allowSingleItem(Collection<T> c) {
        if (c == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        if (c.size() != 1) {
            throw new IllegalArgumentException("Incorrect number of elements in collection: "+c.size());
        }
        return c.iterator().next();
    }
    
    public static <T> T getFirstElement(Collection<T> c) {
        if (c == null || c.isEmpty()) {
            return null;
        }
        return c.iterator().next();
    }
    
    public static <T> T getLastElement(Collection<T> c) {
        if (c == null || c.isEmpty()) {
            return null;
        }
        T lastElement = null;
        for (Iterator<T> collectionItr = c.iterator(); collectionItr.hasNext();) {
            lastElement = collectionItr.next();
        }
        return lastElement;
    }
}
