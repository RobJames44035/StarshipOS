/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.net.InetAddress;
import java.util.*;

public class Presets {
    public static final String TEST_DEFAULT_EXECUTION;
    public static final String TEST_STANDARD_EXECUTION;

    static {
        String loopback = InetAddress.getLoopbackAddress().getHostAddress();

        TEST_DEFAULT_EXECUTION = "failover:0(jdi:hostname(" + loopback + "))," +
                                 "1(jdi:launch(true)), 2(jdi), 3(local)";
        TEST_STANDARD_EXECUTION = "failover:0(jdi:hostname(" + loopback + "))," +
                                  "1(jdi:launch(true)), 2(jdi)";
    }

    public static String[] addExecutionIfMissing(String[] args) {
        if (Arrays.stream(args).noneMatch(Presets::remoteRelatedOption)) {
            List<String> augmentedArgs = new ArrayList<>();

            augmentedArgs.add("--execution");
            augmentedArgs.add(Presets.TEST_DEFAULT_EXECUTION);
            augmentedArgs.addAll(List.of(args));

            return augmentedArgs.toArray(s -> new String[s]);
        }

        return args;
    }

    private static boolean remoteRelatedOption(String option) {
        return "--execution".equals(option) ||
               "--add-modules".equals(option) ||
               option.startsWith("-R");
    }
}
