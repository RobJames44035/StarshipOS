/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

/*
 * @test TestTableSwitch
 * @bug 8311583
 * @library /test/lib
 * @compile TableSwitchp1.jasm LookupSwitchp1.jasm
 * @run driver TestTableSwitch
 */

public class TestTableSwitch {

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            if (args[0].equals("runTable")) {
                TableSwitchp1.runTable();
            } else {  // if (args[0].equals("runLookup"))
                LookupSwitchp1.runLookup();
            }
        } else {
           ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder("TestTableSwitch", "runTable");
           OutputAnalyzer output = new OutputAnalyzer(pb.start());
           output.shouldContain("java.lang.VerifyError: Bad instruction");
           output.shouldHaveExitValue(1);

           pb = ProcessTools.createTestJavaProcessBuilder("TestTableSwitch", "runLookup");
           output = new OutputAnalyzer(pb.start());
           output.shouldContain("java.lang.VerifyError: Bad instruction");
           output.shouldHaveExitValue(1);
        }
    }
}


