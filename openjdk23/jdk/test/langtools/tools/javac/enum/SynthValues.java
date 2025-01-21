/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4984627
 * @summary Enums: +VALUES field has no synthetic attribute
 * @author gafter
 */

public enum SynthValues {
    red, blue, green;
    SynthValues[] $VALUES = null; // test for conflict with synth.
    public static void main(String[] args) {
        for (SynthValues t : values()) {
            System.out.println(t);
        }
    }
}
