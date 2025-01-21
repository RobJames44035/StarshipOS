/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6222762
 * @summary Primitive arrays and varargs inference leads to crash in TreeMaker.Type(TreeMaker.java:531)
 */

import java.util.Arrays;

public class T6222762 {
    static <T> void varargs(T... args) {
        for(T t : args)
            System.out.println(Arrays.toString((int[])t));
    }
    public static void main(String[] args) {
        varargs(new int[]{1, 2, 3}, new int[]{4, 5, 6});
    }
}
