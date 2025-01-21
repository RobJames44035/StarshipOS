/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.win32.coff;

public interface DebugVC50SSModule extends DebugVC50Subsection {
  public short getOverlayNumber();
  /** Index into sstLibraries subsection if this module was linked
      from a library. */
  public short getLibrariesIndex();

  /** Count of the number of code segments this module contributes to.
      This is the length of the SegInfo array, below. */
  public short getNumCodeSegments();

  /** Debugging style for this module. Currently only "CV" is defined.
      A module can have only one debugging style. If a module contains
      debugging information in an unrecognized style, the information
      will be discarded. */
  public short getDebuggingStyle();

  /** Fetch description of segment to which this module contributes
      code (0..getNumCodeSegments - 1) */
  public DebugVC50SegInfo getSegInfo(int i);

  /** Name of the module */
  public String getName();
}
