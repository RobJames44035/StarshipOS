/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.io.*;

public class NewBeforeOuterConstructed extends PrintStream {
      private class NullOutputStream extends OutputStream {
              public NullOutputStream() {
                      super();
              }
              public void write(int b) { }
              public void write(byte b[]) { }
              public void write(byte b[], int off, int len) { }
              public void flush() { }
              public void close() { }
      }
       public NewBeforeOuterConstructed() {
                // The 'new' below is illegal, as the outer
                // constructor has not been called when the
                // implicit reference to 'this' is evaluated
                // during the new instance expression.
              super(new NullOutputStream());
      }
}
