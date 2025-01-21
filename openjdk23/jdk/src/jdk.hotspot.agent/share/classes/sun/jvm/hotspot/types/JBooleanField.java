/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.types;

import sun.jvm.hotspot.debugger.*;

/** A specialization of Field which represents a field containing a
    Java boolean value (in either a C/C++ data structure or a Java
    object) and which adds typechecked getValue() routines returning
    booleans. */

public interface JBooleanField extends Field {
  /** The field must be nonstatic and the type of the field must be a
      Java boolean, or a WrongTypeException will be thrown. */
  public boolean getValue(Address addr) throws UnmappedAddressException, UnalignedAddressException, WrongTypeException;

  /** The field must be static and the type of the field must be a
      Java boolean, or a WrongTypeException will be thrown. */
  public boolean getValue() throws UnmappedAddressException, UnalignedAddressException, WrongTypeException;
}
