/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple.internal;

/**
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
class Row {
    final String option;
    final String description;

    Row( String option, String description ) {
        this.option = option;
        this.description = description;
    }

    @Override
    public boolean equals( Object that ) {
        if ( that == this )
            return true;
        if ( that == null || !getClass().equals( that.getClass() ) )
            return false;

        Row other = (Row) that;
        return option.equals( other.option ) && description.equals( other.description );
    }

    @Override
    public int hashCode() {
        return option.hashCode() ^ description.hashCode();
    }
}
