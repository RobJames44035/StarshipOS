/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.jvm.hotspot.ci;

import java.util.*;
import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.runtime.*;
import sun.jvm.hotspot.oops.*;
import sun.jvm.hotspot.types.*;
import sun.jvm.hotspot.utilities.Observable;
import sun.jvm.hotspot.utilities.Observer;

public class ciType extends ciMetadata {
  static {
    VM.registerVMInitializedObserver(new Observer() {
        public void update(Observable o, Object data) {
          initialize(VM.getVM().getTypeDataBase());
        }
      });
  }

  private static synchronized void initialize(TypeDataBase db) throws WrongTypeException {
    Type type      = db.lookupType("ciType");
    basicTypeField = new CIntField(type.getCIntegerField("_basic_type"), 0);
  }

  private static CIntField basicTypeField;

  public ciType(Address addr) {
    super(addr);
  }
}
