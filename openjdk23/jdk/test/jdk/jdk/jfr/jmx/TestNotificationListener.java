/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package jdk.jfr.jmx;

import java.lang.management.ManagementFactory;
import java.util.concurrent.CountDownLatch;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import jdk.management.jfr.FlightRecorderMXBean;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jmx.TestNotificationListener
 */
public class TestNotificationListener {

    private final static CountDownLatch latch = new CountDownLatch(1);

    private final static NotificationListener listener = new NotificationListener() {
        public void handleNotification(Notification notification, Object handback) {
            System.out.println("Got notification: " + notification);
            latch.countDown();
        }
    };

    public static void main(String[] args) throws Throwable {
        ObjectName objectName = new ObjectName(FlightRecorderMXBean.MXBEAN_NAME);
        ManagementFactory.getPlatformMBeanServer().addNotificationListener(objectName, listener, null, null);
        FlightRecorderMXBean bean = ManagementFactory.getPlatformMXBean(FlightRecorderMXBean.class);

        long recId = bean.newRecording();
        bean.startRecording(recId);

        latch.await();
        System.out.println("Completed successfully");
    }
}
