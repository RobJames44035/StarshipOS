/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 8148174
 * @summary repro for: NegativeArraySizeException in Vector.grow(int)
 * @run main/manual/othervm -Xmx17g Bug8148174
 * @requires os.maxMemory>17G
 */

public class Bug8148174 {
    public static void main(String[] args) {
        int size = Integer.MAX_VALUE - 2;
        java.util.Vector<Object> huge = new java.util.Vector<>(size);
        for (int i = 0; i < size; i++)
            huge.add(null);
        try {
            huge.addAll(huge);
            throw new Error("expected OutOfMemoryError not thrown");
        } catch (OutOfMemoryError success) {}
    }
}
