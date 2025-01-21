/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import jdk.test.lib.Platform;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.dcmd.CommandExecutor;
import jdk.test.lib.dcmd.JMXExecutor;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/*
 * @test
 * @bug 8054890
 * @summary Test of VM.set_flag diagnostic command
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @run testng SetVMFlagTest
 */

public class SetVMFlagTest {
    private static final String MANAGEABLE_PATTERN = "\\s*bool\\s+(\\S+)\\s+[\\:]?=\\s+" +
                                                     "(.*?)\\s+\\{manageable\\}";
    private static final String IMMUTABLE_PATTERN = "\\s*uintx\\s+(\\S+)\\s+[\\:]?=\\s+" +
                                                    "(.*?)\\s+\\{product\\}";

    public void run(CommandExecutor executor) {
        setMutableFlag(executor);
        setMutableFlagWithInvalidValue(executor);
        setImmutableFlag(executor);
        setNonExistingFlag(executor);
        setStringFlag(executor);
    }

    @Test
    public void jmx() {
        run(new JMXExecutor());
    }

    private void setMutableFlagInternal(CommandExecutor executor, String flag,
                                        boolean val, boolean isNumeric) {
        String strFlagVal;
        if (isNumeric) {
            strFlagVal = val ? "1" : "0";
        } else {
            strFlagVal = val ? "true" : "false";
        }

        OutputAnalyzer out = executor.execute("VM.set_flag " + flag + " " + strFlagVal);
        out.stderrShouldBeEmpty();

        out = getAllFlags(executor);

        String newFlagVal = out.firstMatch(MANAGEABLE_PATTERN.replace("(\\S+)", flag), 1);

        assertNotEquals(newFlagVal, val ? "1" : "0");
    }

    private void setMutableFlag(CommandExecutor executor) {
        OutputAnalyzer out = getAllFlags(executor);
        String flagName = out.firstMatch(MANAGEABLE_PATTERN, 1);
        String flagVal = out.firstMatch(MANAGEABLE_PATTERN, 2);

        System.out.println("### Setting a mutable flag '" + flagName + "'");

        if (flagVal == null) {
            System.err.println(out.getOutput());
            throw new Error("Can not find a boolean manageable flag");
        }

        Boolean blnVal = Boolean.parseBoolean(flagVal);
        setMutableFlagInternal(executor, flagName, !blnVal, true);
        setMutableFlagInternal(executor, flagName, blnVal, false);
    }

    private void setMutableFlagWithInvalidValue(CommandExecutor executor) {
        OutputAnalyzer out = getAllFlags(executor);
        String flagName = out.firstMatch(MANAGEABLE_PATTERN, 1);
        String flagVal = out.firstMatch(MANAGEABLE_PATTERN, 2);

        System.out.println("### Setting a mutable flag '" + flagName + "' to an invalid value");

        if (flagVal == null) {
            System.err.println(out.getOutput());
            throw new Error("Can not find a boolean manageable flag");
        }

        // a boolean flag accepts only 0/1 as its value
        out = executor.execute("VM.set_flag " + flagName + " unexpected_value");
        out.stderrShouldBeEmpty();
        out.stdoutShouldContain("flag value must be a boolean (1/0 or true/false)");

        out = getAllFlags(executor);

        String newFlagVal = out.firstMatch(MANAGEABLE_PATTERN.replace("(\\S+)", flagName), 1);

        assertEquals(newFlagVal, flagVal);
    }

    private void setImmutableFlag(CommandExecutor executor) {
        OutputAnalyzer out = getAllFlags(executor);
        String flagName = out.firstMatch(IMMUTABLE_PATTERN, 1);
        String flagVal = out.firstMatch(IMMUTABLE_PATTERN, 2);

        System.out.println("### Setting an immutable flag '" + flagName + "'");

        if (flagVal == null) {
            System.err.println(out.getOutput());
            throw new Error("Can not find an immutable uintx flag");
        }

        Long numVal = Long.parseLong(flagVal);

        out = executor.execute("VM.set_flag " + flagName + " " + (numVal + 1));
        out.stderrShouldBeEmpty();
        out.stdoutShouldContain("only 'writeable' flags can be set");

        out = getAllFlags(executor);

        String newFlagVal = out.firstMatch(IMMUTABLE_PATTERN.replace("(\\S+)", flagName), 1);

        assertEquals(newFlagVal, flagVal);
    }

    private void setNonExistingFlag(CommandExecutor executor) {
        String unknownFlag = "ThisIsUnknownFlag";
        System.out.println("### Setting a non-existing flag '" + unknownFlag + "'");
        OutputAnalyzer out = executor.execute("VM.set_flag " + unknownFlag + " 1");
        out.stderrShouldBeEmpty();
        out.stdoutShouldContain("flag " + unknownFlag + " does not exist");
    }

    private void setStringFlag(CommandExecutor executor) {
        // Today we don't have any manageable flags of the string type in the product build,
        // so we can only test DummyManageableStringFlag in the debug build.
        if (!Platform.isDebugBuild()) {
            return;
        }

        String flag = "DummyManageableStringFlag";
        String toValue = "DummyManageableStringFlag_Is_Set_To_Hello";

        System.out.println("### Setting a string flag '" + flag + "'");
        OutputAnalyzer out = executor.execute("VM.set_flag " + flag + " " + toValue);
        out.stderrShouldBeEmpty();

        out = getAllFlags(executor);
        out.stdoutShouldContain(toValue);
    }

    private OutputAnalyzer getAllFlags(CommandExecutor executor) {
        return executor.execute("VM.flags -all", true);
    }
}
