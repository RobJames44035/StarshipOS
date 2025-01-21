/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6669869
 * @summary Tests GuiAvailable property in different application contexts
 * @author Sergey Malenkov
 */

import java.awt.GraphicsEnvironment;
import java.beans.Beans;

public class TestGuiAvailable implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        if (Beans.isGuiAvailable() == GraphicsEnvironment.isHeadless()) {
            throw new Error("unexpected GuiAvailable property");
        }
        Beans.setGuiAvailable(!Beans.isGuiAvailable());
        ThreadGroup group = new ThreadGroup("$$$");
        Thread thread = new Thread(group, new TestGuiAvailable());
        thread.start();
        thread.join();
    }

    public void run() {
        if (Beans.isGuiAvailable() == GraphicsEnvironment.isHeadless()) {
            throw new Error("shared GuiAvailable property");
        }
    }
}
