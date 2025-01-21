/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @summary Tests <null> element
 * @run main/othervm TestNull
 * @author Sergey Malenkov
 */

import java.beans.XMLDecoder;

public final class TestNull extends AbstractTest {
    public static final String XML
            = "<java>\n"
            + " <null/>\n"
            + "</java>";

    public static void main(String[] args) {
        new TestNull().test();
    }

    @Override
    protected void validate(XMLDecoder decoder) {
        if (null != decoder.readObject()) {
            throw new Error("null value expected");
        }
    }
}
