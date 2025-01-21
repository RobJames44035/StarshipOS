/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * The JDWP MODULE command
 */
public class JdwpModuleCmd extends JdwpCmd<JdwpModuleReply> {

    public JdwpModuleCmd(long refId) {
        super(19, 2, JdwpModuleReply.class, refLen());
        putRefId(refId);
    }

}
