/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package sun.security.ssl;

import java.security.PublicKey;

interface NamedGroupCredentials extends SSLCredentials {

    PublicKey getPublicKey();

    NamedGroup getNamedGroup();

}
