/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package sun.nio.ch;

/**
 * Implemented by asynchronous channels that can be associated with an
 * asynchronous channel group.
 */

interface Groupable {
    AsynchronousChannelGroupImpl group();
}
