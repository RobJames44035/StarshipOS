/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6842557
 * @summary confirm that an instance which is created with new Enum ranges is
 * equivalent to another instance which is created with equivalent traditional
 * ranges or the same Enum ranges.
 */

import java.awt.font.NumericShaper;
import java.util.EnumSet;
import static java.awt.font.NumericShaper.*;

public class EqualsTest {
    public static void main(String[] args) {
        NumericShaper ns1 = getContextualShaper(ARABIC | TAMIL, TAMIL);
        NumericShaper ns2 = getContextualShaper(
                                EnumSet.of(Range.ARABIC, Range.TAMIL),
                                Range.TAMIL);
        NumericShaper ns3 = getContextualShaper(
                                EnumSet.of(Range.ARABIC, Range.TAMIL),
                                Range.TAMIL);
        NumericShaper ns4 = getContextualShaper(
                                EnumSet.of(Range.ARABIC, Range.TAMIL),
                                Range.ARABIC);

        if (!ns1.equals(ns2)) {
            throw new RuntimeException("ns1 != ns2: ns1=" + ns1 + ", ns2=" + ns2);
        }
        if (!ns2.equals(ns1)) {
            throw new RuntimeException("ns2 != ns1: ns1=" + ns1 + ", ns2=" + ns2);
        }
        if (!ns2.equals(ns3)) {
            throw new RuntimeException("ns2 != ns3: ns2=" + ns2 + ", ns3=" + ns3);
        }
        if (ns1.equals(ns4)) {
            throw new RuntimeException("ns1 == ns4: ns1=" + ns1 + ", ns4=" + ns4);
        }
        if (ns2.equals(ns4)) {
            throw new RuntimeException("ns2 == ns4: ns2=" + ns2 + ", ns4=" + ns4);
        }
    }
}
