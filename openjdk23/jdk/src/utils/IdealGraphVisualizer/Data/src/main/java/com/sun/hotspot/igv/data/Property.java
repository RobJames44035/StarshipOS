/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.data;

import java.io.Serializable;

/**
 *
 * @author Thomas Wuerthinger
 */
public class Property implements Serializable {

    public static final long serialVersionUID = 1L;
    private final String name;
    private final String value;

    Property(String name, String value) {
        this.name = name;
        this.value = value;

        if (value == null) {
            throw new IllegalArgumentException("Property value must not be null!");
        }

        if (name == null) {
            throw new IllegalArgumentException("Property name must not be null!");
        }
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Property)) {
            return false;
        }
        Property p2 = (Property) o;
        return name.equals(p2.name) && value.equals(p2.value);
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 13 + value.hashCode();
    }
}
