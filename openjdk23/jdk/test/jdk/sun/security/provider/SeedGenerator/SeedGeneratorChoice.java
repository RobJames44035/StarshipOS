/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6998583 8141039
 * @summary NativeSeedGenerator is making 8192 byte read requests from
 *             entropy pool on each init.
 * @run main/othervm -Djava.security.egd=file:/dev/urandom SeedGeneratorChoice
 * @run main/othervm -Djava.security.egd=file:filename  SeedGeneratorChoice
 */

/*
 * Side testcase introduced to ensure changes for 6998583 will always
 * succeed in falling back to ThreadedSeedGenerator if issues are found
 * with the native OS generator request. We should never see an exception
 * causing exit.
 * We should always fall back to the ThreadedSeedGenerator if exceptions
 * are encountered with user defined source of entropy.
 */
import java.security.SecureRandom;
import java.security.Security;

public class SeedGeneratorChoice {

    public static void main(String... arguments) throws Exception {
        for (String mech : new String[]{"SHA1PRNG", "Hash_DRBG", "HMAC_DRBG",
            "CTR_DRBG"}) {

            SecureRandom prng = null;
            if (!mech.contains("_DRBG")) {
                prng = SecureRandom.getInstance(mech);
            } else {
                Security.setProperty("securerandom.drbg.config", mech);
                prng = SecureRandom.getInstance("DRBG");
            }
            prng.generateSeed(1);
        }
    }

}
