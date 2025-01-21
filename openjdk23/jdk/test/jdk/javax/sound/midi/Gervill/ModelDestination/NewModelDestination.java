/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
   @summary Test ModelDestination constructor
   @modules java.desktop/com.sun.media.sound
*/

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.sound.sampled.*;

import com.sun.media.sound.*;

public class NewModelDestination {

    public static void main(String[] args) throws Exception {
        ModelDestination dest = new ModelDestination();
        if(dest.getIdentifier() != ModelDestination.DESTINATION_NONE)
            throw new RuntimeException("dest.getIdentifier() is not equals ModelDestination.DESTINATION_NONE!");
        if(!(dest.getTransform() instanceof ModelStandardTransform))
            throw new RuntimeException("dest.getTransform() is not instancoef ModelStandardTransform!");
    }
}
