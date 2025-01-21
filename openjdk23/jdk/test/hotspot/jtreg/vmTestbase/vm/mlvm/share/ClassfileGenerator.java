/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.share;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import nsk.share.ArgumentParser;

public abstract class ClassfileGenerator {

    public static class Klass {
        private String pkgName;
        private String className;
        private byte[] bytes;
        private String mainMethodName;
        private String mainMethodSignature;

        public Klass(String packageName, String className, String mainMethodName, String mainMethodSignature, byte[] bytes) {
            this.pkgName = packageName;
            this.className = className;
            this.mainMethodName = mainMethodName;
            this.mainMethodSignature = mainMethodSignature;
            this.bytes = bytes;
        }

        public String getClassName() {
            return (this.pkgName != null ? (this.pkgName + ".") : "") + this.className;
        }

        public String getSimpleClassName() {
            return this.className;
        }

        public String getPackageName() {
            return this.pkgName;
        }

        public String getMainMethodName() {
            return mainMethodName;
        }

        public String getMainMethodSignature() {
            return mainMethodSignature;
        }

        public byte[] getBytes() {
            return this.bytes;
        }

        public void writeClass(String destDir) throws IOException {
            boolean wroteOk = false;

            File outDir;
            if (this.pkgName != null)
                outDir = new File(destDir, this.pkgName.replace('.', '/'));
            else
                outDir = new File(".");

            outDir.mkdirs();

            File outFile = new File(outDir, this.className.concat(".class"));
            FileOutputStream outStream = new FileOutputStream(outFile);
            try {
                outStream.write(this.bytes);
                wroteOk = true;
            } finally {
                outStream.close();

                if (!wroteOk)
                    outFile.delete();
            }
        }
    }

    protected String fullClassName, pkgName, shortClassName;

    public void setClassName(String pkgName, String shortClassName) {
        this.pkgName = pkgName;
        this.shortClassName = shortClassName;
        fullClassName = (pkgName == null ? "" : (pkgName.replace('.', '/') + '/'))
                      + shortClassName;
    }

    public abstract Klass[] generateBytecodes();

    public static void main(String[] args) {
        try {
            Env.init(new ArgumentParser(args) {
                @Override
                protected boolean checkOption(String option, String value) {
                    if (option.equals("d"))
                        return true;

                    return super.checkOption(option, value);
                }
            });

            Class<?> caller = Class.forName(Thread.currentThread()
                    .getStackTrace()[2].getClassName());
            ClassfileGenerator gen = (ClassfileGenerator) caller.newInstance();
            gen.setClassName(caller.getPackage().getName(), caller
                    .getSimpleName().replaceFirst("^GENERATE_", ""));

            String destDir = Env.getArgParser().getOptions().getProperty("d");

            Klass[] klasses = gen.generateBytecodes();

            for (Klass k : klasses)
                k.writeClass(destDir);

        } catch (Exception e) {
            Env.complain(e, "Generator caught an error");
            System.exit(1);
        }
    }
}
