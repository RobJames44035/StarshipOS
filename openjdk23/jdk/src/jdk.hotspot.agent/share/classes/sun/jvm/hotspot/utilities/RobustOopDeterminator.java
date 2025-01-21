/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.utilities;

import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.oops.Metadata;
import sun.jvm.hotspot.oops.Oop;
import sun.jvm.hotspot.runtime.*;
import sun.jvm.hotspot.types.*;

/** This class determines to the best of its ability, and in a
    reasonably robust fashion, whether a given pointer is an intact
    oop or not. It does this by checking the integrity of the
    metaclass hierarchy. This is only intended for use in the
    debugging system. It may provide more resilience to unexpected VM
    states than the ObjectHeap code. */

public class RobustOopDeterminator {
  public static boolean oopLooksValid(OopHandle oop) {
    if (oop == null) {
      return false;
    }
    if (!VM.getVM().getUniverse().isIn(oop)) {
      return false;
    }
    try {
      // Try to instantiate the Klass
      Oop.getKlassForOopHandle(oop);
      return true;
    } catch (AddressException | WrongTypeException e) {
      return false;
    }
  }
}
