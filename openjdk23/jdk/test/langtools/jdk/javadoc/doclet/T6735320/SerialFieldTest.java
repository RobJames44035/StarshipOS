/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

import java.io.ObjectStreamField;
import java.io.Serializable;

public class SerialFieldTest implements Serializable {
    /**
     * @serialField
     * @serialField count int
     * @serialField name String a test
     */
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("i", int.class),
        new ObjectStreamField("count", Integer.TYPE),
        new ObjectStreamField("name", String.class)
    };
}
