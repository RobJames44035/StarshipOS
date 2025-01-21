/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class LocalClassCtorPrologue {

    int x;

    LocalClassCtorPrologue() {
        class Local {
            {
                x++;                // this should fail
            }
        }
        super();
    }

    public class Inner {
        public Inner() {
            class Local {
                {
                    x++;            // this should work
                }
            };
            super();
        }
    }
}
