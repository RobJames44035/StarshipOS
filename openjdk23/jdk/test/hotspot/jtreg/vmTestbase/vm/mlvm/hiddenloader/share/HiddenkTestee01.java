/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

package vm.mlvm.hiddenloader.share;

import vm.mlvm.share.Env;

public class HiddenkTestee01 {
    public final static String muzzy = "BIG \uFFFF\u0000\uFFFE\uFEFF MUZZY";
    public final static String
    theDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrum
    = String.format("%65500c%X", 'c', Env.getRNG().nextLong());

    public final String beatingTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrum() {
        return theDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrumIsTheDrum;
    }

    public final String toString() {
        return "Something that looks like " + super.toString();
    }

    @Override
    public int hashCode() {
        throw new RuntimeException("Making fun of errors");
    }
}
