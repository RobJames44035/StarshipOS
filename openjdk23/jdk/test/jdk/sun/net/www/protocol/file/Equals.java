/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4801250
 * @summary URL.equals inconsistent with RFC1738 and InetAddress
 */
import java.net.URL;

public class Equals {
    public static void main(String[] args) throws Exception {
        if (!(new URL("file://localhost/").equals(new URL("file:///"))))
            throw new RuntimeException("file://localhost/ is not equal to file:///");
    }
}
