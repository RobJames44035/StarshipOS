/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8033980
 * @summary test that invalid durations are caught
 * @run main CalendarDuration1416Test
 */

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;


/**
 *
 * @author Joe Wang huizhe.wang@oracle.com
 */
public class CalendarDuration1416Test {

    /**
     * main method.
     *
     * @param args Standard args.
     */
    public static void main(String[] args) {
        test1416("PT1D1H");
        test1416("PT1D1H30M");
        test1416("PT1D1H30S");
    }

    static void test1416(String d) {
        try
        {
            DatatypeFactory dtf = DatatypeFactory.newInstance();
            dtf.newDuration(d);
            throw new Error("no bug for " + d);
        } catch (DatatypeConfigurationException ex) {
            fail(ex.getMessage());
        }
        catch (NullPointerException e) {
            fail("NPE bug ! " + d);

        }
        catch(IllegalArgumentException e)
        {
            System.out.println("OK, BUG FIXED for " + d);
        }

    }

    static void fail(String errMessage) {
        throw new RuntimeException(errMessage);
    }
}
