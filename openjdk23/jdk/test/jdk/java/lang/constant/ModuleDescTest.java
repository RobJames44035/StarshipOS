/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @summary Testing ModuleDesc.
 * @run junit ModuleDescTest
 */
import java.lang.constant.ModuleDesc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class ModuleDescTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "abc\\", "ab\\c", "\u0001", "\u001e",
        ":", ":foo", "foo:", "foo:bar",
        "@", "@foo", "foo@", "foo@bar",
        "\\", "\\foo", "foo\\", "foo\\bar",
        "\u0000", "\u0000foo", "foo\u0000", "foo\u0000bar",
        "\u001f", "\u001ffoo", "foo\u001f", "foo\u001fbar"})
    public void testInvalidModuleNames(String mdl) {
        assertThrows(IllegalArgumentException.class, () -> ModuleDesc.of(mdl));
    }

    @Test
    public void testNullModuleName() {
        assertThrows(NullPointerException.class, () -> ModuleDesc.of(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "", "a\\\\b", "a.b/c", "a\\@b\\: c",
        ".", ".foo", "foo.", "foo.bar",
        "..", "..foo", "foo..", "foo..bar",
        "[", "[foo", "foo[", "foo[bar",
        ";", ";foo", "foo;", "foo;bar",
        "\\\\", "\\\\foo", "foo\\\\", "foo\\\\bar",
        "\\\\\\\\", "\\\\\\\\foo", "foo\\\\\\\\", "foo\\\\\\\\bar",
        "\\:", "\\:foo", "foo\\:", "foo\\:bar",
        "\\:\\:", "\\:\\:foo", "foo\\:\\:", "foo\\:\\:bar",
        "\\@", "\\@foo", "foo\\@", "foo\\@bar",
        "\\@\\@", "\\@\\@foo", "foo\\@\\@", "foo\\@\\@bar"})
    public void testValidModuleNames(String mdl) {
        assertEquals(ModuleDesc.of(mdl), ModuleDesc.of(mdl));
        assertEquals(ModuleDesc.of(mdl).name(), mdl);
    }
}
