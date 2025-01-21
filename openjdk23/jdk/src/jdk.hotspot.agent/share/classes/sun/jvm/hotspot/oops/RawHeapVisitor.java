/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package sun.jvm.hotspot.oops;

import sun.jvm.hotspot.runtime.AddressVisitor;

public interface RawHeapVisitor extends AddressVisitor {
  /** This is called at the beginning of the iteration to provide the
      RawHeapVisitor with information about the amount of memory which
      will be traversed (for example, for displaying a progress bar) */
  public void prologue(long usedSize);

  /** This is called after the traversal is complete */
  public void epilogue();
}
