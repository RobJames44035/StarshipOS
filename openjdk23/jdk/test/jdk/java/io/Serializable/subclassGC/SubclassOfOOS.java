/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.io.*;

public class SubclassOfOOS extends ObjectOutputStream {
        public SubclassOfOOS(OutputStream os) throws IOException {
                super(os);
        }

        public SubclassOfOOS() throws IOException {
                super();
        }
}
