/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * The JDWP ALLMODULES command
 */
public class JdwpAllModulesCmd extends JdwpCmd<JdwpAllModulesReply> {

    public JdwpAllModulesCmd() {
        super(22, 1, JdwpAllModulesReply.class, 0);
    }
}
