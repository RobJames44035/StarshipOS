/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package mappkg;

import java.util.Iterator;

/**
 * Map interface.
 *
 * {@index "multiline
 *          search
 *          tag"}
 */
public interface Map {
    public void put(Object key, Object value);
    public boolean contains(Object key);
    public Object get(Object key);
    public void remove(Object key);
    public Iterator<Object> iterate();
}
