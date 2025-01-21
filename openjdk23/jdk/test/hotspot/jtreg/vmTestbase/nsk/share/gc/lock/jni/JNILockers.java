/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.lock.jni;

import nsk.share.TestBug;
import nsk.share.gc.lock.Lockers;
import nsk.share.gc.lock.Locker;

public class JNILockers implements Lockers {
        public Locker createLocker(Object obj) {
                if (obj instanceof String)
                        return new StringCriticalLocker((String) obj);
                if (obj instanceof boolean[])
                        return new BooleanArrayCriticalLocker((boolean[]) obj);
                if (obj instanceof byte[])
                        return new ByteArrayCriticalLocker((byte[]) obj);
                if (obj instanceof char[])
                        return new CharArrayCriticalLocker((char[]) obj);
                if (obj instanceof double[])
                        return new DoubleArrayCriticalLocker((double[]) obj);
                if (obj instanceof float[])
                        return new FloatArrayCriticalLocker((float[]) obj);
                if (obj instanceof int[])
                        return new IntArrayCriticalLocker((int[]) obj);
                if (obj instanceof long[])
                        return new LongArrayCriticalLocker((long[]) obj);
                if (obj instanceof short[])
                        return new ShortArrayCriticalLocker((short[]) obj);
                throw new TestBug("Cannot create locker for: " + obj);
        }
}
