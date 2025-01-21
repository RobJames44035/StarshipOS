/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.win32.coff;

/** Thrown from accessors of numeric leaves if the type of the numeric
    leaf does not match the return type of the specified accessor (for
    example, a floating-point numeric leaf accessed as an int). */

public class DebugVC50WrongNumericTypeException extends RuntimeException {
  public DebugVC50WrongNumericTypeException() {
    super();
  }

  public DebugVC50WrongNumericTypeException(String message) {
    super(message);
  }
}
