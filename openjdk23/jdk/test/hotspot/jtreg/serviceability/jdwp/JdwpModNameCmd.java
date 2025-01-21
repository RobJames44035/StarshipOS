/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * The JDWP NAME command
 */
public class JdwpModNameCmd extends JdwpCmd<JdwpModNameReply> {

    public JdwpModNameCmd(long modId) {
        super(1, 18, JdwpModNameReply.class, refLen());
        putRefId(modId);
    }

}
