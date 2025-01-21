/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t002.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t002.t002
 */

package jit.t.t002;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

public class t002
{
    public static final GoldChecker goldChecker = new GoldChecker( "t002" );

    public static void main(String argv[])
    {
        int i = 39;

        if(i>=40)t002.goldChecker.println("39>=40");
        else t002.goldChecker.println("!(39>=40)");
        if(i==40)t002.goldChecker.println("39==40");
        else t002.goldChecker.println("!(39==40)");
        if(i<=40)t002.goldChecker.println("39<=40");
        else t002.goldChecker.println("!(39<=40)");
        if(i!=40)t002.goldChecker.println("39!=40");
        else t002.goldChecker.println("!(39!=40)");
        if(i<40)t002.goldChecker.println("39<40");
        else t002.goldChecker.println("!(39<40)");
        if(i>40)t002.goldChecker.println("39>40");
        else t002.goldChecker.println("!(39>40)");

        if(i>=39)t002.goldChecker.println("39>=39");
        else t002.goldChecker.println("!(39>=39)");
        if(i==39)t002.goldChecker.println("39==39");
        else t002.goldChecker.println("!(39==39)");
        if(i<=39)t002.goldChecker.println("39<=39");
        else t002.goldChecker.println("!(39<=39)");
        if(i!=39)t002.goldChecker.println("39!=39");
        else t002.goldChecker.println("!(39!=39)");
        if(i<39)t002.goldChecker.println("39<39");
        else t002.goldChecker.println("!(39<39)");
        if(i>39)t002.goldChecker.println("39>39");
        else t002.goldChecker.println("!(39>39)");
        t002.goldChecker.check();
    }
}
