/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.lock.jniref;

import nsk.share.TestBug;
import nsk.share.gc.lock.Lockers;
import nsk.share.gc.lock.Locker;

public class JNIGlobalRefLockers implements Lockers {
        public Locker createLocker(Object obj) {
                return new JNIGlobalRefLocker(obj);
        }
}
