/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.lock;

import nsk.share.TestBug;
import nsk.share.gc.lock.jni.JNILockers;
import nsk.share.gc.lock.jniref.*;

/**
 * Utility methods for lockers.
 */
public class LockerUtils {
        private LockerUtils() {
        }

        /**
         * Obtain Lockers by id.
         *
         * @param id identifier of Lockers
         */
        public static Lockers getLockers(String id) {
                if (id == null || id.equals("jni"))
                        return new JNILockers();
                else if (id.equals("jniGlobalRef"))
                        return new JNIGlobalRefLockers();
                else if (id.equals("jniLocalRef"))
                        return new JNILocalRefLockers();
                else if (id.equals("jniRef"))
                        return new JNIRefLockers();
                else if (id.equals("jniWeakGlobalRef"))
                        return new JNIWeakGlobalRefLockers();
                else
                        throw new TestBug("Invalid lockers id: " + id);
        }
}
