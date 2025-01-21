/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/**
 * Provides a class and two interfaces for the RMI registry.
 * A registry is a remote object that maps names to remote objects.  A
 * server registers its remote objects with the registry so that they can
 * be looked up.  When an object wants to invoke a method on a remote
 * object, it must first lookup the remote object using its name.  The
 * registry returns to the calling object a reference to the remote
 * object, using which a remote method can be invoked.
 *
 * @since 1.1
 */
package java.rmi.registry;
