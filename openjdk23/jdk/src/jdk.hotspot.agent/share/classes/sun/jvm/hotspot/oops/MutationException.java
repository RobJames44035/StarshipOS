/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.oops;

public class MutationException extends RuntimeException {
  public MutationException() {
    super();
  }

  public MutationException(String detail) {
    super(detail);
  }
}
