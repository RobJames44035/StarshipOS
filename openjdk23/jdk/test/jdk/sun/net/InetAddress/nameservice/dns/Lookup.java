/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

import java.net.InetAddress;

/*
 * Lookup a host using InetAddress.getAllByName
 */

public class Lookup {

    public static void main(String args[]) throws Exception {
        InetAddress addrs[] = InetAddress.getAllByName(args[0]);
        for (int i=0; i<addrs.length; i++) {
            System.out.println(addrs[i].getHostAddress());
        }
    }

}
