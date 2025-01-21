/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * The JDWP CLASSLOADER command
 */
public class JdwpClassLoaderCmd extends JdwpCmd<JdwpClassLoaderReply> {

    public JdwpClassLoaderCmd(long modId) {
        super(2, 18, JdwpClassLoaderReply.class, refLen());
        putRefId(modId);
    }

}
