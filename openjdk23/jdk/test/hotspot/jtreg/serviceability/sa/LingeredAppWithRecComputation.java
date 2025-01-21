/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import jdk.test.lib.apps.LingeredApp;

public class LingeredAppWithRecComputation extends LingeredApp {

    public static final String THREAD_NAME = "LingeredAppWithRecComputation.factorial()";

    private long factorial(int n) {
        if (n <= 1) {
                return 1;
        }
        if (n == 2) {
                return 2;
        }
        return n * factorial(n - 1);
    }

    public void testLoop() {
        long result = 0;
        long[] lastNResults = new long[20];
        int i = 0;
        int j = 0;
        while (true) {
            result = factorial(i);
            lastNResults[j] = result;
            if (i % 12 == 0) {
                    i = -1; // reset i
            }
            if (j % 19 == 0) {
                    j = -1; // reset j
            }
            i++; j++;
        }
    }

    public static void main(String args[]) {
        LingeredAppWithRecComputation app = new LingeredAppWithRecComputation();
        Thread factorial = new Thread(() -> {
            app.testLoop();
        });
        factorial.setName(THREAD_NAME);
        factorial.start();
        LingeredApp.main(args);
    }
 }
