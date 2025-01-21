/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package tools.javac.combo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

/**
 * TemplateTest
 */
class TemplateTest {
    final Map<String, Template> vars = new HashMap<>();

    @BeforeEach
    void before() { vars.clear(); }

    private void assertTemplate(String expected, String template) {
        String result = Template.expandTemplate(template, vars);
        assertEquals(expected, result, "for " + template);
    }

    private String dotIf(String s) {
        return s == null || s.isEmpty() ? "" : "." + s;
    }

    @Test
    void testTemplateExpansion() {
        vars.put("A", s -> "a" + dotIf(s));
        vars.put("B", s -> "b" + dotIf(s));
        vars.put("C", s -> "#{A}#{B}");
        vars.put("D", s -> "#{A" + dotIf(s) + "}#{B" + dotIf(s) + "}");
        vars.put("_D", s -> "d");

        assertTemplate("", "");
        assertTemplate("foo", "foo");
        assertTemplate("a", "#{A}");
        assertTemplate("a", "#{A.}");
        assertTemplate("a.FOO", "#{A.FOO}");
        assertTemplate("aa", "#{A}#{A}");
        assertTemplate("ab", "#{C}");
        assertTemplate("ab", "#{C.FOO}");
        assertTemplate("ab", "#{C.}");
        assertTemplate("a.FOOb.FOO", "#{D.FOO}");
        assertTemplate("ab", "#{D}");
        assertTemplate("d", "#{_D}");
        assertTemplate("#{A", "#{A");
    }

    @Test
    void testIndexedTemplate() {
        vars.put("A[0]", s -> "a" );
        vars.put("A[1]", s -> "b" );
        vars.put("A[2]", s -> "c" );
        vars.put("X", s -> "0");
        assertTemplate("a", "#{A[0]}");
        assertTemplate("b", "#{A[1]}");
        assertTemplate("c", "#{A[2]}");
    }

    @Test
    void testAngleBrackets() {
        vars.put("X", s -> "xyz");
        assertTemplate("List<String> ls = xyz;", "List<String> ls = #{X};");
    }

    @Test
    void testUnknownKey() {
        assertThrows(IllegalStateException.class, () -> assertTemplate("#{Q}", "#{Q}"));
    }
}
