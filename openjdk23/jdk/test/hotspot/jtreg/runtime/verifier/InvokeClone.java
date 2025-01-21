/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test Verifier handling of invoking java/lang/Object::clone() on object arrays.
 * @bug 8286277
 * @build InvokeCloneValid InvokeCloneInvalid
 * @run main/othervm -Xverify InvokeClone
 */

public class InvokeClone {
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            Class.forName("InvokeCloneValid");
        }  catch (VerifyError e) {
            throw new RuntimeException("Unexpected VerifyError", e);
        }

        try {
            Class.forName("InvokeCloneInvalid");
            throw new RuntimeException("VerifyError expected but not thrown");
        } catch (VerifyError e) {
            System.out.println("Expected: " + e);
        }
    }
}
