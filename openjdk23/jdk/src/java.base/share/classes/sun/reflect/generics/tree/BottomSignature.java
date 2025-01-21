/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.reflect.generics.tree;

import sun.reflect.generics.visitor.TypeTreeVisitor;

public class BottomSignature implements FieldTypeSignature {
    private static final BottomSignature singleton = new BottomSignature();

    private BottomSignature(){}

    public static BottomSignature make() {return singleton;}

    public void accept(TypeTreeVisitor<?> v){v.visitBottomSignature(this);}
}
