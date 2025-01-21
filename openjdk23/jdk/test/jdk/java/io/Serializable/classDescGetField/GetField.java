/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
 * @bug 4180735
 * @clean Foo GetField
 * @build GetField
 * @run main GetField
 *
 * @summary Make sure that getField() of ObjectStreamClass works correctly for
 *          object types.
 */
import java.io.*;

public class GetField implements Serializable{
    private static final long serialVersionUID = 1L;

    String str;
    int i;

    public static void main(String[] args) throws Exception {
        ObjectStreamClass cl = ObjectStreamClass.lookup(GetField.class);
        if (cl == null)
            throw new RuntimeException("Cannot resolve class : GetField");

        if (cl.getField("str") == null)
            throw new RuntimeException(
                "ObjectStreamClass.getField() failed for object type");

        if (cl.getField("i") == null)
            throw new RuntimeException(
                "ObjectStreamClass.getField() failed for primitive type");
    }
}
