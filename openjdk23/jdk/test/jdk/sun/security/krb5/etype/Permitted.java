/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
/*
 * @test
 * @bug 8262389
 * @modules java.security.jgss/sun.security.krb5
 * @library /test/lib
 * @summary Use permitted_enctypes if default_tkt_enctypes or default_tgs_enctypes is not present
 */

import jdk.test.lib.Asserts;
import sun.security.krb5.Config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Permitted {
    public static void main(String[] args) throws Exception {

        System.setProperty("java.security.krb5.conf", "permitted.conf");

        Files.write(Path.of("permitted.conf"), List.of("[libdefaults]",
                "permitted_enctypes = aes128-cts"));
        Config.refresh();
        Asserts.assertEQ(Config.getInstance().defaultEtype("default_tkt_enctypes").length, 1);
        Asserts.assertEQ(Config.getInstance().defaultEtype("default_tgs_enctypes").length, 1);

        Files.write(Path.of("permitted.conf"), List.of("[libdefaults]",
                "default_tkt_enctypes = aes128-cts aes256-cts",
                "default_tgs_enctypes = aes128-cts aes256-cts aes256-sha2",
                "permitted_enctypes = aes128-cts"));
        Config.refresh();
        Asserts.assertEQ(Config.getInstance().defaultEtype("default_tkt_enctypes").length, 2);
        Asserts.assertEQ(Config.getInstance().defaultEtype("default_tgs_enctypes").length, 3);
    }
}
