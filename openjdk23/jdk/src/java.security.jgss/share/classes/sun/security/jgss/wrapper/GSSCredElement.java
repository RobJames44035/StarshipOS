/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */
package sun.security.jgss.wrapper;

import org.ietf.jgss.*;
import java.lang.ref.Cleaner;
import java.security.Provider;
import sun.security.jgss.spi.GSSCredentialSpi;
import sun.security.jgss.spi.GSSNameSpi;

/**
 * This class is essentially a wrapper class for the gss_cred_id_t
 * structure of the native GSS library.
 * @author Valerie Peng
 * @since 1.6
 */
public class GSSCredElement implements GSSCredentialSpi {
    private final Cleaner.Cleanable cleanable;

    private final int usage;
    final long pCred; // Pointer to the gss_cred_id_t structure
    private GSSNameElement name;
    private final GSSLibStub cStub;

    // Construct delegation cred using the actual context mech and srcName
    // Warning: called by NativeUtil.c
    GSSCredElement(long pCredentials, GSSNameElement srcName, Oid mech)
        throws GSSException {
        pCred = pCredentials;
        cStub = GSSLibStub.getInstance(mech);
        usage = GSSCredential.INITIATE_ONLY;
        name = srcName;
        cleanable = Krb5Util.cleaner.register(this, disposerFor(cStub, pCred));
    }

    GSSCredElement(GSSNameElement name, int lifetime, int usage,
                   GSSLibStub stub) throws GSSException {
        cStub = stub;
        this.usage = usage;

        if (name != null) { // Could be GSSNameElement.DEF_ACCEPTOR
            this.name = name;
            pCred = cStub.acquireCred(this.name.pName, lifetime, usage);
        } else {
            pCred = cStub.acquireCred(0, lifetime, usage);
            this.name = new GSSNameElement(cStub.getCredName(pCred), cStub);
        }

        cleanable = Krb5Util.cleaner.register(this, disposerFor(cStub, pCred));
    }

    public Provider getProvider() {
        return SunNativeProvider.INSTANCE;
    }

    public void dispose() {
        name = null;
        cleanable.clean();
    }

    private static Runnable disposerFor(GSSLibStub stub, long pCredentials) {
        return () -> {
            stub.releaseCred(pCredentials);
        };
    }

    public GSSNameElement getName() throws GSSException {
        return (name == GSSNameElement.DEF_ACCEPTOR ?
            null : name);
    }

    public int getInitLifetime() throws GSSException {
        if (isInitiatorCredential()) {
            return cStub.getCredTime(pCred);
        } else return 0;
    }

    public int getAcceptLifetime() throws GSSException {
        if (isAcceptorCredential()) {
            return cStub.getCredTime(pCred);
        } else return 0;
    }

    public boolean isInitiatorCredential() {
        return (usage != GSSCredential.ACCEPT_ONLY);
    }

    public boolean isAcceptorCredential() {
        return (usage != GSSCredential.INITIATE_ONLY);
    }

    public Oid getMechanism() {
        return cStub.getMech();
    }

    public String toString() {
        // No hex bytes available for native impl
        return "N/A";
    }

    @Override
    public GSSCredentialSpi impersonate(GSSNameSpi name) throws GSSException {
        throw new GSSException(GSSException.FAILURE, -1,
                "Not supported yet");
    }
}
