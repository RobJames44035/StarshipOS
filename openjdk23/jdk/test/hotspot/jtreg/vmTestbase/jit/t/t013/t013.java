/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t013.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t013.t013
 */

package jit.t.t013;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

class Globals {
        static public int NumDisks;
        static public int MaxDisks = 64; // this will do!
}

public class t013 {

    public static final GoldChecker goldChecker = new GoldChecker( "t013" );

    static Peg peg1 = new Peg(1),
               peg2 = new Peg(2),
               peg3 = new Peg(3);

    public static void main(String args[]) {

       Globals.NumDisks = 4;

       t013.goldChecker.println("moving " + Globals.NumDisks + " disks...");

       if (Globals.NumDisks > Globals.MaxDisks)
           Globals.NumDisks = Globals.MaxDisks;

       for (int i = Globals.NumDisks; i > 0; i--)
           peg1.addDisk(i);

        long start = System.currentTimeMillis();

        moveDisks(Globals.NumDisks, peg1, peg3, peg2);

        long stop = System.currentTimeMillis();

        long t = (stop - start) / 100;

        t013.goldChecker.println("finished, but I won't tell you how long it took");
        t013.goldChecker.check();
    }

    public static void moveDisks(int numDisks, Peg fromPeg, Peg toPeg, Peg usingPeg) {
        t013.goldChecker.println
        (
            "moveDisks(" +
            numDisks +
            ", " +
            fromPeg.pegNum +
            ", " +
            toPeg.pegNum +
            ", " +
            usingPeg.pegNum +
            ")"
        );
        if (numDisks == 1) {
            int disk;
            toPeg.addDisk(disk = fromPeg.removeDisk());
        } else {
            moveDisks(numDisks - 1, fromPeg, usingPeg, toPeg);
            moveDisks(1, fromPeg, toPeg, usingPeg);
            moveDisks(numDisks - 1, usingPeg, toPeg, fromPeg);
        }
    }
}

class Peg {

    int pegNum;
    int disks[] = new int[64];
    int nDisks;

    public Peg(int n) {
        t013.goldChecker.println("Peg(" + n + ")");
        pegNum = n;
        for (int i = 0; i < Globals.NumDisks; i++)
            disks[i] = 0;
        nDisks = 0;
    }

    public int pegNum() {
        return pegNum;
    }

    public void addDisk(int diskNum) {
        t013.goldChecker.println("addDisk(" + diskNum + ", " + pegNum() +")");
        disks[nDisks++] = diskNum;
    }

    public int removeDisk() {
        t013.goldChecker.println("removeDisk(" + pegNum() + ")");
        return disks[--nDisks];
    }

}
