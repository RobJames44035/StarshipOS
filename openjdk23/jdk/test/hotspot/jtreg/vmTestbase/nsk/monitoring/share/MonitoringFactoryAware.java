/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package nsk.monitoring.share;

/**
 * Marker interface that signifies that MonitoringFactory is needed.
 *
 * @see nsk.monitoring.share.MonitoringFactory
 */
public interface MonitoringFactoryAware {
        public void setMonitoringFactory(MonitoringFactory mfactory);
}
