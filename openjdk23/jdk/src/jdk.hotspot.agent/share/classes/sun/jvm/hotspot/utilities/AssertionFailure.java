/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.utilities;

public class AssertionFailure extends RuntimeException {
  public AssertionFailure() {
    super();
  }

  public AssertionFailure(String message) {
    super(message);
  }
}
