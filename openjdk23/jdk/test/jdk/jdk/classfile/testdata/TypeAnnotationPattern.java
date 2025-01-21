/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package testdata;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeAnnotationPattern {

    class Middle {
        class Inner {}
    }

    @Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, TYPE_PARAMETER, TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Foo {
    }

    @Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, TYPE_PARAMETER, TYPE_USE})
    @Retention(RetentionPolicy.CLASS)
    @interface Bar {
    }

    @Foo String @Bar [][]  fa;
    String @Foo [] @Bar[] fb;
    @Bar String[] @Foo [] fc;

    @Foo TypeAnnotationPattern.@Bar Middle.Inner fd;
    TypeAnnotationPattern.@Foo Middle.@Bar Inner fe;
    @Bar TypeAnnotationPattern.Middle.@Foo Inner ff;

    @Foo Map<@Bar String,Object> fg;
    Map<@Foo String,@Bar Object> fh;
    @Bar Map<String,@Foo Object> fi;

    List<@Foo ? extends @Bar String> fj;
    List<@Bar ? extends @Foo String> fk;

    @SuppressWarnings("unchecked")
    <E> void annotatedCode(
        @Foo String @Bar [][]  mpa,
        String @Foo [] @Bar[] mpb,
        @Bar String[] @Foo [] mpc,

        @Foo TypeAnnotationPattern.@Bar Middle.Inner mpd,
        TypeAnnotationPattern.@Foo Middle.@Bar Inner mpe,
        @Bar TypeAnnotationPattern.Middle.@Foo Inner mpf,

        @Foo Map<@Bar String,Object> mpg,
        Map<@Foo String,@Bar Object> mph,
        @Bar Map<String,@Foo Object> mpi,

        List<@Foo ? extends @Bar String> mpj,
        List<@Bar ? extends @Foo String> mpk
    ) {
        @Foo String[][]  lva;
//        String @Foo [][] lvb; // AssertionError from javac
//        String[] @Foo [] lvc; // AssertionError from javac

        @Foo TypeAnnotationPattern.@Bar Middle.Inner lvd;
//        TypeAnnotationPattern.@Foo Middle.@Bar Inner lve; // AssertionError from javac
//        @Bar TypeAnnotationPattern.Middle.@Foo Inner lvf; // AssertionError from javac

        @Foo Map<@Bar String,Object> lvg;
        Map<@Foo String,@Bar Object> lvh;
        @Bar Map<String,@Foo Object> lvi;

        List<@Foo ? extends @Bar String> lvj;
        List<@Bar ? extends @Foo String> lvk;

        Object o = null;
//        var cea = (@Foo String [][]) o; // AssertionError from javac
//        var ceb = (String @Foo [] @Bar[]) o; // AssertionError from javac
//        var cec = (@Bar String[] @Foo []) o; // AssertionError from javac

        var ced = (@Foo TypeAnnotationPattern.@Bar Middle.Inner) o;
//        var cee = (TypeAnnotationPattern.@Foo Middle.@Bar Inner) o; // AssertionError from javac
//        var cef = (@Bar TypeAnnotationPattern.Middle.@Foo Inner) o; // AssertionError from javac

//        var ceg = (@Foo Map<@Bar String,Object> ) o; // AssertionError from javac
        var ceh = (Map<@Foo String,@Bar Object>) o;
//        var cei = (@Bar Map<String,@Foo Object>) o; // AssertionError from javac

        var cej = (List<@Foo ? extends @Bar String>) o;
        var cek = (List<@Bar ? extends @Foo String>) o;

//        var na = new @Foo String [][] {}; // AssertionError from javac
//        var nb = new String @Foo [] @Bar[] {}; // AssertionError from javac
//        var nc = new @Bar String[] @Foo [] {}; // AssertionError from javac

//        var ng = new @Foo HashMap<@Bar String,Object>(); // AssertionError from javac
        var nh = new HashMap<@Foo String,@Bar Object>();
//        var ni = new @Bar HashMap<String,@Foo Object>(); // AssertionError from javac

//        if (o instanceof @Foo String [][]) {} // AssertionError from javac
//        if (o instanceof String @Foo [] @Bar[]) {} // AssertionError from javac
//        if (o instanceof @Bar String[] @Foo []) {} // AssertionError from javac

        if (o instanceof @Foo TypeAnnotationPattern.Middle.Inner) {}
//        if (o instanceof TypeAnnotationPattern.@Foo Middle.@Bar Inner) {} // AssertionError from javac
//        if (o instanceof @Bar TypeAnnotationPattern.Middle.@Foo Inner) {} // AssertionError from javac
    }
}
