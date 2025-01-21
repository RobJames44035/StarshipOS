/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/* @test
 * @bug 4373844
 * @summary Verify that partially initialized ObjectStreamClass instances
 *          cannot be obtained from ObjectStreamClass.lookup() in the event
 *          that the target class is uninitializable.
 */

import java.io.*;

class A implements Serializable {
    // existence of SVUID forces class initialization during classdesc init
    private static final long serialVersionUID = 0L;

    static {
        if ("foo".equals("foo")) {      // force class initialization failure
            throw new Error();
        }
    }
}

public class PartialClassDesc {
    public static void main(String[] args) throws Exception {
        Class<?> cl = Class.forName(
            "A", false, PartialClassDesc.class.getClassLoader());
        ObjectStreamClass desc = null;
        try {
            desc = ObjectStreamClass.lookup(cl);
        } catch (Throwable th) {
        }
        try {
            desc = ObjectStreamClass.lookup(cl);
        } catch (Throwable th) {
        }
        if (desc != null) {
            throw new Error("should not be able to obtain class descriptor");
        }
    }
}
