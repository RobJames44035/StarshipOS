/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package testgetglobal;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;

/**
 *
 * @author danielfuchs
 */
public class HandlerImpl extends ConsoleHandler {

    public static final List<String> received = new CopyOnWriteArrayList<>();

    public HandlerImpl() {
    }

    @Override
    public void publish(LogRecord record) {
        received.add(record.getMessage());
        super.publish(record);
    }
}
