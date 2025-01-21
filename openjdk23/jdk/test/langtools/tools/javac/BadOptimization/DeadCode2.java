/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4057345 4120016 4120014
 * @summary try-catch 2:  Verify that overzealous dead-code elimination no
 * longer removes live code.
 * @author dps
 *
 * @run clean DeadCode2
 * @run compile -O DeadCode2.java
 * @run main DeadCode2
 */

class cls
{
   int [] ai = null;
}

public class DeadCode2 extends cls
{
    int [] bi = null;

    static int[] func()  {  return (int[])null; }

    public static void main(String argv[]) {
        int [] ci = null;
        int m = 0;
        int errcnt = 0;

        try { int i = func()[m = 7]; }
        catch(Exception e) {  System.out.println(e + " found "); errcnt++; }
        try { DeadCode2 ox = new DeadCode2(); int i = ox.ai[m = 7]; }
        catch(Exception e) {  System.out.println(e + " found "); errcnt++; }
        try { DeadCode2 ox = new DeadCode2(); int i = ox.bi[m = 7]; }
        catch(Exception e) {  System.out.println(e + " found "); errcnt++; }
        try { int i = ci[m = 7]; }
        catch(Exception e) {  System.out.println(e + " found "); errcnt++; }
        try { int i = ((int[])null)[0]; }
        catch(Exception e) {  System.out.println(e + " found "); errcnt++; }

        if (errcnt != 5)
            throw new RuntimeException("live code accidentally removed");
    }
}
