/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8217047
 * @summary Verify the parameter names can be injected using ParameterNameProvider.
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.JavacTask toolbox.TestRunner toolbox.ToolBox
 * @run main LoadParameterNamesLazily
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import com.sun.source.util.*;

import toolbox.JavacTask;
import toolbox.Task;
import toolbox.TestRunner;
import toolbox.ToolBox;

public class LoadParameterNamesLazily extends TestRunner {

    public static void main(String... args) throws Exception {
        LoadParameterNamesLazily t = new LoadParameterNamesLazily();
        t.runTests();
    }

    private static final String libClass =
            "package lib;" +
            "/**Lib javadoc.*/" +
            "public class Lib {" +
            "    /**Lib method javadoc.*/" +
            "    public static void m(int param, int other) {}" +
            "}";
    private final ToolBox tb = new ToolBox();

    LoadParameterNamesLazily() throws IOException {
        super(System.err);
    }

    @Test
    public void testLoadTreesLazily() throws IOException {
        Path libSrc = Paths.get("lib-src");
        tb.writeJavaFiles(libSrc, libClass);
        Path libClasses = Paths.get("lib-classes");
        Files.createDirectories(libClasses);

        new JavacTask(tb)
              .outdir(libClasses)
              .options()
              .files(tb.findJavaFiles(libSrc))
              .run(Task.Expect.SUCCESS)
              .writeAll();

        Path src = Paths.get("src");
        tb.writeJavaFiles(src,
                          "class Use {" +
                          " lib.Lib lib;" +
                          "}");
        Path classes = Paths.get("classes");
        Files.createDirectories(classes);

        new JavacTask(tb)
              .outdir(classes)
              .options("-classpath", libClasses.toString())
              .files(tb.findJavaFiles(src))
              .callback(task -> {
                  task.setParameterNameProvider(parameter -> {
                      ExecutableElement method = (ExecutableElement) parameter.getEnclosingElement();
                      TypeElement clazz =
                              (TypeElement) method.getEnclosingElement();
                      if (clazz.getQualifiedName().contentEquals("lib.Lib")) {
                          if (method.getParameters().indexOf(parameter) == 0) {
                              return "testName";
                          } else {
                              return null;
                          }
                      }
                      return null;
                  });
                  task.addTaskListener(new TaskListener() {
                      @Override
                      public void finished(TaskEvent e) {
                          if (e.getKind() == TaskEvent.Kind.ANALYZE) {
                              TypeElement lib = task.getElements().getTypeElement("lib.Lib");
                              lib.getClass(); //not null
                              ExecutableElement method =
                                      ElementFilter.methodsIn(lib.getEnclosedElements()).get(0);
                              Name paramName0 = method.getParameters().get(0).getSimpleName();
                              if (!paramName0.contentEquals("testName")) {
                                  throw new IllegalStateException("Unexpected parameter name: " +
                                                                  paramName0);
                              }
                              Name paramName1 = method.getParameters().get(1).getSimpleName();
                              if (!paramName1.contentEquals("arg1")) {
                                  throw new IllegalStateException("Unexpected parameter name: " +
                                                                  paramName1);
                              }
                          }
                      }
                  });
              })
              .run(Task.Expect.SUCCESS)
              .writeAll();
    }

}
