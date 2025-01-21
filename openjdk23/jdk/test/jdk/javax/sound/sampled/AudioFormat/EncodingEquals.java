/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import javax.sound.sampled.AudioFormat;

/**
 * @test
 * @bug 4925483
 * @summary RFE: equals() should compare string in Encoding and Type
 */
public class EncodingEquals {

    public static void main(String argv[]) throws Exception {
         // first test that we can create our own encoding
         // (the constructor was made public)
         AudioFormat.Encoding myType = new AudioFormat.Encoding("PCM_SIGNED");

         // then check if this one equals this new one
         // with the static instance in AudioFormat.Encoding
         if (!myType.equals(AudioFormat.Encoding.PCM_SIGNED)) {
             throw new Exception("Encodings do not equal!");
         }
    }
}
