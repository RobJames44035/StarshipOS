/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package jdk.internal.foreign.abi;

import jdk.internal.foreign.MemorySessionImpl;
import jdk.internal.foreign.Utils;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public final class UpcallStubs {

    private UpcallStubs() {
    }

    private static void freeUpcallStub(long stubAddress) {
        if (!freeUpcallStub0(stubAddress)) {
            throw new IllegalStateException("Not a stub address: " + Utils.toHexString(stubAddress));
        }
    }

    // natives

    // returns true if the stub was found (and freed)
    private static native boolean freeUpcallStub0(long addr);

    private static native void registerNatives();
    static {
        registerNatives();
    }

    @SuppressWarnings("restricted")
    static MemorySegment makeUpcall(long entry, Arena arena) {
        MemorySessionImpl.toMemorySession(arena).addOrCleanupIfFail(new MemorySessionImpl.ResourceList.ResourceCleanup() {
            @Override
            public void cleanup() {
                freeUpcallStub(entry);
            }
        });
        return MemorySegment.ofAddress(entry).reinterpret(arena, null); // restricted
    }
}
