/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.intrinsics;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class TestFloatClassCheck {
    RandomGenerator rng;
    int BUFFER_SIZE = 1024;
    float[] inputs;
    boolean[] outputs;

    public TestFloatClassCheck() {
        outputs = new boolean[BUFFER_SIZE];
        inputs = new float[BUFFER_SIZE];
        RandomGenerator rng = RandomGeneratorFactory.getDefault().create(0);
        float input;
        for (int i = 0; i < BUFFER_SIZE; i++) {
            if (i % 5 == 0) {
                input = (i%2 == 0) ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
            }
            else if (i % 3 == 0) input = Float.NaN;
            else input = rng.nextFloat();
            inputs[i] = input;
        }
    }

    public void checkResult(String method) {
        for (int i=0; i < BUFFER_SIZE; i++) {
            boolean expected = floatClassCheck(inputs[i], method);
            if (expected != outputs[i]) {
                String errorMsg = "Correctness check failed for Float." + method +
                "() for input = " + inputs[i];
                throw new RuntimeException(errorMsg);
            }
        }
    }

    public boolean floatClassCheck(float f, String method) {
        int infBits = Float.floatToRawIntBits(Float.POSITIVE_INFINITY);
        int bits =  Float.floatToRawIntBits(f);
        bits = bits & Integer.MAX_VALUE;
        switch (method) {
            case "isFinite": return (bits < infBits);
            case "isInfinite": return (bits == infBits);
            case "isNaN": return (bits > infBits);
            default: throw new IllegalArgumentException("incorrect method for Float");
        }
    }

}
