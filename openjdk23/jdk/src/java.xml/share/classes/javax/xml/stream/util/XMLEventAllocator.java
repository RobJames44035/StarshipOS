/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package javax.xml.stream.util;

import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;

public interface XMLEventAllocator {

  /**
   * This method creates an instance of the XMLEventAllocator. This
   * allows the XMLInputFactory to allocate a new instance per reader.
   * @return an instance of the {@code XMLEventAllocator}
   */
  public XMLEventAllocator newInstance();

  /**
   * This method allocates an event given the current
   * state of the XMLStreamReader.  If this XMLEventAllocator
   * does not have a one-to-one mapping between reader states
   * and events this method will return null.  This method
   * must not modify the state of the XMLStreamReader.
   * @param reader The XMLStreamReader to allocate from
   * @return the event corresponding to the current reader state
   * @throws XMLStreamException if an error occurs
   */
  public XMLEvent allocate(XMLStreamReader reader)
    throws XMLStreamException;

  /**
   * This method allocates an event or set of events
   * given the current
   * state of the XMLStreamReader and adds the event
   * or set of events to the
   * consumer that was passed in.  This method can be used
   * to expand or contract reader states into event states.
   * This method may modify the state of the XMLStreamReader.
   * @param reader The XMLStreamReader to allocate from
   * @param consumer The XMLEventConsumer to add to.
   * @throws XMLStreamException if an error occurs
   */
  public void allocate(XMLStreamReader reader, XMLEventConsumer consumer)
    throws XMLStreamException;

}
