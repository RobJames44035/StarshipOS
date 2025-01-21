/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8273584
 * @summary Verify TypeElement.getSuperclass works for records when j.l.Record is unavailable
 * @modules jdk.compiler
 */

import com.sun.source.tree.CompilationUnitTree;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaCompiler;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import com.sun.source.util.JavacTask;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

import static javax.tools.JavaFileObject.Kind.CLASS;
import static javax.tools.JavaFileObject.Kind.SOURCE;

public class RecordsErrorRecovery {
    public static void main(String... args) throws IOException {
        new RecordsErrorRecovery().getSuperclass();
    }

    public void getSuperclass() throws IOException {
        JavaCompiler c = ToolProvider.getSystemJavaCompiler();
        try (JavaFileManager fm = c.getStandardFileManager(null, null, null);
             JavaFileManager filtering = new FilteringJavaFileManager(fm)) {
            for (String code : new String[] {"record R(String s) {}", "enum E {A}"}) {
                JavacTask t = (JavacTask) c.getTask(null, filtering, null, null, null,
                        List.of(new MyFileObject(code)));
                CompilationUnitTree cut = t.parse().iterator().next();

                t.analyze();

                Trees trees = Trees.instance(t);
                TreePath tp = new TreePath(new TreePath(cut), cut.getTypeDecls().get(0));
                TypeElement record = (TypeElement) trees.getElement(tp);
                TypeMirror superclass = record.getSuperclass();

                if (superclass.getKind() != TypeKind.ERROR) {
                    throw new AssertionError("Unexpected superclass!");
                }
            }
        }
    }

    class MyFileObject extends SimpleJavaFileObject {
        private final String code;

        MyFileObject(String code) {
            super(URI.create("myfo:///Test.java"), SOURCE);
            this.code = code;
        }

        @Override
        public String getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }

    }

    private static final class FilteringJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

        public FilteringJavaFileManager(JavaFileManager fileManager) {
            super(fileManager);
        }

        @Override
        public Iterable<JavaFileObject> list(JavaFileManager.Location location,
                                             String packageName,
                                             Set<JavaFileObject.Kind> kinds,
                                             boolean recurse) throws IOException {
            Iterable<JavaFileObject> files = super.list(location, packageName, kinds, recurse);

            if ("java.lang".equals(packageName)) {
                files = StreamSupport.stream(files.spliterator(), false)
                                     .filter(fo -> !fo.isNameCompatible("Record", CLASS))
                                     .filter(fo -> !fo.isNameCompatible("Enum", CLASS))
                                     .toList();
            }
            return files;
        }

    }
}
