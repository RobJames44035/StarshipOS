/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @modules java.xml
 * @library /test/lib
 * @build DescribeModuleTest
 * @run testng DescribeModuleTest
 * @summary Basic test for java --describe-module
 */

import jdk.test.lib.process.ProcessTools;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

@Test
public class DescribeModuleTest {

    /**
     * Test that the output describes java.base
     */
    private void expectJavaBase(String... args) throws Exception {
        int exitValue = ProcessTools.executeTestJava(args)
                .outputTo(System.out)
                .errorTo(System.out)
                .stdoutShouldContain("java.base")
                .stdoutShouldContain("exports java.lang")
                .stdoutShouldContain("uses java.nio.file.spi.FileSystemProvider")
                .stdoutShouldContain("contains sun.launcher")
                .stdoutShouldNotContain("requires ")
                .getExitValue();
        assertTrue(exitValue == 0);
    }

    /**
     * Test that the output describes java.xml
     */
    private void expectJavaXml(String... args) throws Exception {
        int exitValue = ProcessTools.executeTestJava(args)
                .outputTo(System.out)
                .errorTo(System.out)
                .stdoutShouldContain("java.xml")
                .stdoutShouldContain("exports javax.xml")
                .stdoutShouldContain("requires java.base")
                .stdoutShouldContain("uses javax.xml.stream.XMLInputFactory")
                .getExitValue();
        assertTrue(exitValue == 0);
    }

    /**
     * Test output/exitValue when describing an unknown module
     */
    private void expectUnknownModule(String... args) throws Exception {
        int exitValue = ProcessTools.executeTestJava(args)
                .outputTo(System.out)
                .errorTo(System.out)
                .stdoutShouldNotContain("requires java.base")
                .getExitValue();
        assertTrue(exitValue != 0);
    }


    public void testDescribeJavaBase() throws Exception {
        expectJavaBase("--describe-module", "java.base");
        expectJavaBase("--describe-module=java.base");
        expectJavaBase("-d", "java.base");
    }

    public void testDescribeJavaXml() throws Exception {
        expectJavaXml("--describe-module", "java.xml");
        expectJavaXml("--describe-module=java.xml");
        expectJavaXml("-d", "java.xml");
    }

    public void testDescribeUnknownModule() throws Exception {
        expectUnknownModule("--describe-module", "jdk.rhubarb");
        expectUnknownModule("--describe-module=jdk.rhubarb");
        expectUnknownModule("-d", "jdk.rhubarb");
    }

}
