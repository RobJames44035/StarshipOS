/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8266856
 * @modules jdk.javadoc/jdk.javadoc.internal.doclint
 *          jdk.javadoc/jdk.javadoc.internal.html
 * @run main TestVoidHtmlElements
 */

import jdk.javadoc.internal.html.HtmlTree;
import jdk.javadoc.internal.html.HtmlTag;

public class TestVoidHtmlElements {

    public static void main(String[] args) {
        int checks = 0;

        // For tags that are represented as both an HtmlTag and a TagName,
        // check that the definition of void-ness is the same.
        for (HtmlTag htmlTag : HtmlTag.values()) {
            try {
                checks++;
                check(htmlTag);
            } catch (IllegalArgumentException e) {
                // no matching TagName
            }
        }

        if (checks == 0) { // suspicious
            throw new AssertionError();
        }
        System.out.println(checks + " checks passed");
    }

    private static void check(HtmlTag htmlTag) {
        boolean elementIsVoid = HtmlTree.of(htmlTag).isVoid();
        boolean elementHasNoEndTag = htmlTag.endKind == HtmlTag.EndKind.NONE;
        if (elementIsVoid != elementHasNoEndTag) {
            throw new AssertionError(htmlTag + ", " + elementIsVoid + ", " + elementHasNoEndTag);
        }
    }
}
