/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.check;

import gc.g1.unloading.check.cleanup.CleanupAction;

import java.util.LinkedList;
import java.util.List;

/**
 * Superclass for assertion.
 */
public abstract class Assertion {

    public abstract void check();

    private List<Object> storage = new LinkedList<>();

    public void keepLink(Object object) {
        storage.add(object);
    }

    public void cleanup() {
        try {
            for (Object o : storage) {
                if (o instanceof CleanupAction) {
                    ((CleanupAction) o).cleanup();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Something bad happened while cleaning after checked assertion", e);
        }
    }

}
