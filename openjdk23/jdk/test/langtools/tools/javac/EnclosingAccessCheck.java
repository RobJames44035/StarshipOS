/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4143715
 * @summary Compiler should diagnose attempt to access a member of a package-private
 * class contained in another package.
 *
 * @run compile/fail EnclosingAccessCheck.java
 */

public class EnclosingAccessCheck extends packone.Mediator {
    public void test() {
        getSecret().greet();
    }

    public static void main(String[] args) {
        new EnclosingAccessCheck().test();
    }
}
