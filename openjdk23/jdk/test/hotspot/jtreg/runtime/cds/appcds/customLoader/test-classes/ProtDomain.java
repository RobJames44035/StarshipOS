/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.security.ProtectionDomain;
import java.net.URLClassLoader;
import java.net.URL;
import java.io.File;

// Intended to be called from test ProtectionDomain.java
//
// ProtDomainClassForArchive is     stored in CDS archive.
// ProtDomainNotForArchive   is NOT stored in CDS archive.
//
// However, they should have the same ProtectionDomain instance.
public class ProtDomain {
    public static void main(String args[]) throws Exception {
        String customLdrPath = args[0];

        URL[] urls = new URL[] {new File(customLdrPath).toURI().toURL()};
        URLClassLoader ldr = new URLClassLoader(urls);
        ProtectionDomain domain1 = ldr.loadClass("ProtDomainClassForArchive").getProtectionDomain();
        ProtectionDomain domain2 = ldr.loadClass("ProtDomainNotForArchive").getProtectionDomain();

        System.out.println("domain1 = " + domain1);
        System.out.println("domain2  = " + domain2);

        if (domain1 != domain2)
            throw new RuntimeException("Protection Domains do not match!");
    }
}

class ProtDomainClassForArchive {}

class ProtDomainNotForArchive {}
