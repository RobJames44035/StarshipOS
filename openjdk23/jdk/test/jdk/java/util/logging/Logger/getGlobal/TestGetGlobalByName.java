/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * @test
 * @bug 7184195
 * @summary checks that java.util.logging.Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info() logs without configuration
 * @build TestGetGlobalByName testgetglobal.HandlerImpl testgetglobal.LogManagerImpl1 testgetglobal.LogManagerImpl2 testgetglobal.LogManagerImpl3 testgetglobal.BadLogManagerImpl testgetglobal.DummyLogManagerImpl
 * @run main/othervm TestGetGlobalByName
 * @run main/othervm -Djava.util.logging.manager=testgetglobal.LogManagerImpl1 TestGetGlobalByName
 * @run main/othervm -Djava.util.logging.manager=testgetglobal.LogManagerImpl2 TestGetGlobalByName
 * @run main/othervm -Djava.util.logging.manager=testgetglobal.LogManagerImpl3 TestGetGlobalByName
 * @run main/othervm -Djava.util.logging.manager=testgetglobal.BadLogManagerImpl TestGetGlobalByName
 * @run main/othervm -Djava.util.logging.manager=testgetglobal.DummyLogManagerImpl TestGetGlobalByName
 * @author danielfuchs
 */
public class TestGetGlobalByName {

    static final String[] messages = {
        "1. This message should not appear on the console.",
        "2. This message should appear on the console.",
        "3. This message should now appear on the console too."
    };

    static {
        System.setProperty("java.util.logging.config.file",
            System.getProperty("test.src", ".") + java.io.File.separator + "logging.properties");
    }

    public static void main(String... args) {

        Logger.global.info(messages[0]); // at this point LogManager is not
             // initialized yet, so this message should not appear.
        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info(messages[1]); // calling getLogger() will
             // initialize the LogManager - and thus this message should appear.
        Logger.global.info(messages[2]); // Now that the LogManager is
             // initialized, this message should appear too.

        final List<String> expected = Arrays.asList(Arrays.copyOfRange(messages, 1, messages.length));
        if (!testgetglobal.HandlerImpl.received.equals(expected)) {
            throw new Error("Unexpected message list: "+testgetglobal.HandlerImpl.received+" vs "+ expected);
        }
    }
}
