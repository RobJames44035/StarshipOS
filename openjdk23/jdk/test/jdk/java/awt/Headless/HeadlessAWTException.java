/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check that AWTException can be created in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessAWTException
 */

public class HeadlessAWTException {

    public static void main (String[] args) {
        AWTException e = new AWTException("aa");
    }
}
