/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * The JDWP VISIBLE CLASSES command
 */
public class JdwpVisibleClassesCmd extends JdwpCmd<JdwpVisibleClassesReply> {

    public JdwpVisibleClassesCmd(long classLoaderId) {
        super(1, 14, JdwpVisibleClassesReply.class, refLen());
        putRefId(classLoaderId);
    }

}
