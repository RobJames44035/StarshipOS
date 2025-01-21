/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.remote.riscv64;

import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.debugger.riscv64.*;
import sun.jvm.hotspot.debugger.remote.*;

public class RemoteRISCV64ThreadContext extends RISCV64ThreadContext {
  private RemoteDebuggerClient debugger;

  public RemoteRISCV64ThreadContext(RemoteDebuggerClient debugger) {
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
