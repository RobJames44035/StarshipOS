/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check that Checkbox constructors trow HeadlessException in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessCheckbox
 */

public class HeadlessCheckbox {
    public static void main(String args[]) {
        Checkbox b;

        boolean exceptions = false;
        try {
            b = new Checkbox();
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");

        exceptions = false;
        try {
            b = new Checkbox("Hey, check it out!");
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");

        exceptions = false;
        try {
            b = new Checkbox("Hey, check it out!", true);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");

        exceptions = false;
        try {
            b = new Checkbox("Hey, check it out!", false);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");

        CheckboxGroup cbg = new CheckboxGroup();

        exceptions = false;
        try {
            b = new Checkbox("Hey, check it out!", true, cbg);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");

        exceptions = false;
        try {
            b = new Checkbox("Hey, check it out!", false, cbg);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");

        exceptions = false;
        try {
            b = new Checkbox("Hey, check it out!", cbg, true);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");

        exceptions = false;
        try {
            b = new Checkbox("Hey, check it out!", cbg, false);
        } catch (HeadlessException e) {
            exceptions = true;
        }
        if (!exceptions)
            throw new RuntimeException("Constructor did not throw HeadlessException");
    }
}
