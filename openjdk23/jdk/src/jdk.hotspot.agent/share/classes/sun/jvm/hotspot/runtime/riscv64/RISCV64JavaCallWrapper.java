/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package sun.jvm.hotspot.runtime.riscv64;

import java.util.*;
import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.types.*;
import sun.jvm.hotspot.runtime.*;
import sun.jvm.hotspot.utilities.*;
import sun.jvm.hotspot.utilities.Observable;
import sun.jvm.hotspot.utilities.Observer;

public class RISCV64JavaCallWrapper extends JavaCallWrapper {
  private static AddressField lastJavaFPField;

  static {
    VM.registerVMInitializedObserver(new Observer() {
        public void update(Observable o, Object data) {
          initialize(VM.getVM().getTypeDataBase());
        }
      });
  }

  private static synchronized void initialize(TypeDataBase db) {
    Type type = db.lookupType("JavaFrameAnchor");

    lastJavaFPField  = type.getAddressField("_last_Java_fp");
  }

  public RISCV64JavaCallWrapper(Address addr) {
    super(addr);
  }

  public Address getLastJavaFP() {
    return lastJavaFPField.getValue(addr.addOffsetTo(anchorField.getOffset()));
  }
}
