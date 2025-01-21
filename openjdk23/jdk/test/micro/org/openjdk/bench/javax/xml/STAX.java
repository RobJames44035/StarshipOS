/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.javax.xml;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class STAX extends AbstractXMLMicro {

    /** Live data */
    public Map<String, LinkedList<String>> liveData;

    @Setup(Level.Iteration)
    public void setupLiveData() {
        Map<String, LinkedList<String>> map = new HashMap<>();
        // Somewhere around 100 MB live, but fragmented to start with.
        for (int i = 0; i < 1000; i++) {
            LinkedList<String> list = new LinkedList<>();
            String key = "Dummy linked list " + i;
            list.add(key);
            for (int j = 0; j < 1000; j++) {
                list.add("Dummy string " + i + "." + j);
            }
            map.put(key, list);
        }
        // thread safe, will only be one
        liveData = map;
    }

    @TearDown(Level.Iteration)
    public void teardownLiveData() {
        liveData = null;
    }

    @Benchmark
    public int testParse() throws Exception {
        int intDummy = 0;
        byte[] bytes = getFileBytesFromResource(doc);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        XMLInputFactory factory = XMLInputFactory.newInstance();

        XMLStreamReader parser = factory.createXMLStreamReader(bais);
        int acc;
        do {
            acc = parser.next();
            intDummy += acc;
        } while (acc != XMLStreamConstants.END_DOCUMENT);
        return intDummy;
    }

}
