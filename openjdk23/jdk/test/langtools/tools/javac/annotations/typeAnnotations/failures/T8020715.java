/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @summary Regression: compiling program with lambda crashed compiler
 * @bug 8020715
 * @compile T8020715.java
 */
class T8020715 {
    // This crashed.
    private static  void  makeTask1() {
        class LocalClass {
            private Runnable r = () -> {};
        }
    }

    // This crashed, too.
    private  void  makeTask2() {
        class LocalClass {
            private Runnable r = () -> {};
        }
    }

    // This is fine.
    private class InnerClass {
        private Runnable r = () -> {};
    }
}
