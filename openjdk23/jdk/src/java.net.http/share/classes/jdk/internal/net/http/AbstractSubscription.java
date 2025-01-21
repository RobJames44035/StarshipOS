/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package jdk.internal.net.http;

import java.util.concurrent.Flow;
import jdk.internal.net.http.common.Demand;

/**
 * A {@link Flow.Subscription} wrapping a {@link Demand} instance.
 */
abstract class AbstractSubscription implements Flow.Subscription {

    private final Demand demand = new Demand();

    /**
     * Returns the subscription's demand.
     * @return the subscription's demand.
     */
    protected Demand demand() { return demand; }
}
