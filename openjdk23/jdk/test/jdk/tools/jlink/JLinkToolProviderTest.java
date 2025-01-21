/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.spi.ToolProvider;

/*
 * @test
 * @modules jdk.jlink
 * @run main JLinkToolProviderTest
 */
public class JLinkToolProviderTest {
    static final ToolProvider JLINK_TOOL = ToolProvider.findFirst("jlink")
        .orElseThrow(() ->
            new RuntimeException("jlink tool not found")
        );

    private static void checkJlinkOptions(String... options) {
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        JLINK_TOOL.run(pw, pw, options);
    }

    public static void main(String[] args) throws Exception {
        checkJlinkOptions("--help");
        checkJlinkOptions("--list-plugins");
    }
}
