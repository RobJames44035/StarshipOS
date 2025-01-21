/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package jdk.jpackage.internal.util;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

@FunctionalInterface
public interface XmlConsumer {

    void accept(XMLStreamWriter xml) throws IOException, XMLStreamException;

}
