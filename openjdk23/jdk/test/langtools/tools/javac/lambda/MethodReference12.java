/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that Object methods are dispatched accordingly
 * @author  Maurizio Cimadamore
 * @run main MethodReference12
 */

public class MethodReference12 {

    interface SAM { void foo(int i); }

    static void print(int i) {
        System.out.println(i);
    }

    public static void main(String[] args) {
        try {
            test(MethodReference12::print);
            test(i -> { System.out.println(i); } );
        }
        catch (Throwable t) {
            t.printStackTrace();
            throw new AssertionError("An error occurred");
        }
    }

    static void test(SAM s) throws Throwable {
        s.hashCode();
        s.equals(null);
        s.toString();
        try {
            s.notify(); //will throw IllegalMonitorStateException
        }
        catch (final IllegalMonitorStateException e) {  }
        try {
            s.notifyAll(); //will throw IllegalMonitorStateException
        }
        catch (final IllegalMonitorStateException e) {  }
        try {
            s.wait(1); //will throw IllegalMonitorStateException
        }
        catch (final IllegalMonitorStateException | InterruptedException e) {  }
        try {
            s.wait(1,1); //will throw IllegalMonitorStateException
        }
        catch (final IllegalMonitorStateException | InterruptedException e) {  }
        try {
            s.wait(); //will throw IllegalMonitorStateException
        }
        catch (final IllegalMonitorStateException | InterruptedException e) {  }
    }
}
