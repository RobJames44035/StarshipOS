/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.spi.ToolProvider;

import jdk.tools.jlink.internal.LinkableRuntimeImage;

/*
 * @test
 * @summary Test jlink --help for capability output
 * @modules jdk.jlink/jdk.tools.jlink.internal
 * @requires vm.compMode != "Xcomp"
 * @run main/othervm -Duser.language=en JLinkHelpCapabilityTest
 */
public class JLinkHelpCapabilityTest {
    static final ToolProvider JLINK_TOOL = ToolProvider.findFirst("jlink")
        .orElseThrow(() ->
            new RuntimeException("jlink tool not found")
        );

    public static void main(String[] args) throws Exception {
        boolean runtimeLinkCap = LinkableRuntimeImage.isLinkableRuntime();
        String capabilities = String.format("Linking from run-time image %s",
                                            runtimeLinkCap ? "enabled" : "disabled");
        {
            // Verify capability in --help output
            StringWriter writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer);
            JLINK_TOOL.run(pw, pw, "--help");
            String output = writer.toString().trim();
            String lines[] = output.split("\n");
            String capabilitiesMsg = null;
            boolean seenCap = false;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].startsWith("Capabilities:")) {
                    seenCap = true;
                    continue; // skip 'Capabilities:'
                }
                if (!seenCap) {
                    continue;
                } else {
                    // Line after capabilities is the message we care about
                    capabilitiesMsg = lines[i].trim();
                    break;
                }
            }
            System.out.println("DEBUG: Capabilities:");
            System.out.println("DEBUG:   " + capabilitiesMsg);
            if (!capabilities.equals(capabilitiesMsg)) {
                System.err.println(output);
                throw new AssertionError("'--help': Capabilities mismatch. Expected: '" +
                                         capabilities +"' but got '" + capabilitiesMsg + "'");
            }
        }
    }
}
