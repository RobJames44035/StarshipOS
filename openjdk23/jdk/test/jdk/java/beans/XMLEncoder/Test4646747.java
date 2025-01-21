/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4646747
 * @summary Tests that persistence delegate is correct after memory stress
 * @run main/othervm -Xms16m -Xmx16m Test4646747
 */

import java.beans.DefaultPersistenceDelegate;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;

/**
 * This bug was introduced in 1.4 FCS but was working in 1.4.beta3
 */
public class Test4646747 {
    public static void main(String[] args) {
        XMLEncoder encoder = new XMLEncoder(System.out);
        encoder.setPersistenceDelegate(Test4646747.class, new MyPersistenceDelegate());
        // WARNING: This can eat up a lot of memory
        Object[] obs = new Object[10000];
        while (obs != null) {
            try {
                obs = new Object[obs.length + obs.length / 3];
            }
            catch (OutOfMemoryError error) {
                obs = null;
            }
        }
        PersistenceDelegate pd = encoder.getPersistenceDelegate(Test4646747.class);
        if (!(pd instanceof MyPersistenceDelegate))
            throw new Error("persistence delegate has been lost");
    }

    private static class MyPersistenceDelegate extends DefaultPersistenceDelegate {
    }
}
