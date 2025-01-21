/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

package propertiesparser.parser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class to facilitate manipulating compiler.properties.
 */
public class MessageFile {

    MessageLine firstLine;
    public Map<String, Message> messages = new TreeMap<>();
    public File file;
    public String keyPrefix;

    public MessageFile(File file, String keyPrefix) throws IOException {
        this.file = file;
        this.keyPrefix = keyPrefix;
        read(file);
    }

    final void read(File in) throws IOException {
        MessageLine currLine = null;
        for (String line : Files.readAllLines(in.toPath())) {
            if (currLine == null)
                firstLine = currLine = new MessageLine(line);
            else
                currLine = currLine.append(line);
            if (line.startsWith(keyPrefix + ".")) {
                int eq = line.indexOf("=");
                if (eq > 0)
                    messages.put(line.substring(0, eq).trim(), new Message(currLine));
            }
        }
    }
}
