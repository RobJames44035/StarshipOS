/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */


/*
 * The Original Code is HAT. The Initial Developer of the
 * Original Code is Bill Foote, with contributions from others
 * at JavaSoft/Sun.
 */

package jdk.test.lib.hprof.model;

import java.io.IOException;
import jdk.test.lib.hprof.parser.ReadBuffer;

/*
 * Base class for lazily read Java heap objects.
 */
public abstract class JavaLazyReadObject extends JavaHeapObject {

    // file offset from which this object data starts
    private final long offset;

    protected JavaLazyReadObject(long offset) {
        this.offset = offset;
    }

    @Override
    public final long getSize() {
        return getValueLength() + getClazz().getMinimumObjectSize();
    }

    protected final long getOffset() {
        return offset;
    }

    protected ReadBuffer buf() {
        return getClazz().getReadBuffer();
    }

    protected int idSize() {
        return getClazz().getIdentifierSize();
    }

    // return the length of the data for this object
    protected final long getValueLength() {
        try {
            return readValueLength();
        } catch (IOException exp) {
            System.err.println("lazy read failed at offset " + offset);
            exp.printStackTrace();
            return 0;
        }
    }

    // get this object's content as byte array
    protected final JavaThing[] getValue() {
        try {
            return readValue();
        } catch (IOException exp) {
            System.err.println("lazy read failed at offset " + offset);
            exp.printStackTrace();
            return Snapshot.EMPTY_JAVATHING_ARRAY;
        }
    }

    // get ID of this object
    public final long getId() {
        try {
        if (idSize() == 4) {
                return ((long)buf().getInt(offset)) & Snapshot.SMALL_ID_MASK;
            } else {
                return buf().getLong(offset);
            }
        } catch (IOException exp) {
            System.err.println("lazy read failed at offset " + offset);
            exp.printStackTrace();
            return -1;
        }
    }

    protected abstract long readValueLength() throws IOException;
    protected abstract JavaThing[] readValue() throws IOException;

    // make Integer or Long for given object ID
    protected static Number makeId(long id) {
        if ((id & ~Snapshot.SMALL_ID_MASK) == 0) {
            return (int)id;
        } else {
            return id;
        }
    }

    // get ID as long value from Number
    protected static long getIdValue(Number num) {
        long id = num.longValue();
        if (num instanceof Integer) {
            id &= Snapshot.SMALL_ID_MASK;
        }
        return id;
    }

    // read object ID from given index from given byte array
    protected final long objectIdAt(long offset) throws IOException {
        if (idSize() == 4) {
            return ((long)intAt(offset)) & Snapshot.SMALL_ID_MASK;
        } else {
            return longAt(offset);
        }
    }

    // utility methods to read primitive types from byte array
    protected byte byteAt(long offset) throws IOException {
        return buf().getByte(offset);
    }

    protected boolean booleanAt(long offset) throws IOException {
        return byteAt(offset) == 0 ? false : true;
    }

    protected char charAt(long offset) throws IOException {
        return buf().getChar(offset);
    }

    protected short shortAt(long offset) throws IOException {
        return buf().getShort(offset);
    }

    protected int intAt(long offset) throws IOException {
        return buf().getInt(offset);
    }

    protected long longAt(long offset) throws IOException {
        return buf().getLong(offset);
    }

    protected float floatAt(long offset) throws IOException {
        int val = intAt(offset);
        return Float.intBitsToFloat(val);
    }

    protected double doubleAt(long offset) throws IOException {
        long val = longAt(offset);
        return Double.longBitsToDouble(val);
    }
}
