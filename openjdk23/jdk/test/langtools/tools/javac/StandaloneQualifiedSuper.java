/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4147520
 * @summary Qualified 'super' must further qualify a member -- it cannot stand alone.
 *
 * @run compile/fail StandaloneQualifiedSuper.java
 */

public class StandaloneQualifiedSuper {

    public class AS { }

    public class BS { }

    public class CS { }

    public class A extends AS {
        A() { super(); }
        public class B extends BS {
            B() { super(); }
            public class C extends CS {
                C() { super(); }
                void test() {
                    // '<class>.super' must qualify another field or
                    // method -- it cannot stand alone.
                    System.out.println(B.super);  // ERROR
                }
            }
        }
    }
}
