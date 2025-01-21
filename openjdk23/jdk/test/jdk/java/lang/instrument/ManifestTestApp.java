/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

public class ManifestTestApp {
    public static void main(String args[]) {
        System.out.println("Hello from ManifestTestApp!");

        new ManifestTestApp().doTest();
        System.exit(0);
    }

    private void doTest() {
        try {
            // load the class only found via the Boot-Class-Path attribute
            Object instance = loadExampleClass();
            if (instance.getClass().getClassLoader() == null) {
                System.out.println("PASS: ExampleForBootClassPath was loaded" +
                    " by the boot class path loader.");
            } else {
                System.out.println("FAIL: ExampleForBootClassPath was loaded" +
                    " by a non-boot class path loader.");
                System.exit(1);
            }
        } catch (NoClassDefFoundError ncdfe) {
            // This message just lets ManifestTest.sh know whether or
            // not ExampleForBootClassPath was loaded. Depending on
            // the current test case, that will be either a PASSing
            // condition or a FAILing condition as determined by
            // ManifestTest.sh.
            System.out.println("ExampleForBootClassPath was not loaded.");
        }
    }

    Object loadExampleClass() {
        ExampleForBootClassPath instance = new ExampleForBootClassPath();
        System.out.println("ExampleForBootClassPath was loaded.");
        if (instance.fifteen() == 15) {
            System.out.println("PASS: the correct" +
                " ExampleForBootClassPath was loaded.");
        } else {
            System.out.println("FAIL: the wrong ExampleForBootClassPath" +
                " was loaded.");
            System.out.println("FAIL: instance.fifteen()=" +
                instance.fifteen());
            System.exit(1);
        }
        return instance;
    }
}
