/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.win32.coff;

/** Models debug information in Visual C++ 5.0 format. */

public interface DebugVC50 {
  public int getSubsectionDirectoryOffset();

  public DebugVC50SubsectionDirectory getSubsectionDirectory();
}
