/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
 @summary Test DLSSoundbankReader getSoundbank(File) method
 @modules java.desktop/com.sun.media.sound
*/

import java.io.File;
import java.net.URL;

import javax.sound.midi.Patch;
import javax.sound.midi.Soundbank;

import com.sun.media.sound.DLSSoundbankReader;

public class TestGetSoundbankUrl {

    private static void assertTrue(boolean value) throws Exception
    {
        if(!value)
            throw new RuntimeException("assertTrue fails!");
    }

    public static void main(String[] args) throws Exception {
        File file = new File(System.getProperty("test.src", "."), "ding.dls");
        URL url = file.toURI().toURL();
        Soundbank dls = new DLSSoundbankReader().getSoundbank(url);
        assertTrue(dls.getInstruments().length == 1);
        Patch patch = dls.getInstruments()[0].getPatch();
        assertTrue(patch.getProgram() == 0);
        assertTrue(patch.getBank() == 0);
    }
}
