/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/* @test
 * @bug 4334623
 * @summary Verify that serialPersistentFields cannot be used to cause
 *          deserialization to set the value of a static field.
 */

import java.io.*;

public class ModifyStaticFields implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final ObjectStreamField[] serialPersistentFields =
        new ObjectStreamField[] { new ObjectStreamField("str", String.class) };

    static String str = "foo";

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);
        oout.writeObject(new ModifyStaticFields());
        oout.close();

        String origStr = str;
        ObjectInputStream oin = new ObjectInputStream(
            new ByteArrayInputStream(bout.toByteArray()));
        oin.readObject();
        if (str != origStr) {
            throw new Error("deserialization modified static field");
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        ObjectOutputStream.PutField pf = out.putFields();
        pf.put("str", "bar");
        out.writeFields();
    }
}
