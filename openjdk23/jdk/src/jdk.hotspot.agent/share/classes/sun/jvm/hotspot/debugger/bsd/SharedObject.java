/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.bsd;

import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.debugger.cdbg.*;
import sun.jvm.hotspot.debugger.posix.*;

/** An Object can represent either a .so or an a.out file. */

class SharedObject extends DSO {
  SharedObject(BsdDebugger dbg, String filename, long size, Address relocation) {
    super(filename, size, relocation);
    this.dbg     = dbg;
  }

  protected Address newAddress(long address) {
    return dbg.newAddress(address);
  }

  protected long getAddressValue(Address addr) {
    return dbg.getAddressValue(addr);
  }

  public ClosestSymbol closestSymbolToPC(Address pcAsAddr) throws DebuggerException {
    return dbg.lookup(dbg.getAddressValue(pcAsAddr));
  }

  private BsdDebugger   dbg;
}
