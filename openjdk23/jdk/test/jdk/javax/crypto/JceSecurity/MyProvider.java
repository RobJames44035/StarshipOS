/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * test
 * @bug 6377058 8130181
 * @summary SunJCE depends on sun.security.provider.SignatureImpl
 * behaviour, BC can't load into 1st slot.
 * @author Brad R. Wetmore
 */

import java.security.*;

public class MyProvider extends Provider {

    public MyProvider() {
        super("MyProvider", "1.0", "CertImpl");
        put("CertificateFactory.X.509", "MyCertificateFactory");
    }
}
