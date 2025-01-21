/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.io.DataInputStream;
import java.io.IOException;

/**
 * The reply to the JDWP MODULE command
 */
public class JdwpModuleReply extends JdwpReply {

    private long moduleId;

    protected void parseData(DataInputStream ds) throws IOException {
        moduleId = readRefId(ds);
    }

    public long getModuleId() {
        return moduleId;
    }

}
