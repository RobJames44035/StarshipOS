/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8075118
 * @summary JVM stuck in infinite loop during verification
 * @compile HandlerInTry.jasm
 * @compile IsolatedHandlerInTry.jasm
 * @run main/othervm -Xverify:all LoadHandlerInTry
 */

/*
 * This test has two cases:
 *
 * 1. class HandlerInTry:  Class HandlerInTry contains a TRY block in a
 *    constructor whose handler is inside the same TRY block.  The last
 *    few bytecodes and exception table look like this:
 *
 *         ...
 *      87: athrow
 *      88: astore        4
 *      90: invokestatic  #9
 *      93: aload         4
 *      95: athrow
 *      96: return
 *    Exception table:
 *       from    to  target type
 *          36    46    53   Class java/lang/Throwable
 *          36    46    88   any
 *          53    90    88   any
 *
 * Note that the target for the third handler in the Exception table is
 * inside its TRY block.
 * Without the fix for bug JDK-8075118, this test will time out.
 *
 *
 * 2. class IsolatedHandlerInTry: Class IsolatedHandlerInTry also contains
 *    a TRY block in a constructoer whose handler is inside its TRY block.
 *    But the handler is only reachable if an exception is thrown.  The
 *    handler's bytecodes will not get parsed as part of parsing the TRY
 *    block.  They will only get parsed as a handler for the TRY block.
 *    Since the isolated handler does a 'return', a VerifyError exception
 *    should get thrown.
 */

public class LoadHandlerInTry {

    public static void main(String[] args) throws Exception {
        System.out.println("Regression test for bug 8075118");
        try {
            Class newClass = Class.forName("HandlerInTry");
            throw new RuntimeException(
                 "Failed to throw VerifyError for HandlerInTry");
        } catch (java.lang.VerifyError e) {
            System.out.println("Passed: VerifyError exception was thrown");
        }

        try {
            Class newClass = Class.forName("IsolatedHandlerInTry");
            throw new RuntimeException(
                 "Failed to throw VerifyError for IsolatedHandlerInTry");
        } catch (java.lang.VerifyError e) {
            System.out.println("Passed: VerifyError exception was thrown");
        }
    }
}
