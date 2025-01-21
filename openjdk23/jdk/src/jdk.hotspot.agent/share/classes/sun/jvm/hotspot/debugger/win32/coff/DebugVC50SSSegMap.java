/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.win32.coff;

public interface DebugVC50SSSegMap extends DebugVC50Subsection {
  /** Count of the number of segment descriptors in table. */
  public short getNumSegDesc();

  /** The total number of logical segments. All group descriptors
      follow the logical segment descriptors. The number of group
      descriptors is given by <i>cSeg - cSegLog</i>. */
  public short getNumLogicalSegDesc();

  /** Get the <i>i</i>th segment descriptor (0..getNumSegDesc() -
      1). */
  public DebugVC50SegDesc getSegDesc(int i);
}
