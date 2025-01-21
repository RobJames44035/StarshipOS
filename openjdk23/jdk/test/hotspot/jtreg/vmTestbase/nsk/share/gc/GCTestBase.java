/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package nsk.share.gc;

import nsk.share.test.TestBase;
import nsk.share.runner.RunParams;
import nsk.share.runner.RunParamsAware;

public abstract class GCTestBase extends TestBase implements RunParamsAware, GCParamsAware {
        protected RunParams runParams;
        protected GCParams gcParams;

        public final void setRunParams(RunParams runParams) {
                this.runParams = runParams;
        }

        public final void setGCParams(GCParams gcParams) {
                this.gcParams = gcParams;
        }
}
