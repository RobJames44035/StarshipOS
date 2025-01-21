/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.win32.coff;

public interface DataDirectory {
  /** The relative virtual address of the table. The RVA is the
      address of the table, when loaded, relative to the base address
      of the image. */
  public int getRVA();

  /** The size in bytes of this directory. */
  public int getSize();
}
