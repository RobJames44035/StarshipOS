/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestG1RemSetFlags
 * @requires vm.gc.G1 & vm.opt.UnlockExperimentalVMOptions == null & vm.opt.G1RemSetHowlNumBuckets == null & vm.opt.G1RemSetHowlMaxNumBuckets == null
 * @summary Verify that the remembered set flags are updated as expected
 * @modules java.base/jdk.internal.misc
 * @modules java.management/sun.management
 * @library /test/lib
 * @library /
 * @run driver gc.arguments.TestG1RemSetFlags
 */

import java.util.ArrayList;
import java.util.Arrays;

import jdk.test.lib.process.OutputAnalyzer;

public class TestG1RemSetFlags {

  private static void checkG1RemSetFlags(String[] flags, int exitValue) throws Exception {
    ArrayList<String> flagList = new ArrayList<String>();
    flagList.addAll(Arrays.asList(flags));
    flagList.add("-XX:+UseG1GC");
    flagList.add("-XX:+PrintFlagsFinal");
    flagList.add("-version");

    OutputAnalyzer output = GCArguments.executeTestJava(flagList);
    output.shouldHaveExitValue(exitValue);
  }

  public static void main(String args[]) throws Exception {
    checkG1RemSetFlags(new String[] { "-XX:+UnlockExperimentalVMOptions", "-XX:G1RemSetHowlNumBuckets=8", "-XX:G1RemSetHowlMaxNumBuckets=8"  },  0);
    checkG1RemSetFlags(new String[] { "-XX:+UnlockExperimentalVMOptions", "-XX:G1RemSetHowlNumBuckets=8", "-XX:G1RemSetHowlMaxNumBuckets=16"  },  0);
    checkG1RemSetFlags(new String[] { "-XX:+UnlockExperimentalVMOptions", "-XX:G1RemSetHowlNumBuckets=16", "-XX:G1RemSetHowlMaxNumBuckets=8"  },  1);
    checkG1RemSetFlags(new String[] { "-XX:+UnlockExperimentalVMOptions", "-XX:G1RemSetHowlNumBuckets=7"  },  1);
    checkG1RemSetFlags(new String[] { "-XX:+UnlockExperimentalVMOptions", "-XX:G1RemSetHowlMaxNumBuckets=7"  },  1);
  }
}
