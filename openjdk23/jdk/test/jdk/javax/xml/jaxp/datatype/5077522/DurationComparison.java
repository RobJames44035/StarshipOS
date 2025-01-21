/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @bug 5077522
 * @summary test INDETERMINATE relations
 * @run main DurationComparison
 */
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

/**
 *
 * This is a JCK failure. The implementation fails with a list of
 * INDETERMINATE comparisons
 *
 *
 * @author Joe Wang <huizhe.wang@oracle.com>
 */
public class DurationComparison {
    static String errMsg;
    int passed = 0, failed = 0;

    public static void main(String[] args) {
        DurationComparison test = new DurationComparison();
        test.testCompareWithInderterminateRelation();
        test.testVerifyOtherRelations();
        test.tearDown();
    }

    /**
     * See JDK-5077522, Duration.compare returns equal for INDETERMINATE
     * comparisons
     */
    public void testCompareWithInderterminateRelation() {

        final String [][] partialOrder = { // partialOrder
           {"P1Y", "<>", "P365D"},
           {"P1Y", "<>", "P366D"},
           {"P1M", "<>", "P28D"},
           {"P1M", "<>", "P29D"},
           {"P1M", "<>", "P30D"},
           {"P1M", "<>", "P31D"},
           {"P5M", "<>", "P150D"},
           {"P5M", "<>", "P151D"},
           {"P5M", "<>", "P152D"},
           {"P5M", "<>", "P153D"},
           {"PT2419200S", "<>", "P1M"},
           {"PT2678400S", "<>", "P1M"},
           {"PT31536000S", "<>", "P1Y"},
           {"PT31622400S", "<>", "P1Y"},
           {"PT525600M", "<>", "P1Y"},
           {"PT527040M", "<>", "P1Y"},
           {"PT8760H", "<>", "P1Y"},
           {"PT8784H", "<>", "P1Y"},
           {"P365D", "<>", "P1Y"},
        };

        DatatypeFactory df = null;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }

        for (int valueIndex = 0; valueIndex < partialOrder.length; ++valueIndex) {
            Duration duration1 = df.newDuration(partialOrder[valueIndex][0]);
            Duration duration2 = df.newDuration(partialOrder[valueIndex][2]);
            int cmp = duration1.compare(duration2);
            int expected = ">".equals(partialOrder[valueIndex][1])
                     ? DatatypeConstants.GREATER
                     : "<".equals(partialOrder[valueIndex][1])
                     ? DatatypeConstants.LESSER
                     : "==".equals(partialOrder[valueIndex][1])
                     ? DatatypeConstants.EQUAL
                     : DatatypeConstants.INDETERMINATE;

            if (expected != cmp) {
                fail("returned " + cmp2str(cmp)
                    + " for durations \'" + duration1 + "\' and "
                    + duration2 + "\', but expected " + cmp2str(expected));
            } else {
                success("Comparing " + duration1 + " and " + duration2 +
                        ": INDETERMINATE");
            }
        }
    }

    /**
     * Verify cases around the INDETERMINATE relations
     */
    public void testVerifyOtherRelations() {

        final String [][] partialOrder = { // partialOrder
           {"P1Y", ">", "P364D"},
           {"P1Y", "<", "P367D"},
           {"P1Y2D", ">", "P366D"},
           {"P1M", ">", "P27D"},
           {"P1M", "<", "P32D"},
           {"P1M", "<", "P31DT1H"},
           {"P5M", ">", "P149D"},
           {"P5M", "<", "P154D"},
           {"P5M", "<", "P153DT1H"},
           {"PT2419199S", "<", "P1M"},  //PT2419200S -> P28D
           {"PT2678401S", ">", "P1M"},  //PT2678400S -> P31D
           {"PT31535999S", "<", "P1Y"}, //PT31536000S -> P365D
           {"PT31622401S", ">", "P1Y"}, //PT31622400S -> P366D
           {"PT525599M59S", "<", "P1Y"},  //PT525600M -> P365D
           {"PT527040M1S", ">", "P1Y"},  //PT527040M -> P366D
           {"PT8759H59M59S", "<", "P1Y"},    //PT8760H -> P365D
           {"PT8784H1S", ">", "P1Y"},    //PT8784H -> P366D
        };

        DatatypeFactory df = null;
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            ex.printStackTrace();
            fail(ex.toString());
        }

        for (int valueIndex = 0; valueIndex < partialOrder.length; ++valueIndex) {
            Duration duration1 = df.newDuration(partialOrder[valueIndex][0]);
            Duration duration2 = df.newDuration(partialOrder[valueIndex][2]);
            int cmp = duration1.compare(duration2);
            int expected = ">".equals(partialOrder[valueIndex][1])
                     ? DatatypeConstants.GREATER
                     : "<".equals(partialOrder[valueIndex][1])
                     ? DatatypeConstants.LESSER
                     : "==".equals(partialOrder[valueIndex][1])
                     ? DatatypeConstants.EQUAL
                     : DatatypeConstants.INDETERMINATE;

            if (expected != cmp) {
                fail("returned " + cmp2str(cmp)
                    + " for durations \'" + duration1 + "\' and "
                    + duration2 + "\', but expected " + cmp2str(expected));
            } else {
                success("Comparing " + duration1 + " and " + duration2 +
                        ": expected: " + cmp2str(expected) +
                        " actual: " + cmp2str(cmp));
            }
        }
    }
    public static String cmp2str(int cmp) {
        return cmp == DatatypeConstants.LESSER ? "LESSER"
             : cmp == DatatypeConstants.GREATER ? "GREATER"
             : cmp == DatatypeConstants.EQUAL ? "EQUAL"
             : cmp == DatatypeConstants.INDETERMINATE ? "INDETERMINATE"
             : "UNDEFINED";
    }

    public void tearDown() {

        System.out.println("\nNumber of tests passed: " + passed);
        System.out.println("Number of tests failed: " + failed + "\n");

        if (errMsg != null ) {
            throw new RuntimeException(errMsg);
        }
    }
    void fail(String msg) {
        if (errMsg == null) {
            errMsg = msg;
        } else {
            errMsg = errMsg + "\n" + msg;
        }
        failed++;
    }

    void success(String msg) {
        passed++;
        System.out.println(msg);
    }

}
