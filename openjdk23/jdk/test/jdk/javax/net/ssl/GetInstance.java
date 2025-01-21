/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4898428 7022855
 * @summary verify getInstance() works using Provider.getService()
 *          Export "PKIX" as the standard algorithm name of KeyManagerFactory
 * @author Andreas Sterbenz
 */

import java.security.*;

import javax.net.ssl.*;

public class GetInstance {

    private static void same(Provider p1, Provider p2) throws Exception {
        if (p1 != p2) {
            throw new Exception("not same object");
        }
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        Provider p = Security.getProvider("SunJSSE");

        SSLContext context;
        context = SSLContext.getInstance("SSL");
        same(p, context.getProvider());
        context = SSLContext.getInstance("SSL", "SunJSSE");
        same(p, context.getProvider());
        context = SSLContext.getInstance("SSL", p);
        same(p, context.getProvider());

        KeyManagerFactory kmf;
        kmf = KeyManagerFactory.getInstance("SunX509");
        same(p, kmf.getProvider());
        kmf = KeyManagerFactory.getInstance("SunX509", "SunJSSE");
        same(p, kmf.getProvider());
        kmf = KeyManagerFactory.getInstance("SunX509", p);
        same(p, kmf.getProvider());

        kmf = KeyManagerFactory.getInstance("NewSunX509");
        same(p, kmf.getProvider());
        kmf = KeyManagerFactory.getInstance("NewSunX509", "SunJSSE");
        same(p, kmf.getProvider());
        kmf = KeyManagerFactory.getInstance("NewSunX509", p);
        same(p, kmf.getProvider());

        kmf = KeyManagerFactory.getInstance("PKIX");
        same(p, kmf.getProvider());
        kmf = KeyManagerFactory.getInstance("PKIX", "SunJSSE");
        same(p, kmf.getProvider());
        kmf = KeyManagerFactory.getInstance("PKIX", p);
        same(p, kmf.getProvider());

        TrustManagerFactory tmf;
        tmf = TrustManagerFactory.getInstance("SunX509");
        same(p, tmf.getProvider());
        tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE");
        same(p, tmf.getProvider());
        tmf = TrustManagerFactory.getInstance("SunX509", p);
        same(p, tmf.getProvider());

        tmf = TrustManagerFactory.getInstance("PKIX");
        same(p, tmf.getProvider());
        tmf = TrustManagerFactory.getInstance("PKIX", "SunJSSE");
        same(p, tmf.getProvider());
        tmf = TrustManagerFactory.getInstance("PKIX", p);
        same(p, tmf.getProvider());

        tmf = TrustManagerFactory.getInstance("SunPKIX");
        same(p, tmf.getProvider());
        tmf = TrustManagerFactory.getInstance("SunPKIX", "SunJSSE");
        same(p, tmf.getProvider());
        tmf = TrustManagerFactory.getInstance("SunPKIX", p);
        same(p, tmf.getProvider());

        tmf = TrustManagerFactory.getInstance("X509");
        same(p, tmf.getProvider());
        tmf = TrustManagerFactory.getInstance("X509", "SunJSSE");
        same(p, tmf.getProvider());
        tmf = TrustManagerFactory.getInstance("X509", p);
        same(p, tmf.getProvider());

        tmf = TrustManagerFactory.getInstance("X.509");
        same(p, tmf.getProvider());
        tmf = TrustManagerFactory.getInstance("X.509", "SunJSSE");
        same(p, tmf.getProvider());
        tmf = TrustManagerFactory.getInstance("X.509", p);
        same(p, tmf.getProvider());

        long stop = System.currentTimeMillis();
        System.out.println("Done (" + (stop - start) + " ms).");
    }
}
