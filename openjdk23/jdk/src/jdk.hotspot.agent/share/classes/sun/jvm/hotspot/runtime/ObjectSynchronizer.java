/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.runtime;

import java.util.*;

import sun.jvm.hotspot.oops.*;
import sun.jvm.hotspot.utilities.*;
import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.types.*;
import sun.jvm.hotspot.utilities.Observable;
import sun.jvm.hotspot.utilities.Observer;

public class ObjectSynchronizer {
  static {
    VM.registerVMInitializedObserver(new Observer() {
        public void update(Observable o, Object data) {
          initialize(VM.getVM().getTypeDataBase());
        }
      });
  }

  private static synchronized void initialize(TypeDataBase db) throws WrongTypeException {
    Type objectSynchronizerType = db.lookupType("ObjectSynchronizer");
    Type monitorListType = db.lookupType("MonitorList");
    Address monitorListAddr = objectSynchronizerType.getField("_in_use_list").getStaticFieldAddress();
    inUseListHead = monitorListType.getAddressField("_head").getAddress(monitorListAddr);
  }

  public long identityHashValueFor(Oop obj) {
    Mark mark = obj.getMark();
    if (mark.isUnlocked()) {
      // FIXME: can not generate marks in debugging system
      return mark.hash();
    } else if (mark.hasMonitor()) {
      if (VM.getVM().getCommandLineFlag("UseObjectMonitorTable").getBool()) {
        return mark.hash();
      }
      ObjectMonitor monitor = mark.monitor();
      Mark temp = monitor.header();
      return temp.hash();
    } else {
      if (Assert.ASSERTS_ENABLED) {
        Assert.that(VM.getVM().isDebugging(), "Can not access displaced header otherwise");
      }
      if (mark.hasDisplacedMarkHelper()) {
        Mark temp = mark.displacedMarkHelper();
        return temp.hash();
      }
      // FIXME: can not do anything else here in debugging system
      return 0;
    }
  }

  public static Iterator objectMonitorIterator() {
    return new ObjectMonitorIterator();
  }

  private static class ObjectMonitorIterator implements Iterator {

    // JVMTI raw monitors are not included in _in_use_list and
    // are not returned by this Iterator.

    ObjectMonitorIterator() {
      mon = inUseListHead == null ? null : new ObjectMonitor(inUseListHead);
    }

    public boolean hasNext() {
      return (mon != null);
    }

    public Object next() {
      ObjectMonitor ret = mon;
      if (ret == null) {
        throw new NoSuchElementException();
      }
      // advance to next entry
      Address nextMon = mon.nextOM();
      mon = nextMon == null ? null : new ObjectMonitor(nextMon);

      return ret;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    private ObjectMonitor mon;
  }

  private static Address inUseListHead;

}
