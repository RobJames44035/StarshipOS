/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.keepref;

import gc.g1.unloading.check.cleanup.CleanupAction;
import gc.g1.unloading.loading.LibLoader;

/**
 * This holder keeps reference through JNI local reference.
 */
public class JNILocalRefHolder implements RefHolder {

    static {
        //Force loading library
        new LibLoader().hashCode();
    }

    // We use this field to transfer object into native JNI call. Idea is to avoid transferring link through method
    // arguments.
    private Object objectToKeep;

    private native void holdWithJNILocalReference(Object syncObject);

    @Override
    public Object hold(Object object) {
        objectToKeep = object;
        final Object syncObject = new Object();
        Thread keepingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (syncObject) {
                    holdWithJNILocalReference(syncObject);
                }
            }
        });
        keepingThread.setDaemon(true);
        keepingThread.start();
        return new CleanupAction() {
            @Override
            public void cleanup() throws Exception {
                synchronized (syncObject) {
                    syncObject.notify();
                }
            }
        };
    }


}
