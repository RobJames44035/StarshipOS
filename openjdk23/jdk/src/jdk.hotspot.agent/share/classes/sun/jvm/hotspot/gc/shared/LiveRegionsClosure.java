/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package sun.jvm.hotspot.gc.shared;

public interface LiveRegionsClosure {
  public void doLiveRegions(LiveRegionsProvider lrp);
}
