/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402062 6487891
 * @summary Tests Statement encoding
 * @run main/othervm java_beans_Statement
 * @author Sergey Malenkov
 */

import java.beans.Statement;

public final class java_beans_Statement extends AbstractTest<Statement> {
    public static void main(String[] args) {
        new java_beans_Statement().test();
    }

    protected Statement getObject() {
        return new Statement("target", "method", new String[] {"arg1", "arg2"});
    }

    protected Statement getAnotherObject() {
        return null; // TODO: could not update property
        // return new Statement("target", "method", null);
    }
}
