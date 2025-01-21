/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6176120
 * @summary Tests bean that contains constructor marked with ConstructorProperties annotation
 * @run main/othervm Test6176120
 * @author Sergey Malenkov
 */

import java.beans.ConstructorProperties;

public final class Test6176120 extends AbstractTest {
    public static void main(String[] args) {
        new Test6176120().test();
    }

    protected ImmutableBean getObject() {
        return new ImmutableBean(1, -1);
    }

    protected ImmutableBean getAnotherObject() {
        return null; // TODO: could not update property
        // return new ImmutableBean(-1, 1);
    }

    public static final class ImmutableBean {
        private final int x;
        private final int y;

        @ConstructorProperties({"x", "y"})
        public ImmutableBean( int x, int y ) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }
}
