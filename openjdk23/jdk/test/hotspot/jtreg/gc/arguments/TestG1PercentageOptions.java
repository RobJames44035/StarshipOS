/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package gc.arguments;

/*
 * @test TestG1PercentageOptions
 * @bug 8068942
 * @requires vm.gc.G1 & vm.opt.G1ConfidencePercent == null
 * @summary Test argument processing of various percentage options
 * @library /test/lib
 * @library /
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver gc.arguments.TestG1PercentageOptions
 */

import jdk.test.lib.process.OutputAnalyzer;

public class TestG1PercentageOptions {

    private static final class OptionDescription {
        public final String name;
        public final String[] valid;
        public final String[] invalid;

        OptionDescription(String name_, String[] valid_, String[] invalid_) {
            name = name_;
            valid = valid_;
            invalid = invalid_;
        }
    }

    private static final String[] rangeOneToHundredValid = new String[] {
        "1", "50", "95", "100" };
    private static final String[] rangeOneToHundredInvalid = new String[] {
        "0", "-10", "110", "bad" };

    // All of the G1 product arguments that are percentages.
    private static final OptionDescription[] percentOptions = new OptionDescription[] {
        new OptionDescription("G1ConfidencePercent", rangeOneToHundredValid, rangeOneToHundredInvalid)
        // Other percentage options are not yet validated by argument processing.
    };

    private static void check(String flag, boolean is_valid) throws Exception {
        OutputAnalyzer output = GCArguments.executeTestJava("-XX:+UseG1GC", flag, "-version");
        if (is_valid) {
            output.shouldHaveExitValue(0);
        } else {
            output.shouldHaveExitValue(1);
        }
    }

    private static void check(String name, String value, boolean is_valid) throws Exception {
        check("-XX:" + name + "=" + value, is_valid);
    }

    public static void main(String args[]) throws Exception {
        for (OptionDescription option : percentOptions) {
            for (String value : option.valid) {
                check(option.name, value, true);
            }
            for (String value : option.invalid) {
                check(option.name, value, false);
            }
            check("-XX:" + option.name, false);
            check("-XX:+" + option.name, false);
            check("-XX:-" + option.name, false);
        }
    }
}
