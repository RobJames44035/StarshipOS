/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.oops;

import java.io.*;
import java.util.*;
import sun.jvm.hotspot.debugger.*;
import sun.jvm.hotspot.runtime.*;
import sun.jvm.hotspot.types.*;
import sun.jvm.hotspot.utilities.*;
import sun.jvm.hotspot.utilities.Observable;
import sun.jvm.hotspot.utilities.Observer;

// ObjArrayKlass is the klass for ObjArrays

public class ObjArrayKlass extends ArrayKlass {
  static {
    VM.registerVMInitializedObserver(new Observer() {
        public void update(Observable o, Object data) {
          initialize(VM.getVM().getTypeDataBase());
        }
      });
  }

  private static synchronized void initialize(TypeDataBase db) throws WrongTypeException {
    Type type = db.lookupType("ObjArrayKlass");
    elementKlass = new MetadataField(type.getAddressField("_element_klass"), 0);
    bottomKlass  = new MetadataField(type.getAddressField("_bottom_klass"), 0);
  }

  public ObjArrayKlass(Address addr) {
    super(addr);
  }

  private static MetadataField elementKlass;
  private static MetadataField bottomKlass;

  public Klass getElementKlass() { return (Klass) elementKlass.getValue(this); }
  public Klass getBottomKlass()  { return (Klass) bottomKlass.getValue(this); }

  public void iterateFields(MetadataVisitor visitor) {
    super.iterateFields(visitor);
    visitor.doMetadata(elementKlass, true);
    visitor.doMetadata(bottomKlass, true);
  }

  public Klass arrayKlassImpl(boolean orNull, int n) {
    if (Assert.ASSERTS_ENABLED) {
      Assert.that(getDimension() <= n, "check order of chain");
    }
    int dimension = (int) getDimension();
    if (dimension == n) {
      return this;
    }
    ObjArrayKlass ak = getHigherDimension();
    if (ak == null) {
      if (orNull) return null;
      // FIXME: would need to change in reflective system to actually
      // allocate klass
      throw new RuntimeException("Can not allocate array klasses in debugging system");
    }

    if (orNull) {
      return ak.arrayKlassOrNull(n);
    }
    return ak.arrayKlass(n);
  }

  public Klass arrayKlassImpl(boolean orNull) {
    return arrayKlassImpl(orNull, (int) (getDimension() + 1));
  }

  public void printValueOn(PrintStream tty) {
    tty.print("ObjArrayKlass for ");
    getElementKlass().printValueOn(tty);
  }
};
