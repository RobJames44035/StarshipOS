/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package jdk.test.whitebox.cpuinfo;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import jdk.test.whitebox.WhiteBox;

/**
 * Information about CPU on test box.
 *
 * CPUInfo uses WhiteBox to gather information,
 * so WhiteBox class should be added to bootclasspath
 * and option -XX:+WhiteBoxAPI should be explicitly
 * specified on command line.
 */
public class CPUInfo {

    private static final List<String> features;
    private static final String additionalCPUInfo;

    static {
        WhiteBox wb = WhiteBox.getWhiteBox();

        Pattern additionalCPUInfoRE =
            Pattern.compile("([^(]*\\([^)]*\\)[^,]*),\\s*");

        String cpuFeaturesString = wb.getCPUFeatures();
        Matcher matcher = additionalCPUInfoRE.matcher(cpuFeaturesString);
        if (matcher.find()) {
            additionalCPUInfo = matcher.group(1);
        } else {
            additionalCPUInfo = "";
        }
        String splittedFeatures[] = matcher.replaceAll("").split("(, )| ");

        features = Collections.unmodifiableList(Arrays.
                                                asList(splittedFeatures));
    }

    /**
     * Get additional information about CPU.
     * For example, on X86 in will be family/model/stepping
     * and number of cores.
     *
     * @return additional CPU info
     */
    public static String getAdditionalCPUInfo() {
        return additionalCPUInfo;
    }

    /**
     * Get all known features supported by CPU.
     *
     * @return unmodifiable list with names of all known features
     *         supported by CPU.
     */
    public static List<String> getFeatures() {
        return features;
    }

    /**
     * Check if some feature is supported by CPU.
     *
     * @param feature Name of feature to be tested.
     * @return <b>true</b> if tested feature is supported by CPU.
     */
    public static boolean hasFeature(String feature) {
        return features.contains(feature.toLowerCase());
    }
}
