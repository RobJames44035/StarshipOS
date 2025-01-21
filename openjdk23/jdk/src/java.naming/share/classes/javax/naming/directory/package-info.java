/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/**
 *
 * Extends the {@code javax.naming} package to provide functionality
 * for accessing directory services.
 *
 * <p>
 * This package defines the directory operations of the Java Naming and
 * Directory Interface (JNDI). &nbsp;
 * JNDI provides naming and directory functionality to applications
 * written in the Java programming language. It is designed to be
 * independent of any specific naming or directory service
 * implementation. Thus a variety of services--new, emerging, and
 * already deployed ones--can be accessed in a common way.
 *
 * <p>
 * This package allows applications to retrieve and update attributes
 * associated with objects stored in a directory, and to search for
 * objects using specified attributes.
 *
 * <h2>The Directory Context</h2>
 *
 * The {@code DirContext}
 * interface represents a <em>directory context</em>.
 * It defines methods for examining and updating attributes associated with a
 * <em>directory object</em>, or <em>directory entry</em> as it is sometimes
 * called.
 * <p>
 * You use {@code getAttributes()} to retrieve the attributes
 * associated with a directory object (for which you supply the name).
 * Attributes are modified using {@code modifyAttributes()}.
 * You can add, replace, or remove attributes and/or attribute values
 * using this operation.
 * <p>
 * {@code DirContext} also behaves as a naming context
 * by extending the {@code Context} interface in the {@code javax.naming} package.
 * This means that any directory object can also provide
 * a naming context.
 * For example, the directory object for a person might contain
 * the attributes of that person, and at the same time provide
 * a context for naming objects relative to that person
 * such as his printers and home directory.
 *
 * <h3>Searches</h3>
 * {@code DirContext} contains methods for
 * performing content-based searching of the directory.
 * In the simplest and most common form of usage, the application
 * specifies a set of attributes--possibly with specific
 * values--to match, and submits this attribute set, to the
 * {@code search()} method.
 * There are other overloaded forms of {@code search()}
 * that support more sophisticated <em>search filters</em>.
 *
 *
 * <h2>Package Specification</h2>
 *
 * The JNDI API Specification and related documents can be found in the
 * {@extLink jndi_overview JNDI documentation}.
 *
 * @since 1.3
 */
package javax.naming.directory;
