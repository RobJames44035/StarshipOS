/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */
package jdk.internal.org.jline.reader;

/**
 * A reference to a {@link Widget}.
 */
public class Reference implements Binding {

    private final String name;

    public Reference(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference func = (Reference) o;
        return name.equals(func.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Reference[" + name + ']';
    }
}
