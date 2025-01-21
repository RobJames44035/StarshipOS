/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.test.failurehandler;

import java.nio.file.Path;

public interface CoreInfoGatherer {
    void gatherCoreInfo(HtmlSection section, Path core);
}
