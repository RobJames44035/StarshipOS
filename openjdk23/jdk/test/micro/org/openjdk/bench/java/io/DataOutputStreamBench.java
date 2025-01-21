/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package org.openjdk.bench.java.io;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(2)
@Measurement(iterations = 6, time = 1)
@Warmup(iterations = 4, time = 2)
@State(Scope.Thread)
public class DataOutputStreamBench {

    @Param({"ascii", "utf8_2_bytes", "utf8_3_bytes", "emoji"})
    public String charType;

    ByteArrayOutputStream bytesOutput;
    DataOutputStream dataOutput;
    ObjectOutputStream objectOutput;
    String[] strings;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        byte[] bytes = HexFormat.of().parseHex(
                switch (charType) {
                    case "ascii"        -> "78";
                    case "utf8_2_bytes" -> "c2a9";
                    case "utf8_3_bytes" -> "e6b8a9";
                    case "emoji"        -> "e29da3efb88f";
                    default -> throw new IllegalArgumentException("bad charType: " + charType);
                }
        );
        String s = new String(bytes, 0, bytes.length, StandardCharsets.UTF_8);
        strings = new String[128];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = "A".repeat(i).concat(s.repeat(i));
        }

        bytesOutput = new ByteArrayOutputStream(1024 * 64);
        dataOutput = new DataOutputStream(bytesOutput);
        objectOutput = new ObjectOutputStream(bytesOutput);
    }

    @Benchmark
    public void dataOutwriteUTF(Blackhole bh) throws Exception {
        bytesOutput.reset();
        for (var s : strings) {
            dataOutput.writeUTF(s);
        }
        dataOutput.flush();
        bh.consume(bytesOutput.size());
    }

    @Benchmark
    public void objectWriteUTF(Blackhole bh) throws Exception {
        bytesOutput.reset();
        for (var s : strings) {
            objectOutput.writeUTF(s);
        }
        objectOutput.flush();
        bh.consume(bytesOutput.size());
    }
}
