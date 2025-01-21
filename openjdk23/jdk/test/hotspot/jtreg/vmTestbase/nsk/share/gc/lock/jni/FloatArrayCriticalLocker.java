/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.lock.jni;

import nsk.share.gc.lock.CriticalSectionObjectLocker;
import nsk.share.TestFailure;

public class FloatArrayCriticalLocker extends CriticalSectionObjectLocker<float[]> {
        private native float criticalNative(long enterTime, long sleepTime);

        static {
                System.loadLibrary("FloatArrayCriticalLocker");
        }

        public FloatArrayCriticalLocker(float[] obj) {
                super(obj);
        }

        protected void criticalSection(long enterTime, long sleepTime) {
                float javaHash = hashValue(obj);
                float nativeHash = criticalNative(enterTime, sleepTime);
                if (nativeHash != 0 && nativeHash != javaHash)
                        throw new TestFailure("Native hash: " + nativeHash + " != Java hash: " + javaHash);
        }

        private float hashValue(float[] obj) {
                float hash = 0;
                for (int i = 0; i < obj.length; ++i)
                        hash += obj[i];
                return hash;
        }
}
