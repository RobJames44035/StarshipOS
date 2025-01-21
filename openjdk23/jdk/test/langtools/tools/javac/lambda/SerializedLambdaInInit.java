/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
@test
@bug 8009742
@summary Bad method name: Serialized lambda in a constructor or class init
*/

import java.io.*;
import java.lang.reflect.Method;

public class SerializedLambdaInInit {
    static int assertionCount = 0;

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond)
            throw new AssertionError();
    }

    static LSI cvisi = z -> "[" + z + "]";
    static LSI cisi;

    static {
        cisi = z -> z + z;
    }

    LSI ivsi = z -> "blah";
    LSI iisi;

    SerializedLambdaInInit() {
        iisi = z -> "*" + z;
    }

    public static void main(String[] args) throws Exception {
        try {
            // Write lambdas out
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(baos);
            SerializedLambdaInInit slii = new SerializedLambdaInInit();

            write(out, cvisi );
            write(out, cisi );
            write(out, slii.ivsi );
            write(out, slii.iisi );
            out.flush();
            out.close();

            // Read them back
            ByteArrayInputStream bais =
                new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bais);
            readAssert(in, "[X]");
            readAssert(in, "XX");
            readAssert(in, "blah");
            readAssert(in, "*X");
            in.close();

            // Reflectively test for valid method names
            for (Method meth : slii.getClass().getDeclaredMethods()) {
                checkIdentifier(meth.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        assertTrue(assertionCount == 4);
    }

    static void write(ObjectOutput out, LSI lamb) throws IOException {
        out.writeObject(lamb);
    }

    static void readAssert(ObjectInputStream in, String expected)  throws IOException, ClassNotFoundException {
        LSI ls = (LSI) in.readObject();
        String result = ls.convert("X");
        System.out.printf("Result: %s\n", result);
        assertTrue(result.equals(expected));
    }

    public static void checkIdentifier(String str) throws Exception {
        // null and zero length identifers will throw their own exceptions
        char[] chars = str.toCharArray();
        if (!Character.isJavaIdentifierStart(chars[0])) {
            throw new IllegalArgumentException(str + ": bad identifier start character: '" + chars[0] + "'");
        }
        for (char ch : chars) {
            if (!Character.isJavaIdentifierPart(ch)) {
                throw new IllegalArgumentException(str + ": bad identifier character: '" + ch + "'");
            }
        }
    }

    interface LSI extends Serializable {
        String convert(String x);
    }
}
