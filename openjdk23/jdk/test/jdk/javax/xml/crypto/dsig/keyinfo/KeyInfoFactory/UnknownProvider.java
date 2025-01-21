/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @bug 8172003
 * @summary Test that KeyInfoFactory.getInstance() throws a
 *   NoSuchProviderException when provider is unknown
 * @run main UnknownProvider
 */

import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import java.security.NoSuchProviderException;

public class UnknownProvider {

    public static void main(String[] args) {
        try {
            KeyInfoFactory fac = KeyInfoFactory.getInstance(
                "DOM", "SomeProviderThatDoesNotExist");
        }
        catch(NoSuchProviderException e) {
            // this is expected
        }
    }
}
