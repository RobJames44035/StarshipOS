/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test 1.1 03/10/27
 * @bug 4769840
 * @library /java/text/testlib
 * @build Bug4769840 HexDumpReader
 * @run main Bug4769840
 * @summary Confirm serialization compatibility
 */

import java.io.*;
import java.text.*;

public class Bug4769840 {

    public static void main(String[] args) throws Exception {
        if (args.length == 1 && args[0].equals("-ser")) {
            serialize();
        } else {
            deserialize();
        }
    }

    /* Serialization */
    private static void serialize() throws Exception {
        /* Serialize with JDK 1.1 */
        serialize("ChoiceFormat.ser", new ChoiceFormat("0# foo|1# bar"));

        /*
         * Serialize with JDK1.4.0 because the Field class was added in the
         * version.
         */
        serialize("DateFormat.Field.ser", DateFormat.Field.TIME_ZONE);
        serialize("MessageFormat.Field.ser", MessageFormat.Field.ARGUMENT);
        serialize("NumberFormat.Field.ser", NumberFormat.Field.INTEGER);
    }

    private static void serialize(String filename, Object o) throws Exception {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fos);
        out.writeObject(o);
        out.close();
    }

    /* Deserialization */
    private static void deserialize() throws Exception {
        deserialize("ChoiceFormat.ser");
        deserialize("DateFormat.Field.ser");
        deserialize("MessageFormat.Field.ser");
        deserialize("NumberFormat.Field.ser");
    }

    private static void deserialize(String filename) throws Exception {
        InputStream is = HexDumpReader.getStreamFromHexDump(filename + ".txt");
        ObjectInputStream in = new ObjectInputStream(is);
        Object obj = in.readObject();
        in.close();
        System.out.println("Deserialization of <" + filename + "> succeeded.");
    }
}
