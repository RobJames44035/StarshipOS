/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */


/*
 * The Original Code is HAT. The Initial Developer of the
 * Original Code is Bill Foote, with contributions from others
 * at JavaSoft/Sun.
 */

package jdk.test.lib.hprof.model;

/**
 * A visitor for a JavaThing.  @see JavaObject#visitReferencedObjects()
 *
 * @author      Bill Foote
 */


public interface JavaHeapObjectVisitor {
    public void visit(JavaHeapObject other);

    /**
     * Should the given field be excluded from the set of things visited?
     * @return true if it should.
     */
    public boolean exclude(JavaClass clazz, JavaField f);

    /**
     * @return true iff exclude might ever return true
     */
    public boolean mightExclude();
}
