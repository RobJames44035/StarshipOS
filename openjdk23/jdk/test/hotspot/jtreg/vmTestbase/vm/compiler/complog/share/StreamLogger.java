/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
package vm.compiler.complog.share;

import nsk.share.log.Log;

/*
 * StreamListener that logs everything to Log at debug priority.
 */
public class StreamLogger implements StreamListener {
        private String prefix;
        private Log log;

        public StreamLogger(String prefix, Log log) {
                this.prefix = prefix;
                this.log = log;
        }

        public void onStart() {
                // do nothing
        }

        public void onFinish() {
                // do nothing
        }

        public void onRead(String s) {
                log.debug(prefix + s);
        }

        public void onException(Throwable e) {
                log.debug(prefix + "Exception");
                log.debug(e);
        }
}
