/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package jdk.internal.org.jline.terminal.impl;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.function.Function;

import jdk.internal.org.jline.terminal.Attributes;
import jdk.internal.org.jline.terminal.Size;
import jdk.internal.org.jline.terminal.Terminal;
import jdk.internal.org.jline.terminal.TerminalBuilder;
import jdk.internal.org.jline.terminal.spi.SystemStream;
import jdk.internal.org.jline.terminal.spi.TerminalProvider;

public class DumbTerminalProvider implements TerminalProvider {

    @Override
    public String name() {
        return TerminalBuilder.PROP_PROVIDER_DUMB;
    }

    @Override
    public Terminal sysTerminal(
            String name,
            String type,
            boolean ansiPassThrough,
            Charset encoding,
            boolean nativeSignals,
            Terminal.SignalHandler signalHandler,
            boolean paused,
            SystemStream systemStream,
            Function<InputStream, InputStream> inputStreamWrapper)
            throws IOException {
        return new DumbTerminal(
                this,
                systemStream,
                name,
                type,
                new FileInputStream(FileDescriptor.in),
                new FileOutputStream(systemStream == SystemStream.Error ? FileDescriptor.err : FileDescriptor.out),
                encoding,
                signalHandler,
                inputStreamWrapper);
    }

    @Override
    public Terminal newTerminal(
            String name,
            String type,
            InputStream masterInput,
            OutputStream masterOutput,
            Charset encoding,
            Terminal.SignalHandler signalHandler,
            boolean paused,
            Attributes attributes,
            Size size)
            throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSystemStream(SystemStream stream) {
        return false;
    }

    @Override
    public String systemStreamName(SystemStream stream) {
        return null;
    }

    @Override
    public int systemStreamWidth(SystemStream stream) {
        return 0;
    }

    @Override
    public String toString() {
        return "TerminalProvider[" + name() + "]";
    }
}
