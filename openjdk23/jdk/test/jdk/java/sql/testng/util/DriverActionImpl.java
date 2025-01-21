/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package util;

import java.sql.DriverAction;

/**
 * Simple implementation of DriverAction which calls back into the Driver when
 * release is called.
 */
class DriverActionImpl implements DriverAction {

    public DriverActionImpl(StubDriverDA d) {
        driver = d;
    }

    private final StubDriverDA driver;

    @Override
    public void deregister() {
        driver.release();
    }
}
