/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package sun.jvm.hotspot.types.basic;

import java.util.*;
import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.types.*;

/** This specialization of BasicType implements the PointerType
    interface and describes all C pointer types. */

public class BasicPointerType extends BasicType implements PointerType {
  private Type targetType;

  public BasicPointerType(BasicTypeDataBase db, String name, Type targetType) {
    super(db, name, null);

    this.targetType = targetType;
  }

  public boolean isPointerType() {
    return true;
  }

  public Type getTargetType() {
    return targetType;
  }
}
