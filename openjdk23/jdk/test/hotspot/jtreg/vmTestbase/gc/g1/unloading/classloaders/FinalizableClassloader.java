/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.classloaders;

import java.security.SecureClassLoader;

import gc.g1.unloading.check.FinalizedAssertion;

/**
 * Classloader that keeps reference to FinalizedAssertion and marks it as passed when finalized.
 *
 */
public class FinalizableClassloader extends SecureClassLoader {

    private FinalizedAssertion finalizedAssertion;

    public void setFinalizedAssertion(FinalizedAssertion finalizedAssertion) {
        this.finalizedAssertion = finalizedAssertion;
    }

    @Override
    protected void finalize() throws Throwable {
        if (finalizedAssertion != null) {
            finalizedAssertion.setFinalized();
        }
    }

}
