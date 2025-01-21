/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7152121 8194486
 * @summary Krb5LoginModule no longer handles keyTabNames with "file:" prefix
 * @library /test/lib
 * @compile -XDignore.symbol.file FileKeyTab.java
 * @run main jdk.test.lib.FileInstaller TestHosts TestHosts
 * @run main/othervm -Djdk.net.hosts.file=TestHosts FileKeyTab
 */

import java.io.File;
import java.io.FileOutputStream;

// The basic krb5 test skeleton you can copy from
public class FileKeyTab {

    public static void main(String[] args) throws Exception {
        new OneKDC(null).writeJAASConf();
        String ktab = new File(OneKDC.KTAB).getAbsolutePath().replace('\\', '/');
        File f = new File(OneKDC.JAAS_CONF);
        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write((
                "server {\n" +
                "    com.sun.security.auth.module.Krb5LoginModule required\n" +
                "    principal=\"" + OneKDC.SERVER + "\"\n" +
                "    useKeyTab=true\n" +
                "    keyTab=\"file:" + ktab + "\"\n" +
                "    storeKey=true;\n};\n"
                ).getBytes());
        }
        Context.fromJAAS("server");
    }
}
