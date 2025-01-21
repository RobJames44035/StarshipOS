/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.linux.riscv64;

import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.debugger.riscv64.*;
import sun.jvm.hotspot.debugger.linux.*;

public class LinuxRISCV64ThreadContext extends RISCV64ThreadContext {
  private LinuxDebugger debugger;

  public LinuxRISCV64ThreadContext(LinuxDebugger debugger) {
    super();
    this.debugger = debugger;
  }

  public void setRegisterAsAddress(int index, Address value) {
    setRegister(index, debugger.getAddressValue(value));
  }

  public Address getRegisterAsAddress(int index) {
    return debugger.newAddress(getRegister(index));
  }
}
