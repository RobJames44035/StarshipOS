/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import jdk.test.whitebox.WhiteBox;

public final class Settings {

    final static long ROOT_CHUNK_WORD_SIZE = WhiteBox.getWhiteBox().rootChunkWordSize();
    final static long WORD_SIZE = WhiteBox.getWhiteBox().wordSize();
    static Settings theSettings;

    static Settings settings()  {
       if (theSettings == null) {
            theSettings = new Settings();
       }
       return theSettings;
    }

}
