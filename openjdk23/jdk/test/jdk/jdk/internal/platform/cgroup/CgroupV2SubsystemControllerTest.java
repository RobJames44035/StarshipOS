/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jdk.internal.platform.cgroupv2.CgroupV2SubsystemController;

/*
 * @test
 * @key cgroups
 * @requires os.family == "linux"
 * @modules java.base/jdk.internal.platform.cgroupv2
 * @library /test/lib
 * @run junit/othervm CgroupV2SubsystemControllerTest
 */
public class CgroupV2SubsystemControllerTest {


    /*
     * Common case: No nested cgroup path (i.e. at the unified root)
     */
    @Test
    public void testCgPathAtRoot() {
        String mountPoint = "/sys/fs/cgroup";
        String cgroupPath = "/";
        CgroupV2SubsystemController ctrl = new CgroupV2SubsystemController(mountPoint, cgroupPath);
        assertEquals(mountPoint, ctrl.path());
    }

    /*
     * Cgroup path at a sub-path
     */
    @Test
    public void testCgPathNonEmptyRoot() {
        String mountPoint = "/sys/fs/cgroup";
        String cgroupPath = "/foobar";
        CgroupV2SubsystemController ctrl = new CgroupV2SubsystemController(mountPoint, cgroupPath);
        String expectedPath = mountPoint + cgroupPath;
        assertEquals(expectedPath, ctrl.path());
    }

}
