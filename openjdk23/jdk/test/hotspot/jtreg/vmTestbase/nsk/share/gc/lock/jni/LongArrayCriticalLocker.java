/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.lock.jni;

import nsk.share.gc.lock.CriticalSectionObjectLocker;
import nsk.share.TestFailure;

public class LongArrayCriticalLocker extends CriticalSectionObjectLocker<long[]> {
        private native long criticalNative(long enterTime, long sleepTime);

        static {
                System.loadLibrary("LongArrayCriticalLocker");
        }

        public LongArrayCriticalLocker(long[] obj) {
                super(obj);
        }

        protected void criticalSection(long enterTime, long sleepTime) {
                long javaHash = hashValue(obj);
                long nativeHash = criticalNative(enterTime, sleepTime);
                if (nativeHash != 0 && nativeHash != javaHash)
                        throw new TestFailure("Native hash: " + nativeHash + " != Java hash: " + javaHash);
        }

        private long hashValue(long[] obj) {
                long hash = 0;
                for (int i = 0; i < obj.length; ++i)
                        hash ^= obj[i];
                return hash;
        }
}
