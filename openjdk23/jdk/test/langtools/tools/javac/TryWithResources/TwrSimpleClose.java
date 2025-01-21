/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8194978
 * @summary Verify than an appropriate number of close method invocations is generated.
 * @library /tools/lib
 * @build toolbox.ToolBox TwrSimpleClose
 * @run main TwrSimpleClose
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.sun.source.util.JavacTask;
import java.lang.classfile.*;
import java.lang.classfile.attribute.CodeAttribute;
import java.lang.classfile.constantpool.MemberRefEntry;
import java.lang.classfile.instruction.InvokeInstruction;

import toolbox.ToolBox;

public class TwrSimpleClose {

    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public static void main(String... args) throws Exception {
        new TwrSimpleClose().run();
    }

    void run() throws Exception {
        run("try (Test t = new Test()) { System.err.println(0); }", 2);
        run("try (Test t = new Test()) {\n" +
            "    if (t.hashCode() == 42)\n" +
            "        return;\n" +
            "    System.err.println(0);\n" +
            "}\n", 3);
    }

    void run(String trySpec, int expectedCloseCount) throws Exception {
        String template = "public class Test implements AutoCloseable {\n" +
                          "    void t(int i) {\n" +
                          "        TRY\n" +
                          "    }\n" +
                          "    public void close() { }\n" +
                          "}\n";
        String code = template.replace("TRY", trySpec);
        int closeCount = 0;

        try (StandardJavaFileManager sfm = compiler.getStandardFileManager(null, null, null);
             JFMImpl fm = new JFMImpl(sfm)) {
            Iterable<ToolBox.JavaSource> files = Arrays.asList(new ToolBox.JavaSource(code));
            JavacTask task = (JavacTask) compiler.getTask(null, fm, null, null, null, files);
            task.call();

            if (fm.classBytes.size() != 1) {
                throw new AssertionError();
            }

            byte[] data = fm.classBytes.values().iterator().next();
            ClassModel cf = ClassFile.of().parse(new ByteArrayInputStream(data).readAllBytes());

            for (MethodModel m : cf.methods()) {
                CodeAttribute codeAttr = m.findAttribute(Attributes.code()).orElseThrow();
                for (CodeElement ce : codeAttr.elementList()) {
                    if (ce instanceof InvokeInstruction ins && ins.opcode() == Opcode.INVOKEVIRTUAL) {
                        MemberRefEntry method = ins.method();
                        if (method.name().equalsString("close")) {
                            closeCount++;
                        }
                    }
                }
            }
            if (expectedCloseCount != closeCount) {
                throw new IllegalStateException("expected close count: " + expectedCloseCount +
                                                "; actual: " + closeCount + "; code:\n" + code);
            }
        }
    }

    private static final class JFMImpl extends ForwardingJavaFileManager<JavaFileManager> {

        private final Map<String, byte[]> classBytes = new HashMap<>();

        public JFMImpl(JavaFileManager fileManager) {
            super(fileManager);
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind,
                                                   FileObject sibling) throws IOException {
            try {
                return new SimpleJavaFileObject(new URI("mem://" + className + ".class"), kind) {
                    @Override
                    public OutputStream openOutputStream() throws IOException {
                        return new ByteArrayOutputStream() {
                            @Override
                            public void close() throws IOException {
                                super.close();
                                classBytes.put(className, toByteArray());
                            }
                        };
                    }
                };
            } catch (URISyntaxException ex) {
                throw new IOException(ex);
            }
        }
    }

}
