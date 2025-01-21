/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/* @test
   @summary Test ModelStandardTransform setDirection method
   @modules java.desktop/com.sun.media.sound
*/

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.sound.sampled.*;

import com.sun.media.sound.*;

public class SetDirection {

    public static void main(String[] args) throws Exception {
        ModelStandardTransform transform = new ModelStandardTransform();
        transform.setDirection(ModelStandardTransform.DIRECTION_MAX2MIN);
        if(transform.getDirection() != ModelStandardTransform.DIRECTION_MAX2MIN)
            throw new RuntimeException("transform.getDirection() doesn't return ModelStandardTransform.DIRECTION_MAX2MIN!");
    }
}
