/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.starship.eventapi

interface EventListener {
    void onEvent(EventMessage message, Closure ackHandler)
}
