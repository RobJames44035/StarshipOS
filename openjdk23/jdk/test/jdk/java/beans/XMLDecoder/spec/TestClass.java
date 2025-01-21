/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @summary Tests <class> element
 * @run main/othervm TestClass
 * @author Sergey Malenkov
 */

import java.beans.XMLDecoder;

public final class TestClass extends AbstractTest {
    public static final String PREFIX = "javax.swing.colorchooser.";
    public static final String INTERFACE = "ColorSelectionModel";
    public static final String PUBLIC_CLASS = "DefaultColorSelectionModel";
    public static final String PRIVATE_CLASS = "DiagramComponent";
    public static final String XML
            = "<java>\n"
            + " <class>" + PREFIX + INTERFACE + "</class>\n"
            + " <class>" + PREFIX + PUBLIC_CLASS + "</class>\n"
            + " <class>" + PREFIX + PRIVATE_CLASS + "</class>\n"
            + "</java>";

    public static void main(String[] args) {
        new TestClass().test();
    }

    @Override
    protected void validate(XMLDecoder decoder) {
        validate(PREFIX + INTERFACE, decoder.readObject());
        validate(PREFIX + PUBLIC_CLASS, decoder.readObject());
        validate(PREFIX + PRIVATE_CLASS, decoder.readObject());
    }

    private static void validate(String name, Object object) {
        Class type = (Class) object;
        if (!type.getName().equals(name)) {
            throw new Error(name + " expected");
        }
    }
}
