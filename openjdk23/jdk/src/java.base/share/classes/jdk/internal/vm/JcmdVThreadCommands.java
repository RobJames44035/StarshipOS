/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package jdk.internal.vm;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.IntStream;
import jdk.internal.access.JavaLangAccess;
import jdk.internal.access.SharedSecrets;
import sun.nio.ch.Poller;

/**
 * The implementation for the jcmd Thread.vthread_* diagnostic commands. These methods are
 * called from the "Attach Listener" thread.
 */
public class JcmdVThreadCommands {
    private static final JavaLangAccess JLA = SharedSecrets.getJavaLangAccess();

    private JcmdVThreadCommands() { }

    /**
     * Invoked by the VM to print the virtual scheduler to a byte[].
     */
    private static byte[] printScheduler() {
        StringBuilder sb = new StringBuilder();

        // virtual thread scheduler
        sb.append(JLA.virtualThreadDefaultScheduler())
          .append(System.lineSeparator());

        // break
        sb.append(System.lineSeparator());

        // delayed task schedulers
        sb.append("Delayed task schedulers:").append(System.lineSeparator());
        var delayedTaskSchedulers = JLA.virtualThreadDelayedTaskSchedulers().toList();
        IntStream.range(0, delayedTaskSchedulers.size())
                .forEach(i -> sb.append('[')
                                .append(i)
                                .append("] ")
                                .append(delayedTaskSchedulers.get(i))
                                .append(System.lineSeparator()));

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Invoked by the VM to print the I/O pollers to a byte[].
     */
    private static byte[] printPollers() {
        StringBuilder sb = new StringBuilder();

        Poller masterPoller = Poller.masterPoller();
        List<Poller> readPollers = Poller.readPollers();
        List<Poller> writePollers = Poller.writePollers();

        if (masterPoller != null) {
            sb.append("Master I/O poller:")
              .append(System.lineSeparator())
              .append(masterPoller)
              .append(System.lineSeparator());

            // break
            sb.append(System.lineSeparator());
        }

        sb.append("Read I/O pollers:");
        sb.append(System.lineSeparator());
        IntStream.range(0, readPollers.size())
                .forEach(i -> sb.append('[')
                                .append(i)
                                .append("] ")
                                .append(readPollers.get(i))
                                .append(System.lineSeparator()));

        // break
        sb.append(System.lineSeparator());

        sb.append("Write I/O pollers:");
        sb.append(System.lineSeparator());
        IntStream.range(0, writePollers.size())
                .forEach(i -> sb.append('[')
                                .append(i)
                                .append("] ")
                                .append(writePollers.get(i))
                                .append(System.lineSeparator()));

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }
}
