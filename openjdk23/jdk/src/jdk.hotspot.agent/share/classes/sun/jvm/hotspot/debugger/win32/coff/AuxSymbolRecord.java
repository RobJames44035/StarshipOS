/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.win32.coff;

public interface AuxSymbolRecord {
  public static final int FUNCTION_DEFINITION   = 0;
  public static final int BF_EF_RECORD          = 1;
  public static final int WEAK_EXTERNAL         = 2;
  public static final int FILE                  = 3;
  public static final int SECTION_DEFINITION    = 4;

  /** Returns {@link #FUNCTION_DEFINITION}, {@link #BF_EF_RECORD},
      {@link #WEAK_EXTERNAL}, {@link #FILE}, or {@link
      #SECTION_DEFINITION}, indicating the concrete subtype of this
      interface. */
  public int getType();
}
