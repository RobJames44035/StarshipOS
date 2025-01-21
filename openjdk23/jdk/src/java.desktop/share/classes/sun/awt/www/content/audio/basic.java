/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package sun.awt.www.content.audio;

import java.io.IOException;
import java.net.ContentHandler;
import java.net.URLConnection;

import com.sun.media.sound.JavaSoundAudioClip;

/**
 * Basic .au and .snd audio handler returns an JavaSoundAudioClip object.
 * <p>
 * This provides backwards compatibility with the behavior
 * of ClassLoader.getResource().getContent() on JDK1.1.
 *
 * @author Jeff Nisewanger
 */
public class basic extends ContentHandler {
    public Object getContent(URLConnection uc) throws IOException {
        return JavaSoundAudioClip.create(uc);
    }
}
