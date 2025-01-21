/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package sun.jvm.hotspot.gc.g1;

import java.util.Iterator;
import sun.jvm.hotspot.utilities.Observable;
import sun.jvm.hotspot.utilities.Observer;

import sun.jvm.hotspot.debugger.Address;
import sun.jvm.hotspot.runtime.VM;
import sun.jvm.hotspot.runtime.VMObject;
import sun.jvm.hotspot.runtime.VMObjectFactory;
import sun.jvm.hotspot.types.AddressField;
import sun.jvm.hotspot.types.CIntegerField;
import sun.jvm.hotspot.types.Type;
import sun.jvm.hotspot.types.TypeDataBase;

// Mirror class for G1HeapRegionSetBase. Represents a group of regions.

public class G1HeapRegionSetBase extends VMObject {

    // uint _length
    private static CIntegerField lengthField;

    static {
        VM.registerVMInitializedObserver(new Observer() {
                public void update(Observable o, Object data) {
                    initialize(VM.getVM().getTypeDataBase());
                }
            });
    }

    private static synchronized void initialize(TypeDataBase db) {
        Type type = db.lookupType("G1HeapRegionSetBase");

        lengthField = type.getCIntegerField("_length");
    }

    public long length() {
        return lengthField.getValue(addr);
    }

    public G1HeapRegionSetBase(Address addr) {
        super(addr);
    }
}
