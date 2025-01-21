/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib /java/security/testlibrary
 * @bug 4470717
 * @summary fix default handling and other misc
 * @run main/othervm Default
 */

import com.sun.security.auth.callback.TextCallbackHandler;
import jdk.test.lib.Asserts;

import javax.security.auth.callback.*;
import java.io.*;

public class Default {
    public static void main(String args[]) throws Exception {
        InputStream in = System.in;
        PrintStream err = System.err;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final String defaultName = "charlie";
        final String simulatedInput = "-1\n-1\n";
        HumanInputStream humanInputStream = new HumanInputStream(simulatedInput);

        try (PrintStream prints = new PrintStream(baos)) {
            System.setIn(humanInputStream);
            System.setErr(prints);
            NameCallback nameCallback = new NameCallback("Name: ", defaultName);
            ConfirmationCallback confirmationCallback = new ConfirmationCallback(
                    "Correct?",
                    ConfirmationCallback.INFORMATION,
                    ConfirmationCallback.YES_NO_OPTION,
                    ConfirmationCallback.NO);
            new TextCallbackHandler().handle(new Callback[]{nameCallback, confirmationCallback});

            Asserts.assertEquals(nameCallback.getDefaultName(), defaultName);
            Asserts.assertEquals(confirmationCallback.getSelectedIndex(), ConfirmationCallback.NO);

        } finally {
            System.setIn(in);
            System.setErr(err);
        }

        // check that the default name and confirmation were visible in the output
        Asserts.assertTrue(baos.toString().contains(String.format("Name:  [%s]", defaultName)));
        Asserts.assertTrue(baos.toString().contains("1. No [default]"));
    }
}
