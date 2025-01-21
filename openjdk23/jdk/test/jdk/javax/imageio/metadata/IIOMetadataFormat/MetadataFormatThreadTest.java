/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @bug 4929170
 * @summary Tests that user-supplied IIOMetadata implementations
 *           is able to load correspnding IIOMetadataFormat implementations.
 */

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;


public class MetadataFormatThreadTest implements Runnable {

    String test_class;

    public static void main(String[] args) throws Exception {
        String codebase = args[0];
        String code = args[1];

        Thread t = createTest(codebase, code);
        try {
            t.start();
        } catch (IllegalStateException e) {
            System.out.println("Test failed.");
            e.printStackTrace();

            System.exit(1);
        }
    }

    public MetadataFormatThreadTest(String c) {
        test_class = c;
    }

    public void run() {
        try {
            ClassLoader loader = (ClassLoader) Thread.currentThread().getContextClassLoader();

            Class ct = loader.loadClass(test_class);

            MetadataTest t = (MetadataTest)ct.newInstance();

            t.doTest();
        } catch (Exception e) {
            System.out.println("Test failed.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected static Thread createTest(String codebase,
                                             String code) throws Exception {

        URL[] urls = { new File(codebase).toURL()};
        final ClassLoader loader = new URLClassLoader(urls);

        final Thread t = new Thread(new MetadataFormatThreadTest(code));
        t.setContextClassLoader(loader);

        return t;
    }

}
