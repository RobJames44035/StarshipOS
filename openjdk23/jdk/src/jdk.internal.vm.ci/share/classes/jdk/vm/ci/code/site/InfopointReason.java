/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package jdk.vm.ci.code.site;

/**
 * A reason for infopoint insertion.
 */
public enum InfopointReason {

    SAFEPOINT,
    CALL,
    IMPLICIT_EXCEPTION,
    METHOD_START,
    METHOD_END,
    BYTECODE_POSITION;
}
