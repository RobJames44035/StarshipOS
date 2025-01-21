/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.io.IOException;
import java.io.InputStream;
import java.lang.module.ModuleDescriptor;
import java.lang.module.InvalidModuleDescriptorException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Uses the ModuleDescriptor.read API to read the module-info.class in the
 * ${test.classes} directory and expects InvalidModuleDescriptorException
 * to be thrown.
 */
public class CheckBadModuleInfo {
    public static void main(String[] args) throws IOException {
        Path mi = Path.of(System.getProperty("test.classes"), "module-info.class");
        try (InputStream in = Files.newInputStream(mi)) {
            try {
                ModuleDescriptor descriptor = ModuleDescriptor.read(in);
                System.out.println(descriptor);
                throw new RuntimeException("InvalidModuleDescriptorException expected");
            } catch (InvalidModuleDescriptorException e) {
                // expected
                System.out.println(e);
            }
        }
    }
}
