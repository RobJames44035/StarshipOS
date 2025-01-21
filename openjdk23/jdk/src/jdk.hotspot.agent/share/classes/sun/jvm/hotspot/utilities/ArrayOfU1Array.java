/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package sun.jvm.hotspot.utilities;

import sun.jvm.hotspot.debugger.Address;
import sun.jvm.hotspot.runtime.VM;
import sun.jvm.hotspot.types.Type;
import sun.jvm.hotspot.types.TypeDataBase;
import sun.jvm.hotspot.types.WrongTypeException;
import sun.jvm.hotspot.utilities.Observable;
import sun.jvm.hotspot.utilities.Observer;

public class ArrayOfU1Array extends GenericArray {
  static {
    VM.registerVMInitializedObserver(new Observer() {
      public void update(Observable o, Object data) {
        initialize(VM.getVM().getTypeDataBase());
      }
    });
  }

  private static synchronized void initialize(TypeDataBase db) throws WrongTypeException {
    elemType = db.lookupType("Array<u1>*");

    Type type = db.lookupType("Array<Array<u1>*>");
    dataFieldOffset = type.getAddressField("_data").getOffset();
  }

  private static long dataFieldOffset;
  protected static Type elemType;

  public ArrayOfU1Array(Address addr) {
    super(addr, dataFieldOffset);
  }

  public Address at(int i) {
    return getAddressAt(i);
  }

  public Type getElemType() {
    return elemType;
  }
}
