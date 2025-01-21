/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
/*
 * @test
 * @bug 7144530
 * @summary KeyTab.getInstance(String) no longer handles keyTabNames with "file:" prefix
 * @modules java.security.jgss/sun.security.krb5
 *          java.security.jgss/sun.security.krb5.internal.ktab
 */
import java.io.File;
import sun.security.krb5.PrincipalName;
import sun.security.krb5.internal.ktab.KeyTab;

public class FileKeyTab {
    public static void main(String[] args) throws Exception {
        String name = "ktab";
        KeyTab kt = KeyTab.create(name);
        kt.addEntry(new PrincipalName("a@A"), "x".toCharArray(), 1, true);
        kt.save();
        check(name);
        check("FILE:" + name);

        name = new File(name).getAbsolutePath().toString();

        check(name);
        check("FILE:" + name);

        // The bug reporter uses this style, should only work for
        // absolute path
        check("FILE:/" + name);
    }

    static void check(String file) throws Exception {
        System.out.println("Checking for " + file + "...");
        KeyTab kt2 = KeyTab.getInstance(file);
        if (kt2.isMissing()) {
            throw new Exception("FILE:ktab cannot be loaded");
        }
    }
}
