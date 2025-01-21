/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6521805
 * @summary Regression: JDK5/JDK6 javac allows write access to outer class reference
 * @author mcimadamore
 *
 * @compile T6521805c.java
 */

class T6521805c {

    static class Outer {
         T6521805c this$0() { return null;}
    }

    public class Inner extends Outer {
        public void foo() {
            this$0();
        }
    }
}
