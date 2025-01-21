/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package q;

import sun.security.util.DerEncoder;
import sun.security.util.DerOutputStream;

public class NoRepl implements DerEncoder {
    @Override
    public void encode(DerOutputStream out) {
        throw new RuntimeException();
    }
}
