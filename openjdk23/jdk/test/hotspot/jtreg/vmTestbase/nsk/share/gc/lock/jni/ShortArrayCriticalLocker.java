/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.lock.jni;

import nsk.share.gc.lock.CriticalSectionObjectLocker;
import nsk.share.TestFailure;

public class ShortArrayCriticalLocker extends CriticalSectionObjectLocker<short[]> {
        private native short criticalNative(long enterTime, long sleepTime);

        static {
                System.loadLibrary("ShortArrayCriticalLocker");
        }

        public ShortArrayCriticalLocker(short[] obj) {
                super(obj);
        }

        protected void criticalSection(long enterTime, long sleepTime) {
                short javaHash = hashValue(obj);
                short nativeHash = criticalNative(enterTime, sleepTime);
                if (nativeHash != 0 && nativeHash != javaHash)
                        throw new TestFailure("Native hash: " + nativeHash + " != Java hash: " + javaHash);
        }

        private short hashValue(short[] obj) {
                short hash = 0;
                for (int i = 0; i < obj.length; ++i)
                        hash ^= obj[i];
                return hash;
        }
}
