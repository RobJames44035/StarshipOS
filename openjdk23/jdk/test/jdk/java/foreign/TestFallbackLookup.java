/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @run testng/othervm -Dos.name=Windows --enable-native-access=ALL-UNNAMED TestFallbackLookup
 */

import org.testng.annotations.*;
import static org.testng.Assert.*;

import java.lang.foreign.Linker;

public class TestFallbackLookup {
    @Test
    void testBadSystemLookupRequest() {
        // we request a Linker, forcing OS name to be "Windows". This should trigger an exception when
        // attempting to load a non-existent ucrtbase.dll. Make sure that no error is generated at this stage.
        assertTrue(Linker.nativeLinker().defaultLookup().find("nonExistentSymbol").isEmpty());
    }
}
