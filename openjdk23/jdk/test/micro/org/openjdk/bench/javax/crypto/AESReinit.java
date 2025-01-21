/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package org.openjdk.bench.javax.crypto;

import org.openjdk.jmh.annotations.*;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3, jvmArgs = {"-Xms1g", "-Xmx1g"})
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class AESReinit {

    private Cipher cipher;
    private Random random;

    byte[] key = new byte[16];
    byte[] iv  = new byte[16];

    @Setup
    public void prepare() throws Exception {
        random = new Random();
        cipher = Cipher.getInstance("AES/GCM/NoPadding");
        key = new byte[16];
        iv = new byte[16];
    }

    @Benchmark
    public void test() throws Exception {
        random.nextBytes(key);
        random.nextBytes(iv);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        GCMParameterSpec param = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);
    }

}
