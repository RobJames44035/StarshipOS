/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
   @summary Test SoftReceiver send method
   @modules java.desktop/com.sun.media.sound
*/

import javax.sound.midi.*;
import javax.sound.sampled.*;

import com.sun.media.sound.*;

public class Send_PitchBend {

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
        Receiver receiver = soft.synth.getReceiver();

        ShortMessage smsg = new ShortMessage();
        smsg.setMessage(ShortMessage.PITCH_BEND,0, 10,0);
        receiver.send(smsg, -1);
        assertEquals(channel.getPitchBend(), 10);
        smsg.setMessage(ShortMessage.PITCH_BEND,0, 9000%128,9000/128);
        receiver.send(smsg, -1);
        assertEquals(channel.getPitchBend(), 9000);

        soft.close();
    }
}
