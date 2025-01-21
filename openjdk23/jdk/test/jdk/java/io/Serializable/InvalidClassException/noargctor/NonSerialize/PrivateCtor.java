/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @bug 4093279
 */

package NonSerializable;

public class PrivateCtor {
    private PrivateCtor() {
    }
    /* need to have at least one protected constructor to extend this class.*/
    public PrivateCtor(int i) {
    }
};
