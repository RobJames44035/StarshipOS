/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.debugger;

public class UnmappedAddressException extends AddressException {
  public UnmappedAddressException(long addr) {
    super(addr);
  }

  public UnmappedAddressException(String detail, long addr) {
    super(detail, addr);
  }
}
