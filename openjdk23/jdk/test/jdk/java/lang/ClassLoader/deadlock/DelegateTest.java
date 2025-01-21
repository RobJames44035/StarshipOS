/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 4735126
 * @summary (cl) ClassLoader.loadClass locks all instances in chain when delegating
 * @modules java.base/java.lang:open
 *          jdk.compiler
 * @library /test/lib
 * @build jdk.test.lib.compiler.CompilerUtils
 * @run main/othervm -Xlog:class+load DelegateTest one-way
 * @run main/othervm -Xlog:class+load DelegateTest cross
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.test.lib.compiler.CompilerUtils;

public class DelegateTest implements Runnable {

    private static final Path TEST_DIR = Paths.get(System.getProperty("user.dir", "."));
    private static final Path SRC_DIR = Paths.get(System.getProperty("test.src"), "src");

    private String id;
    private DelegatingLoader dl;
    private String startClass;

    private static DelegatingLoader saLoader, sbLoader;

    public static void log(String line) {
        System.out.println(line);
    }

    public static void main(String[] args) throws Exception {
        if (!CompilerUtils.compile(SRC_DIR, TEST_DIR)) {
            throw new RuntimeException("Failed to compile "
                    + SRC_DIR.toAbsolutePath().toString());
        }

        URL[] url = new URL[1];
        try {
            url[0] = new URL("file://" + TEST_DIR + File.separator);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // Set up Classloader delegation hierarchy
        saLoader = new DelegatingLoader(url);
        sbLoader = new DelegatingLoader(url);

        String[] saClasses = { "comSA.SupBob", "comSA.Alice" };
        String[] sbClasses = { "comSB.SupAlice", "comSB.Bob" };

        saLoader.setDelegate(sbClasses, sbLoader);
        sbLoader.setDelegate(saClasses, saLoader);

        // test one-way delegate
        String testType = args[0];
        if (testType.equals("one-way")) {
            test("comSA.Alice", "comSA.SupBob");
        } else if (testType.equals("cross")) {
            // test cross delegate
            test("comSA.Alice", "comSB.Bob");
        } else {
            System.out.println("ERROR: unsupported - " + testType);
        }
    }

    private static void test(String clsForSA, String clsForSB) throws InterruptedException {
        DelegateTest ia = new DelegateTest("SA", saLoader, clsForSA);
        DelegateTest ib = new DelegateTest("SB", sbLoader, clsForSB);
        Thread ta = new Thread(ia);
        Thread tb = new Thread(ib);
        ta.start();
        tb.start();
        ta.join();
        tb.join();
    }

    public static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log("Thread interrupted");
        }
    }

    private DelegateTest(String id, DelegatingLoader dl, String startClass) {
        this.id = id;
        this.dl = dl;
        this.startClass = startClass;
    }

    public void run() {
        log("Spawned thread " + id + " running");
        try {
            // To mirror the WAS deadlock, need to ensure class load
            // is routed via the VM.
            Class.forName(startClass, true, dl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        log("Thread " + id + " terminating");
    }
}
