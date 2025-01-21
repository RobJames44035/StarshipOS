/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug     6419926
 * @summary JSR 199: FileObject.toUri() generates URI without schema (Solaris)
 * @modules java.compiler
 *          jdk.compiler
 */

import java.io.*;
import java.net.*;
import java.util.*;
import javax.tools.*;

public class T6419926 {
    public static void main(String[] argv) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        try (StandardJavaFileManager mgr = compiler.getStandardFileManager( new DiagnosticCollector<JavaFileObject>(), null, null)) {
            System.out.println( new File( new File(".").toURI() ).getAbsolutePath() );
            mgr.setLocation(StandardLocation.CLASS_OUTPUT,
                                Collections.singleton(new File(".")));

            FileObject fo = mgr.getFileForOutput(StandardLocation.CLASS_OUTPUT,
                                    "", "file.to.delete", null);
            URI uri = fo.toUri();
            System.out.println( uri );

            if (!"file".equals(uri.getScheme()))
                throw new Exception("unexpected scheme for uri: " + uri.getScheme());
        }
    }
}
