/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package q;

import java.io.*;
import java.util.spi.ToolProvider;

public class ProviderImpl3 implements ToolProvider {
    public String name() {
        return "provider3";
    }

    public int run(PrintWriter out, PrintWriter err, String... args) {
        return 0;
    }
}
