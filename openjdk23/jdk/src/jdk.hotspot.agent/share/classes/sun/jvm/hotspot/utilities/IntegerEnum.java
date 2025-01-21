/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.utilities;

/** A class enabling simple construction of type-safe integer
    enumerations */

public class IntegerEnum {
  private int value;

  /** Constructor is protected because it will only be used by the
      subclass */
  protected IntegerEnum(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
