/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.security.*;

public final class MyProvider extends Provider {

    public MyProvider() {
        super("MyProvider", "1.0",
                "Test Provider: SHA1/MD5/SHA256 exhaustion testing");
        put("MessageDigest.SHA", "DigestBase$SHA");
        put("MessageDigest.MD5", "DigestBase$MD5");
        put("MessageDigest.SHA-256", "DigestBase$SHA256");
    }
}
