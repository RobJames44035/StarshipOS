/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8332463
 * @summary Byte conditional pattern case element dominates short constant case element
 * @compile --enable-preview --source ${jdk.version} T8332463a.java
 */
public class T8332463a {
    public int test2() {
        Byte i = (byte) 42;
        return switch (i) {
            case Byte ib  -> 1;
            case short s  -> 2;
        };
    }

    public int test4() {
        int i = 42;
        return switch (i) {
            case Integer ib -> 1;
            case byte ip    -> 2;
        };
    }

    public int test3() {
        int i = 42;
        return switch (i) {
            case Integer ib -> 1;
            case (byte) 0   -> 2;
        };
    }
}
