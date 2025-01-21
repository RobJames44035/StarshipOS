/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
import java.lang.StackWalker.StackFrame;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;
import jdk.test.whitebox.WhiteBox;

public class LambdaVerification {
    static void verifyCallerIsArchivedLambda(boolean isRuntime) {
        // getCallerClass(0) = verifyCallerIsArchivedLambda
        // getCallerClass(1) = testNonCapturingLambda or testCapturingLambda
        // getCallerClass(2) = the lambda proxy class that should be archived by CDS.
        Class<?> c = getCallerClass(2);
        System.out.println("Lambda proxy class = " + c);
        String cn = c.getName();
        System.out.println(" cn = " + cn);
        String hiddenClassName = cn.replace('/', '+');
        String lambdaClassName = cn.substring(0, cn.lastIndexOf('/'));
        System.out.println(" lambda name = " + lambdaClassName);
        WhiteBox wb = WhiteBox.getWhiteBox();
        if (isRuntime) {
            // check that c is a shared class
            if (wb.isSharedClass(c)) {
                System.out.println("As expected, " + c + " is in shared space.");
            } else {
                throw new java.lang.RuntimeException(c + " must be in shared space.");
            }
            // check that lambda class cannot be found manually
            try {
                Class.forName(hiddenClassName);
            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace(System.out);
                System.out.println("As expected, loading of " + hiddenClassName + " should result in ClassNotFoundException.");
            } catch (Exception ex) {
                throw ex;
            }
            // check that lambda class is alive
            if (wb.isClassAlive(hiddenClassName)) {
                System.out.println("As expected, " + cn + " is alive.");
            } else {
                throw new java.lang.RuntimeException(cn + " should be alive.");
            }
        } else {
            if (wb.isSharedClass(c)) {
                throw new java.lang.RuntimeException(c + " must not be in shared space.");
            } else {
                System.out.println("As expected, " + c + " is not in shared space.");
            }
        }
        //System.out.println("=== Here's the call stack");
        //(new Throwable()).printStackTrace(System.out);
        //System.out.println("===");
        System.out.println("Succeeded");
    }

    // depth is 0-based -- i.e., depth==0 returns the class of the immediate caller of getCallerClass
    static Class<?> getCallerClass(int depth) {
        // Need to add the frame of the getCallerClass -- so the immediate caller (depth==0) of this method
        // is at stack.get(1) == stack.get(depth+1);
        StackWalker walker = StackWalker.getInstance(
            Set.of(StackWalker.Option.RETAIN_CLASS_REFERENCE,
                   StackWalker.Option.SHOW_HIDDEN_FRAMES));
        List<StackFrame> stack = walker.walk(s -> s.limit(depth+2).collect(Collectors.toList()));
        return stack.get(depth+1).getDeclaringClass();
    }
}
