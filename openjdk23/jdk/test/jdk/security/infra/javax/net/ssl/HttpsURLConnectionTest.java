/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8320362
 * @summary Verifies successful connection to external server with
 *          KEYCHAINSTORE-ROOT trust store
 * @library /test/lib
 * @requires os.family == "mac"
 * @run main/othervm/manual HttpsURLConnectionTest https://github.com KeychainStore-Root
 */
import java.io.*;
import java.net.*;
import javax.net.ssl.*;

public class HttpsURLConnectionTest {
    public static void main(String[] args) {
        System.setProperty( "javax.net.ssl.trustStoreType", args[1]);
        try {
            HttpsURLConnection httpsCon = (HttpsURLConnection) new URL(args[0]).openConnection();
            if(httpsCon.getResponseCode() != 200) {
                throw new RuntimeException("Test failed : bad http response code : "+ httpsCon.getResponseCode());
            }
        } catch(IOException ioe) {
            throw new RuntimeException("Test failed: " + ioe.getMessage());
        }
    }
}
