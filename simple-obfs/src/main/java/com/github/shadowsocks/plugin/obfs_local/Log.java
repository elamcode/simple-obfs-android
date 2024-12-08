package com.github.shadowsocks.plugin.obfs_local;

import java.util.Arrays;
import java.util.Locale;

public class Log {
    interface ILogLevel {
        int VERBOSE = 0;
        int DEBUG = 1;
        int INFO = 2;
        int WARN = 3;
        int ERROR = 4;
    }

    private static final int LOG_LEVEL = BuildConfig.DEBUG ? ILogLevel.VERBOSE : ILogLevel.INFO;

    public static void v(String tag, Throwable tr) {
        v(tag, "", tr);
    }

    public static void v(String tag, String format, Object... obj) {
        if (LOG_LEVEL > ILogLevel.VERBOSE) {
            return;
        }
        android.util.Log.v(tag, formatLog(format, obj));
    }

    public static void d(String tag, Throwable tr) {
        d(tag, "", tr);
    }

    public static void d(String tag, String format, Object... obj) {
        if (LOG_LEVEL > ILogLevel.DEBUG) {
            return;
        }
        android.util.Log.d(tag, formatLog(format, obj));
    }

    public static void i(String tag, Throwable tr) {
        i(tag, "", tr);
    }

    public static void i(String tag, String format, Object... obj) {
        if (LOG_LEVEL > ILogLevel.INFO) {
            return;
        }
        android.util.Log.i(tag, formatLog(format, obj));
    }

    public static void w(String tag, Throwable tr) {
        w(tag, "", tr);
    }

    public static void w(String tag, String format, Object... obj) {
        if (LOG_LEVEL > ILogLevel.WARN) {
            return;
        }
        android.util.Log.w(tag, formatLog(format, obj));
    }

    public static void e(String tag, Throwable tr) {
        e(tag, "", tr);
    }

    public static void e(String tag, String format, Object... obj) {
        if (LOG_LEVEL > ILogLevel.ERROR) {
            return;
        }
        android.util.Log.e(tag, formatLog(format, obj));
    }


    private static String formatLog(String format, Object... obj) {
        Throwable tr = getThrowableToLog(obj);
        if (tr != null) {
            obj = Arrays.copyOf(obj, obj.length - 1);
        }

        String log = obj != null && obj.length != 0 && format != null ? String.format(Locale.US, format, obj) : format;
        if (log == null) {
            log = "";
        }

        if (null != tr) {
            log = log + "\n" + android.util.Log.getStackTraceString(tr);
        }

        return log;
    }

    private static Throwable getThrowableToLog(Object[] args) {
        if (args != null && args.length != 0) {
            Object lastArg = args[args.length - 1];
            return !(lastArg instanceof Throwable) ? null : (Throwable) lastArg;
        } else {
            return null;
        }
    }
}

