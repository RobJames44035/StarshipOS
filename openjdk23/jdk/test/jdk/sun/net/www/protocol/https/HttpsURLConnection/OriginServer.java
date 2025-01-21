/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 *
 * This is a HTTP test server.
 */

import java.io.*;
import java.net.*;
import javax.net.*;

/*
 * OriginServer.java -- a simple server that can serve
 * http requests in both clear and secure channel.
 */

public abstract class OriginServer implements Runnable {

    private ServerSocket server = null;
    Exception serverException = null;

    /**
     * Constructs a OriginServer based on ss and
     * obtains a response data's bytecodes using the method
     * getBytes.
     */
    protected OriginServer(ServerSocket ss) throws Exception
    {
        server = ss;
        newListener();
        if (serverException != null)
            throw serverException;
    }

    /**
     * Returns an array of bytes containing the bytes for
     * data sent in the response.
     *
     * @return the bytes for the information that is being sent
     */
    public abstract byte[] getBytes();

    /**
     * The "listen" thread that accepts a connection to the
     * server, parses header and sends back the response
     */
    public void run()
    {
        Socket socket;

        // accept a connection
        try {
            socket = server.accept();
        } catch (IOException e) {
            System.out.println("Class Server died: " + e.getMessage());
            serverException = e;
            return;
        }
        try {
            DataOutputStream out =
                new DataOutputStream(socket.getOutputStream());
            try {
                BufferedReader in =
                    new BufferedReader(new InputStreamReader(
                                socket.getInputStream()));
                // read the request
                readRequest(in);
                // retrieve bytecodes
                byte[] bytecodes = getBytes();
                // send bytecodes in response (assumes HTTP/1.0 or later)
                try {
                    out.writeBytes("HTTP/1.0 200 OK\r\n");
                    out.writeBytes("Content-Length: " + bytecodes.length +
                                   "\r\n");
                    out.writeBytes("Content-Type: text/html\r\n\r\n");
                    out.write(bytecodes);
                    out.flush();
                } catch (IOException ie) {
                    serverException = ie;
                    return;
                }

            } catch (Exception e) {
                // write out error response
                out.writeBytes("HTTP/1.0 400 " + e.getMessage() + "\r\n");
                out.writeBytes("Content-Type: text/html\r\n\r\n");
                out.flush();
            }

        } catch (IOException ex) {
            System.out.println("Server: Error writing response: "
                                 + ex.getMessage());
            serverException = ex;

        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                serverException = e;
            }
        }
    }

    /**
     * Create a new thread to listen.
     */
    private void newListener()
    {
        (new Thread(this)).start();
    }

    /**
     * read the response, don't care for the syntax of the request-line
     */
    private static void readRequest(BufferedReader in)
        throws IOException
    {
        String line = null;
        do {
            line = in.readLine();
            System.out.println("Server recieved: " + line);
        } while ((line.length() != 0) &&
                (line.charAt(0) != '\r') && (line.charAt(0) != '\n'));

        // read the last line to clear the POST input buffer
        // would be best to make sure all bytes are read, maybe later.
        line = in.readLine();
        System.out.println("Command received: " + line);

        System.out.println("");
    }
}
