/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8009582
 * @summary Method reference generic constructor gives: IllegalArgumentException: Invalid lambda deserialization
 * @author  Robert Field
 * @run main GenericMethodRefImplClass
 */

import java.io.*;
import java.util.*;

public class GenericMethodRefImplClass {

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

            write(out, HashMap::new );
            out.flush();
            out.close();

            // Read them back
            ByteArrayInputStream bais =
                new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bais);
            readIt(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        assertTrue(assertionCount == 1);
    }

    static void write(ObjectOutput out, GenericMethodRefImplClassLSI lamb) throws IOException {
        out.writeObject(lamb);
    }

    static void readIt(ObjectInputStream in)  throws IOException, ClassNotFoundException {
        GenericMethodRefImplClassLSI ls = (GenericMethodRefImplClassLSI) in.readObject();
        Map result = ls.convert();
        assertTrue(result.getClass().getName().equals("java.util.HashMap"));
    }
}

interface GenericMethodRefImplClassLSI extends Serializable {
    Map convert();
}
