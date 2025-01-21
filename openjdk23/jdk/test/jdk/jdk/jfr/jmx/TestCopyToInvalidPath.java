/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.jmx;

import java.io.IOException;
import java.nio.file.Paths;

import jdk.management.jfr.FlightRecorderMXBean;
import jdk.test.lib.jfr.CommonHelper;
import jdk.test.lib.jfr.SimpleEventHelper;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jmx.TestCopyToInvalidPath
 */
public class TestCopyToInvalidPath {
    public static void main(String[] args) throws Throwable {
        FlightRecorderMXBean bean = JmxHelper.getFlighteRecorderMXBean();

        long recId = bean.newRecording();
        bean.startRecording(recId);
        SimpleEventHelper.createEvent(1);
        bean.stopRecording(recId);

        CommonHelper.verifyException(()->{bean.copyTo(recId, null);}, "copyTo(null)", NullPointerException.class);
        CommonHelper.verifyException(()->{bean.copyTo(recId, "");}, "copyTo('')", IOException.class);

        String p = Paths.get(".", "thisdir", "doesnot", "exists").toString();
        CommonHelper.verifyException(()->{bean.copyTo(recId, p);}, "copyTo(dirNotExist)", IOException.class);

        bean.closeRecording(recId);
    }
}
