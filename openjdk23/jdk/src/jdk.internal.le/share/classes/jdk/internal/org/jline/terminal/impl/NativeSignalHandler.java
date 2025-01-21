/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */
package jdk.internal.org.jline.terminal.impl;

import jdk.internal.org.jline.terminal.Terminal.Signal;
import jdk.internal.org.jline.terminal.Terminal.SignalHandler;

public final class NativeSignalHandler implements SignalHandler {

    public static final NativeSignalHandler SIG_DFL = new NativeSignalHandler();

    public static final NativeSignalHandler SIG_IGN = new NativeSignalHandler();

    private NativeSignalHandler() {}

    public void handle(Signal signal) {
        throw new UnsupportedOperationException();
    }
}
