/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package lookup;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

public class Lookup {
    static {
        System.loadLibrary("Foo");
    }

    public static MemorySegment fooSymbol() {
        return SymbolLookup.loaderLookup().find("foo").get();
    }
}
