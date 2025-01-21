/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.event.runtime;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.Platform;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 *
 * @run main/othervm jdk.jfr.event.runtime.TestNetworkUtilizationEvent
 */
public class TestNetworkUtilizationEvent {

    private static final long packetSendCount = 100;

    public static void main(String[] args) throws Throwable {

        Recording recording = new Recording();
        recording.enable(EventNames.NetworkUtilization).with("period", "endChunk");
        recording.start();

        DatagramSocket socket = new DatagramSocket();
        String msg = "hello!";
        byte[] buf = msg.getBytes();
        forceEndChunk();
        // Send a few packets to the loopback address
        DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getLoopbackAddress(), 12345);
        for (int i = 0; i < packetSendCount; ++i) {
            socket.send(packet);
        }
        forceEndChunk();
        socket.close();
        recording.stop();

        Set<String> networkInterfaces = new HashSet<>();
        List<RecordedEvent> events = Events.fromRecording(recording);
        for (RecordedEvent event : events) {
            System.out.println(event);
            Events.assertField(event, "writeRate").atLeast(0L).atMost(1000L * Integer.MAX_VALUE);
            Events.assertField(event, "readRate").atLeast(0L).atMost(1000L * Integer.MAX_VALUE);
            Events.assertField(event, "networkInterface").notNull();
            if (event.getLong("writeRate") > 0) {
                networkInterfaces.add(event.getString("networkInterface"));
            }
        }
        // Windows does not track statistics for the loopback
        // interface
        if (!Platform.isWindows()) {
            Asserts.assertGreaterThanOrEqual(networkInterfaces.size(), 1);
        }
    }

    private static void forceEndChunk() {
       try(Recording r = new Recording()) {
           r.start();
           r.stop();
       }
    }
}
