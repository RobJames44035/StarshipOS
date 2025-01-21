/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8139836
 * @summary Can't use super::x method reference when x is protected
 * @run main MethodReference74
 */

public class MethodReference74 extends pkg.Parent {

    public void protectedMethod() {
        log = "In child, calling ... ";
        Runnable r = super::protectedMethod;
        r.run();
        run(super::protectedMethod); // test also in invocation context.
    }

    private void run(Runnable r) {
        r.run();
    }

    public static void main(String[] args) {
        MethodReference74 child = new MethodReference74();
        child.protectedMethod();
        if (!child.log.equals("In child, calling ...  parent's  parent's ")) {
            throw new AssertionError("Unexpected output" + child.log);
        }
    }
}
