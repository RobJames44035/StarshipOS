/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package java.nio.charset.spi;

import java.nio.charset.Charset;
import java.util.Iterator;


/**
 * Charset service-provider class.
 *
 * <p> A charset provider is a concrete subclass of this class that has a
 * zero-argument constructor and some number of associated {@code Charset}
 * implementation classes.  Charset providers are deployed on the application
 * module path or the application class path. In order to be looked up, charset
 * providers must be visible to the {@link ClassLoader#getSystemClassLoader() system
 * class loader}. See {@link java.util.ServiceLoader##developing-service-providers
 * Deploying Service Providers} for further detail on deploying a charset
 * provider as a module or on the class path.
 *
 * <p> For a charset provider deployed in a module, the <i>provides</i>
 * directive must be specified in the module declaration. The provides directive
 * specifies both the service and the service provider. In this case, the service
 * is {@code java.nio.charset.spi.CharsetProvider}.
 *
 * <p> As an example, a charset provider deployed as a module might specify the
 * following directive:
 * <pre>{@code
 *     provides java.nio.charset.spi.CharsetProvider with com.example.ExternalCharsetProvider;
 * }</pre>
 *
 * <p> For a charset provider deployed on the class path, it identifies itself
 * with a provider-configuration file named {@code
 * java.nio.charset.spi.CharsetProvider} in the resource directory
 * {@code META-INF/services}.  The file should contain a list of
 * fully-qualified concrete charset-provider class names, one per line.  A line
 * is terminated by any one of a line feed ({@code '\n'}), a carriage return
 * ({@code '\r'}), or a carriage return followed immediately by a line feed.
 * Space and tab characters surrounding each name, as well as blank lines, are
 * ignored.  The comment character is {@code '#'} (<code>'&#92;u0023'</code>); on
 * each line all characters following the first comment character are ignored.
 * The file must be encoded in UTF-8.
 *
 * <p> If a particular concrete charset provider class is named in more than
 * one configuration file, or is named in the same configuration file more than
 * once, then the duplicates will be ignored.  The configuration file naming a
 * particular provider need not be in the same jar file or other distribution
 * unit as the provider itself.  The provider must be accessible from the same
 * class loader that was initially queried to locate the configuration file;
 * this is not necessarily the class loader that loaded the file. </p>
 *
 *
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 *
 * @see java.nio.charset.Charset
 */

public abstract class CharsetProvider {

    /**
     * Initializes a new charset provider.
     */
    protected CharsetProvider() {
    }

    /**
     * Creates an iterator that iterates over the charsets supported by this
     * provider.  This method is used in the implementation of the {@link
     * java.nio.charset.Charset#availableCharsets Charset.availableCharsets}
     * method.
     *
     * @return  The new iterator
     */
    public abstract Iterator<Charset> charsets();

    /**
     * Retrieves a charset for the given charset name.
     *
     * @param  charsetName
     *         The name of the requested charset; may be either
     *         a canonical name or an alias
     *
     * @return  A charset object for the named charset,
     *          or {@code null} if the named charset
     *          is not supported by this provider
     */
    public abstract Charset charsetForName(String charsetName);

}
