/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public final class FauxEnum3 extends SpecializedEnum {}

enum SpecializedEnum {
    RED {
        boolean special() {return true;}
    },
    GREEN,
    BLUE;
    boolean special() {return false;}
}
