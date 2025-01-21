/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple.internal;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

import static jdk.internal.joptsimple.internal.Strings.*;

/**
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
public class Rows {
    private final int overallWidth;
    private final int columnSeparatorWidth;
    private final List<Row> rows = new ArrayList<>();

    private int widthOfWidestOption;
    private int widthOfWidestDescription;

    public Rows( int overallWidth, int columnSeparatorWidth ) {
        this.overallWidth = overallWidth;
        this.columnSeparatorWidth = columnSeparatorWidth;
    }

    public void add( String option, String description ) {
        add( new Row( option, description ) );
    }

    private void add( Row row ) {
        rows.add( row );
        widthOfWidestOption = max( widthOfWidestOption, row.option.length() );
        widthOfWidestDescription = max( widthOfWidestDescription, row.description.length() );
    }

    public void reset() {
        rows.clear();
        widthOfWidestOption = 0;
        widthOfWidestDescription = 0;
    }

    public void fitToWidth() {
        Columns columns = new Columns( optionWidth(), descriptionWidth() );

        List<Row> fitted = new ArrayList<>();
        for ( Row each : rows )
            fitted.addAll( columns.fit( each ) );

        reset();

        for ( Row each : fitted )
            add( each );
    }

    public String render() {
        StringBuilder buffer = new StringBuilder();

        for ( Row each : rows ) {
            pad( buffer, each.option, optionWidth() ).append( repeat( ' ', columnSeparatorWidth ) );
            pad( buffer, each.description, descriptionWidth() ).append( LINE_SEPARATOR );
        }

        return buffer.toString();
    }

    private int optionWidth() {
        return min( ( overallWidth - columnSeparatorWidth ) / 2, widthOfWidestOption );
    }

    private int descriptionWidth() {
        return min( overallWidth - optionWidth() - columnSeparatorWidth, widthOfWidestDescription );
    }

    private StringBuilder pad( StringBuilder buffer, String s, int length ) {
        buffer.append( s ).append( repeat( ' ', length - s.length() ) );
        return buffer;
    }
}
