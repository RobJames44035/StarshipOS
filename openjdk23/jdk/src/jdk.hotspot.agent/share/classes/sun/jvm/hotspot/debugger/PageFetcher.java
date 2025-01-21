/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.jvm.hotspot.debugger;

/** This interface specifies how a page is fetched by the PageCache. */

public interface PageFetcher {
  public Page fetchPage(long pageBaseAddress, long numBytes);
}
