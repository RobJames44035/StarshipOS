/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4030426
 * @summary Compiler should accept non-identifier expressions as
 * the outer instance in a 'new' expression for an inner class.
 *
 * @compile CascadedInnerNewInstance.java
 */

class CascadedInnerNewInstance {

    Object createInner1InnerInnerMost() {
        return new Inner1().new InnerMost().new InnerInnerMost();
    }

    class Inner1 {
        class InnerMost {
            class InnerInnerMost { }
        }
    }

    Inner2.InnerMost createInner2InnerMost() {
        return new Inner2().new InnerMost();
    }

    Object createInner2InnerInnerMost() {
        return createInner2InnerMost().new InnerInnerMost();
    }

    class Inner2 {
        class InnerMost {
            class InnerInnerMost { }
        }
    }

}
