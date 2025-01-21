/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/* @test
 * @bug 8005281
 * @summary Test that the Properties storeToXML and loadFromXML methods are
 *   thread safe
 * @key randomness
 */

import java.io.*;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ConcurrentLoadAndStoreXML {

    static final Random RAND = new Random();

    static volatile boolean done;

    /**
     * Simple task that bashes on storeToXML and loadFromXML until the "done"
     * flag is set.
     */
    static class Basher implements Callable<Void> {
        final Properties props;

        Basher(Properties props) {
            this.props = props;
        }

        public Void call() throws IOException {
            while (!done) {

                // store as XML format
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                props.storeToXML(out, null, "UTF-8");

                // load from XML format
                Properties p = new Properties();
                ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
                p.loadFromXML(in);

                // check that the properties are as expected
                if (!p.equals(props))
                    throw new RuntimeException("Properties not equal");
            }
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        final int NTASKS = 4 + RAND.nextInt(4);

        // create Bashers with Properties of random keys and values
        Basher[] basher = new Basher[NTASKS];
        for (int i=0; i<NTASKS; i++) {
            Properties props = new Properties();
            for (int j=0; j<RAND.nextInt(100); j++) {
                String key = "k" + RAND.nextInt(1000);
                String value = "v" + RAND.nextInt(1000);
                props.put(key, value);
            }
            basher[i] = new Basher(props);
        }

        ExecutorService pool = Executors.newFixedThreadPool(NTASKS);
        try {
            // kick off the bashers
            Future<Void>[] task = new Future[NTASKS];
            for (int i=0; i<NTASKS; i++) {
                task[i] = pool.submit(basher[i]);
            }

            // give them time to interfere with each other
            Thread.sleep(2000);
            done = true;

            // check the result
            for (int i=0; i<NTASKS; i++) {
                task[i].get();
            }
        } finally {
            done = true;
            pool.shutdown();
        }

    }
}
