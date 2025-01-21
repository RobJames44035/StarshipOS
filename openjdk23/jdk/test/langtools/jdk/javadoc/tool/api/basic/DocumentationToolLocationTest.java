/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8025844
 * @summary test DocumentationTool.Location methods
 * @modules java.compiler
 *          jdk.compiler
 * @build APITest
 * @run main DocumentationToolLocationTest
 */

import javax.tools.DocumentationTool;
import java.util.Objects;

/**
 * Test for DocumentationTool.Location methods.
 */
public class DocumentationToolLocationTest extends APITest {
    public static void main(String[] args) throws Exception {
        new DocumentationToolLocationTest().run();
    }

    /**
     * Test getName() method
     */
    @Test
    public void testGetName() throws Exception {
        // getName() returns name(). This is for test coverage of getName.
        for (DocumentationTool.Location dl: DocumentationTool.Location.values()) {
            String expect = dl.name();
            String found = dl.getName();
            if (!Objects.equals(expect, found))
                throw new Exception("mismatch for " + dl + "; expected " + expect + ", found " + found);
        }
    }

    /**
     * Test generated enum methods values() and valueOf()
     */
    @Test
    public void testEnumMethods() throws Exception {
        DocumentationTool.Location[] values = DocumentationTool.Location.values();
        if (values.length != 4)
            throw new Exception("unexpected number of values returned");

        for (DocumentationTool.Location dl: values) {
            DocumentationTool.Location expect = dl;
            DocumentationTool.Location found = DocumentationTool.Location.valueOf(dl.name());
            if (!Objects.equals(expect, found))
                throw new Exception("mismatch for " + dl + "; expected " + expect + ", found " + found);
        }
    }
}
