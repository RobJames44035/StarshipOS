/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package sun.jvm.hotspot.asm;

public class DummySymbolFinder implements SymbolFinder {
   public String getSymbolFor(long address) {
      return "0x" + Long.toHexString(address);
   }
}
