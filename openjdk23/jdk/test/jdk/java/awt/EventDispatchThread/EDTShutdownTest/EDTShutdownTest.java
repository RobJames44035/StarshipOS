/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
  @test
  @bug 8031694
  @summary [macosx] TwentyThousandTest test intermittently hangs
  @author Oleg Pekhovskiy
  @modules java.desktop/sun.awt
  @modules java.desktop/java.awt:open
  @run main EDTShutdownTest
 */

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import sun.awt.AWTAccessor;

public class EDTShutdownTest {

    private static boolean passed = false;

    public static void main(String[] args) {
        // Force EDT start with InvocationEvent
        EventQueue.invokeLater(() -> {
            // EventQueue is empty now
            EventQueue queue = Toolkit.getDefaultToolkit()
                               .getSystemEventQueue();
            Thread thread = AWTAccessor.getEventQueueAccessor()
                            .getDispatchThread(queue);
            try {
                /*
                 * Clear EventDispatchThread.doDispatch flag to break message
                 * loop in EventDispatchThread.pumpEventsForFilter()
                 */
                Method stopDispatching = thread.getClass()
                        .getDeclaredMethod("stopDispatching", null);
                stopDispatching.setAccessible(true);
                stopDispatching.invoke(thread, null);

                /*
                 * Post another InvocationEvent that must be handled by another
                 * instance of EDT
                 */
                EventQueue.invokeLater(() -> {
                    passed = true;
                });
            }
            catch (InvocationTargetException | NoSuchMethodException
                   | IllegalAccessException e) {
            }
        });

        // Wait for EDT shutdown
        EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        Thread thread = AWTAccessor.getEventQueueAccessor()
                        .getDispatchThread(queue);
        try {
            thread.join();

            /*
             * Wait for another EDT instance to handle the InvocationEvent
             * and shutdown
             */
            thread = AWTAccessor.getEventQueueAccessor()
                     .getDispatchThread(queue);
            if (thread != null) {
                thread.join();
            }
        }
        catch (InterruptedException e) {
        }

        if (passed) {
            System.out.println("Test PASSED!");
        }
        else {
            throw new RuntimeException("Test FAILED!");
        }
    }
}
