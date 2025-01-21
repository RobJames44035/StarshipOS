/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @summary Tests <byte> element
 * @run main/othervm TestByte
 * @author Sergey Malenkov
 */

import java.beans.XMLDecoder;

public final class TestByte extends AbstractTest {
    public static final String XML
            = "<java>\n"
            + " <byte>0</byte>\n"
            + " <byte>127</byte>\n"
            + " <byte>-128</byte>\n"
            + "</java>";

    public static void main(String[] args) {
        new TestByte().test();
    }

    @Override
    protected void validate(XMLDecoder decoder) {
        validate((byte) 0, decoder.readObject());
        validate(Byte.MAX_VALUE, decoder.readObject());
        validate(Byte.MIN_VALUE, decoder.readObject());
    }

    private static void validate(byte value, Object object) {
        if (!object.equals(Byte.valueOf(value))) {
            throw new Error("byte " + value + " expected");
        }
    }
}
