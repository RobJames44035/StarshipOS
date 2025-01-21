/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package compiler.testlibrary;

import java.util.Arrays;
import jdk.test.lib.Asserts;
import jdk.test.lib.Platform;
import jdk.test.whitebox.WhiteBox;

import java.util.stream.IntStream;

public class CompilerUtils {

    private CompilerUtils() {
        // to prevent from instantiation
    }

    /**
     * Returns available compilation levels
     *
     * @return int array with compilation levels
     */
    public static int[] getAvailableCompilationLevels() {
        if (!WhiteBox.getWhiteBox().getBooleanVMFlag("UseCompiler")) {
            return new int[0];
        }
        if (WhiteBox.getWhiteBox().getBooleanVMFlag("TieredCompilation")) {
            Long flagValue = WhiteBox.getWhiteBox()
                    .getIntxVMFlag("TieredStopAtLevel");
            int maxLevel = flagValue.intValue();
            Asserts.assertEQ(new Long(maxLevel), flagValue,
                    "TieredStopAtLevel has value out of int capacity");
            return IntStream.rangeClosed(1, maxLevel).toArray();
        } else {
            if (Platform.isServer() && !Platform.isEmulatedClient()) {
                return new int[]{4};
            }
            if (Platform.isClient() || Platform.isMinimal() || Platform.isEmulatedClient()) {
                return new int[]{1};
            }
        }
        return new int[0];
    }

    /**
     * Returns maximum compilation level available
     * @return an int value representing maximum compilation level available
     */
    public static int getMaxCompilationLevel() {
        return Arrays.stream(getAvailableCompilationLevels())
                .max()
                .getAsInt();
    }
}
