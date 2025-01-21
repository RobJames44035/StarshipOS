/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6402516
 * @summary need Trees.getScope(TreePath)
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.comp
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.tree
 *          jdk.compiler/com.sun.tools.javac.util
 * @build Checker CheckClass
 * @run main CheckClass
 */

import java.util.*;
import com.sun.source.tree.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;

/*
 * Check the enclosing class of a scope against the contents of string literals.
 */
public class CheckClass extends Checker {
    public static void main(String... args) throws Exception {
        Checker chk = new CheckClass();
        chk.check("TestClass.java");
    }

    @Override
    protected boolean checkLocal(Scope s, String ref) {
        //System.err.println("checkLocal: " + s + " " + ref + " " + s.getEnclosingClass());
        TypeElement te = s.getEnclosingClass();
        boolean ok;
        if (te == null)
            ok = ref.equals("0");
        else {
            CharSequence name = te.getQualifiedName();
            ok = ref.equals(name == null || name.length() == 0 ? "-" : name.toString());
        }
        if (!ok)
            error(s, ref, "bad enclosing class found: " + te);
        return ok;
    }
}
