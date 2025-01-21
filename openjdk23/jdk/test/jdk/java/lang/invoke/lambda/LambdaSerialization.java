/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
@test
@bug 8004970
@summary Lambda serialization
*/

import java.io.*;

public class LambdaSerialization {

    static int assertionCount = 0;

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond)
            throw new AssertionError();
    }

    public static void main(String[] args) throws Exception {
        try {
            // Write lambdas out
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(baos);

            write(out, z -> "[" + z + "]" );
            write(out, z -> z + z );
            write(out, z -> "blah" );
            out.flush();
            out.close();

            // Read them back
            ByteArrayInputStream bais =
                new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bais);
            readAssert(in, "[X]");
            readAssert(in, "XX");
            readAssert(in, "blah");
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        assertTrue(assertionCount == 3);
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
}

interface LSI extends Serializable {
    String convert(String x);
}
