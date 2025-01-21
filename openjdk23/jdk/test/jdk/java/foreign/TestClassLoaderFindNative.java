/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @run testng/othervm --enable-native-access=ALL-UNNAMED TestClassLoaderFindNative
 */

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.nio.ByteOrder;
import org.testng.annotations.Test;

import static java.lang.foreign.ValueLayout.JAVA_INT;
import static org.testng.Assert.*;

// FYI this test is run on 64-bit platforms only for now,
// since the windows 32-bit linker fails and there
// is some fallback behaviour to use the 64-bit linker,
// where cygwin gets in the way and we accidentally pick up its
// link.exe
public class TestClassLoaderFindNative {
    static {
        System.loadLibrary("LookupTest");
    }

    @Test
    public void testSimpleLookup() {
        assertFalse(SymbolLookup.loaderLookup().find("f").isEmpty());
    }

    @Test
    public void testInvalidSymbolLookup() {
        assertTrue(SymbolLookup.loaderLookup().find("nonExistent").isEmpty());
    }

    @Test
    public void testVariableSymbolLookup() {
        MemorySegment segment = SymbolLookup.loaderLookup().find("c").get().reinterpret(4);
        assertEquals(segment.get(JAVA_INT, 0), 42);
    }

    @Test
    void testLoadLibraryBadLookupName() {
        assertTrue(SymbolLookup.loaderLookup().find("f\u0000foobar").isEmpty());
    }
}
