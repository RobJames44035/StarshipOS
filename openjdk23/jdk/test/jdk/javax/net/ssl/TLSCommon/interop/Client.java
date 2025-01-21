/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.io.IOException;

/*
 * This interface defines functions for SSL/TLS client.
 */
public interface Client extends Peer {

    /*
     * Connect to the specified server.
     */
    public void connect(String host, int port) throws IOException;
}
