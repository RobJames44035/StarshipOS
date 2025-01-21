/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package jdk.test.lib.cli.predicate;

import jdk.test.lib.Platform;
import jdk.test.whitebox.cpuinfo.CPUInfo;

import java.util.function.BooleanSupplier;

public class CPUSpecificPredicate implements BooleanSupplier {
    private final String cpuArchPattern;
    private final String supportedCPUFeatures[];
    private final String unsupportedCPUFeatures[];

    public CPUSpecificPredicate(String cpuArchPattern,
            String supportedCPUFeatures[],
            String unsupportedCPUFeatures[]) {
        this.cpuArchPattern = cpuArchPattern;
        this.supportedCPUFeatures = supportedCPUFeatures;
        this.unsupportedCPUFeatures = unsupportedCPUFeatures;
    }

    @Override
    public boolean getAsBoolean() {
        if (!Platform.getOsArch().matches(cpuArchPattern)) {
            System.out.println("CPU arch " + Platform.getOsArch() + " does not match " + cpuArchPattern);
            return false;
        }

        if (supportedCPUFeatures != null) {
            for (String feature : supportedCPUFeatures) {
                if (!CPUInfo.hasFeature(feature)) {
                    System.out.println("CPU does not support " + feature
                            + " feature");
                    return false;
                }
            }
        }

        if (unsupportedCPUFeatures != null) {
            for (String feature : unsupportedCPUFeatures) {
                if (CPUInfo.hasFeature(feature)) {
                    System.out.println("CPU support " + feature + " feature");
                    return false;
                }
            }
        }
        return true;
    }
}
