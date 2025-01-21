/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.debugger;

public class NotInHeapException extends AddressException {
  public NotInHeapException(long addr) {
    super(addr);
  }

  public NotInHeapException(String detail, long addr) {
    super(detail, addr);
  }
}
