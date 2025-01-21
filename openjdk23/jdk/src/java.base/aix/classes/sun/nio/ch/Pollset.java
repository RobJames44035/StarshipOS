/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package sun.nio.ch;

import java.io.IOException;
import jdk.internal.misc.Unsafe;

public class Pollset {

    private static final Unsafe unsafe = Unsafe.getUnsafe();

   /**
     * struct pollfd {
     *     int fd;
     *     short events;
     *     short revents;
     * }
     */
    public static final int SIZEOF_POLLFD    = eventSize();
    public static final int OFFSETOF_EVENTS  = eventsOffset();
    public static final int OFFSETOF_REVENTS = reventsOffset();
    public static final int OFFSETOF_FD      = fdOffset();

    // opcodes
    public static final int PS_ADD     = 0x0;
    public static final int PS_MOD     = 0x1;
    public static final int PS_DELETE  = 0x2;

    // event
    public static final int PS_POLLPRI = 0x4;

    // revent errcodes
    public static final char PS_POLLNVAL = 0x8000;
    public static final char PS_POLLERR  = 0x4000;

    public static final int PS_NO_TIMEOUT = -1;

    /**
     * Allocates a poll array to handle up to {@code count} events.
     */
    public static long allocatePollArray(int count) {
        return unsafe.allocateMemory(count * SIZEOF_POLLFD);
    }

    /**
     * Free a poll array
     */
    public static void freePollArray(long address) {
        unsafe.freeMemory(address);
    }

    /**
     * Returns event[i];
     */
    public static long getEvent(long address, int i) {
        return address + (SIZEOF_POLLFD * i);
    }

    /**
     * Returns event->fd
     */
    public static int getDescriptor(long eventAddress) {
        return unsafe.getInt(eventAddress + OFFSETOF_FD);
    }

    /**
     * Returns event->events
     */
    public static int getEvents(long eventAddress) {
        return unsafe.getChar(eventAddress + OFFSETOF_EVENTS);
    }

    /**
     * Returns event->revents
     */
    public static char getRevents(long eventAddress) {
        return unsafe.getChar(eventAddress + OFFSETOF_REVENTS);
    }

    public static boolean isReventsError(long eventAddress) {
        char revents = getRevents(eventAddress);
        return (revents & PS_POLLNVAL) != 0 || (revents & PS_POLLERR) != 0;
    }

    // -- Native methods --
    public static native int pollsetCreate() throws IOException;
    public static native int pollsetCtl(int pollset, int opcode, int fd, int events);
    public static native int pollsetPoll(int pollset, long pollAddress, int numfds, int timeout)
        throws IOException;
    public static native void pollsetDestroy(int pollset);
    public static native void init();
    public static native int eventSize();
    public static native int eventsOffset();
    public static native int reventsOffset();
    public static native int fdOffset();
    public static native void socketpair(int[] sv) throws IOException;
    public static native void interrupt(int fd) throws IOException;
    public static native void drain1(int fd) throws IOException;
    public static native void close0(int fd);
}
