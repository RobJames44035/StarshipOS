/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @run junit/othervm --enable-native-access=ALL-UNNAMED TestSymbolLookupFindOrThrow
 */

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

final class TestSymbolLookupFindOrThrow {

    static {
        System.loadLibrary("Foo");
    }

    @Test
    void findOrThrow() {
        MemorySegment symbol = SymbolLookup.loaderLookup().findOrThrow("foo");
        Assertions.assertNotEquals(0, symbol.address());
    }

    @Test
    void findOrThrowNotFound() {
        assertThrows(NoSuchElementException.class, () ->
                SymbolLookup.loaderLookup().findOrThrow("bar"));
    }

}
