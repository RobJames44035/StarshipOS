/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/**
 * Provides the API that defines the contract between the transaction manager
 * and the resource manager, which allows the transaction manager to enlist
 * and delist resource objects (supplied by the resource manager driver) in
 * JTA transactions. The driver vendor for a specific resource manager provides
 * the implementation of this API.
 *
 * @since 1.4
 */

package javax.transaction.xa;
