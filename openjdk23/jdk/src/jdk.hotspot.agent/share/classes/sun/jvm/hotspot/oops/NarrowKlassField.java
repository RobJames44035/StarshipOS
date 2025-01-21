/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package sun.jvm.hotspot.oops;

import sun.jvm.hotspot.debugger.*;

public class NarrowKlassField extends MetadataField {

  public NarrowKlassField(sun.jvm.hotspot.types.AddressField vmField, long startOffset) {
    super(vmField, startOffset);
  }

  public Metadata getValue(Address addr) {
    return Metadata.instantiateWrapperFor(addr.getCompKlassAddressAt(getOffset()));
  }
  public void setValue(Oop obj, long value) throws MutationException {
    // Fix this: set* missing in Address
  }
}
