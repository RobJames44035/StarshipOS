/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * @test
 * @bug 8152515
 * @summary Checks that LinkageError are ignored when closing handlers
 *          during Shutdown.
 * @build LinkageErrorTest
 * @run main/othervm LinkageErrorTest
 */

public class LinkageErrorTest {

    public static class TestHandler extends Handler {

        private volatile boolean closed;
        public TestHandler() {
            INSTANCES.add(this);
        }

        @Override
        public void publish(LogRecord record) {
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
            closed = true;
            try {
                System.out.println(INSTANCES);
            } catch (Throwable t) {
                // ignore
            }
            throw new LinkageError();
        }

        @Override
        public String toString() {
            return super.toString() + "{closed=" + closed + '}';
        }

        private static final CopyOnWriteArrayList<Handler> INSTANCES
                = new CopyOnWriteArrayList<>();
    }

    private static final Logger LOGGER = Logger.getLogger("test");
    private static final Logger GLOBAL = Logger.getGlobal();

    public static void main(String[] args) {
        LOGGER.addHandler(new TestHandler());
        LOGGER.addHandler(new TestHandler());
        GLOBAL.addHandler(new TestHandler());
    }
}
