/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4671007
 * @summary SocketPermission equals inconsistent with implies for resolve-only action
 */

import java.net.*;

public class Equals {
    public static void main(String[] args) {
        SocketPermission p1 = new SocketPermission("*:38", "resolve");
        SocketPermission p2 = new SocketPermission("*:39", "resolve");

        if (!p1.equals(p2)) {
            throw new RuntimeException(p1 + " isn't equal to " + p2);
        }
    }
}
