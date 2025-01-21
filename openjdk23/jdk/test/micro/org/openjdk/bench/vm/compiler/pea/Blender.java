/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package org.openjdk.bench.vm.compiler.pea;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Fork(value = 3)
public class Blender {
    @Param({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    int iteration;

    private static class Color {
        double r, g, b;

        private Color(double r, double g, double b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public static Color color() {
            return new Color(0, 0, 0);
        }

        public void add(Color other) {
            r += other.r;
            g += other.g;
            b += other.b;
        }

        public void add(double nr, double ng, double nb) {
            r += nr;
            g += ng;
            b += nb;
        }

        public void multiply(double factor) {
            r *= factor;
            g *= factor;
            b *= factor;
        }
    }

    private static final Color[][][] colors = new Color[100][100][100];

    @Benchmark
    public void initialize() {
        Color id = new Color(iteration / 20, 0, 1);
        for (int x = 0; x < colors.length; x++) {
            Color[][] plane = colors[x];
            for (int y = 0; y < plane.length; y++) {
                Color[] row = plane[y];
                for (int z = 0; z < row.length; z++) {
                    Color color = new Color(x, y, z);
                    color.add(id);
                    if ((color.r + color.g + color.b) % 42 == 0) {
                         // PEA only allocates a color object here
                         row[z] = color;
                    } else {
                         // Here the color object is not allocated at all
                    }
                }
            }
        }
    }
}

