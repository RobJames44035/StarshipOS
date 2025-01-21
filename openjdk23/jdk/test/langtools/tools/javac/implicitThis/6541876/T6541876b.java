/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 6541876 6569091
 * @summary  "Enclosing Instance" error new in 1.6
 *
 */

public class T6541876b {

    enum ENUM {
        ENUM_CONST {
            public AbstractClass method() {
                return new AbstractClass() {
                    public boolean method() {
                        return true;
                    }
                };
            }
        };

        public abstract AbstractClass method();

        private abstract class AbstractClass {
            public abstract boolean method();
        }
    }

    public static void main(String[] args) {
        ENUM.ENUM_CONST.method();
    }
}
