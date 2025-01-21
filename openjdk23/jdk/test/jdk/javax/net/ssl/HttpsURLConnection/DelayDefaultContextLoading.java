/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4417268
 * @summary Update HttpsURLConnection to not call getDefault in initializer.
 * @author Brad Wetmore
 */

import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.*;

public class DelayDefaultContextLoading {

    /*
     * Pick some static method in HttpsURLConnection, and
     * then run it.  If we're still broken, we'll call
     * SSLSocketFactory.getDefault(), which will create a
     * new SecureRandom number generator, which takes a while
     * to load.
     */
    public static void main(String[] args) throws Exception {
        Date date1 = new Date();
        HttpsURLConnection.getDefaultHostnameVerifier();
        Date date2 = new Date();
        long delta = (date2.getTime() - date1.getTime()) / 1000;

        /*
         * Did it take longer than 5 second to run?
         * If so, we're probably still loading incorrectly.
         */
        if (delta > 5) {
            throw new Exception("FAILED:  HttpsURLConnection took " + delta +
                " seconds to load");
        }
        System.out.println("PASSED:  HttpsURLConnection took " + delta +
            " seconds to load");
    }
}
