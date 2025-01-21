/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.parser.hotspot;

import compiler.lib.ir_framework.driver.irmatching.irmethod.IRMethod;
import compiler.lib.ir_framework.driver.irmatching.parser.IREncodingParser;
import compiler.lib.ir_framework.driver.irmatching.parser.TestMethods;
import compiler.lib.ir_framework.shared.TestFrameworkException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class to parse the ideal compile phases and PrintOptoAssembly outputs of the test class from the hotspot_pid* file
 * of all methods identified by {@link IREncodingParser}.
 *
 * @see IRMethod
 * @see IREncodingParser
 */
public class HotSpotPidFileParser {
    private final State state;

    public HotSpotPidFileParser(String testClass, TestMethods testMethods) {
        this.state = new State(testClass, testMethods);
    }

    /**
     * Parse the hotspot_pid*.log file from the test VM. Read the ideal compile phase and PrintOptoAssembly outputs for
     * all methods defined by the IR encoding.
     */
    public LoggedMethods parse(String hotspotPidFileName) {
        try (var reader = Files.newBufferedReader(Paths.get(hotspotPidFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                state.update(line);
            }
            return state.loggedMethods();
        } catch (IOException e) {
            throw new TestFrameworkException("Error while reading " + hotspotPidFileName, e);
        }
    }
}
