/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6669869
 * @summary Tests DesignTime property in different application contexts
 * @author Sergey Malenkov
 */

import java.beans.Beans;

public class TestDesignTime implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        if (Beans.isDesignTime()) {
            throw new Error("unexpected DesignTime property");
        }
        Beans.setDesignTime(!Beans.isDesignTime());
        ThreadGroup group = new ThreadGroup("$$$");
        Thread thread = new Thread(group, new TestDesignTime());
        thread.start();
        thread.join();
    }

    public void run() {
        if (Beans.isDesignTime()) {
            throw new Error("shared DesignTime property");
        }
    }
}
