/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4057345 4120016 4120014
 * @summary static init:  Verify that overzealous dead-code elimination no
 * longer removes live code.
 * @author dps
 *
 * @run clean DeadCode4
 * @run compile -O DeadCode4.java
 * @run main DeadCode4
 */

class cls
{
    static int arr[];

    static {
        arr = new int[2];
        arr[0] = 0;
        arr[1] = 2;
    }
}

public class DeadCode4
{
    final int x = 9;

    private final void fun1() {
        try {
            int i = cls.arr[3];
            // if we got here, then there must be a problem
            throw new RuntimeException("accidental removal of live code");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ArrayIndexOutOfBoundsException correctly thrown");
            e.printStackTrace();
        }
    }

    public static void main( String[] args )
    {
        DeadCode4 r1 = new DeadCode4();
        r1.fun1();
    }
}
