/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.util.function.Function;

public class HiddenLambda implements HiddenTest {
     public void test() {
         Function<Object, String> f = o -> o.toString();
         String s = f.apply(this);
         throw new Error("thrown by " + s);
     }

     public String toString() {
         return getClass().getName();
     }
}
