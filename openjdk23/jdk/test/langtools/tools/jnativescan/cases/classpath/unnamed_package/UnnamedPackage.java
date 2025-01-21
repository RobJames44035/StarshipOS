/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.lang.foreign.*;

public class UnnamedPackage {
    public static void main(String[] args) {
        MemorySegment.ofAddress(1234).reinterpret(10);
    }

    private static native void m();
}
