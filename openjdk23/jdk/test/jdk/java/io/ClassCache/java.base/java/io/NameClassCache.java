/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package java.io;

public class NameClassCache extends ClassCache<String> {
    protected String computeValue(Class<?> cl) {
        // Return string that is not interned and specific to class
        return "ClassCache-" + cl.getName();
    }

    public String get(Class<?> cl) {
        return super.get(cl);
    }
}
