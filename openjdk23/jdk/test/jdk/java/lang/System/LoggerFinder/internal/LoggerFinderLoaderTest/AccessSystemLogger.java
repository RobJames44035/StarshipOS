/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
import java.io.IOException;
import java.lang.System.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

/**
 *
 * @author danielfuchs
 */
public final class AccessSystemLogger {

    public AccessSystemLogger() {
        this(check());
    }

    private AccessSystemLogger(Void unused) {
    }

    private static Void check() {
        if (AccessSystemLogger.class.getClassLoader() != null) {
            throw new RuntimeException("AccessSystemLogger should be loaded by the null classloader");
        }
        return null;
    }

    public Logger getLogger(String name) {
        Logger logger = System.getLogger(name);
        System.out.println("System.getLogger(\"" + name + "\"): " + logger);
        return logger;
    }

    public Logger getLogger(String name, ResourceBundle bundle) {
        Logger logger = System.getLogger(name, bundle);
        System.out.println("System.getLogger(\"" + name + "\", bundle): " + logger);
        return logger;
    }

    static final Class<?>[] toCopy = { AccessSystemLogger.class, CustomSystemClassLoader.class };

    // copy AccessSystemLogger.class to ./boot
    public static void main(String[] args) throws IOException {
        Path testDir = Paths.get(System.getProperty("user.dir", "."));
        Path bootDir = Paths.get(testDir.toString(), "boot");
        Path classes = Paths.get(System.getProperty("test.classes", "build/classes"));
        if (Files.notExists(bootDir)) {
            Files.createDirectory(bootDir);
        }
        for (Class<?> c : toCopy) {
            Path thisClass = Paths.get(classes.toString(),
                c.getSimpleName()+".class");
            Path dest = Paths.get(bootDir.toString(),
                c.getSimpleName()+".class");
            Files.copy(thisClass, dest, StandardCopyOption.REPLACE_EXISTING);
        }
    }

}
