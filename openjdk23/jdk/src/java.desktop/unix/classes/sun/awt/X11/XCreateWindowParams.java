/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.awt.X11;

import java.util.HashMap;

@SuppressWarnings("serial") // JDK-implementation class
public class XCreateWindowParams extends HashMap<Object, Object> {
    public XCreateWindowParams() {
    }
    public XCreateWindowParams(Object[] map) {
        init(map);
    }
    private void init(Object[] map) {
        if (map.length % 2 != 0) {
            throw new IllegalArgumentException("Map size should be divisible by two");
        }
        for (int i = 0; i < map.length; i += 2) {
            put(map[i], map[i+1]);
        }
    }

    public XCreateWindowParams putIfNull(Object key, Object value) {
        if (!containsKey(key)) {
            put(key, value);
        }
        return this;
    }
    public XCreateWindowParams putIfNull(Object key, int value) {
        if (!containsKey(key)) {
            put(key, Integer.valueOf(value));
        }
        return this;
    }
    public XCreateWindowParams putIfNull(Object key, long value) {
        if (!containsKey(key)) {
            put(key, Long.valueOf(value));
        }
        return this;
    }

    public XCreateWindowParams add(Object key, Object value) {
        put(key, value);
        return this;
    }
    public XCreateWindowParams add(Object key, int value) {
        put(key, Integer.valueOf(value));
        return this;
    }
    public XCreateWindowParams add(Object key, long value) {
        put(key, Long.valueOf(value));
        return this;
    }
    public XCreateWindowParams delete(Object key) {
        remove(key);
        return this;
    }
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (Entry<Object, Object> entry : entrySet()) {
            buf.append(entry.getKey())
               .append(": ")
               .append(entry.getValue())
               .append("\n");
        }
        return buf.toString();
    }

}
