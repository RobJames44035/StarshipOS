/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 6312706
 * @summary A serialized EnumMap can be successfully de-serialized.
 * @author Neil Richards <neil.richards@ngmr.net>, <neil_richards@uk.ibm.com>
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.EnumMap;

public class SimpleSerialization {
    private enum TestEnum { e00, e01, e02, e03, e04, e05, e06, e07 }
    public static void main(final String[] args) throws Exception {
        final EnumMap<TestEnum, String> enumMap = new EnumMap<>(TestEnum.class);

        enumMap.put(TestEnum.e01, TestEnum.e01.name());
        enumMap.put(TestEnum.e04, TestEnum.e04.name());
        enumMap.put(TestEnum.e05, TestEnum.e05.name());

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(enumMap);
        oos.close();

        final byte[] data = baos.toByteArray();
        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final ObjectInputStream ois = new ObjectInputStream(bais);

        final Object deserializedObject = ois.readObject();
        ois.close();

        if (false == enumMap.equals(deserializedObject)) {
            throw new RuntimeException(getFailureText(enumMap, deserializedObject));
        }
    }

    private static String getFailureText(final Object orig, final Object copy) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);

        pw.println("Test FAILED: Deserialized object is not equal to the original object");
        pw.print("\tOriginal: ");
        printObject(pw, orig).println();
        pw.print("\tCopy:     ");
        printObject(pw, copy).println();

        pw.close();
        return sw.toString();
    }

    private static PrintWriter printObject(final PrintWriter pw, final Object o) {
        pw.printf("%s@%08x", o.getClass().getName(), System.identityHashCode(o));
        return pw;
    }
}
