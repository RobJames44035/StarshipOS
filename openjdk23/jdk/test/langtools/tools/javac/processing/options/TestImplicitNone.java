/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6935638
 * @summary -implicit:none prevents compilation with annotation processing
 * @modules jdk.compiler
 */

import java.io.*;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;


@SupportedAnnotationTypes("*")
public class TestImplicitNone extends AbstractProcessor {
    public static void main(String... args) throws Exception {
        new TestImplicitNone().run();
    }

    void run() throws Exception {
        File classesDir = new File("tmp", "classes");
        classesDir.mkdirs();
        File test_java = new File(new File("tmp", "src"), "Test.java");
        writeFile(test_java, "class Test { }");

        // build up list of options and files to be compiled
        List<String> opts = new ArrayList<String>();
        List<File> files = new ArrayList<File>();

        opts.add("-d");
        opts.add(classesDir.getPath());
        opts.add("-processorpath");
        opts.add(System.getProperty("test.classes"));
        opts.add("-implicit:none");
        opts.add("-processor");
        opts.add(TestImplicitNone.class.getName());
        files.add(test_java);

        compile(opts, files);

        File test_class = new File(classesDir, "Test.class");
        if (!test_class.exists())
            throw new Exception("Test.class not generated");
    }

    /** Compile files with options provided. */
    void compile(List<String> opts, List<File> files) throws Exception {
        System.err.println("javac: " + opts + " " + files);
        List<String> args = new ArrayList<String>();
        args.addAll(opts);
        for (File f: files)
            args.add(f.getPath());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        int rc = com.sun.tools.javac.Main.compile(args.toArray(new String[args.size()]), pw);
        pw.flush();
        if (sw.getBuffer().length() > 0)
            System.err.println(sw.toString());
        if (rc != 0)
            throw new Exception("compilation failed: rc=" + rc);
    }

    /** Write a file with a given body. */
    void writeFile(File f, String body) throws Exception {
        if (f.getParentFile() != null)
            f.getParentFile().mkdirs();
        Writer out = new FileWriter(f);
        try {
            out.write(body);
        } finally {
            out.close();
        }
    }

    //----- annotation processor methods -----

    public boolean process(Set<? extends TypeElement> annotations,
                         RoundEnvironment roundEnv) {
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
}
