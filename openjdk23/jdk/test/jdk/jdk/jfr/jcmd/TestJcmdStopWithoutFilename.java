/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.jfr.jcmd;

import jdk.test.lib.process.OutputAnalyzer;

/**
 * @test
 * @summary The test verifies JFR.stop
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jcmd.TestJcmdStopWithoutFilename
 */
public class TestJcmdStopWithoutFilename {

    public static void main(String[] args) throws Exception {

        JcmdHelper.jcmd("JFR.start name=test");
        OutputAnalyzer output = JcmdHelper.jcmd("JFR.stop name=test");
        output.shouldNotContain("written to");

        String filename = "output.jfr";
        JcmdHelper.jcmd("JFR.start name=test filename=" + filename);
        output = JcmdHelper.jcmd("JFR.stop name=test");
        output.shouldContain("written to").shouldContain(filename);
    }
}

