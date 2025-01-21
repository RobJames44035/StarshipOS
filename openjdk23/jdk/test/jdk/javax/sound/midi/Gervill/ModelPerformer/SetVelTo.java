/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
   @summary Test ModelPerformer setVelTo method
   @modules java.desktop/com.sun.media.sound
*/

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.sound.sampled.*;

import com.sun.media.sound.*;

public class SetVelTo {

    public static void main(String[] args) throws Exception {
        ModelPerformer performer = new ModelPerformer();
        performer.setVelTo(10);
        if(performer.getVelTo() != 10)
            throw new RuntimeException("performer.getVelTo() didn't return 10!");
    }
}
