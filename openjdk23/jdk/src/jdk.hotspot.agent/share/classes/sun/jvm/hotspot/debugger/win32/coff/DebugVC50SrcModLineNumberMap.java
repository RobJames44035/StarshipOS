/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.win32.coff;

/** Supplies line number/address mapping for a given source file. */

public interface DebugVC50SrcModLineNumberMap {
  /** Segment index for this table. */
  public int getSegment();

  /** Number of source line pairs. */
  public int getNumSourceLinePairs();

  /** Get the <i>i</i>th (i..getNumSourceLinePairs() - 1) offset
      within the code segment of the start of the line in the parallel
      line number array. */
  public int getCodeOffset(int i);

  /** Get the <i>i</i>th (i..getNumSourceLinePairs() - 1) line
      number. */
  public int getLineNumber(int i);
}
