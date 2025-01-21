/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public interface InterfaceMemberClassModifiers {

    Object nullWriter = null;

    class SomeClass1 implements InterfaceMemberClassModifiers {                 // OK
        public Object getOut() {
            return nullWriter;
        }
    }

    public class SomeClass2 implements InterfaceMemberClassModifiers {          // OK
        public Object getOut() {
            return nullWriter;
        }
    }

    // Compiler used to crash on these!  (after reporting error)

    protected class SomeClass3 implements InterfaceMemberClassModifiers {       // illegal
        public Object getOut() {
            return nullWriter;
        }
    }

    private class SomeClass4 implements InterfaceMemberClassModifiers {         // illegal
        public Object getOut() {
            return nullWriter;
        }
    }

}
