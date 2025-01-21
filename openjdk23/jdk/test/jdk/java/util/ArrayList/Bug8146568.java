/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 8146568
 * @summary repro for: NegativeArraySizeException in ArrayList.grow(int)
 * @run main/manual/othervm -Xmx17g Bug8146568
 * @requires os.maxMemory>17G
 */

public class Bug8146568 {
    public static void main(String[] args) {
        int size = Integer.MAX_VALUE - 2;
        java.util.ArrayList<Object> huge = new java.util.ArrayList<>(size);
        for (int i = 0; i < size; i++)
            huge.add(null);
        try {
            huge.addAll(huge);
            throw new Error("expected OutOfMemoryError not thrown");
        } catch (OutOfMemoryError success) {}
    }
}
