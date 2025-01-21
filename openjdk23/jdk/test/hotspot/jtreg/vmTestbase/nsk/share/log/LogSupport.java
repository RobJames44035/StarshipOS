/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.log;

import java.io.PrintStream;

/**
 * Basic Log that outputs to PrintStream.
 *
 * This log also tries to catch OutOfMemoryErrors and retry.
 *
 * @see nsk.share.log.Log
 */
public class LogSupport implements Log {
        private final int attempts = 2;
        private PrintStream out;
        private boolean infoEnabled = true;
        private boolean debugEnabled = true;
        private boolean warnEnabled = true;
        private boolean errorEnabled = true;

        public LogSupport() {
                this(System.out);
        }

        public LogSupport(PrintStream out) {
                this.out = out;
                // pre-load all classes used by Thread.sleep()
                // to don't hit OOME in OOME handler
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ie) {
                }
        }

        protected void logObject(Object o) {
                if (o instanceof Throwable) {
                        logThrowable((Throwable) o);
                        return;
                }
                for (int i = 0; i < attempts; ++i) {
                        try {
                                out.println(o.toString());
                                out.flush();
                                break;
                        } catch (OutOfMemoryError e) {
                                System.gc();
                                try {
                                        Thread.sleep(500);
                                } catch (InterruptedException ie) {
                                }
                        }
                }
                out.flush();
        }

        protected void logThrowable(Throwable o) {
                for (int i = 0; i < attempts; ++i) {
                        try {
                                o.printStackTrace(out);
                                out.flush();
                                break;
                        } catch (OutOfMemoryError e) {
                                System.gc();
                                try {
                                        Thread.sleep(500);
                                } catch (InterruptedException ie) {
                                }
                        }
                }
                out.flush();
        }

        public void info(Object o) {
                if (infoEnabled)
                        logObject(o);
        }

        public void debug(Object o) {
                if (debugEnabled)
                        logObject(o);
        }

        public void warn(Object o) {
                if (warnEnabled)
                        logObject(o);
        }

        public void error(Object o) {
                if (errorEnabled)
                        logObject(o);
        }

        public boolean isInfoEnabled() {
                return infoEnabled;
        }

        public void setInfoEnabled(boolean infoEnabled) {
                this.infoEnabled = infoEnabled;
        }

        public boolean isDebugEnabled() {
                return debugEnabled;
        }

        public void setDebugEnabled(boolean debugEnabled) {
                this.debugEnabled = debugEnabled;
        }

        public boolean isWarnEnabled() {
                return warnEnabled;
        }

        public void setWarnEnabled(boolean warnEnabled) {
                this.warnEnabled = warnEnabled;
        }

        public boolean isErrorEnabled() {
                return errorEnabled;
        }

        public void setErrorEnabled(boolean errorEnabled) {
                this.errorEnabled = errorEnabled;
        }

        public void setOut(PrintStream out) {
                this.out = out;
        }
}
