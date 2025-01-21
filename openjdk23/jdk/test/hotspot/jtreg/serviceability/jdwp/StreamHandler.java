/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Handles the process output (either stdin or stdout)
 * passing the lines to a listener
 */
public class StreamHandler implements Runnable {

    public interface Listener {
        /**
         * Called when a line has been read from the process output stream
         * @param s the line
         */
        void onStringRead(String s);
    }

    private final ExecutorService executor;
    private final InputStream is;
    private final Listener listener;

    /**
     * @param is input stream to read from
     * @param listener listener to pass the read lines to
     * @throws IOException
     */
    public StreamHandler(InputStream is, Listener listener) throws IOException {
        this.is = is;
        this.listener = listener;
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Starts asynchronous reading
     */
    public void start() {
        executor.submit(this);
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                listener.onStringRead(line);
            }
        } catch (Exception x) {
            throw new RuntimeException(x);
        }
    }

}
