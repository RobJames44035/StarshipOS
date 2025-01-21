/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.mapping;

/**
 * Exception that is thrown if two {@link PhaseInterval} ranges overlap in {@link MultiPhaseRangeEntry}.
 *
 * @see PhaseInterval
 * @see MultiPhaseRangeEntry
 */
class OverlappingPhaseRangesException extends RuntimeException {
    public OverlappingPhaseRangesException(PhaseInterval entry, PhaseInterval entry2) {
        super("The following two PhaseRangeEntry objects overlap which is forbidden:" + System.lineSeparator() + "- "
              + entry + System.lineSeparator() + "- " + entry2);
    }
}
