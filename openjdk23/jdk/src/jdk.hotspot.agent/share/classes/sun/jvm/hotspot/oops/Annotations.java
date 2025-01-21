/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package sun.jvm.hotspot.oops;

import java.io.*;
import java.util.*;
import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.interpreter.Bytecodes;
import sun.jvm.hotspot.runtime.*;
import sun.jvm.hotspot.types.*;
import sun.jvm.hotspot.utilities.*;
import sun.jvm.hotspot.utilities.Observable;
import sun.jvm.hotspot.utilities.Observer;

// An Annotation is an oop containing annotations as described in the class file

public class Annotations extends Metadata {
  static {
    VM.registerVMInitializedObserver(new Observer() {
        public void update(Observable o, Object data) {
          initialize(VM.getVM().getTypeDataBase());
        }
      });
  }

  private ArrayOfU1Array fieldAnnotationsArray;
  private ArrayOfU1Array fieldTypeAnnotationsArray;

  public Annotations(Address addr) {
    super(addr);
  }

  public boolean isAnnotations()            { return true; }

  private static synchronized void initialize(TypeDataBase db) throws WrongTypeException {
    Type type = db.lookupType("Annotations");
    classAnnotations = type.getAddressField("_class_annotations");
    fieldsAnnotations = type.getAddressField("_fields_annotations");
    classTypeAnnotations = type.getAddressField("_class_type_annotations");
    fieldsTypeAnnotations = type.getAddressField("_fields_type_annotations");
  }

  private static AddressField classAnnotations;
  private static AddressField fieldsAnnotations;
  private static AddressField classTypeAnnotations;
  private static AddressField fieldsTypeAnnotations;

  public U1Array getClassAnnotations() {
    Address addr = classAnnotations.getValue(getAddress());
    return VMObjectFactory.newObject(U1Array.class, addr);
  }

  public U1Array getFieldAnnotations(int fieldIndex) {
    if (fieldAnnotationsArray == null) {
      Address addr = fieldsAnnotations.getValue(getAddress());
      fieldAnnotationsArray = VMObjectFactory.newObject(ArrayOfU1Array.class, addr);
    }
    if (fieldAnnotationsArray != null) {
      Address addr = fieldAnnotationsArray.at(fieldIndex);
      return VMObjectFactory.newObject(U1Array.class, addr);
    } else {
      return null;
    }
  }

  public U1Array getClassTypeAnnotations() {
    Address addr = classTypeAnnotations.getValue(getAddress());
    return VMObjectFactory.newObject(U1Array.class, addr);
  }

  public U1Array getFieldTypeAnnotations(int fieldIndex) {
    if (fieldTypeAnnotationsArray == null) {
      Address addr = fieldsTypeAnnotations.getValue(getAddress());
      fieldTypeAnnotationsArray = VMObjectFactory.newObject(ArrayOfU1Array.class, addr);
    }
    if (fieldTypeAnnotationsArray != null) {
      Address addr = fieldTypeAnnotationsArray.at(fieldIndex);
      return VMObjectFactory.newObject(U1Array.class, addr);
    } else {
      return null;
    }
  }

  public void printValueOn(PrintStream tty) {
      tty.print("Annotations" + "@" + getAddress());
  }
}
