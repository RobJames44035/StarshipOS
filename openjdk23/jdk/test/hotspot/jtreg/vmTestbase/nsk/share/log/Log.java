/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.log;

/**
 * Log interface.
 */
public interface Log {
        /**
         * Log at INFO level.
         *
         * @param o object to log
         */
        public void info(Object o);

        /**
         * Log at DEBUG level.
         *
         * @param o object to log
         */
        public void debug(Object o);

        /**
         * Log at WARN level.
         *
         * @param o object to log
         */
        public void warn(Object o);

        /**
         * Log at ERROR level
         *
         * @param o object to log
         */
        public void error(Object o);

        /**
         * Determine if logging at DEBUG level.
         *
         * @return true if debug logging is enabled, false otherwise
         */
        public boolean isDebugEnabled();

        /**
         * Determine if logging at INFO level.
         *
         * @return true if info logging is enabled, false otherwise
         */
        public boolean isInfoEnabled();

        /**
         * Determine if logging at WARN level.
         *
         * @return true if warn logging is enabled, false otherwise
         */
        public boolean isWarnEnabled();

        /**
         * Determine if logging at ERROR level.
         *
         * @return true if error logging is enabled, false otherwise
         */
        public boolean isErrorEnabled();

        /**
         * Enable/disable info output.
         *
         * @param infoEnabled
         */
        public void setInfoEnabled(boolean infoEnabled);

        /**
         * Enable/disable debug output.
         *
         * @param debugEnabled
         */
        public void setDebugEnabled(boolean debugEnabled);

        /**
         * Enable/disable warn output.
         * @param warnEnabled
         */
        public void setWarnEnabled(boolean warnEnabled);

        /**
         * Enable/disable error output.
         * @param errorEnabled
         */
        public void setErrorEnabled(boolean errorEnabled);
}
