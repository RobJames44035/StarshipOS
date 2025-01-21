/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 4396492 4396496 4778510 6850113
 * @summary verify that the embedding values processed by the bidi code use negative values to
 * indicate overrides, rather than using bit 7.  Also tests Bidi without loading awt classes to
 * confirm that Bidi can be used without awt. Verify that embedding level 0 is properly mapped
 * to the base embedding level.
 * @modules java.desktop
 */

import java.awt.Color;
import java.awt.Frame;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.text.Bidi;

public class BidiEmbeddingTest {
    public static void main(String[] args) {
        // to regress embedding test against old fix, call with an arg.  A window will pop
        // up causing awt lib to be loaded so the vm won't die with the unsatisfied link error.
        if (args.length > 0) {
            Frame f = new Frame();
            f.setSize(300, 300);
            f.setBackground(Color.white);
            f.show();
        }

        test1();
        test2();
    }

    static void test1() {
        String target = "BACK WARDS";
        String str = "If this text is >" + target + "< the test passed.";
        int start = str.indexOf(target);
        int limit = start + target.length();

        System.out.println("start: " + start + " limit: " + limit);

        AttributedString astr = new AttributedString(str);
        astr.addAttribute(TextAttribute.BIDI_EMBEDDING,
                         new Integer(-1),
                         start,
                         limit);

        Bidi bidi = new Bidi(astr.getIterator());

        for (int i = 0; i < bidi.getRunCount(); ++i) {
            System.out.println("run " + i +
                               " from " + bidi.getRunStart(i) +
                               " to " + bidi.getRunLimit(i) +
                               " at level " + bidi.getRunLevel(i));
        }

        System.out.println(bidi);

        byte[] embs = new byte[str.length() + 3];
        for (int i = start + 1; i < limit + 1; ++i) {
            embs[i] = -1;
        }

        Bidi bidi2 = new Bidi(str.toCharArray(), 0, embs, 1, str.length(), Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        for (int i = 0; i < bidi2.getRunCount(); ++i) {
            System.out.println("run " + i +
                               " from " + bidi2.getRunStart(i) +
                               " to " + bidi2.getRunLimit(i) +
                               " at level " + bidi2.getRunLevel(i));
        }

        System.out.println(bidi2 + "\n");

        if (bidi.getRunCount() != 3 || bidi2.getRunCount() != 3) {
            throw new Error("Bidi run count incorrect");
        } else {
            System.out.println("test1() passed.\n");
        }
    }

    // make sure BIDI_EMBEDDING values of 0 are mapped to base run direction, instead of flagging an error.
    static void test2() {
        String target = "BACK WARDS";
        String str = "If this text is >" + target + "< the test passed.";
        int length = str.length();
        int start = str.indexOf(target);
        int limit = start + target.length();

        System.out.println("start: " + start + " limit: " + limit);

        AttributedString astr = new AttributedString(str);
        astr.addAttribute(TextAttribute.RUN_DIRECTION, TextAttribute.RUN_DIRECTION_RTL);

        astr.addAttribute(TextAttribute.BIDI_EMBEDDING,
                         new Integer(-3),
                         start,
                         limit);

        Bidi bidi = new Bidi(astr.getIterator());

        for (int i = 0; i < bidi.getRunCount(); ++i) {
            System.out.println("run " + i +
                               " from " + bidi.getRunStart(i) +
                               " to " + bidi.getRunLimit(i) +
                               " at level " + bidi.getRunLevel(i));
        }

        System.out.println(bidi + "\n");

        if (bidi.getRunCount() != 6) { // runs of spaces and angles at embedding bound,s and final period, each get level 1
            throw new Error("Bidi embedding processing failed");
        } else {
            System.out.println("test2() passed.\n");
        }
    }
}
