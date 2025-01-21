/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package java.lang.invoke;

/**
 * Base class for memory segment var handle view implementations.
 */
abstract sealed class VarHandleSegmentViewBase extends VarHandle permits
        VarHandleSegmentAsBytes,
        VarHandleSegmentAsChars,
        VarHandleSegmentAsDoubles,
        VarHandleSegmentAsFloats,
        VarHandleSegmentAsInts,
        VarHandleSegmentAsLongs,
        VarHandleSegmentAsShorts {

    /** endianness **/
    final boolean be;

    /** alignment constraint (in bytes, expressed as a bit mask) **/
    final long alignmentMask;

    VarHandleSegmentViewBase(VarForm form, boolean be, long alignmentMask, boolean exact) {
        super(form, exact);
        this.be = be;
        this.alignmentMask = alignmentMask;
    }

    static UnsupportedOperationException newUnsupportedAccessModeForAlignment(long alignment) {
        return new UnsupportedOperationException("Unsupported access mode for alignment: " + alignment);
    }
}
