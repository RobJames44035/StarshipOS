/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/**
 * Provides the classes and interfaces for cryptographic
 * operations. The cryptographic operations defined in this package
 * include encryption, key generation and key agreement, and Message
 * Authentication Code (MAC) generation.
 *
 * <p>Support for encryption includes symmetric, asymmetric, block,
 * and stream ciphers. This package also supports secure streams and
 * sealed objects.
 *
 * <p>Many of the classes provided in this package are provider-based.
 * The class itself defines a programming interface to which
 * applications may write.  The implementations themselves may then be
 * written by independent third-party vendors and plugged in
 * seamlessly as needed.  Therefore, application developers may take
 * advantage of any number of provider-based implementations without
 * having to add or rewrite code.
 *
 * <ul>
 *   <li><a href="{@docRoot}/../specs/security/standard-names.html">
 *     <b>Java Security Standard Algorithm Names Specification
 *     </b></a></li>
 * </ul>
 *
 * <h2>Related Documentation</h2>
 *
 * For further documentation, please see:
 * <ul>
 *   <li>
 *     {@extLink security_guide_jca
 *       Java Cryptography Architecture (JCA) Reference Guide}</li>
 *   <li>
 *     {@extLink security_guide_impl_provider
 *       How to Implement a Provider in the Java Cryptography Architecture}</li>
 * </ul>
 *
 * @spec security/standard-names.html Java Security Standard Algorithm Names
 * @since 1.4
 */
package javax.crypto;
