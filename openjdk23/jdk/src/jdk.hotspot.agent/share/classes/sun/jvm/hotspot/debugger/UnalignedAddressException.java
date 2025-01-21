/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.debugger;

public class UnalignedAddressException extends AddressException {
  public UnalignedAddressException(long addr) {
    super(addr);
  }

  public UnalignedAddressException(String detail, long addr) {
    super(detail, addr);
  }
}
