/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @summary Unchecked method call on a method declared inside anonymous inner causes javac to crash
 * @compile -Xlint:unchecked T6881645.java
 */

class T6881645 {
   Object o = new Object() {
       <Z> void m (Class<Z> x) {}
       void test() {
           m((Class)null);
       }
   };
}

