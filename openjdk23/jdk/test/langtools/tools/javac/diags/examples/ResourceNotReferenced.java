/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.try.resource.not.referenced
// options: -Xlint:try

import java.io.*;

class ResourceNotReferenced {
    void m() throws IOException {
        try (Writer out = new StringWriter()) {
        }
    }
}
