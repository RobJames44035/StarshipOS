/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.vectorapi;

import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.VectorSpecies;

/*
 * @test
 * @bug 8267375
 * @requires os.arch == "aarch64" & vm.debug == true & vm.compiler2.enabled
 * @modules jdk.incubator.vector
 * @run main/othervm -XX:CompileCommand=compileonly,compiler.vectorapi.TestVectorInsertByte::* -XX:PrintIdealGraphLevel=3 -XX:PrintIdealGraphFile=TestVectorInsertByte.xml compiler.vectorapi.TestVectorInsertByte
 */

public class TestVectorInsertByte {
    static final VectorSpecies<Byte> SPECIESb = ByteVector.SPECIES_MAX;

    static final int INVOC_COUNT = 50000;
    static final int size = SPECIESb.length();

    static byte[] ab = new byte[size];
    static byte[] rb = new byte[size];

    static void init() {
        for (int i = 0; i < size; i++) {
            ab[i] = (byte) (size - 1 - i);
        }
    }

    static void testByteVectorInsert() {
        ByteVector av = ByteVector.fromArray(SPECIESb, ab, 0);
        av = av.withLane(0, (byte) (0));
        av.intoArray(rb, 0);
    }

    public static void main(String[] args) {
        init();
        for (int ic = 0; ic < INVOC_COUNT; ic++) {
            testByteVectorInsert();
        }
    }
}
