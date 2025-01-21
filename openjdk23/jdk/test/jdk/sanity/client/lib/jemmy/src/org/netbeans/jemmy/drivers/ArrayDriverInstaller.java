/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers;

/**
 * Auxiliary class making driver registration easier.
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public class ArrayDriverInstaller implements DriverInstaller {

    String[] ids;
    Object[] drivers;

    /**
     * Constructs an ArrayDriverInstaller object. Both parameter arrays mush
     * have same length, {@code drivers} must keep instances of
     * <a href = "Driver.html">Driver</a> or
     * <a href = "Driver.html">LightDriver</a> implementations.
     *
     * @param ids an array of driver IDs
     * @param drivers an array of drivers.
     */
    public ArrayDriverInstaller(String[] ids, Object[] drivers) {
        this.ids = ids;
        this.drivers = drivers;
    }

    /**
     * Installs drivers from the array passed into constructor.
     */
    @Override
    public void install() {
        for (int i = 0; i < ids.length; i++) {
            DriverManager.setDriver(ids[i], drivers[i]);
        }
    }
}
