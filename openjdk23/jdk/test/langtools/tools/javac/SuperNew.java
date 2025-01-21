/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4500836
 * @summary javac fails to find enclosing instance early in constructor
 * @author gafter
 *
 * @compile SuperNew.java
 */


public class SuperNew {
    class Inner1 {
    }
    class Inner2 {
        Inner2(Inner1 ignore) {}
        Inner2() {
            this(new Inner1()); //BAD
        }
        Inner2(String s) {
            this(SuperNew.this.new Inner1()); //OK
        }
        Inner2(char junk) {
            this(newInner1()); //OK
        }
        Inner2(byte junk) {
            this(SuperNew.this.newInner1()); //OK
        }
    }
    Inner1 newInner1() { return new Inner1(); }
}
