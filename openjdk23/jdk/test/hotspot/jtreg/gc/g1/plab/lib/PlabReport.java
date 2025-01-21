/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package gc.g1.plab.lib;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Class contains representation of GC PLAB log.
 */
public class PlabReport {

    private final Map<Long, PlabGCStatistics> report = new HashMap<>();

    public PlabReport() {
    }

    /**
     * Checks if underlying Map contains requested GC ID.
     */
    public boolean containsKey(Long gcId) {
        return report.containsKey(gcId);
    }

    /**
     * Puts GC ID and PlabGCStatistics to underlying Map.
     */
    public void put(Long gcId, PlabGCStatistics plabStat) {
        report.put(gcId, plabStat);
    }

    /**
     * Returns PlabGCStatistics for specified GC ID.
     */
    public PlabGCStatistics get(Long gcId) {
        return report.get(gcId);
    }

    /**
     * Returns Stream of Map.Entry of underlying Map.
     */
    public Stream<Map.Entry<Long, PlabGCStatistics>> entryStream() {
        return report.entrySet().stream();
    }
}
