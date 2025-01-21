/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package org.openjdk.bench.java.net;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark DatagramChannel send/receive.
 */

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
public class DatagramChannelSendReceive {

    private int counter = 0;

    private ByteBuffer buf;
    private DatagramChannel channel1, channel2, connectedWriteChannel,
            connectedReadChannel, multipleReceiveChannel, multipleSendChannel;
    private DatagramChannel[] dca;

    @Param({"128", "32768"})
    public int size;
    @Param({"4"})
    public int channelCount;
    @Param({"true"})
    public boolean useDirectBuffer;

    @Setup
    public void setUp() throws IOException {
        buf = (useDirectBuffer) ? ByteBuffer.allocateDirect(size) :
                ByteBuffer.allocate(size);
        buf.clear();

        InetSocketAddress addr =
                new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);

        // single send - same socket; different sockets
        channel1 = DatagramChannel.open().bind(addr);
        channel2 = DatagramChannel.open().bind(addr);

        // connected read / write
        connectedWriteChannel = DatagramChannel.open().bind(addr);
        connectedReadChannel = DatagramChannel.open().bind(addr);
        connectedWriteChannel.connect(connectedReadChannel.getLocalAddress());
        connectedReadChannel.connect(connectedWriteChannel.getLocalAddress());

        // multiple senders / multiple receivers
        dca = new DatagramChannel[channelCount];
        for (int i = 0; i < dca.length; i++) {
            dca[i] = DatagramChannel.open().bind(addr);
        }
        multipleReceiveChannel = DatagramChannel.open().bind(addr);
        multipleSendChannel = DatagramChannel.open().bind(addr);
    }

    // same sender receiver
    @Benchmark
    public void sendReceiveSingleSocket() throws IOException {
        buf.clear();
        channel1.send(buf, channel1.getLocalAddress());
        buf.clear();
        channel1.receive(buf);
    }

    // single sender, single receiver
    @Benchmark
    public void sendReceive() throws IOException {
        buf.clear();
        channel1.send(buf, channel2.getLocalAddress());
        buf.clear();
        channel2.receive(buf);
    }

    // connected sender receiver
    @Benchmark
    public void sendReceiveConnected() throws IOException {
        buf.clear();
        connectedWriteChannel.write(buf);
        buf.clear();
        connectedReadChannel.read(buf);
    }

    // multiple senders, single receiver
    @Benchmark
    public void sendMultiple() throws IOException {
        int i = counter;
        buf.clear();
        dca[i].send(buf, multipleReceiveChannel.getLocalAddress());
        buf.clear();
        multipleReceiveChannel.receive(buf);
        counter = ++i % dca.length;
    }

    // single sender, multiple receivers
    @Benchmark
    public void receiveMultiple() throws IOException {
        int i = counter;
        buf.clear();
        multipleSendChannel.send(buf, dca[i].getLocalAddress());
        buf.clear();
        dca[i].receive(buf);
        counter = ++i % dca.length;
    }

    @TearDown
    public void tearDown() throws IOException {
        channel1.close();
        channel2.close();
        connectedWriteChannel.close();
        connectedReadChannel.close();
        multipleReceiveChannel.close();
        multipleSendChannel.close();
        for (DatagramChannel dc : dca) {
            dc.close();
        }
    }
}
