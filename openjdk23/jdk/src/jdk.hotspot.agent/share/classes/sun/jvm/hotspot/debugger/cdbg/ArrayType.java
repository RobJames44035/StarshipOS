/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.cdbg;

public interface ArrayType extends Type {
  public Type getElementType();
  public int  getLength();
}
