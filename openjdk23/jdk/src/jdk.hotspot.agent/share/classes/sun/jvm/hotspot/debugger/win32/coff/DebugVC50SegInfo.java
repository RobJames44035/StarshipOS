/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.win32.coff;

public interface DebugVC50SegInfo {
  /** Segment that this structure describes */
  public short getSegment();

  /** Offset in segment where the code starts */
  public int getOffset();

  /** Count of the number of bytes of code in the segment */
  public int getSegmentCodeSize();
}
