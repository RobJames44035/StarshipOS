/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @summary Sanity check the output of NMT
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=summary -XX:+WhiteBoxAPI SummarySanityCheck
 */

import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.JDKToolFinder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jdk.test.whitebox.WhiteBox;

public class SummarySanityCheck {

  private static String jcmdout;
  public static void main(String args[]) throws Exception {
    // Grab my own PID
    String pid = Long.toString(ProcessTools.getProcessId());

    ProcessBuilder pb = new ProcessBuilder();

    // Run  'jcmd <pid> VM.native_memory summary scale=KB'
    pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), pid, "VM.native_memory", "summary", "scale=KB"});
    OutputAnalyzer output = new OutputAnalyzer(pb.start());

    jcmdout = output.getOutput();
    // Split by '-' to get the 'groups'
    String[] lines = jcmdout.split("\n");

    if (lines.length == 0) {
      throwTestException("Failed to parse jcmd output");
    }

    long totalCommitted = 0, totalReserved = 0;
    long totalCommittedSum = 0, totalReservedSum = 0;

    // Match '- <mtType> (reserved=<reserved>KB, committed=<committed>KB) and some times readonly=<readonly>KB
    Pattern mtTypePattern = Pattern.compile("-\\s+(?<typename>[\\w\\s]+)\\(reserved=(?<reserved>\\d+)KB,\\scommitted=(?<committed>\\d+)KB((,\\sreadonly=(?<readonly>\\d+)KB)|)\\)");
    // Match 'Total: reserved=<reserved>KB, committed=<committed>KB'
    Pattern totalMemoryPattern = Pattern.compile("Total\\:\\sreserved=(?<reserved>\\d+)KB,\\scommitted=(?<committed>\\d+)KB");

    for (int i = 0; i < lines.length; i++) {
      if (lines[i].startsWith("Total")) {
        Matcher totalMemoryMatcher = totalMemoryPattern.matcher(lines[i]);

        if (totalMemoryMatcher.matches()) {
          totalCommitted = Long.parseLong(totalMemoryMatcher.group("committed"));
          totalReserved = Long.parseLong(totalMemoryMatcher.group("reserved"));
        } else {
          throwTestException("Failed to match the expected groups in 'Total' memory part");
        }
      } else if (lines[i].startsWith("-")) {
        Matcher typeMatcher = mtTypePattern.matcher(lines[i]);
        if (typeMatcher.matches()) {
          long typeCommitted = Long.parseLong(typeMatcher.group("committed"));
          long typeReserved = Long.parseLong(typeMatcher.group("reserved"));

          // Only Shared class space has readonly component
          if (lines[i].contains("Shared class space") && typeMatcher.group("readonly") != null) {
            long typeReadOnly = Long.parseLong(typeMatcher.group("readonly"));
            // Make sure readonly is always less or equal to committed
            if (typeReadOnly > typeCommitted) {
              throwTestException("Readonly (" + typeReadOnly + ") was more than Committed ("
                  + typeCommitted + ") for mtType: " + typeMatcher.group("typename"));
            }
          }

          // Make sure reserved is always less or equals
          if (typeCommitted > typeReserved) {
            throwTestException("Committed (" + typeCommitted + ") was more than Reserved ("
                + typeReserved + ") for mtType: " + typeMatcher.group("typename"));
          }

          // Add to total and compare them in the end
          totalCommittedSum += typeCommitted;
          totalReservedSum += typeReserved;
        } else {
          throwTestException("Failed to match the group on line " + i);
        }
      }
    }

    // See if they add up correctly, rounding is a problem so make sure we're within +/- 8KB
    long committedDiff = totalCommitted - totalCommittedSum;
    if (committedDiff > 8 || committedDiff < -8) {
      throwTestException("Total committed (" + totalCommitted + ") did not match the summarized committed (" + totalCommittedSum + ")" );
    }

    long reservedDiff = totalReserved - totalReservedSum;
    if (reservedDiff > 8 || reservedDiff < -8) {
      throwTestException("Total reserved (" + totalReserved + ") did not match the summarized reserved (" + totalReservedSum + ")" );
    }
  }

  private static void throwTestException(String reason) throws Exception {
      throw new Exception(reason + " . Stdout is :\n" + jcmdout);
  }
}
