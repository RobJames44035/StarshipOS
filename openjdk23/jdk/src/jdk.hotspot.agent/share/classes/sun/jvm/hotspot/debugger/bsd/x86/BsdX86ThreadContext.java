/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.bsd.x86;

import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.debugger.x86.*;
import sun.jvm.hotspot.debugger.bsd.*;

public class BsdX86ThreadContext extends X86ThreadContext {
  private BsdDebugger debugger;

  public BsdX86ThreadContext(BsdDebugger debugger) {
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
