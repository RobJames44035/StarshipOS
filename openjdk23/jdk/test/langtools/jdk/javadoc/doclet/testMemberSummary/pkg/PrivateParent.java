/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg;

class PrivateParent {
    /**
     * Test private constructor.
     * @param i a test parameter.
     */
    private PrivateParent(int i) {
    }

    /**
     * Test to make sure the member summary inherits documentation
     * properly.
     */
    public PrivateParent returnTypeTest() {
        return this;
    }
}
