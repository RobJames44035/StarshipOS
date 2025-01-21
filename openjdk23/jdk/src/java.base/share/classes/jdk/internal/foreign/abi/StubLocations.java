/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.foreign.abi;

// must keep in sync with StubLocations in VM code
public enum StubLocations {
    TARGET_ADDRESS,
    RETURN_BUFFER,
    CAPTURED_STATE_BUFFER;

    public VMStorage storage(byte type) {
        return new VMStorage(type, (short) 8, ordinal());
    }
}
