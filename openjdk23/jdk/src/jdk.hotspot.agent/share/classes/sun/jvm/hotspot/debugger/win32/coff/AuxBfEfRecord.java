/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.win32.coff;

public interface AuxBfEfRecord extends AuxSymbolRecord {
  /** Actual ordinal line number (1, 2, 3, etc.) within source file,
      corresponding to the .bf or .ef record. */
  public short getLineNumber();

  /** Symbol-table index of the next .bf symbol record. If the
      function is the last in the symbol table, this field is set to
      zero. Not used for .ef records. */
  public int getPointerToNextFunction();
}
