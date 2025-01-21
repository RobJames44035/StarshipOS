/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8332463
 * @summary Byte conditional pattern case element dominates short constant case element
 * @enablePreview
 * @compile T8332463b.java
 *  @compile --enable-preview --source ${jdk.version} T8332463b.java
 */
public class T8332463b {
    public int test1() {
        Byte i = (byte) 42;
        return switch (i) {
            case Byte ib   -> 1;
            case (short) 0 -> 2;
        };
    }
}
