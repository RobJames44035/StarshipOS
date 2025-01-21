/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.gc.serial;

import java.io.*;
import java.util.*;

import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.gc.shared.*;
import sun.jvm.hotspot.runtime.*;
import sun.jvm.hotspot.types.*;
import sun.jvm.hotspot.utilities.Observable;
import sun.jvm.hotspot.utilities.Observer;

/** <P> TenuredGeneration models a heap of old objects contained
    in a single contiguous space. </P>

    <P> Garbage collection is performed using mark-compact. </P> */

public class TenuredGeneration extends Generation {
  private static AddressField theSpaceField;

  static {
    VM.registerVMInitializedObserver(new Observer() {
        public void update(Observable o, Object data) {
          initialize(VM.getVM().getTypeDataBase());
        }
      });
  }

  private static synchronized void initialize(TypeDataBase db) {
    Type type = db.lookupType("TenuredGeneration");

    theSpaceField = type.getAddressField("_the_space");
  }

  public TenuredGeneration(Address addr) {
    super(addr);
  }

  public ContiguousSpace theSpace() {
    return VMObjectFactory.newObject(ContiguousSpace.class, theSpaceField.getValue(addr));
  }

  public boolean isIn(Address p) {
    return theSpace().contains(p);
  }

  /** Space queries */
  public long capacity()            { return theSpace().capacity();                                }
  public long used()                { return theSpace().used();                                    }
  public long free()                { return theSpace().free();                                    }
  public long contiguousAvailable() { return theSpace().free() + virtualSpace().uncommittedSize(); }

  public void liveRegionsIterate(LiveRegionsClosure closure) {
    closure.doLiveRegions(theSpace());
  }

  public void printOn(PrintStream tty) {
    tty.print("  old ");
    theSpace().printOn(tty);
  }

  public String name() {
    return "tenured generation";
  }
}
