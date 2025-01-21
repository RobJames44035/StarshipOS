/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class X<T> {
   private T t;
   X(T t) {
       this.t = t;
   }
   public static void meth() {
       new X<String>("OUTER").bar();
   }
   void bar() {
       new X<X>(this) {     // #1
           void run() {
               new Object() {  // #2
                   void run() {
                       X x = t;        // #3 <--- which t is bound ?
                   System.out.println(x);
                   }
               }.run();
           }
       }.run();
   }
}
