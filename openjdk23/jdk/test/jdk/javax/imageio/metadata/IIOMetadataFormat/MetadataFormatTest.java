/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @bug 4929170
 * @summary  Tests that user-supplied IIOMetadata implementations
 *           is able to load correspnding IIOMetadataFormat implementations.
 */

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;


public class MetadataFormatTest {
    public static void main(String[] args) throws Exception {
        String codebase = args[0];
        String code = args[1];

        MetadataTest t = createTest(codebase, code);
        try {
            t.doTest();
        } catch (IllegalStateException e) {
            System.out.println("Test failed.");
            e.printStackTrace();

            System.exit(1);
        }
    }

    protected static MetadataTest createTest(String codebase,
                                             String code) throws Exception {
        URL[] urls = { new File(codebase).toURL()};
        ClassLoader loader = new URLClassLoader(urls);

        Class ct = loader.loadClass(code);

        return (MetadataTest)ct.newInstance();
    }
}
