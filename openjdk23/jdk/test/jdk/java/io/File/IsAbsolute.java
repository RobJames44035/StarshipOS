/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/* @test
 * @bug 4022397 8287843
 * @summary General test for isAbsolute
 * @run junit IsAbsolute
 */

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class IsAbsolute {
    @EnabledOnOs(OS.WINDOWS)
    @ParameterizedTest
    @ValueSource(strings = {"c:\\foo\\bar", "c:/foo/bar", "\\\\foo\\bar"})
    public void windowsAbsolute(String path) throws IOException {
        assertTrue(new File(path).isAbsolute());
    }

    @EnabledOnOs(OS.WINDOWS)
    @ParameterizedTest
    @ValueSource(strings = {"/foo/bar", "\\foo\\bar", "c:foo\\bar"})
    public void windowsNotAbsolute(String path) throws IOException {
        assertFalse(new File(path).isAbsolute());
    }

    @EnabledOnOs({OS.LINUX, OS.MAC})
    @ParameterizedTest
    @ValueSource(strings = {"/foo", "/foo/bar"})
    public void unixAbsolute(String path) throws IOException {
        assertTrue(new File(path).isAbsolute());
    }

    @EnabledOnOs({OS.LINUX, OS.MAC})
    @ParameterizedTest
    @ValueSource(strings = {"foo", "foo/bar"})
    public void unixNotAbsolute(String path) throws IOException {
        assertFalse(new File(path).isAbsolute());
    }
}
