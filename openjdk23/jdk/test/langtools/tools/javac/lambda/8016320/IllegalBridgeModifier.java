/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8016320
 * @summary Check that 8016320 is fixed,
 *          that bridges have valid modifier bits
 * @author  Robert Field
 * @run main IllegalBridgeModifier
 */

interface SAM {
    int m();
}

interface SuperI {
    public default int foo() { return 1234; }
}

interface I extends SuperI {
}

interface T extends I {
    public default SAM boo() { return I.super::foo; }
}

public class IllegalBridgeModifier {
    public static void main(String argv[])throws Exception {
        T t = new T(){};
        if (t.boo().m() != 1234) {
            throw new Exception("Failed test");
        }
    }
}
