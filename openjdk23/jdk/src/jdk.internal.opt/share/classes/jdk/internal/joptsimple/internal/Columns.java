/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple.internal;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import static java.text.BreakIterator.*;

import static jdk.internal.joptsimple.internal.Strings.*;

/**
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
class Columns {
    private static final int INDENT_WIDTH = 2;

    private final int optionWidth;
    private final int descriptionWidth;

    Columns( int optionWidth, int descriptionWidth ) {
        this.optionWidth = optionWidth;
        this.descriptionWidth = descriptionWidth;
    }

    List<Row> fit( Row row ) {
        List<String> options = piecesOf( row.option, optionWidth );
        List<String> descriptions = piecesOf( row.description, descriptionWidth );

        List<Row> rows = new ArrayList<>();
        for ( int i = 0; i < Math.max( options.size(), descriptions.size() ); ++i )
            rows.add( new Row( itemOrEmpty( options, i ), itemOrEmpty( descriptions, i ) ) );

        return rows;
    }

    private static String itemOrEmpty( List<String> items, int index ) {
        return index >= items.size() ? "" : items.get( index );
    }

    private List<String> piecesOf( String raw, int width ) {
        List<String> pieces = new ArrayList<>();

        for ( String each : raw.trim().split( LINE_SEPARATOR ) )
            pieces.addAll( piecesOfEmbeddedLine( each, width ) );

        return pieces;
    }

    private List<String> piecesOfEmbeddedLine( String line, int width ) {
        List<String> pieces = new ArrayList<>();

        BreakIterator words = BreakIterator.getLineInstance();
        words.setText( line );

        StringBuilder nextPiece = new StringBuilder();

        int start = words.first();
        for ( int end = words.next(); end != DONE; start = end, end = words.next() )
            nextPiece = processNextWord( line, nextPiece, start, end, width, pieces );

        if ( nextPiece.length() > 0 )
            pieces.add( nextPiece.toString() );

        return pieces;
    }

    private StringBuilder processNextWord( String source, StringBuilder nextPiece, int start, int end, int width,
                                           List<String> pieces ) {
        StringBuilder augmented = nextPiece;

        String word = source.substring( start, end );
        if ( augmented.length() + word.length() > width ) {
            pieces.add( augmented.toString().replaceAll( "\\s+$", "" ) );
            augmented = new StringBuilder( repeat( ' ', INDENT_WIDTH ) ).append( word );
        }
        else
            augmented.append( word );

        return augmented;
    }
}
