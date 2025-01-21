/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/**
 * @test
 * @bug 7197642
 * @summary test ServiceLoader.load methods for NullPointerException.
 */
import java.util.ServiceLoader;
import java.util.Arrays;

public final class NPE {
    abstract static class Test {
        String name;

        Test(String name) { this.name = name; }

        abstract void run();
    }

    static Test load = new Test("ServiceLoader.load(null)") {
        void run() { ServiceLoader.load(null); }
    };

    static Test loadWithClassLoader = new Test("ServiceLoader.load(null, loader)") {
        void run() { ServiceLoader.load(null, NPE.class.getClassLoader()); }
    };

    static Test loadInstalled = new Test("ServiceLoader.loadInstalled(null)") {
        void run() { ServiceLoader.loadInstalled(null); }
    };

    public static void main(String[] args) throws Exception {
        for (Test t : Arrays.asList(load, loadWithClassLoader, loadInstalled)) {
            NullPointerException caught = null;
            try {
                t.run();
            } catch (NullPointerException e) {
                caught = e;
            }
            if (caught == null) {
                throw new RuntimeException("NullPointerException expected for method invocation of " + t.name);
            }
        }
    }
}

