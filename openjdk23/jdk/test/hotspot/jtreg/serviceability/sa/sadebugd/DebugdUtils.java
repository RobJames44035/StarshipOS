/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.Utils;


public class DebugdUtils {
    private static final String GOLDEN = "Debugger attached";
    private String serverID;
    private int registryPort;
    private boolean disableRegistry;
    private String serverName;
    private Process debugdProcess;

    public DebugdUtils() {
        this.serverID = null;
        this.registryPort = 0;
        this.disableRegistry = false;
        this.serverName = null;
        debugdProcess = null;
    }

    public void setRegistryPort(int registryPort) {
        this.registryPort = registryPort;
    }

    public void setDisableRegistry(boolean disableRegistry) {
        this.disableRegistry = disableRegistry;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void attach(long pid) throws IOException {
        JDKToolLauncher jhsdbLauncher = JDKToolLauncher.createUsingTestJDK("jhsdb");
        jhsdbLauncher.addVMArgs(Utils.getTestJavaOpts());
        jhsdbLauncher.addToolArg("debugd");
        jhsdbLauncher.addToolArg("--pid");
        jhsdbLauncher.addToolArg(Long.toString(pid));
        if (serverID != null) {
            jhsdbLauncher.addToolArg("--serverid");
            jhsdbLauncher.addToolArg(serverID);
        }
        if (registryPort != 0) {
            jhsdbLauncher.addToolArg("--registryport");
            jhsdbLauncher.addToolArg(Integer.toString(registryPort));
        }
        if (disableRegistry) {
            jhsdbLauncher.addToolArg("--disable-registry");
        }
        if (serverName != null) {
            jhsdbLauncher.addToolArg("--servername");
            jhsdbLauncher.addToolArg(serverName);
        }
        debugdProcess = (new ProcessBuilder(jhsdbLauncher.getCommand())).start();

        // Wait until debug server attached
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(debugdProcess.getErrorStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(GOLDEN)) {
                    break;
                }
            }
        }
    }

    public void detach() throws InterruptedException {
        if (debugdProcess != null) {
            debugdProcess.destroy();
            debugdProcess.waitFor();
        }
    }
}
