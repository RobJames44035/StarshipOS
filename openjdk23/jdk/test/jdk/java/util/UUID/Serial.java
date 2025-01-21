/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5014447
 * @summary Test deserialization of UUID
 * @key randomness
 */

import java.io.*;
import java.util.*;

/**
 * This class tests to see if UUID can be serialized and
 * deserialized properly. This originally failed because
 * the transient fields which were computed on demand are
 * not set back to the uninitialized value upon reconstitition.
 */
public class Serial {
    public static void main(String[] args) throws Exception {
        UUID a = UUID.randomUUID();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(a);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        UUID b = (UUID)ois.readObject();
        if (!a.equals(b))
            throw new RuntimeException("UUIDs not equal");
        oos.close();
        ois.close();
    }
}
