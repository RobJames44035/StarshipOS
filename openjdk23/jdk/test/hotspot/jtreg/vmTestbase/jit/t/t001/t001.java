/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/t/t001.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.t.t001.t001
 */

package jit.t.t001;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

public class t001
{
    public static final GoldChecker goldChecker = new GoldChecker( "t001" );

    public static void main(String argv[])
    {
        int i = 39;
        int j = 40;

        if(i>=j)t001.goldChecker.println("39>=40");
        else t001.goldChecker.println("!(39>=40)");
        if(i==j)t001.goldChecker.println("39==40");
        else t001.goldChecker.println("!(39==40)");
        if(i<=j)t001.goldChecker.println("39<=40");
        else t001.goldChecker.println("!(39<=40)");
        if(i!=j)t001.goldChecker.println("39!=40");
        else t001.goldChecker.println("!(39!=40)");
        if(i<j)t001.goldChecker.println("39<40");
        else t001.goldChecker.println("!(39<40)");
        if(i>j)t001.goldChecker.println("39>40");
        else t001.goldChecker.println("!(39>40)");

        if(i>=i)t001.goldChecker.println("39>=39");
        else t001.goldChecker.println("!(39>=39)");
        if(i==i)t001.goldChecker.println("39==39");
        else t001.goldChecker.println("!(39==39)");
        if(i<=i)t001.goldChecker.println("39<=39");
        else t001.goldChecker.println("!(39<=39)");
        if(i!=i)t001.goldChecker.println("39!=39");
        else t001.goldChecker.println("!(39!=39)");
        if(i<i)t001.goldChecker.println("39<39");
        else t001.goldChecker.println("!(39<39)");
        if(i>i)t001.goldChecker.println("39>39");
        else t001.goldChecker.println("!(39>39)");
        t001.goldChecker.check();
    }
}
