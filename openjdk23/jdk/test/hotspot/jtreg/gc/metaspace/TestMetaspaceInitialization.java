/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package gc.metaspace;

import java.util.ArrayList;

/* @test TestMetaspaceInitialization
 * @bug 8024945
 * @summary Tests to initialize metaspace with a very low MetaspaceSize
 * @modules java.base/jdk.internal.misc
 * @run main/othervm -XX:MetaspaceSize=0 gc.metaspace.TestMetaspaceInitialization
 */
public class TestMetaspaceInitialization {
    private class Internal {
        @SuppressWarnings("unused")
        public int x;
        public Internal(int x) {
            this.x = x;
        }
    }

    private void test() {
        ArrayList<Internal> l = new ArrayList<>();
        l.add(new Internal(17));
    }

    public static void main(String[] args) {
        new TestMetaspaceInitialization().test();
    }
}
