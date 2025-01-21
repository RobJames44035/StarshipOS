/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @summary Tests <char> element
 * @run main/othervm TestChar
 * @author Sergey Malenkov
 */

import java.beans.XMLDecoder;

public final class TestChar extends AbstractTest {
    public static final String XML
            = "<java>\n"
            + " <char>X</char>\n"
            + " <char code=\"#20\"/>\n"
            + "</java>";

    public static void main(String[] args) {
        new TestChar().test();
    }

    @Override
    protected void validate(XMLDecoder decoder) {
        if (!decoder.readObject().equals(Character.valueOf('X'))) {
            throw new Error("unexpected character");
        }
        if (!decoder.readObject().equals(Character.valueOf((char) 0x20))) {
            throw new Error("unexpected character code");
        }
    }
}
