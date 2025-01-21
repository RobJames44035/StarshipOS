/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6256789
 * @summary Legal cast rejected
 * @compile -Xlint:unchecked -Werror T6256789.java
 */

import java.lang.ref.*;

public class T6256789 {

    public ExtraRef prob(ReferenceQueue<Object> refQ) throws InterruptedException {
        return ((ExtraRef)refQ.remove());
    }

    public static class ExtraRef extends WeakReference<Object> {
        public ExtraRef(Object value) {
            super(value);
        }
    }
}
