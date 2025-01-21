/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */


package com.sun.java.swing.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class HelpMenu extends AbstractAction
{

    public HelpMenu()
    {
        super("Help");
        putValue("ActionCommandKey", "help-menu-command");
        putValue("ShortDescription", "Help operations");
        putValue("LongDescription", "Help operations");
        putValue("MnemonicKey", VALUE_MNEMONIC);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
    }

    public static final String VALUE_COMMAND = "help-menu-command";
    public static final String VALUE_NAME = "Help";
    public static final Integer VALUE_MNEMONIC = 72;
    public static final String VALUE_SHORT_DESCRIPTION = "Help operations";
    public static final String VALUE_LONG_DESCRIPTION = "Help operations";

}
