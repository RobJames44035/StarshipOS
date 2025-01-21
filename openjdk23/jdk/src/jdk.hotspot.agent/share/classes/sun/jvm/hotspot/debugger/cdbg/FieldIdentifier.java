/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.cdbg;

/** Abstraction over named fields and indices of arrays. Call
    toString() on a FieldIdentifier to get a printable name for the
    field. */

public interface FieldIdentifier {
  public Type getType();
  public String toString();
}
