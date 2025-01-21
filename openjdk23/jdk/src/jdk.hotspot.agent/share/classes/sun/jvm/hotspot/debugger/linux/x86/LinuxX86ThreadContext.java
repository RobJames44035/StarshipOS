/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.linux.x86;

import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.debugger.x86.*;
import sun.jvm.hotspot.debugger.linux.*;

public class LinuxX86ThreadContext extends X86ThreadContext {
  private LinuxDebugger debugger;

  public LinuxX86ThreadContext(LinuxDebugger debugger) {
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
