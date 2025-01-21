/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import javax.sound.sampled.Control;

/**
 * @test
 * @bug 8236980
 */
public final class ToString {

    public static void main(String[] args) {
        Control.Type type = new Control.Type("nameToTest") {};
        if (!type.toString().equals("nameToTest")) {
            throw new RuntimeException("wrong string: " + type);
        }
    }
}
