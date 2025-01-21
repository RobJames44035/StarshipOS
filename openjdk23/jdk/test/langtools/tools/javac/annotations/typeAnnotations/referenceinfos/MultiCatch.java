/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import static java.lang.classfile.TypeAnnotation.TargetType.*;

/*
 * @test
 * @bug 8006732 8006775 8042451
 * @summary Test population of reference info for multicatch exception parameters
 * @author Werner Dietl
 * @compile -g Driver.java ReferenceInfoUtil.java MultiCatch.java
 * @run main Driver MultiCatch
 */
public class MultiCatch {

    @TADescription(annotation = "TA", type = EXCEPTION_PARAMETER, exceptionIndex = 0)
    @TADescription(annotation = "TB", type = EXCEPTION_PARAMETER, exceptionIndex = 1)
    public String multiCatch1() {
        return "void multiCatch1() { " +
            "try { new Object(); } catch (@TA NullPointerException | @TB IndexOutOfBoundsException e) { e.toString(); } }";
    }

    @TADescription(annotation = "TA", type = EXCEPTION_PARAMETER, exceptionIndex = 0)
    @TADescription(annotation = "TB", type = EXCEPTION_PARAMETER, exceptionIndex = 1)
    @TADescription(annotation = "TC", type = EXCEPTION_PARAMETER, exceptionIndex = 2)
    public String multiCatch2() {
        return "void multiCatch2() { " +
            "try { new Object(); } catch (@TA NullPointerException | @TB IndexOutOfBoundsException | @TC IllegalArgumentException e) { e.toString(); } }";
    }

    @TADescription(annotation = "TA", type = EXCEPTION_PARAMETER, exceptionIndex = 1)
    @TADescription(annotation = "TB", type = EXCEPTION_PARAMETER, exceptionIndex = 1)
    @TADescription(annotation = "TC", type = EXCEPTION_PARAMETER, exceptionIndex = 2)
    @TADescription(annotation = "TD", type = EXCEPTION_PARAMETER, exceptionIndex = 2)
    @TADescription(annotation = "TE", type = EXCEPTION_PARAMETER, exceptionIndex = 3)
    public String multiCatch3() {
        return "void multiCatch3() { " +
            "try { new Object(); } catch (NullPointerException e1) {}" +
            "try { new Object(); } catch (@TA @TB NullPointerException | @TC @TD IndexOutOfBoundsException | @TE IllegalArgumentException e2) { e2.toString(); } }";
    }

    @TADescription(annotation = "RTAs", type = EXCEPTION_PARAMETER, exceptionIndex = 0)
    @TADescription(annotation = "RTBs", type = EXCEPTION_PARAMETER, exceptionIndex = 1)
    public String multiCatchRepeatableAnnotation1() {
        return "void multiCatch1() { " +
                "try { new Object(); } catch (@RTA @RTA NullPointerException |" +
                " @RTB @RTB IndexOutOfBoundsException e) { e.toString(); } }";
    }

    @TADescription(annotation = "RTAs", type = EXCEPTION_PARAMETER, exceptionIndex = 0)
    @TADescription(annotation = "RTBs", type = EXCEPTION_PARAMETER, exceptionIndex = 1)
    @TADescription(annotation = "RTCs", type = EXCEPTION_PARAMETER, exceptionIndex = 2)
    public String multiCatchRepeatableAnnotation2() {
        return "void multiCatch2() { " +
                "try { new Object(); } catch (@RTA @RTA NullPointerException |" +
                " @RTB @RTB IndexOutOfBoundsException | @RTC @RTC IllegalArgumentException e) { e.toString(); } }";
    }

    @TADescription(annotation = "RTAs", type = EXCEPTION_PARAMETER, exceptionIndex = 1)
    @TADescription(annotation = "RTBs", type = EXCEPTION_PARAMETER, exceptionIndex = 1)
    @TADescription(annotation = "RTCs", type = EXCEPTION_PARAMETER, exceptionIndex = 2)
    @TADescription(annotation = "RTDs", type = EXCEPTION_PARAMETER, exceptionIndex = 2)
    @TADescription(annotation = "RTEs", type = EXCEPTION_PARAMETER, exceptionIndex = 3)
    public String multiCatchRepeatableAnnotation3() {
        return "void multiCatch3() { " +
                "try { new Object(); } catch (NullPointerException e1) {}" +
                "try { new Object(); } catch (@RTA @RTA @RTB @RTB NullPointerException |" +
                " @RTC @RTC @RTD @RTD IndexOutOfBoundsException |" +
                " @RTE @RTE IllegalArgumentException e2) { e2.toString(); } }";
    }
}
