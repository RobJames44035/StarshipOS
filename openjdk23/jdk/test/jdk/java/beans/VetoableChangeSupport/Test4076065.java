/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4076065
 * @summary Tests that constructor check for null source
 * @author Graham Hamilton
 */

import java.beans.VetoableChangeSupport;

public class Test4076065 {
    public static void main(String[] args) {
        try {
            new VetoableChangeSupport(null);
        } catch (NullPointerException exception) {
            return;
        }
        throw new Error("didn't get expected NullPointerException");
    }
}
