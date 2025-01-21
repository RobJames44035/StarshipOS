/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
   @summary Test SoftChannel omni method
   @modules java.desktop/com.sun.media.sound
*/

import javax.sound.midi.*;
import javax.sound.sampled.*;

import com.sun.media.sound.*;

public class Omni {

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
        SoftTestUtils soft = new SoftTestUtils();
        MidiChannel channel = soft.synth.getChannels()[0];

        channel.setOmni(true);
        // Poly or Omni not supported by GM2
        // getOmni() should always return false
        assertEquals(channel.getOmni(), false);
        channel.setOmni(false);
        assertEquals(channel.getOmni(), false);

        soft.close();
    }
}
