/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7021280
 * @summary SocketPermission should accept wildcards
 */

import java.net.SocketPermission;

public class Wildcard
{
    public static void main(String[] args) throws Exception {
        SocketPermission star_All =
                new SocketPermission("*.blabla.bla", "listen,accept,connect");
        SocketPermission www_All =
                new SocketPermission("bla.blabla.bla", "listen,accept,connect");

        if (!star_All.implies(www_All)) {
            throw new RuntimeException(
                   "Failed: " + star_All + " does not imply " + www_All);
        }
    }
}
