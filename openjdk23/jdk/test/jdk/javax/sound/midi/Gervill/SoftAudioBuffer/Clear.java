/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
   @summary Test SoftAudioBuffer clear method
   @modules java.desktop/com.sun.media.sound
*/

import javax.sound.midi.Patch;
import javax.sound.sampled.*;

import com.sun.media.sound.*;

public class Clear {

    private static void assertEquals(Object a, Object b) throws Exception
    {
        if(!a.equals(b))
            throw new RuntimeException("assertEquals fails!");
    }

    private static void assertTrue(boolean value) throws Exception
    {
        if(!value)
            throw new RuntimeException("assertTrue fails!");
    }

    public static void main(String[] args) throws Exception {
        AudioFormat frm = new AudioFormat(8000, 8, 1, true, false);
        SoftAudioBuffer buff = new SoftAudioBuffer(377, frm);
        buff.array();
        assertTrue(!buff.isSilent());
        buff.clear();
        assertTrue(buff.isSilent());
    }
}
