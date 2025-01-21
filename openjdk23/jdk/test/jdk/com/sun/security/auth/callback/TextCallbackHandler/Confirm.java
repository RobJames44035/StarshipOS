/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 6599079 8240848
 * @summary Non-standard ConfirmationCallback throws NPE
 * @modules jdk.security.auth
 */

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.ConfirmationCallback;
import com.sun.security.auth.callback.TextCallbackHandler;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Confirm {

    public static void main(String[] args) throws Exception {
        InputStream in = System.in;
        try {
            // Provide answer in an individual stream so that the program
            // does not block.
            System.setIn(new ByteArrayInputStream("1\n".getBytes()));
            new TextCallbackHandler().handle(new Callback[]{
                    new ConfirmationCallback("Prince",
                            ConfirmationCallback.INFORMATION,
                            new String[]{"To be", "Not to be"}, 0)});

            System.setIn(new ByteArrayInputStream("-1\n".getBytes()));
            new TextCallbackHandler().handle(new Callback[]{
                    new ConfirmationCallback(
                            ConfirmationCallback.INFORMATION,
                            ConfirmationCallback.OK_CANCEL_OPTION,
                            ConfirmationCallback.OK)});
        } finally {
            System.setIn(in);
        }
    }
}
