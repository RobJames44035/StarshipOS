/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8331446 8340554
 * @summary Check correctness of deserialization
 * @run junit SerializationTest
 */

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializationTest {

    // Ensure basic correctness of serialization round trip
    @ParameterizedTest
    @MethodSource
    public void serializationRoundTrip(MessageFormat expectedMf)
            throws IOException, ClassNotFoundException {
        byte[] bytes = ser(expectedMf);
        MessageFormat actualMf = (MessageFormat) deSer(bytes);
        assertEquals(expectedMf, actualMf);
    }

    // Various valid MessageFormats
    private static Stream<MessageFormat> serializationRoundTrip() {
        return Stream.of(
                // basic pattern
                new MessageFormat("{0} foo"),
                // Multiple arguments
                new MessageFormat("{0} {1} foo"),
                // duplicate arguments
                new MessageFormat("{0} {0} {1} foo"),
                // Non-ascending arguments
                new MessageFormat("{1} {0} foo"),
                // With locale
                new MessageFormat("{1} {0} foo", Locale.UK),
                // With null locale. (NPE not thrown, if no format defined)
                new MessageFormat("{1} {0} foo", null),
                // With formats
                new MessageFormat("{0,number,short} {0} {1,date,long} foo"),
                // Offset equal to pattern length (0)
                new MessageFormat("{0}"),
                // Offset equal to pattern length (1)
                new MessageFormat("X{0}"),
                // Offset 1 under pattern length
                new MessageFormat("X{0}X")
        );
    }

    // Utility method to serialize
    private static byte[] ser(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new
                ByteArrayOutputStream();
        ObjectOutputStream oos = new
                ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(obj);
        return byteArrayOutputStream.toByteArray();
    }

    // Utility method to deserialize
    private static Object deSer(byte[] bytes) throws
            IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new
                ByteArrayInputStream(bytes);
        ObjectInputStream ois = new
                ObjectInputStream(byteArrayInputStream);
        return ois.readObject();
    }
}
