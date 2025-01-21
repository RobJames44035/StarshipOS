/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4725678
 * @summary null pointer check too late for qualifying expr of anon class creation
 * @author gafter
 *
 * @run compile NullQualifiedNew2.java
 * @run main NullQualifiedNew2
 */

public class NullQualifiedNew2 {
    class Inner {
        Inner(int i) {}
    }
    public static void main(String[] args) {
        int i = 1;
        a: try {
            NullQualifiedNew2 c = null;
            c.new Inner(i++) {};
        } catch (NullPointerException e) {
            break a;
        }
        if (i != 1) throw new Error("i = " + i);
    }
}
