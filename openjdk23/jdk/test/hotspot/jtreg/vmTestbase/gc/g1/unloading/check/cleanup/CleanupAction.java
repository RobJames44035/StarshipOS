/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.check.cleanup;

/**
 * Some classes requires cleanup after check. This is cleanup action.
 */
public interface CleanupAction {

    public void cleanup() throws Exception;

}
