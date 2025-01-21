/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package javacserver.client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import javacserver.shared.PortFile;
import javacserver.util.Log;

/**
 * Description of the arguments needed to start a javacserver client, as extracted from
 * the command line and configuration file.
 */
public record ClientConfiguration(PortFile portFile, String javaCommand, String[] javacArgs) {
    static ClientConfiguration fromCommandLineArguments(String... args) {
        String confFileName = getConfFileName(args);
        if (confFileName == null) {
            return null;
        }

        String confFileContent = getConfFileContent(confFileName);
        if (confFileContent == null) {
            return null;
        }

        String portFileName = getPortFileName(confFileContent);
        if (portFileName == null) {
            return null;
        }
        String javaCommand = getJavaCommandString(confFileContent);
        if (javaCommand == null) {
            return null;
        }

        PortFile portFile = new PortFile(portFileName);
        String[] javacArgs = Arrays.copyOfRange(args, 1, args.length);

        ClientConfiguration conf = new ClientConfiguration(portFile, javaCommand, javacArgs);
        return conf;
    }

    private static String getConfFileName(String[] args) {
        if (args.length < 1) {
            Log.error("Error: javacserver client: missing --conf=<conf file> argument");
            return null;
        }
        String[] conf = args[0].split("=", 2);
        if (conf.length != 2 || !conf[0].equalsIgnoreCase("--conf")) {
            Log.error("Error: javacserver client: first argument must be --conf=<conf file>");
            return null;
        }
        String confFileName = conf[1];
        if (!Files.exists(Path.of(confFileName))) {
            Log.error("Error: javacserver client: specified conf file does not exist");
            return null;
        }
        return confFileName;
    }

    private static String getConfFileContent(String confFile) {
        try {
            List<String> confFileLines = Files.readAllLines(Path.of(confFile));
            String confFileContent = String.join("\n", confFileLines);
            return confFileContent;
        } catch (IOException e) {
            Log.error("Cannot read configuration file " + confFile);
            Log.debug(e);
            return null;
        }
    }

    private static String getJavaCommandString(String confFileContent) {
        String serverCommandString = getConfValue("javacmd", confFileContent);
        if (serverCommandString.isEmpty()) {
            Log.error("Configuration file missing value for 'javacmd'");
            return null;
        } else {
            return serverCommandString;
        }
    }

    private static String getPortFileName(String confFileContent) {
        String portfileName = getConfValue("portfile", confFileContent);
        if (portfileName.isEmpty()) {
            Log.error("Configuration file missing value for 'portfile'");
            return null;
        } else {
            return portfileName;
        }
    }

    private static String getConfValue(String optionName, String content) {
        String result;
        int p = content.indexOf(optionName + "=");
        if (p == -1) {
            result = "";
        } else {
            p += optionName.length() + 1;
            int pe = content.indexOf('\n', p);
            if (pe == -1) pe = content.length();
            result = content.substring(p, pe);
        }
        return result.strip();
    }
}