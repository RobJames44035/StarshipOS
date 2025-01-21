/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @bug 8186694
 * @summary Check that JavacTaskPool reuses JavacTask internals when it should
 * @modules jdk.compiler/com.sun.tools.javac.api
 * @run main JavacTaskPoolTest
 */

import java.util.List;

import javax.lang.model.util.Types;

import com.sun.tools.javac.api.JavacTaskPool;

public class JavacTaskPoolTest {
    public static void main(String... args) throws Exception {
        new JavacTaskPoolTest().run();
    }

    void run() throws Exception {
        JavacTaskPool pool = new JavacTaskPool(2);
        Types tps1 = pool.getTask(null, null, null, List.of("-XDone"), null, null, task -> {
            task.getElements(); //initialize
            return task.getTypes();
        });
        Types tps2  = pool.getTask(null, null, null, List.of("-XDone"), null, null, task -> {
            task.getElements(); //initialize
            return task.getTypes();
        });

        assertSame(tps1, tps2);

        Types tps3 = pool.getTask(null, null, null, List.of("-XDtwo"), null, null, task -> {
            task.getElements(); //initialize
            return task.getTypes();
        });

        assertNotSame(tps1, tps3);

        Types tps4 = pool.getTask(null, null, null, List.of("-XDthree"), null, null, task -> {
            task.getElements(); //initialize
            return task.getTypes();
        });

        assertNotSame(tps1, tps4);
        assertNotSame(tps3, tps4);

        Types tps5 = pool.getTask(null, null, null, List.of("-XDone"), null, null, task -> {
            task.getElements(); //initialize
            return task.getTypes();
        });

        assertNotSame(tps1, tps5);
    }

    void assertSame(Object expected, Object actual) {
        if (expected != actual) {
            throw new IllegalStateException("expected=" + expected + "; actual=" + actual);
        }
    }

    void assertNotSame(Object expected, Object actual) {
        if (expected == actual) {
            throw new IllegalStateException("expected=" + expected + "; actual=" + actual);
        }
    }

}
