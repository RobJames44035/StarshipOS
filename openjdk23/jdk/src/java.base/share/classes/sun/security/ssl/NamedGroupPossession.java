/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package sun.security.ssl;

import java.security.PrivateKey;
import java.security.PublicKey;

interface NamedGroupPossession extends SSLPossession {

    NamedGroup getNamedGroup();

    PublicKey getPublicKey();

    PrivateKey getPrivateKey();
}
