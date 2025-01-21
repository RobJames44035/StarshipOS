/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

 package sun.jvm.hotspot.oops;

 import sun.jvm.hotspot.debugger.Address;
 import sun.jvm.hotspot.runtime.VM;
 import sun.jvm.hotspot.types.Type;
 import sun.jvm.hotspot.types.TypeDataBase;
 import sun.jvm.hotspot.types.WrongTypeException;
 import sun.jvm.hotspot.utilities.GenericArray;
 import sun.jvm.hotspot.utilities.Observable;
 import sun.jvm.hotspot.utilities.Observer;

 public class ResolvedMethodArray extends GenericArray {
     static {
         VM.registerVMInitializedObserver(new Observer() {
             public void update(Observable o, Object data) {
                 initialize(VM.getVM().getTypeDataBase());
             }
         });
     }

     private static synchronized void initialize(TypeDataBase db) throws WrongTypeException {
         elemType = db.lookupType("ResolvedMethodEntry");

         Type type = db.lookupType("Array<ResolvedMethodEntry>");
         dataFieldOffset = type.getAddressField("_data").getOffset();
     }

     private static long dataFieldOffset;
     protected static Type elemType;

     public ResolvedMethodArray(Address addr) {
         super(addr, dataFieldOffset);
     }

     public ResolvedMethodEntry getAt(int index) {
         if (index < 0 || index >= length()) throw new ArrayIndexOutOfBoundsException(index + " " + length());

         Type elemType = getElemType();

         Address data = getAddress().addOffsetTo(dataFieldOffset);
         long elemSize = elemType.getSize();

         return new ResolvedMethodEntry(data.addOffsetTo(index* elemSize));
     }

     public Type getElemType() {
         return elemType;
     }
 }
