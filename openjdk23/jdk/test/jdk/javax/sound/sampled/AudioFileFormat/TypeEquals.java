/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import javax.sound.sampled.AudioFileFormat;

/**
 * @test
 * @bug 4925483
 * @summary RFE: equals() should compare string in Encoding and Type
 */
public class TypeEquals {

    public static void main(String argv[]) throws Exception {
        // first test that we can create our own type
        // (the constructor was made public)
        AudioFileFormat.Type myType = new AudioFileFormat.Type("WAVE", "wav");

        // then check if this one equals this new one
        // with the static instance in AudioFileFormat.Type
        if (!myType.equals(AudioFileFormat.Type.WAVE)) {
         throw new Exception("Types do not equal!");
        }
    }
}
