/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package jdk.jfr.events;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Timespan;
import jdk.jfr.internal.Type;

@Name(Type.EVENT_NAME_PREFIX + "ContainerCPUThrottling")
@Label("CPU Throttling")
@Category({"Operating System", "Processor"})
@Description("Container CPU throttling related information")
public class ContainerCPUThrottlingEvent extends AbstractPeriodicEvent {
  @Label("CPU Elapsed Slices")
  @Description("Number of time-slice periods that have elapsed if a CPU quota has been setup for the container")
  public long cpuElapsedSlices;

  @Label("CPU Throttled Slices")
  @Description("Number of time-slice periods that the CPU has been throttled or limited due to exceeding CPU quota")
  public long cpuThrottledSlices;

  @Label("CPU Throttled Time")
  @Description("Total time duration, in nanoseconds, that the CPU has been throttled or limited due to exceeding CPU quota")
  @Timespan
  public long cpuThrottledTime;
}
