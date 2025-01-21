/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package test;

public class TestGetElementReferenceDataWithErrors {

    class Constructors extends BaseWithConstructor {
        Constructors(String name) {
            System.err.println();
            this(name/*getElement:PARAMETER:name*/.length/*getElement:METHOD:java.lang.String.length()*/());
            super(name/*getElement:PARAMETER:name*/.length/*getElement:METHOD:java.lang.String.length()*/());
            this(name/*getElement:PARAMETER:name*/.length/*getElement:METHOD:java.lang.String.length()*/());
        }
        Constructors(int name) {
            System.err.println();
            super(name/*getElement:PARAMETER:name*/);
            this(name/*getElement:PARAMETER:name*/);
            super(String/*getElement:CLASS:java.lang.String*/.valueOf(name/*getElement:PARAMETER:name*/));
        }
    }
    class BaseWithConstructor {
        BaseWithConstructor(int len) {}
    }
}
