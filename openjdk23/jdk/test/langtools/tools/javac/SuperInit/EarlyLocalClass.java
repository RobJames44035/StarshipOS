/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
public class EarlyLocalClass {
    EarlyLocalClass() {
        class Local {
            void foo() {
                EarlyLocalClass.this.hashCode();    // this should FAIL
            }
        }
        new Local();                                // this is OK
        super();
        new Local();                                // this is OK
    }
}
