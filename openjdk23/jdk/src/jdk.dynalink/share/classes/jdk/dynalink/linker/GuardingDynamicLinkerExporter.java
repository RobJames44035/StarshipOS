/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.dynalink.linker;

import java.security.Permission;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Supplier;
import jdk.dynalink.DynamicLinkerFactory;

/**
 * A class acting as a supplier of guarding dynamic linkers that can be
 * automatically loaded by other language runtimes. Language runtimes wishing
 * to export their own linkers should subclass this class and implement the
 * {@link #get()} method to return a list of exported linkers and declare the
 * subclass in
 * {@code /META-INF/services/jdk.dynalink.linker.GuardingDynamicLinkerExporter}
 * resource of their distribution (typically, JAR file) so that dynamic linker
 * factories can discover them using the {@link ServiceLoader} mechanism.
 * @see DynamicLinkerFactory#setClassLoader(ClassLoader)
 * @since 9
 */
public abstract class GuardingDynamicLinkerExporter implements Supplier<List<GuardingDynamicLinker>> {
    /**
     * The name of the runtime permission for creating instances of this class.
     * @apiNote
     * This permission cannot be used for controlling access to resources
     * anymore as the Security Manager is no longer supported.
     */
    public static final String AUTOLOAD_PERMISSION_NAME = "dynalink.exportLinkersAutomatically";

    /**
     * Creates a new linker exporter.
     */
    protected GuardingDynamicLinkerExporter() {
    }
}
