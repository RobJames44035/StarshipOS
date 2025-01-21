/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package sun.jvm.hotspot.runtime;

import sun.jvm.hotspot.debugger.Address;
import sun.jvm.hotspot.types.AddressField;
import sun.jvm.hotspot.types.Type;
import sun.jvm.hotspot.types.TypeDataBase;
import sun.jvm.hotspot.types.WrongTypeException;
import sun.jvm.hotspot.utilities.*;

public class Mutex extends VMObject {
  private static long          nameFieldOffset;
  private static long          ownerFieldOffset;

  private static AddressField  mutex_array;
  private static int           maxNum;

  private static final long addrSize = VM.getVM().getAddressSize();

  static {
    VM.registerVMInitializedObserver(new Observer() {
        public void update(Observable o, Object data) {
          initialize(VM.getVM().getTypeDataBase());
        }
      });
  }

  private static synchronized void initialize(TypeDataBase db) throws WrongTypeException {
    Type type = db.lookupType("Mutex");

    sun.jvm.hotspot.types.Field nameField = type.getField("_name");
    nameFieldOffset = nameField.getOffset();
    sun.jvm.hotspot.types.Field ownerField = type.getField("_owner");
    ownerFieldOffset = ownerField.getOffset();

    mutex_array = type.getAddressField("_mutex_array");
    maxNum = type.getCIntegerField("_num_mutex").getJInt();
  }

  public Mutex(Address addr) {
    super(addr);
  }

  public String name() { return CStringUtilities.getString(addr.getAddressAt(nameFieldOffset)); }

  public Address owner() { return addr.getAddressAt(ownerFieldOffset); }

  public static Address at(int i) { return mutex_array.getValue().getAddressAt(i * addrSize); }

  public static int maxNum() { return maxNum; }

}
