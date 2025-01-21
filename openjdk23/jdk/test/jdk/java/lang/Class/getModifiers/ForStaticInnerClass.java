/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4163105
   @summary VM loses static modifier of inner class.
 */

import java.lang.reflect.Modifier;

public class ForStaticInnerClass {
    static class Static {
    }

    public static void main(String[] args) throws Exception {
        if (!Modifier.isStatic(Static.class.getModifiers()))
            throw new Exception("VM lost static modifier of innerclass.");
    }
}
