/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @summary Tests <boolean> element
 * @run main/othervm TestBoolean
 * @author Sergey Malenkov
 */

import java.beans.XMLDecoder;

public final class TestBoolean extends AbstractTest {
    public static final String XML
            = "<java>\n"
            + " <boolean>true</boolean>\n"
            + " <boolean>false</boolean>\n"
            + "</java>";

    public static void main(String[] args) {
        new TestBoolean().test();
    }

    @Override
    protected void validate(XMLDecoder decoder) {
        if (!Boolean.TRUE.equals(decoder.readObject())) {
            throw new Error("true expected");
        }
        if (!Boolean.FALSE.equals(decoder.readObject())) {
            throw new Error("false expected");
        }
    }
}
