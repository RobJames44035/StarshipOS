/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package jdk.jfr.events;

import jdk.jfr.Category;
import jdk.jfr.DataAmount;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.internal.Type;


@Name(Type.EVENT_NAME_PREFIX + "ContainerIOUsage")
@Label("Container IO Usage")
@Category({"Operating System", "File System"})
@Description("Container IO usage related information")
public class ContainerIOUsageEvent extends AbstractPeriodicEvent {

  @Label("Block IO Request Count")
  @Description("Number of block IO requests to the disk that have been issued by the container")
  public long serviceRequests;

  @Label("Block IO Transfer")
  @Description("Number of block IO bytes that have been transferred to/from the disk by the container")
  @DataAmount
  public long dataTransferred;
}
