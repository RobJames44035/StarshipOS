/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */


// Simple framework to run all the tests in sequence
// Because all the tests are marked @ignore as they require special hardware,
// we cannot use jtreg to do this.

import java.lang.reflect.Method;

public class TestAll {

    private static final Class[] CLASSES = {
        TestDefault.class,
        TestChannel.class,
        TestConnect.class,
        TestConnectAgain.class,
        TestControl.class,
        TestExclusive.class,
        TestMultiplePresent.class,
        TestPresent.class,
        TestTransmit.class,
        TestDirect.class,
    };

    public static void main(String[] args) throws Exception {
        Utils.setLibrary(args);
        for (Class clazz : CLASSES) {
            run(clazz, args);
        }
    }

    private static void run(Class clazz, Object args) throws Exception {
        System.out.println("===== Running test " + clazz.getName() + " =====");
        Method method = clazz.getMethod("main", String[].class);
        method.invoke(null, args);
        System.out.println("===== Passed  test " + clazz.getName() + " =====");
    }

}
