/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyBundle extends ResourceBundle {
    Map<String, String> mapping = new ConcurrentHashMap<>();

    /**
     * Gets an object for the given key from this resource bundle.
     * Returns null if this resource bundle does not contain an
     * object for the given key.
     *
     * @param key the key for the desired object
     * @throws    NullPointerException if {@code key} is {@code null}
     * @return the object for the given key, or null
     */
    protected Object handleGetObject(String key) {
        return mapping.computeIfAbsent(key,
                (k) -> k + "-" + System.identityHashCode(this.getClass().getClassLoader()));
    }

    /**
     * Returns an enumeration of the keys.
     *
     * @return an {@code Enumeration} of the keys contained in
     *         this {@code ResourceBundle} and its parent bundles.
     */
    public Enumeration<String> getKeys() {
        return Collections.enumeration(mapping.keySet());
    }
}
