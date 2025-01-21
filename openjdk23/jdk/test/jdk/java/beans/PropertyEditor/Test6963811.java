/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6963811
 * @summary Tests deadlock in PropertyEditorManager
 * @author Sergey Malenkov
 * @modules java.desktop/com.sun.beans.editors
 * @compile -XDignore.symbol.file Test6963811.java
 * @run main Test6963811
 */

import java.beans.PropertyEditorManager;
import com.sun.beans.editors.StringEditor;

public class Test6963811 implements Runnable {
    private final long time;
    private final boolean sync;

    public Test6963811(long time, boolean sync) {
        this.time = time;
        this.sync = sync;
    }

    public void run() {
        try {
            Thread.sleep(this.time); // increase the chance of the deadlock
            if (this.sync) {
                synchronized (Test6963811.class) {
                    PropertyEditorManager.findEditor(Super.class);
                }
            }
            else {
                PropertyEditorManager.findEditor(Sub.class);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[2];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Test6963811(0L, i > 0));
            threads[i].start();
            Thread.sleep(500L); // increase the chance of the deadlock
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    public static class Super {
    }

    public static class Sub extends Super {
    }

    public static class SubEditor extends StringEditor {
        public SubEditor() {
            new Test6963811(1000L, true).run();
        }
    }
}
