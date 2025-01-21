/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.nio;

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
import org.openjdk.jmh.annotations.Warmup;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.concurrent.TimeUnit;

/**
 * This benchmark tests the encode/decode loops on different Charsets. It was created from an adhoc benchmark addressing
 * a performance issue which in the end boiled down to the encode/decode loops. This is the reason for the values on the
 * char and byte arrays.
 */
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Fork(3)
public class CharsetEncodeDecode {

    private byte[] BYTES;
    private char[] CHARS;

    private CharsetEncoder encoder;
    private CharsetDecoder decoder;

    @Param({"UTF-8", "BIG5", "ISO-8859-15", "ISO-8859-1", "ASCII", "UTF-16"})
    private String type;

    @Param("16384")
    private int size;

    @Setup
    public void prepare() {
        BYTES = new byte[size];
        CHARS = new char[size];
        for (int i = 0; i < size; ++i) {
            int val = 48 + (i % 16);
            BYTES[i] = (byte) val;
            CHARS[i] = (char) val;
        }

        encoder = Charset.forName(type).newEncoder();
        decoder = Charset.forName(type).newDecoder();
    }

    @Benchmark
    public ByteBuffer encode() throws CharacterCodingException {
        CharBuffer charBuffer = CharBuffer.wrap(CHARS);
        return encoder.encode(charBuffer);
    }

    @Benchmark
    public CharBuffer decode() throws CharacterCodingException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(BYTES);
        return decoder.decode(byteBuffer);
    }

}
