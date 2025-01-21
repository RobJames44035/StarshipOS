/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
package vm.share.options;

import nsk.share.TestBug;

/**
  * This class represents a simple Parser exception.
  */
public class ParserException extends TestBug {
        public ParserException(String message, Throwable cause) {
                super(message, cause);
        }

        public ParserException(String message) {
                super(message);
        }

        public ParserException(Throwable cause) {
                super(cause);
        }
}
