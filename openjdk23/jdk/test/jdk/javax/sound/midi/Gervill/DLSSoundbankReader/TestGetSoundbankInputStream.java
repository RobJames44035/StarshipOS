/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
 @summary Test DLSSoundbankReader getSoundbank(InputStream) method
 @modules java.desktop/com.sun.media.sound
*/

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.sound.midi.Patch;
import javax.sound.midi.Soundbank;

import com.sun.media.sound.DLSSoundbankReader;

public class TestGetSoundbankInputStream {

    private static void assertTrue(boolean value) throws Exception
    {
        if(!value)
            throw new RuntimeException("assertTrue fails!");
    }

    public static void main(String[] args) throws Exception {
        File file = new File(System.getProperty("test.src", "."), "ding.dls");
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        try
        {
            Soundbank dls = new DLSSoundbankReader().getSoundbank(bis);
            assertTrue(dls.getInstruments().length == 1);
            Patch patch = dls.getInstruments()[0].getPatch();
            assertTrue(patch.getProgram() == 0);
            assertTrue(patch.getBank() == 0);
        }
        finally
        {
            bis.close();
        }
    }
}
