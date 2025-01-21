/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.types;

public class WrongTypeException extends RuntimeException {
  public WrongTypeException() {
    super();
  }

  public WrongTypeException(String detail) {
    super(detail);
  }
}
