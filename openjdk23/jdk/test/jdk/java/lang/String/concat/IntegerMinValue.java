/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test
 * @bug 8267773
 * @summary Test
 *
 * @compile IntegerMinValue.java
 * @run main/othervm -Xverify:all -Xbatch IntegerMinValue
 *
 * @compile -XDstringConcat=inline IntegerMinValue.java
 * @run main/othervm -Xverify:all -Xbatch IntegerMinValue
 *
 * @compile -XDstringConcat=indy IntegerMinValue.java
 * @run main/othervm -Xverify:all -Xbatch IntegerMinValue
 *
 * @compile -XDstringConcat=indyWithConstants IntegerMinValue.java
 * @run main/othervm -Xverify:all -Xbatch IntegerMinValue
*/

public class IntegerMinValue {

    public void test() {
        int i = Integer.MIN_VALUE;
        String s = "" + i;
        if (!"-2147483648".equals(s)) {
           throw new IllegalStateException("Failed: " + s);
        }
        System.out.println(s);
    }

    public static void main(String[] strArr) {
        IntegerMinValue t = new IntegerMinValue();
        for (int i = 0; i < 100_000; i++ ) {
            t.test();
        }
    }

}
