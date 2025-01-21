/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package sun.jvm.hotspot.gc.shared;

//These definitions should be kept in sync with the definitions in the HotSpot code.

public enum GCWhen {
  BeforeGC ("Before GC"),
  AfterGC ("After GC"),
  GCWhenEndSentinel ("GCWhenEndSentinel");

  private final String value;

  GCWhen(String val) {
    this.value = val;
  }
  public String value() {
    return value;
  }
}



