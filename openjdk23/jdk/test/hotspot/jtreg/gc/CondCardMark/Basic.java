/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package gc.CondCardMark;

/**
 * @test
 * @bug 8076987
 * @bug 8078438
 * @summary Verify UseCondCardMark works
 * @modules java.base/jdk.internal.misc
 * @run main/othervm -Xint gc.CondCardMark.Basic
 * @run main/othervm -Xint -XX:+UseCondCardMark gc.CondCardMark.Basic
 * @run main/othervm -XX:TieredStopAtLevel=1 gc.CondCardMark.Basic
 * @run main/othervm -XX:TieredStopAtLevel=1 -XX:+UseCondCardMark gc.CondCardMark.Basic
 * @run main/othervm -XX:TieredStopAtLevel=4 gc.CondCardMark.Basic
 * @run main/othervm -XX:TieredStopAtLevel=4 -XX:+UseCondCardMark gc.CondCardMark.Basic
 * @run main/othervm -XX:-TieredCompilation gc.CondCardMark.Basic
 * @run main/othervm -XX:-TieredCompilation -XX:+UseCondCardMark gc.CondCardMark.Basic
*/
public class Basic {

    static volatile MyObject sink;

    public static void main(String args[]) {
        final int COUNT = 10000000;
        for (int c = 0; c < COUNT; c++) {
             MyObject o = new MyObject();
             o.x = c;
             doStore(o);
        }

        if (sink.x != COUNT-1) {
             throw new IllegalStateException("Failed");
        }
    }

    public static void doStore(MyObject o) {
        sink = o;
    }

    static class MyObject {
        int x;
    }

}
