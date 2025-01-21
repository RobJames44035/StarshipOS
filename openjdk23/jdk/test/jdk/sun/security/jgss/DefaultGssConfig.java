/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6483218
 * @summary Provide a default login configuration
 * @modules java.security.jgss/sun.security.jgss
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.URIParameter;
import javax.security.auth.login.Configuration;
import sun.security.jgss.GSSUtil;
import sun.security.jgss.GSSCaller;
import sun.security.jgss.LoginConfigImpl;

public class DefaultGssConfig {

    public static void main(String[] argv) throws Exception {

        // 1. Make sure the FileNotFoundException is hidden
        try {
            Configuration.getInstance("JavaLoginConfig", new URIParameter(new URI("file:///no/such/file")));
        } catch (NoSuchAlgorithmException nsae) {
            if (nsae.getCause() instanceof IOException &&
                    !(nsae.getCause() instanceof FileNotFoundException)) {
                // ignore
            } else {
                throw nsae;
            }
        }

        // 2. Make sure there's always a Configuration even if no config file exists
        Configuration.getConfiguration();

        // 3. Make sure there're default entries for GSS krb5 client/server
        LoginConfigImpl lc = new LoginConfigImpl(GSSCaller.CALLER_INITIATE, GSSUtil.GSS_KRB5_MECH_OID);
        if (lc.getAppConfigurationEntry("").length == 0) {
            throw new Exception("No default config for GSS krb5 client");
        }
        lc = new LoginConfigImpl(GSSCaller.CALLER_ACCEPT, GSSUtil.GSS_KRB5_MECH_OID);
        if (lc.getAppConfigurationEntry("").length == 0) {
            throw new Exception("No default config for GSS krb5 server");
        }
    }
}
