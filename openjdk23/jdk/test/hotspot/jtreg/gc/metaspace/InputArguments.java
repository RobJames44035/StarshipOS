/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.metaspace;

import java.lang.management.RuntimeMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * This class provides access to the input arguments to the VM.
 */
public class InputArguments {
    private static final List<String> args;

    static {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        args = runtimeMxBean.getInputArguments();
    }

    /**
     * Returns true if {@code arg} is an input argument to the VM.
     *
     * This is useful for checking boolean flags such as -XX:+UseSerialGC or
     * -XX:-UsePerfData.
     *
     * @param arg The name of the argument.
     * @return {@code true} if the given argument is an input argument,
     *         otherwise {@code false}.
     */
    public static boolean contains(String arg) {
        return args.contains(arg);
    }

    /**
     * Returns true if {@code prefix} is the start of an input argument to the
     * VM.
     *
     * This is useful for checking if flags describing a quantity, such as
     * -XX:+MaxMetaspaceSize=100m, is set without having to know the quantity.
     * To check if the flag -XX:MaxMetaspaceSize is set, use
     * {@code InputArguments.containsPrefix("-XX:MaxMetaspaceSize")}.
     *
     * @param prefix The start of the argument.
     * @return {@code true} if the given argument is the start of an input
     *         argument, otherwise {@code false}.
     */
    public static boolean containsPrefix(String prefix) {
        for (String arg : args) {
            if (arg.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }
}
