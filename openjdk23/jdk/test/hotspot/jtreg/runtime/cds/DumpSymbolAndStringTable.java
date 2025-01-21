/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8059510 8213445
 * @summary Test jcmd VM.symboltable, VM.stringtable and VM.systemdictionary options
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions
 *                   -XX:+WhiteBoxAPI DumpSymbolAndStringTable
 */
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.JDKToolFinder;
import jdk.test.whitebox.WhiteBox;

public class DumpSymbolAndStringTable {
    public static void main(String[] args) throws Exception {
        // Grab my own PID
        String pid = Long.toString(ProcessTools.getProcessId());

        WhiteBox wb = WhiteBox.getWhiteBox();
        boolean sharingEnabled = wb.isSharingEnabled();

        ProcessBuilder pb = new ProcessBuilder();
        pb.command(new String[] {JDKToolFinder.getJDKTool("jcmd"), pid, "VM.symboltable", "-verbose"});
        OutputAnalyzer output = CDSTestUtils.executeAndLog(pb, "jcmd-symboltable");
        final String sharedSymbolsHeader = "Shared symbols:\n";
        try {
            output.shouldContain("24 2: DumpSymbolAndStringTable\n");
            if (sharingEnabled) {
                output.shouldContain(sharedSymbolsHeader);
                output.shouldContain("17 65535: java.lang.runtime\n");
            }
        } catch (RuntimeException e) {
            output.shouldContain("Unknown diagnostic command");
        }

        pb.command(new String[] {JDKToolFinder.getJDKTool("jcmd"), pid, "VM.stringtable", "-verbose"});
        output = CDSTestUtils.executeAndLog(pb, "jcmd-stringtable");
        final String sharedStringsHeader = "Shared strings:\n";
        try {
            output.shouldContain("24: DumpSymbolAndStringTable\n");
            if (sharingEnabled && wb.canWriteJavaHeapArchive()) {
                output.shouldContain(sharedStringsHeader);
                if (!wb.isSharedInternedString("MILLI_OF_SECOND")) {
                    throw new RuntimeException("'MILLI_OF_SECOND' should be a shared string");
                }
            }
        } catch (RuntimeException e) {
            output.shouldContain("Unknown diagnostic command");
        }

        pb.command(new String[] {JDKToolFinder.getJDKTool("jcmd"), pid, "VM.systemdictionary"});
        output = CDSTestUtils.executeAndLog(pb, "jcmd-systemdictionary");
        try {
            output.shouldContain("System Dictionary for 'app' class loader statistics:");
            output.shouldContain("Number of buckets");
            output.shouldContain("Number of entries");
            output.shouldContain("Maximum bucket size");
        } catch (RuntimeException e) {
            output.shouldContain("Unknown diagnostic command");
        }

        pb.command(new String[] {JDKToolFinder.getJDKTool("jcmd"), pid, "VM.systemdictionary", "-verbose"});
        output = CDSTestUtils.executeAndLog(pb, "jcmd-systemdictionary");
        try {
            output.shouldContain("Dictionary for loader data: 0x");
            output.shouldContain("^java.lang.String");
        } catch (RuntimeException e) {
            output.shouldContain("Unknown diagnostic command");
        }
    }
}
