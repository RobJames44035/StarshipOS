/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8006263
 * @summary Supplementary test cases needed for doclint
 * @modules jdk.javadoc/jdk.javadoc.internal.doclint
 *          jdk.javadoc/jdk.javadoc.internal.html
 */

import java.util.Objects;

import jdk.javadoc.internal.doclint.Checker;
import jdk.javadoc.internal.doclint.Messages;
import jdk.javadoc.internal.html.HtmlAttr;
import jdk.javadoc.internal.html.HtmlTag;

public class CoverageExtras {
    public static void main(String... args) {
        new CoverageExtras().run();
    }

    void run() {
        check(HtmlTag.A, HtmlTag.valueOf("A"), HtmlTag.values());
        check(HtmlAttr.ABBR, HtmlAttr.valueOf("ABBR"), HtmlAttr.values());
        check(HtmlAttr.AttrKind.INVALID, HtmlAttr.AttrKind.valueOf("INVALID"), HtmlAttr.AttrKind.values());
        check(HtmlTag.BlockType.BLOCK, HtmlTag.BlockType.valueOf("BLOCK"), HtmlTag.BlockType.values());
        check(HtmlTag.EndKind.NONE, HtmlTag.EndKind.valueOf("NONE"), HtmlTag.EndKind.values());
        check(HtmlTag.Flag.EXPECT_CONTENT, HtmlTag.Flag.valueOf("EXPECT_CONTENT"), HtmlTag.Flag.values());

        check(Checker.Flag.TABLE_HAS_CAPTION, Checker.Flag.valueOf("TABLE_HAS_CAPTION"), Checker.Flag.values());

        check(Messages.Group.ACCESSIBILITY, Messages.Group.valueOf("ACCESSIBILITY"), Messages.Group.values());
    }

    <T extends Enum<T>> void check(T expect, T value, T[] values) {
        if (!Objects.equals(expect, value)) {
            error("Mismatch: '" + expect + "', '" + value + "'");
        }
        if (!Objects.equals(expect, values[0])) {
            error("Mismatch: '" + expect + "', '" + values[0] + "'");
        }
    }

    void error(String msg) {
        System.err.println("Error: " + msg);
        errors++;
    }

    int errors;
}
