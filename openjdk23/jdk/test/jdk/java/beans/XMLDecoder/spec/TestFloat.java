/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @summary Tests <float> element
 * @run main/othervm TestFloat
 * @author Sergey Malenkov
 */

import java.beans.XMLDecoder;

public final class TestFloat extends AbstractTest {
    public static final String XML
            = "<java>\n"
            + " <float>0</float>\n"
            + " <float>100</float>\n"
            + " <float>-1e15</float>\n"
            + " <float>100e-20</float>\n"
            + "</java>";

    public static void main(String[] args) {
        new TestFloat().test();
    }

    @Override
    protected void validate(XMLDecoder decoder) {
        validate(0.0f, decoder.readObject());
        validate(100.0f, decoder.readObject());
        validate(-1e15f, decoder.readObject());
        validate(100e-20f, decoder.readObject());
    }

    private static void validate(float value, Object object) {
        if (!object.equals(Float.valueOf(value))) {
            throw new Error("float " + value + " expected");
        }
    }
}
