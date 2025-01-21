/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.cdbg;

public interface RefType extends Type {
  public Type getTargetType();
}
