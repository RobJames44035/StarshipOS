/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.event.runtime;

import java.util.List;
import java.util.stream.Collectors;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @modules java.base/jdk.internal.misc
 * @build jdk.jfr.event.runtime.TestClasses
 * @run main/othervm jdk.jfr.event.runtime.TestTableStatisticsEvent
 * @bug 8185525
 */
public final class TestTableStatisticsEvent {

  public static void main(String[] args) throws Throwable {
    try (Recording recording = new Recording()) {
      recording.enable(EventNames.SymbolTableStatistics);
      recording.enable(EventNames.StringTableStatistics);
      recording.start();
      recording.stop();

      List<RecordedEvent> events = Events.fromRecording(recording);
      verifyTable(events, EventNames.SymbolTableStatistics);
      verifyTable(events, EventNames.StringTableStatistics);
    }
  }

  private static void verifyTable(List<RecordedEvent> allEvents, String eventName) throws Exception {
    List<RecordedEvent> eventsForTable = allEvents.stream().filter(e -> e.getEventType().getName().equals(eventName)).collect(Collectors.toList());
    if (eventsForTable.isEmpty()) {
      throw new Exception("No events for " + eventName);
    }
    for (RecordedEvent event : eventsForTable) {
      Events.assertField(event, "bucketCount").atLeast(0L);
      long entryCount = Events.assertField(event, "entryCount").atLeast(0L).getValue();
      Events.assertField(event, "totalFootprint").atLeast(0L);
      float averageBucketCount = Events.assertField(event, "bucketCountAverage").atLeast(0.0f).getValue();
      Events.assertField(event, "bucketCountMaximum").atLeast((long)averageBucketCount);
      Events.assertField(event, "bucketCountVariance").atLeast(0.0f);
      Events.assertField(event, "bucketCountStandardDeviation").atLeast(0.0f);
      float insertionRate = Events.assertField(event, "insertionRate").atLeast(0.0f).getValue();
      float removalRate = Events.assertField(event, "removalRate").atLeast(0.0f).getValue();
      if ((insertionRate > 0.0f) && (insertionRate > removalRate)) {
        Asserts.assertGreaterThan(entryCount, 0L, "Entries marked as added, but no entries found for " + eventName);
      }
    }
  }
}
