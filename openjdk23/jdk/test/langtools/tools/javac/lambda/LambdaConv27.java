/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8008227
 * @summary Mixing lambdas with anonymous classes leads to NPE thrown by compiler
 * @run main LambdaConv27
 */
public class LambdaConv27 {

     public static void main(String[] args) {
         SAM s = ()-> { SAM s2 = ()->{ new Object() { }; }; s2.m(); };
         s.m();
     }

     interface SAM {
         void m();
     }
}
