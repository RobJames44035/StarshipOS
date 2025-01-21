/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8038455
 * @summary Verify that Types caches (in particular MembersClosureCache) get cleared between rounds.
 * @library /tools/javac/lib
 * @modules jdk.compiler
 * @build JavacTestingAbstractProcessor TypesCachesCleared
 * @compile/process -processor TypesCachesCleared TypesCachesCleared.java
 */

import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import static javax.lang.model.util.ElementFilter.constructorsIn;
import javax.lang.model.util.Elements;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

public class TypesCachesCleared extends JavacTestingAbstractProcessor {
    int round = 1;
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        new TestPathScanner<Void>() {
            @Override
            public void visit(Void t) {
            }
        };
        TypeElement currentClass = elements.getTypeElement("TypesCachesCleared");
        Trees trees = Trees.instance(processingEnv);
        TreePath path = trees.getPath(currentClass);
        new TreePathScanner<Void, Void>() {
            @Override public Void visitClass(ClassTree node, Void p) {
                trees.getElement(getCurrentPath());
                return super.visitClass(node, p);
            }
        }.scan(path, null);
        return false;
    }

    public TypesCachesCleared() {
        class Local { }
        new Object() { };
    }

    public boolean process(Elements elements) {
        TypeElement currentClass = elements.getTypeElement("OnDemandAttribution");
        ExecutableElement constr = constructorsIn(currentClass.getEnclosedElements()).get(0);
        Trees trees = Trees.instance(processingEnv);
        TreePath path = trees.getPath(constr);

        new TreePathScanner<Void, Void>() {
            @Override public Void visitClass(ClassTree node, Void p) {
                if (node.getSimpleName().contentEquals("Local")) {
                     //will also attribute the body on demand:
                    Element el = trees.getElement(getCurrentPath());
                    Name binaryName = elements.getBinaryName((TypeElement) el);
                    if (!binaryName.contentEquals("OnDemandAttribution$1Local")) {
                        throw new IllegalStateException("Incorrect binary name=" + binaryName);
                    }
                }
                return super.visitClass(node, p);
            }
            @Override public Void visitNewClass(NewClassTree node, Void p) {
                Element el = trees.getElement(getCurrentPath());
                Name binaryName = elements.getBinaryName((TypeElement) el.getEnclosingElement());
                if (!binaryName.contentEquals("OnDemandAttribution$1")) {
                    throw new IllegalStateException("Incorrect binary name=" + binaryName);
                }
                return super.visitNewClass(node, p);
            }
        }.scan(path, null);

        return true;
    }
    public static interface TestVisitor<T> {
        public void visit(T t);
    }

    public static class TestScanner<T> implements TestVisitor<T> {
        public void visit(T t) { }
    }

    public static class TestPathScanner<T> extends TestScanner<T> {
        public void visit(T t) { }
    }
}
