/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.startupargs;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.test.lib.Asserts;

/**
 * @test
 * @summary Set repository path. Verify recording created in repo.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:StartFlightRecording:name=TestStartRecording,settings=profile -XX:FlightRecorderOptions:repository=./repo jdk.jfr.startupargs.TestRepositoryPath
 */
public class TestRepositoryPath {

    public static void main(String[] args) throws Exception {

        final Path repo = Paths.get(".", "repo");
        System.out.println("dot is " + Paths.get(".").toRealPath());
        Asserts.assertTrue(Files.exists(repo), "Base repo path does not exist: " + repo);
        Path recordingPath = StartupHelper.findRecordingFileInRepo(repo);
        System.out.println("recordingPath: " + recordingPath);
        Asserts.assertNotNull(recordingPath, "Could not find recording file in repository " + repo);
    }
}
