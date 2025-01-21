/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package org.openjdk.bench.java.io;

import org.openjdk.jmh.annotations.*;

import java.io.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(2)
@Measurement(iterations = 6, time = 1)
@Warmup(iterations=4, time = 2)
@State(Scope.Thread)
public class DataOutputStreamTest {

    public enum BasicType {CHAR, SHORT, INT, STRING}
    @Param({"CHAR", "SHORT", "INT", "STRING"}) BasicType basicType;

    @Param({"4096"}) int size;
    final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(size);
    File f;
    String outputString;
    FileOutputStream fileOutputStream;
    DataOutput bufferedFileStream, rawFileStream, byteArrayStream;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        f = File.createTempFile("DataOutputStreamTest","out");
        fileOutputStream = new FileOutputStream(f);
        byteArrayStream = new DataOutputStream(byteArrayOutputStream);
        rawFileStream = new DataOutputStream(fileOutputStream);
        bufferedFileStream = new DataOutputStream(new BufferedOutputStream(fileOutputStream));
        outputString = new String(new byte[size]);
    }

    public void writeChars(DataOutput dataOutput)
            throws Exception {
        for (int i = 0; i < size; i += 2) {
            dataOutput.writeChar(i);
        }
    }

    public void writeShorts(DataOutput dataOutput)
            throws Exception {
        for (int i = 0; i < size; i += 2) {
            dataOutput.writeShort(i);
        }
    }

    public void writeInts(DataOutput dataOutput)
            throws Exception {
        for (int i = 0; i < size; i += 4) {
            dataOutput.writeInt(i);
        }
    }

    public void writeString(DataOutput dataOutput)
            throws Exception {
        dataOutput.writeChars(outputString);
    }

    public void write(DataOutput dataOutput)
            throws Exception {
        switch (basicType) {
            case CHAR:
                writeChars(dataOutput);
                break;
            case SHORT:
                writeShorts(dataOutput);
                break;
            case INT:
                writeInts(dataOutput);
                break;
            case STRING:
                writeString(dataOutput);
                break;
        }
    }

    @Benchmark
    public void dataOutputStreamOverByteArray() throws Exception {
        byteArrayOutputStream.reset();
        write(byteArrayStream);
        byteArrayOutputStream.flush();
    }

    @Benchmark
    public void dataOutputStreamOverRawFileStream() throws Exception {
        fileOutputStream.getChannel().position(0);
        write(rawFileStream);
        fileOutputStream.flush();
    }

    @Benchmark
    public void dataOutputStreamOverBufferedFileStream() throws Exception{
        fileOutputStream.getChannel().position(0);
        write(bufferedFileStream);
        fileOutputStream.flush();
    }
}
