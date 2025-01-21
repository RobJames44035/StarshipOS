/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package jdk.jfr.internal.periodic;

import jdk.internal.event.Event;
import jdk.jfr.EventType;
import jdk.jfr.internal.MetadataRepository;
import jdk.jfr.internal.PlatformEventType;
import jdk.jfr.internal.PrivateAccess;
/**
 * Class for periodic Java events.
 */
final class JavaEventTask extends EventTask {
    private final Runnable runnable;

    public JavaEventTask(Class<? extends Event> eventClass, Runnable runnable) {
        super(toPlatformEventType(eventClass), new LookupKey(runnable));
        this.runnable = runnable;
        if (getEventType().isJVM()) {
            throw new InternalError("Must not be a JVM event");
        }
    }

    private static PlatformEventType toPlatformEventType(Class<? extends Event> eventClass) {
        EventType eventType = MetadataRepository.getInstance().getEventType(eventClass);
        return PrivateAccess.getInstance().getPlatformEventType(eventType);
    }

    @Override
    public void execute(long timestamp, PeriodicType periodicType) {
        runnable.run();
    }
}
