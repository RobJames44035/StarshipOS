/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package java.time.zone;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.time.ZoneOffset;

/**
 * The shared serialization delegate for this package.
 *
 * @implNote
 * This class is mutable and should be created once per serialization.
 *
 * @serial include
 * @since 1.8
 */
final class Ser implements Externalizable {

    /**
     * Serialization version.
     */
    private static final long serialVersionUID = -8885321777449118786L;

    /** Type for ZoneRules. */
    static final byte ZRULES = 1;
    /** Type for ZoneOffsetTransition. */
    static final byte ZOT = 2;
    /** Type for ZoneOffsetTransitionRule. */
    static final byte ZOTRULE = 3;

    /** The type being serialized. */
    private byte type;
    /** The object being serialized. */
    private Serializable object;

    /**
     * Constructor for deserialization.
     */
    public Ser() {
    }

    /**
     * Creates an instance for serialization.
     *
     * @param type  the type
     * @param object  the object
     */
    Ser(byte type, Serializable object) {
        this.type = type;
        this.object = object;
    }

    //-----------------------------------------------------------------------
    /**
     * Implements the {@code Externalizable} interface to write the object.
     * @serialData
     * Each serializable class is mapped to a type that is the first byte
     * in the stream.  Refer to each class {@code writeReplace}
     * serialized form for the value of the type and sequence of values for the type.
     *
     * <ul>
     * <li><a href="{@docRoot}/serialized-form.html#java.time.zone.ZoneRules">ZoneRules.writeReplace</a>
     * <li><a href="{@docRoot}/serialized-form.html#java.time.zone.ZoneOffsetTransition">ZoneOffsetTransition.writeReplace</a>
     * <li><a href="{@docRoot}/serialized-form.html#java.time.zone.ZoneOffsetTransitionRule">ZoneOffsetTransitionRule.writeReplace</a>
     * </ul>
     *
     * @param out  the data stream to write to, not null
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        writeInternal(type, object, out);
    }

    static void write(Object object, DataOutput out) throws IOException {
        writeInternal(ZRULES, object, out);
    }

    private static void writeInternal(byte type, Object object, DataOutput out) throws IOException {
        out.writeByte(type);
        switch (type) {
            case ZRULES:
                ((ZoneRules) object).writeExternal(out);
                break;
            case ZOT:
                ((ZoneOffsetTransition) object).writeExternal(out);
                break;
            case ZOTRULE:
                ((ZoneOffsetTransitionRule) object).writeExternal(out);
                break;
            default:
                throw new InvalidClassException("Unknown serialized type");
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Implements the {@code Externalizable} interface to read the object.
     * @serialData
     * The streamed type and parameters defined by the type's {@code writeReplace}
     * method are read and passed to the corresponding static factory for the type
     * to create a new instance.  That instance is returned as the de-serialized
     * {@code Ser} object.
     *
     * <ul>
     * <li><a href="{@docRoot}/serialized-form.html#java.time.zone.ZoneRules">ZoneRules</a>
     * - {@code ZoneRules.of(standardTransitions, standardOffsets, savingsInstantTransitions, wallOffsets, lastRules);}
     * <li><a href="{@docRoot}/serialized-form.html#java.time.zone.ZoneOffsetTransition">ZoneOffsetTransition</a>
     * - {@code ZoneOffsetTransition of(LocalDateTime.ofEpochSecond(epochSecond), offsetBefore, offsetAfter);}
     * <li><a href="{@docRoot}/serialized-form.html#java.time.zone.ZoneOffsetTransitionRule">ZoneOffsetTransitionRule</a>
     * - {@code ZoneOffsetTransitionRule.of(month, dom, dow, time, timeEndOfDay, timeDefinition, standardOffset, offsetBefore, offsetAfter);}
     * </ul>
     * @param in  the data to read, not null
     */
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        type = in.readByte();
        object = readInternal(type, in);
    }

    static Serializable read(DataInput in) throws IOException, ClassNotFoundException {
        byte type = in.readByte();
        return readInternal(type, in);
    }

    private static Serializable readInternal(byte type, DataInput in)
            throws IOException, ClassNotFoundException {
        return switch (type) {
            case ZRULES -> ZoneRules.readExternal(in);
            case ZOT -> ZoneOffsetTransition.readExternal(in);
            case ZOTRULE -> ZoneOffsetTransitionRule.readExternal(in);
            default -> throw new StreamCorruptedException("Unknown serialized type");
        };
    }

    /**
     * Returns the object that will replace this one.
     *
     * @return the read object, should never be null
     */
    private Object readResolve() {
         return object;
    }

    //-----------------------------------------------------------------------
    /**
     * Writes the state to the stream.
     *
     * @param offset  the offset, not null
     * @param out  the output stream, not null
     * @throws IOException if an error occurs
     */
    static void writeOffset(ZoneOffset offset, DataOutput out) throws IOException {
        final int offsetSecs = offset.getTotalSeconds();
        int offsetByte = offsetSecs % 900 == 0 ? offsetSecs / 900 : 127;  // compress to -72 to +72
        out.writeByte(offsetByte);
        if (offsetByte == 127) {
            out.writeInt(offsetSecs);
        }
    }

    /**
     * Reads the state from the stream.
     *
     * @param in  the input stream, not null
     * @return the created object, not null
     * @throws IOException if an error occurs
     */
    static ZoneOffset readOffset(DataInput in) throws IOException {
        int offsetByte = in.readByte();
        return (offsetByte == 127 ? ZoneOffset.ofTotalSeconds(in.readInt()) : ZoneOffset.ofTotalSeconds(offsetByte * 900));
    }

    //-----------------------------------------------------------------------
    /**
     * Writes the state to the stream.
     *
     * @param epochSec  the epoch seconds, not null
     * @param out  the output stream, not null
     * @throws IOException if an error occurs
     */
    static void writeEpochSec(long epochSec, DataOutput out) throws IOException {
        if (epochSec >= -4575744000L && epochSec < 10413792000L && epochSec % 900 == 0) {  // quarter hours between 1825 and 2300
            int store = (int) ((epochSec + 4575744000L) / 900);
            out.writeByte((store >>> 16) & 255);
            out.writeByte((store >>> 8) & 255);
            out.writeByte(store & 255);
        } else {
            out.writeByte(255);
            out.writeLong(epochSec);
        }
    }

    /**
     * Reads the state from the stream.
     *
     * @param in  the input stream, not null
     * @return the epoch seconds, not null
     * @throws IOException if an error occurs
     */
    static long readEpochSec(DataInput in) throws IOException {
        int hiByte = in.readByte() & 255;
        if (hiByte == 255) {
            return in.readLong();
        } else {
            int midByte = in.readByte() & 255;
            int loByte = in.readByte() & 255;
            long tot = ((hiByte << 16) + (midByte << 8) + loByte);
            return (tot * 900) - 4575744000L;
        }
    }

}
