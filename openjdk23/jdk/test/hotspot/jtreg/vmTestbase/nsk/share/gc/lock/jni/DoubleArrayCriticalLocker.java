/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.lock.jni;

import nsk.share.gc.lock.CriticalSectionObjectLocker;
import nsk.share.TestFailure;

public class DoubleArrayCriticalLocker extends CriticalSectionObjectLocker<double[]> {
        private native double criticalNative(long enterTime, long sleepTime);

        static {
                System.loadLibrary("DoubleArrayCriticalLocker");
        }

        public DoubleArrayCriticalLocker(double[] obj) {
                super(obj);
        }

        protected void criticalSection(long enterTime, long sleepTime) {
                double javaHash = hashValue(obj);
                double nativeHash = criticalNative(enterTime, sleepTime);
                if (nativeHash != 0 && nativeHash != javaHash)
                        throw new TestFailure("Native hash: " + nativeHash + " != Java hash: " + javaHash);
        }

        private double hashValue(double[] obj) {
                double hash = 0;
                for (int i = 0; i < obj.length; ++i)
                        hash += obj[i];
                return hash;
        }
}
