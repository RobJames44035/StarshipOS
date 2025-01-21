/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 6493690
 * @summary javadoc should have a javax.tools.Tool service provider
 * @modules java.compiler
 *          jdk.compiler
 * @build APITest
 * @run main GetSourceVersionsTest
 */

import java.util.EnumSet;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.tools.DocumentationTool;
import javax.tools.ToolProvider;

/**
 * Tests for DocumentationTool.getSourceVersions method.
 */
public class GetSourceVersionsTest extends APITest {
    public static void main(String... args) throws Exception {
        new GetSourceVersionsTest().run();
    }

    /**
     * Verify getSourceVersions.
     */
    @Test
    public void testRun() throws Exception {
        DocumentationTool tool = ToolProvider.getSystemDocumentationTool();
        Set<SourceVersion> found = tool.getSourceVersions();
        Set<SourceVersion> expect = EnumSet.range(SourceVersion.RELEASE_3, SourceVersion.latest());
        if (!expect.equals(found)) {
            System.err.println("expect: " + expect);
            System.err.println(" found: " + expect);
            error("unexpected versions");
        }
    }
}

