/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package vm.mlvm.share;

import java.util.Random;
import jdk.test.lib.Utils;

import nsk.share.ArgumentParser;
import nsk.share.Log;
import nsk.share.Log.TraceLevel;
import nsk.share.test.LazyFormatString;

public class Env {

    private static class StaticHolder {
        public static ArgumentParser argParser;
        public static Log log;
        static {
            init(new String[0]);
        }

        public static void init(String... args) {
            init(new IgnoreUnknownArgumentParser(args));
        }

        public static void init(ArgumentParser ap) {
            StaticHolder.argParser = ap;
            StaticHolder.log = new Log(System.out, StaticHolder.argParser);
        }
    }

    public static ArgumentParser getArgParser() {
        return StaticHolder.argParser;
    }

    public static Log getLog() {
        return StaticHolder.log;
    }

    public static void init(String... args) {
        StaticHolder.init(args);
    }

    public static void init(ArgumentParser ap) {
        StaticHolder.init(ap);
    }

    //
    // RNG
    //

    private static long _seed = Utils.SEED;

    private static volatile boolean _wasSeedPrinted = false;

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<Random> _threadRNG =
        new ThreadLocal<Random>() {
            @Override protected Random initialValue() {
                if ( ! _wasSeedPrinted ) {
                    _wasSeedPrinted = true;
                    traceImportant("RNG seed = " + _seed + " (0x" + Long.toHexString(_seed) + ")");
                    // ensure we also print out how to change seed
                    Utils.getRandomInstance();
                }

                long seed = _seed;
                String name = Thread.currentThread().getName();
                for ( int n = 0; n < name.length(); n++ )
                    seed ^= name.charAt(n) << ((n % 7) * 8);

                traceVerbose(Thread.currentThread() + " RNG seed = " + seed + " (0x" + Long.toHexString(seed) + ")");

                return new Random(seed);
            }
        };

    public static Random getRNG() {
        return _threadRNG.get();
    }

    //
    // Syntactic sugar
    //

    public static void traceImportant(String msg) {
        getLog().trace(TraceLevel.TRACE_IMPORTANT, msg);
    }

    public static void traceImportant(String msg, Object... args) {
        getLog().trace(TraceLevel.TRACE_IMPORTANT, new LazyFormatString(msg, args));
    }

    public static void traceImportant(Throwable t, String msg, Object... args) {
        getLog().trace(TraceLevel.TRACE_IMPORTANT, new LazyFormatString(msg, args), t);
    }

    public static void traceNormal(String msg) {
        getLog().trace(TraceLevel.TRACE_NORMAL, msg);
    }

    public static void traceNormal(String msg, Object... args) {
        getLog().trace(TraceLevel.TRACE_NORMAL, new LazyFormatString(msg, args));
    }

    public static void traceNormal(Throwable t, String msg, Object... args) {
        getLog().trace(TraceLevel.TRACE_NORMAL, new LazyFormatString(msg, args), t);
    }

    public static void traceVerbose(String msg) {
        getLog().trace(TraceLevel.TRACE_VERBOSE, msg);
    }

    public static void traceVerbose(String msg, Object... args) {
        getLog().trace(TraceLevel.TRACE_VERBOSE, new LazyFormatString(msg, args));
    }

    public static void traceVerbose(Throwable t, String msg, Object... args) {
        getLog().trace(TraceLevel.TRACE_VERBOSE, new LazyFormatString(msg, args), t);
    }

    public static void traceDebug(String msg) {
        getLog().trace(TraceLevel.TRACE_DEBUG, msg);
    }

    public static void traceDebug(String msg, Object... args) {
        getLog().trace(TraceLevel.TRACE_DEBUG, new LazyFormatString(msg, args));
    }

    public static void traceDebug(Throwable t, String msg, Object... args) {
        getLog().trace(TraceLevel.TRACE_DEBUG, new LazyFormatString(msg, args), t);
    }

    public static void display(String msg) {
        getLog().trace(TraceLevel.TRACE_IMPORTANT, msg);
    }

    public static void display(String msg, Object... args) {
        getLog().trace(TraceLevel.TRACE_IMPORTANT, new LazyFormatString(msg, args));
    }

    public static void complain(String msg) {
        getLog().complain(msg);
    }

    public static void complain(String msg, Object... args) {
        getLog().complain(new LazyFormatString(msg, args));
    }

    public static void complain(Throwable t, String msg, Object... args) {
        getLog().complain(new LazyFormatString(msg, args), t);
    }

    /**
     * Throws an arbitrary exception as unchecked one.
     * The method does not return normally.
     *
     * If the exception is not a subclass of java.lang.RuntimeException`
     * or java.lang.Error, it is wrapped into java.lang.RuntimeException
     *
     * @param exception Exception to throw (wrapping it when it is checked on)
     */
    public static void throwAsUncheckedException(Throwable exception) {
        if (exception instanceof RuntimeException) {
            throw (RuntimeException) exception;
        }

        if (exception instanceof Error) {
            throw (Error) exception;
        }

        throw new RuntimeException(exception.getMessage(), exception);
    }
}
