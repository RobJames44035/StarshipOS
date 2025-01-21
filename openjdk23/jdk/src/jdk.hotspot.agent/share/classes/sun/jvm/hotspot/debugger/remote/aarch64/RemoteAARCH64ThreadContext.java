/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.remote.aarch64;

import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.debugger.aarch64.*;
import sun.jvm.hotspot.debugger.remote.*;

public class RemoteAARCH64ThreadContext extends AARCH64ThreadContext {
  private RemoteDebuggerClient debugger;

  public RemoteAARCH64ThreadContext(RemoteDebuggerClient debugger) {
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
