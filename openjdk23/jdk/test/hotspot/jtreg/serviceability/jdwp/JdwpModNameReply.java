/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.io.DataInputStream;
import java.io.IOException;

/**
 * JDWP reply to the NAME command
 */
public class JdwpModNameReply extends JdwpReply {

    private byte[] name;

    protected void parseData(DataInputStream ds) throws IOException {
        name = readJdwpString(ds);
    }

    public String getModuleName() {
        return name == null ? null : new String(name);
    }

}
