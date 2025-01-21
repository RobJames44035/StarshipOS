/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8009367
 * @summary Test that the correct kind of names (binary) are used when comparing
 *          Class and Symbol for repeatable Classes.
 * @library /tools/javac/lib
 * @modules jdk.compiler/com.sun.tools.javac.util
 * @build   JavacTestingAbstractProcessor TestQualifiedNameUsed p.Q p.QQ p.R p.RR
 * @run compile -XDaccessInternalAPI -processor TestQualifiedNameUsed -proc:only TestQualifiedNameUsed.java
 */

import java.lang.annotation.Repeatable;
import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import static javax.lang.model.util.ElementFilter.*;

import com.sun.tools.javac.util.Assert;

public class TestQualifiedNameUsed extends JavacTestingAbstractProcessor {

    @Q
    @p.Q
    @p.R.Q
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            boolean hasRun = false;
            for (Element element : roundEnv.getRootElements()) {
                for (ExecutableElement e : methodsIn(element.getEnclosedElements())) {
                    if (e.getSimpleName().contentEquals("value"))
                        continue; // don't want to look Q.value() in this file

                    hasRun = true;
                    Q[] qs = e.getAnnotationsByType(Q.class);
                    Assert.check(qs.length == 1);
                    Assert.check(qs[0] instanceof Q);

                    p.Q[] ps = e.getAnnotationsByType(p.Q.class);
                    Assert.check(ps.length == 1);
                    Assert.check(ps[0] instanceof p.Q);

                    p.R.Q[] rs = e.getAnnotationsByType(p.R.Q.class);
                    Assert.check(rs.length == 1);
                    Assert.check(rs[0] instanceof p.R.Q);
                }
            }
            if (!hasRun) throw new RuntimeException("No methods!");
        }
        return true;
    }
}

@Repeatable(QQ.class)
@interface Q {}

@interface QQ {
    Q[] value();
}
