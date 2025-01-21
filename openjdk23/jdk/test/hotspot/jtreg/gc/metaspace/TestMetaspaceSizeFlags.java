/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.metaspace;

import jdk.test.lib.Asserts;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

/*
 * @test TestMetaspaceSizeFlags
 * @bug 8024650
 * @summary Test that metaspace size flags can be set correctly
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver gc.metaspace.TestMetaspaceSizeFlags
 */
public class TestMetaspaceSizeFlags {
  public static final long K = 1024L;
  public static final long M = 1024L * K;

  // HotSpot uses a number of different values to align memory size flags.
  // This is currently the largest alignment (unless huge large pages are used).
  public static final long MAX_ALIGNMENT = 32 * M;

  public static void main(String [] args) throws Exception {
    testMaxMetaspaceSizeEQMetaspaceSize(MAX_ALIGNMENT, MAX_ALIGNMENT);
    // 8024650: MaxMetaspaceSize was adjusted instead of MetaspaceSize.
    testMaxMetaspaceSizeLTMetaspaceSize(MAX_ALIGNMENT, MAX_ALIGNMENT * 2);
    testMaxMetaspaceSizeGTMetaspaceSize(MAX_ALIGNMENT * 2, MAX_ALIGNMENT);
  }

  private static void testMaxMetaspaceSizeEQMetaspaceSize(long maxMetaspaceSize, long metaspaceSize) throws Exception {
    MetaspaceFlags mf = runAndGetValue(maxMetaspaceSize, metaspaceSize);
    Asserts.assertEQ(maxMetaspaceSize, metaspaceSize);
    Asserts.assertEQ(mf.maxMetaspaceSize, maxMetaspaceSize);
    Asserts.assertEQ(mf.metaspaceSize, metaspaceSize);
  }

  private static void testMaxMetaspaceSizeLTMetaspaceSize(long maxMetaspaceSize, long metaspaceSize) throws Exception {
    MetaspaceFlags mf = runAndGetValue(maxMetaspaceSize, metaspaceSize);
    Asserts.assertEQ(mf.maxMetaspaceSize, maxMetaspaceSize);
    Asserts.assertEQ(mf.metaspaceSize, maxMetaspaceSize);
  }

  private static void testMaxMetaspaceSizeGTMetaspaceSize(long maxMetaspaceSize, long metaspaceSize) throws Exception {
    MetaspaceFlags mf = runAndGetValue(maxMetaspaceSize, metaspaceSize);
    Asserts.assertGT(maxMetaspaceSize, metaspaceSize);
    Asserts.assertGT(mf.maxMetaspaceSize, mf.metaspaceSize);
    Asserts.assertEQ(mf.maxMetaspaceSize, maxMetaspaceSize);
    Asserts.assertEQ(mf.metaspaceSize, metaspaceSize);
  }

  private static MetaspaceFlags runAndGetValue(long maxMetaspaceSize, long metaspaceSize) throws Exception {
    OutputAnalyzer output = run(maxMetaspaceSize, metaspaceSize);
    output.shouldNotMatch("Error occurred during initialization of VM\n.*");

    String stringMaxMetaspaceSize = output.firstMatch(".* MaxMetaspaceSize .* = (\\d+).*", 1);
    String stringMetaspaceSize = output.firstMatch(".* MetaspaceSize .* = (\\d+).*", 1);

    return new MetaspaceFlags(Long.parseLong(stringMaxMetaspaceSize),
                              Long.parseLong(stringMetaspaceSize));
  }

  private static OutputAnalyzer run(long maxMetaspaceSize, long metaspaceSize) throws Exception {
    return ProcessTools.executeLimitedTestJava(
        "-XX:MaxMetaspaceSize=" + maxMetaspaceSize,
        "-XX:MetaspaceSize=" + metaspaceSize,
        "-XX:-UseLargePages", // Prevent us from using 2GB large pages on solaris + sparc.
        "-XX:+PrintFlagsFinal",
        "-version");
  }

  private static class MetaspaceFlags {
    public long maxMetaspaceSize;
    public long metaspaceSize;
    public MetaspaceFlags(long maxMetaspaceSize, long metaspaceSize) {
      this.maxMetaspaceSize = maxMetaspaceSize;
      this.metaspaceSize = metaspaceSize;
    }
  }
}
