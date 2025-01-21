/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package sun.security.krb5.internal.tools;

import sun.security.krb5.*;
import sun.security.krb5.internal.*;
import sun.security.krb5.internal.ccache.*;
import java.io.IOException;
import java.time.Instant;
import java.io.FileInputStream;

import static sun.security.krb5.internal.Krb5.DEBUG;

/**
 * Maintains user-specific options or default settings when the user requests
 * a KDC ticket using Kinit.
 *
 * @author Yanni Zhang
 * @author Ram Marti
 */
class KinitOptions {

    // 0. Help, 1. acquire, 2. renew, 3. validate
    public int action = 1;

    // forwardable and proxiable flags have two states:
    // -1 - flag set to be not forwardable or proxiable;
    // 1 - flag set to be forwardable or proxiable.
    public short forwardable = 0;
    public short proxiable = 0;
    public KerberosTime lifetime;
    public KerberosTime renewable_lifetime;
    public String target_service;
    public String keytab_file;
    public String cachename;
    private PrincipalName principal;
    public String realm;
    char[] password = null;
    public boolean keytab;
    private boolean includeAddresses = true; // default.
    private boolean useKeytab = false; // default = false.
    private String ktabName; // keytab file name

    public KinitOptions() throws RuntimeException, RealmException {
        // no args were specified in the command line;
        // use default values
        cachename = FileCredentialsCache.getDefaultCacheName();
        if (cachename == null) {
            throw new RuntimeException("default cache name error");
        }
        principal = getDefaultPrincipal();
    }

    public void setKDCRealm(String r) throws RealmException {
        realm = r;
    }

    public String getKDCRealm() {
        if (realm == null) {
            if (principal != null) {
                return principal.getRealmString();
            }
        }
        return null;
    }

    public KinitOptions(String[] args)
        throws KrbException, RuntimeException, IOException {
        // currently we provide support for -f -p -c principal options
        String p = null; // store principal

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-f")) {
                forwardable = 1;
            } else if (args[i].equals("-p")) {
                proxiable = 1;
            } else if (args[i].equals("-c")) {

                if (args[i + 1].startsWith("-")) {
                    throw new IllegalArgumentException("input format " +
                                                       " not correct: " +
                                                       " -c  option " +
                                                       "must be followed " +
                                                       "by the cache name");
                }
                cachename = args[++i];
                if ((cachename.length() >= 5) &&
                    cachename.substring(0, 5).equalsIgnoreCase("FILE:")) {
                    cachename = cachename.substring(5);
                };
            } else if (args[i].equals("-A")) {
                includeAddresses = false;
            } else if (args[i].equals("-k")) {
                useKeytab = true;
            } else if (args[i].equals("-t")) {
                if (ktabName != null) {
                    throw new IllegalArgumentException
                        ("-t option/keytab file name repeated");
                } else if (i + 1 < args.length) {
                    ktabName = args[++i];
                } else {
                    throw new IllegalArgumentException
                        ("-t option requires keytab file name");
                }

                useKeytab = true;
            } else if (args[i].equals("-R")) {
                action = 2;
            } else if (args[i].equals("-l")) {
                lifetime = getTime(Config.duration(args[++i]));
            } else if (args[i].equals("-r")) {
                renewable_lifetime = getTime(Config.duration(args[++i]));
            } else if (args[i].equalsIgnoreCase("-?") ||
                       args[i].equalsIgnoreCase("-h") ||
                       args[i].equalsIgnoreCase("--help") ||
                       // -help: legacy.
                       args[i].equalsIgnoreCase("-help")) {
                printHelp();
                action = 0;
                return;
            } else if (p == null) { // Haven't yet processed a "principal"
                p = args[i];
                try {
                    principal = new PrincipalName(p);
                } catch (Exception e) {
                    throw new IllegalArgumentException("invalid " +
                                                       "Principal name: " + p +
                                                       e.getMessage());
                }
            } else if (this.password == null) {
                // Have already processed a Principal, this must be a password
                password = args[i].toCharArray();
            } else {
                throw new IllegalArgumentException("too many parameters");
            }
        }
        // we should get cache name before getting the default principal name
        if (cachename == null) {
            cachename = FileCredentialsCache.getDefaultCacheName();
            if (cachename == null) {
                throw new RuntimeException("default cache name error");
            }
        }
        if (principal == null) {
            principal = getDefaultPrincipal();
        }
    }

    PrincipalName getDefaultPrincipal() {

        // get default principal name from the cachename if it is
        // available.

        try {
            CCacheInputStream cis =
                new CCacheInputStream(new FileInputStream(cachename));
            int version;
            if ((version = cis.readVersion()) ==
                    FileCCacheConstants.KRB5_FCC_FVNO_4) {
                cis.readTag();
            } else {
                if (version == FileCCacheConstants.KRB5_FCC_FVNO_1 ||
                        version == FileCCacheConstants.KRB5_FCC_FVNO_2) {
                    cis.setNativeByteOrder();
                }
            }
            PrincipalName p = cis.readPrincipal(version);
            cis.close();
            if (DEBUG != null) {
                DEBUG.println(">>>KinitOptions principal name from " +
                                   "the cache is: " + p);
            }
            return p;
        } catch (IOException e) {
            // ignore any exceptions; we will use the user name as the
            // principal name
            if (DEBUG != null) {
                e.printStackTrace(DEBUG.getPrintStream());
            }
        } catch (RealmException e) {
            if (DEBUG != null) {
                e.printStackTrace(DEBUG.getPrintStream());
            }
        }

        String username = System.getProperty("user.name");
        if (DEBUG != null) {
            DEBUG.println(">>>KinitOptions default username is: "
                               + username);
        }
        try {
            PrincipalName p = new PrincipalName(username);
            return p;
        } catch (RealmException e) {
            // ignore exception , return null
            if (DEBUG != null) {
                DEBUG.println ("Exception in getting principal " +
                                    "name " + e.getMessage());
                e.printStackTrace(DEBUG.getPrintStream());
            }
        }
        return null;
    }


    void printHelp() {
        System.out.println("Usage:\n\n1. Initial ticket request:\n" +
                "    kinit [-A] [-f] [-p] [-c cachename] " +
                "[-l lifetime] [-r renewable_time]\n" +
                "          [[-k [-t keytab_file_name]] [principal] " +
                           "[password]");
        System.out.println("2. Renew a ticket:\n" +
                "    kinit -R [-c cachename] [principal]");
        System.out.println("\nAvailable options to " +
                           "Kerberos 5 ticket request:");
        System.out.println("\t-A   do not include addresses");
        System.out.println("\t-f   forwardable");
        System.out.println("\t-p   proxiable");
        System.out.println("\t-c   cache name " +
                "(i.e., FILE:\\d:\\myProfiles\\mykrb5cache)");
        System.out.println("\t-l   lifetime");
        System.out.println("\t-r   renewable time " +
                "(total lifetime a ticket can be renewed)");
        System.out.println("\t-k   use keytab");
        System.out.println("\t-t   keytab file name");
        System.out.println("\tprincipal   the principal name "+
                "(i.e., qweadf@ATHENA.MIT.EDU qweadf)");
        System.out.println("\tpassword    the principal's Kerberos password");
    }

    public boolean getAddressOption() {
        return includeAddresses;
    }

    public boolean useKeytabFile() {
        return useKeytab;
    }

    public String keytabFileName() {
        return ktabName;
    }

    public PrincipalName getPrincipal() {
        return principal;
    }

    private KerberosTime getTime(int s) {
        return new KerberosTime(Instant.now().plusSeconds(s));
    }
}
