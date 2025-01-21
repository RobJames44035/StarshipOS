/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.lock.jni;

import nsk.share.gc.lock.CriticalSectionObjectLocker;
import nsk.share.TestFailure;

public class BooleanArrayCriticalLocker extends CriticalSectionObjectLocker<boolean[]> {
        private native boolean criticalNative(long enterTime, long sleepTime);

        static {
                System.loadLibrary("BooleanArrayCriticalLocker");
        }

        public BooleanArrayCriticalLocker(boolean[] obj) {
                super(obj);
        }

        protected void criticalSection(long enterTime, long sleepTime) {
                boolean javaHash = hashValue(obj);
                boolean nativeHash = criticalNative(enterTime, sleepTime);
                if (nativeHash && nativeHash != javaHash)
                        throw new TestFailure("Native hash: " + nativeHash + " != Java hash: " + javaHash);
        }

        private boolean hashValue(boolean[] obj) {
                boolean hash = true;
                for (int i = 0; i < obj.length; ++i)
                        hash ^= obj[i];
                return hash;
        }
}
