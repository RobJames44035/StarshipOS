/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.types;

import sun.jvm.hotspot.debugger.*;

/** A specialization of Field which represents a field containing a
    Java long value (in either a C/C++ data structure or a Java
    object) and which adds typechecked getValue() routines returning
    longs. */

public interface JLongField extends Field {
  /** The field must be nonstatic and the type of the field must be a
      Java long, or a WrongTypeException will be thrown. */
  public long getValue(Address addr) throws UnmappedAddressException, UnalignedAddressException, WrongTypeException;

  /** The field must be static and the type of the field must be a
      Java long, or a WrongTypeException will be thrown. */
  public long getValue() throws UnmappedAddressException, UnalignedAddressException, WrongTypeException;
}
