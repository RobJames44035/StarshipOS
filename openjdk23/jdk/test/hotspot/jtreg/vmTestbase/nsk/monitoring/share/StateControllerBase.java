/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package nsk.monitoring.share;

import nsk.share.log.*;

/**
 * An abstract base class for operating VM state. The follow methods must
 * be implemented by its subclasses:
 * <ul>
 *      <li><code>run()</code> -- brings VM into defined state.
 *      <li><code>reset()</code> -- tries to reclaim VM into initial state.
 * </ul>
 */
public abstract class StateControllerBase implements LogAware {
        protected Log log;

        public StateControllerBase(Log log) {
                this.log = log;
        }

        /**
         * Brings VM into defined state.
         */
        public abstract void run();

        /**
         * Tries to reclaim VM into initial state
         */
        public abstract void reset();

        /**
         * Defines {@link Log <code>Log.Logger</code>} object.
         */
        public final void setLog(Log log) {
                this.log = log;
        }

        /**
         * Converts an integer to string.
         *
         * @param i an integer to convert.
         * @return a string that represents the int value.
         */
        protected String int2Str(int i) {
                String tmp = "";

                if (i < 10) {
                        tmp = "00";
                } else if (i >= 10 && i < 100) {
                        tmp = "0";
                }
                return tmp + String.valueOf(i);
        }
}
