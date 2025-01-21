/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @bug 4413434
 * @summary Verify that generated java.lang.reflect implementation classes do
 *          not interfere with serialization's class resolution mechanism.
 */

import java.io.*;

// this class should be loaded from bootclasspath
public class Boot {
    public Boot(ObjectInputStream oin) throws Exception {
        oin.readObject();
    }
}
