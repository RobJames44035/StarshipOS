/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4559052
 * @summary Hashtable's hashCode method always returns zero(!)
 * @author Josh Bloch
 */

import java.util.Hashtable;
import java.util.Map;

public class HashCode {
    public static void main(String[] args) throws Exception {
        Map m = new Hashtable();
        if (m.hashCode() != 0)
            throw new Exception("Empty Hashtable has nonzero hashCode.");

    }
}
