/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * @test
 * @summary
 * @requires vm.continuations
 * @library /test/lib
 * @run build TestClass1 TestClass2 TestClass3
 * @compile ParallelTransformerLoaderTest.java
 * @run driver jdk.test.lib.util.JavaAgentBuilder ParallelTransformerLoaderAgent ParallelTransformerLoaderAgent.jar
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar Test.jar TestClass1 TestClass2 TestClass3
 * @run clean ParallelTransformerLoaderAgent TestClass1 TestClass2 TestClass3
 * @run main/othervm -javaagent:ParallelTransformerLoaderAgent.jar=Test.jar ParallelTransformerLoaderTest platform
 * @run main/othervm -javaagent:ParallelTransformerLoaderAgent.jar=Test.jar ParallelTransformerLoaderTest virtual
 */
public class ParallelTransformerLoaderTest {
    private static final int kNumIterations = 1000;

    public static void main(String[] args) throws Exception {
        System.out.println();
        System.out.print("Starting test with " + kNumIterations + " iterations");
        for (int i = 0; i < kNumIterations; i++) {
            // load some classes from multiple threads (this thread and one other)
            Thread thread = new TestThread(2, args[0].equals("virtual")).thread;
            thread.start();
            loadClasses(1);

            // log that it completed and reset for the next iteration
            thread.join();
            System.out.print(".");
            ParallelTransformerLoaderAgent.generateNewClassLoader();
        }

        System.out.println();
        System.out.println("Test completed successfully");
    }

    private static class TestThread implements Runnable {
        private final int fIndex;
        final Thread thread;

        public TestThread(int index, boolean isVirtual) {
            var f = isVirtual ? Thread.ofVirtual().factory() : Thread.ofPlatform().factory();
            thread = f.newThread(this);
            thread.setName("TestThread");
            fIndex = index;
        }

        public void run() {
            loadClasses(fIndex);
        }
    }

    public static void loadClasses(int index) {
        ClassLoader loader = ParallelTransformerLoaderAgent.getClassLoader();
        try {
            Class.forName("TestClass" + index, true, loader);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
