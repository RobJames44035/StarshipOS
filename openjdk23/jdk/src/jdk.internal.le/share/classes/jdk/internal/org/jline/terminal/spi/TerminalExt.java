/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package jdk.internal.org.jline.terminal.spi;

import jdk.internal.org.jline.terminal.Terminal;

/**
 * The {@code TerminalExt} interface is implemented by {@code Terminal}s
 * and provides access to the Terminal's internals.
 */
public interface TerminalExt extends Terminal {

    /**
     * Returns the {@code TerminalProvider} that created this terminal
     * or {@code null} if the terminal was created with no provider.
     */
    TerminalProvider getProvider();

    /**
     * The underlying system stream, may be {@link SystemStream#Output},
     * {@link SystemStream#Error}, or {@code null} if this terminal is not bound
     * to a system stream.
     */
    SystemStream getSystemStream();
}
