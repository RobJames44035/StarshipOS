/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

/**
 * @test
 * @bug 4883060
 * @summary AudioSystem.getAudioFileTypes returns duplicates
 */
public class AudioFileTypeUniqueness {

    public static void main(String[] args) throws Exception {
        boolean foundDuplicates = false;
        AudioFileFormat.Type[]  aTypes = AudioSystem.getAudioFileTypes();
        for (int i = 0; i < aTypes.length; i++)
        {
            for (int j = 0; j < aTypes.length; j++)
            {
                if (aTypes[i].equals(aTypes[j]) && i != j) {
                    foundDuplicates = true;
                }
            }
        }
        if (foundDuplicates) {
            throw new Exception("Test failed");
        } else {
            System.out.println("Test passed");
        }
    }
}
