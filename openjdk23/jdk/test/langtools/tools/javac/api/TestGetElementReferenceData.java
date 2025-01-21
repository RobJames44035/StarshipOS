/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package test/*getElement:PACKAGE:test*/.nested/*getElement:PACKAGE:test.nested*/;
/*getElement:PACKAGE:test.nested*/
import java.lang.annotation.*;
import static test.nested.TestGetElementReferenceData.Sub.*;

public class TestGetElementReferenceData {

    private static void test() {
        StringBuilder/*getElement:CLASS:java.lang.StringBuilder*/ sb = new/*getElement:CONSTRUCTOR:java.lang.StringBuilder()*/ StringBuilder();
        sb/*getElement:LOCAL_VARIABLE:sb*/.append/*getElement:METHOD:java.lang.StringBuilder.append(int)*/(0);
        sb.reverse( /*getElement:METHOD:java.lang.StringBuilder.reverse()*/);
        java.util.List< /*getElement:INTERFACE:java.util.List*/ String> l;
        utility/*getElement:METHOD:test.nested.TestGetElementReferenceData.Base.utility()*/();
        target(TestGetElementReferenceData :: test/*getElement:METHOD:test.nested.TestGetElementReferenceData.test()*/);
        Object/*getElement:CLASS:java.lang.Object*/ o = null;
        if (o/*getElement:LOCAL_VARIABLE:o*/ instanceof String/*getElement:CLASS:java.lang.String*/ str/*getElement:BINDING_VARIABLE:str*/) ;
        I i = null;
        i.toString/*getElement:METHOD:test.nested.TestGetElementReferenceData.I.toString()*/();
        J j = null;
        j.toString/*getElement:METHOD:test.nested.TestGetElementReferenceData.I.toString()*/();
    }
    private static void target(Runnable r) { r.run(); }
    public static class Base {
        public static void utility() {}
    }
    public static class Sub extends @TypeAnnotation( /*getElement:ANNOTATION_TYPE:test.nested.TestGetElementReferenceData.TypeAnnotation*/) Base {
    }
   @Deprecated( /*getElement:ANNOTATION_TYPE:java.lang.Deprecated*/)
    public static class TypeParam<TT/*getElement:TYPE_PARAMETER:TT*/> {
    }
    @Target(ElementType.TYPE_USE)
    @interface TypeAnnotation {
    }
    interface I {
        public String toString();
    }
    interface J extends I {}
}
