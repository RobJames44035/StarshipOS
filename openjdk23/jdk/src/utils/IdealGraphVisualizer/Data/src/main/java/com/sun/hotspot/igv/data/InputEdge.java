/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.data;

import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author Thomas Wuerthinger
 */
public class InputEdge {

    public enum State {
        IMMUTABLE,
        SAME,
        NEW,
        DELETED
    }

    public static final Comparator<InputEdge> OUTGOING_COMPARATOR = (o1, o2) -> {
            if (o1.getFromIndex() == o2.getFromIndex()) {
                return o1.getTo() - o2.getTo();
            }
            return o1.getFromIndex() - o2.getFromIndex();
        };

    public static final Comparator<InputEdge> INGOING_COMPARATOR = (o1, o2) -> {
            if (o1.getToIndex() == o2.getToIndex()) {
                return o1.getFrom() - o2.getFrom();
            }
            return o1.getToIndex() - o2.getToIndex();
        };

    private final char toIndex;
    private final char fromIndex;
    private final int from;
    private final int to;
    private final String label;
    private final String type;
    private State state;

    public InputEdge(char fromIndex, char toIndex, int from, int to, String label, String type) {
        this.toIndex = toIndex;
        this.fromIndex = fromIndex;
        this.from = from;
        this.to = to;
        this.state = State.SAME;
        this.label = label;
        this.type = type.intern();
    }

    public State getState() {
        return state;
    }

    public void setState(State x) {
        if (state == State.IMMUTABLE) {
            throw new InternalError("Can't change immutable instances");
        }
        this.state = x;
    }

    public char getToIndex() {
        return toIndex;
    }

    public char getFromIndex() {
        return fromIndex;
    }

    public String getName() {
        return "in" + toIndex;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof InputEdge)) {
            return false;
        }
        InputEdge conn2 = (InputEdge) o;
        boolean result = conn2.fromIndex == fromIndex && conn2.toIndex == toIndex && conn2.from == from && conn2.to == to;
        if (result && (state == State.IMMUTABLE || conn2.state == State.IMMUTABLE)) {
            // Immutable instances must be exactly the same
            return Objects.equals(conn2.label, label) && conn2.state == state;
        }
        return result;
    }

    @Override
    public String toString() {
        return "Edge from " + from + " to " + to + "(" + (int) fromIndex + ", " + (int) toIndex + ") ";
    }

    @Override
    public int hashCode() {
        return (from << 20 | to << 8 | toIndex << 4 | fromIndex);
    }
}
