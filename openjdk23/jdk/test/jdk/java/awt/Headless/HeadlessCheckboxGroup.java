/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check that CheckboxGroup constructor and toString() method do not throw
 *          exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessCheckboxGroup
 */

public class HeadlessCheckboxGroup {
    public static void main(String args[]) {
        CheckboxGroup cbg = new CheckboxGroup();
        cbg.toString();
    }
}
