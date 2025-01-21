/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @bug 8172003
 * @summary Test that TransformService.getInstance() throws a
 *   NoSuchProviderException when provider is unknown
 * @run main UnknownProvider
 */

import javax.xml.crypto.dsig.TransformService;
import javax.xml.crypto.dsig.Transform;
import java.security.NoSuchProviderException;
import java.security.NoSuchAlgorithmException;

public class UnknownProvider {

    public static void main(String[] args) throws NoSuchAlgorithmException {
       try {
           TransformService ts = TransformService.getInstance(
               Transform.BASE64, "DOM", "SomeProviderThatDoesNotExist");
       }
       catch(NoSuchProviderException e) {
           // this is expected
       }
    }
}
