/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
   @summary Test ModelByteBufferWavetable setIdentifier(ModelIdentifier) method
   @modules java.desktop/com.sun.media.sound
*/

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.sound.sampled.*;

import com.sun.media.sound.*;

public class SetIdentifier {

    public static void main(String[] args) throws Exception {
        ModelDestination dest = new ModelDestination();
        dest.setIdentifier(ModelDestination.DESTINATION_EG1_ATTACK);
        if(dest.getIdentifier() != ModelDestination.DESTINATION_EG1_ATTACK)
            throw new RuntimeException("dest.getIdentifier() is not equals ModelDestination.DESTINATION_EG1_ATTACK!");
    }
}
