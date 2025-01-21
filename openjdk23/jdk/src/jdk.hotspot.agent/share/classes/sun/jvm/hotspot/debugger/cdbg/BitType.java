/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.cdbg;

public interface BitType extends IntType {
  /** Size in bits of this type */
  public int getSizeInBits();

  /** Offset from the least-significant bit (LSB) of the LSB of this
      type */
  public int getOffset();
}
