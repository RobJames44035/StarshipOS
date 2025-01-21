/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.types;

import sun.jvm.hotspot.debugger.*;

/** A specialization of Field which represents a field containing a
    Java char value (in either a C/C++ data structure or a Java
    object) and which adds typechecked getValue() routines returning
    chars. */

public interface JCharField extends Field {
  /** The field must be nonstatic and the type of the field must be a
      Java char, or a WrongTypeException will be thrown. */
  public char getValue(Address addr) throws UnmappedAddressException, UnalignedAddressException, WrongTypeException;

  /** The field must be static and the type of the field must be a
      Java char, or a WrongTypeException will be thrown. */
  public char getValue() throws UnmappedAddressException, UnalignedAddressException, WrongTypeException;
}
