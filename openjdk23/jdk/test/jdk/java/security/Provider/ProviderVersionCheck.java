/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.security.Provider;
import java.security.Security;
import java.lang.Exception;

/*
 * @test
 * @bug 8030823 8130696 8196414
 * @run main/othervm ProviderVersionCheck
 * @summary Verify all providers in the default Providers list have the proper
 * version for the release
 * @author Anthony Scarpino
 */

public class ProviderVersionCheck {

    public static void main(String arg[]) throws Exception{

        boolean failure = false;

        for (Provider p: Security.getProviders()) {
            System.out.print(p.getName() + " ");
            String specVersion = System.getProperty("java.specification.version");
            if (p.getVersion() != Double.parseDouble(specVersion)) {
                System.out.println("failed. " + "Version received was " +
                        p.getVersion());
                failure = true;
            } else {
                System.out.println("passed.");
            }
        }

        if (failure) {
            throw new Exception("Provider(s) failed to have the expected " +
                    "version value.");
        }
    }

}
