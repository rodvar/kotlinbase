package com.rodvar.kotlinbase.base.utils.android;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import com.rodvar.kotlinbase.base.presentation.BaseActivity;

/**
 * Encapsulates logging so that logging can be turned on or off.
 *
 * # As Copied from previous project
 */
public class Logger {
    private static final LogLevel DEFAULT_LOG_LEVEL = LogLevel.WARNING;

    private static final LogLevel NO_LOG_LEVEL = null;
    private static final String TAG = Logger.class.getSimpleName();
    private static Map<String, LogLevel> forcedLoggingLevels;
    private static LogLevel logLevel;

    static {
        forcedLoggingLevels = new HashMap<>();

        forceLogLevel(TAG, LogLevel.WARNING);
    }

    private static void forceLogLevel(final String tag, final LogLevel level) {
        warning(TAG, "Forcing tag '" + tag + "' to be at log level: " + level);

        forcedLoggingLevels.put(tag, level);
    }

    public static void setLogLevel(final LogLevel level) {
        logLevel = level;
        debug(TAG, "General Log Level set to " + level);
    }

    public static void error(final String tag, final String message) {
        if (isErrorLevelApplicable(tag)) {
            Log.e(tag, message);
        }
    }

    public static void error(final String tag, final String message, final Throwable throwable) {
        if (isErrorLevelApplicable(tag)) {
            Log.e(tag, message, throwable);
        }
    }

    public static void warning(final String tag, final String message) {
        if (isWarningLevelApplicable(tag)) {
            Log.w(tag, message);
        }
    }

    public static void warning(final String tag, final String message, final Throwable throwable) {
        if (isWarningLevelApplicable(tag)) {
            Log.w(tag, message, throwable);
        }
    }

    public static void info(final String tag, final String message) {
        if (isInfoLevelApplicable(tag)) {
            Log.i(tag, message);
        }
    }

    public static void debug(final String tag, final String message) {
        if (isDebugLevelApplicable(tag)) {
            Log.d(tag, message);
        }
    }

    public static void verbose(final String tag, final String message) {
        if (isVerboseLevelApplicable(tag)) {
            Log.v(tag, message);
        }
    }

    private static boolean isErrorEnabled() {
        return isLogLevelEnabled(LogLevel.ERROR);
    }

    private static boolean isWarningEnabled() {
        return isLogLevelEnabled(LogLevel.WARNING);
    }

    private static boolean isInfoEnabled() {
        return isLogLevelEnabled(LogLevel.INFO);
    }

    public static boolean isDebugEnabled() {
        return isLogLevelEnabled(LogLevel.DEBUG);
    }

    public static boolean isDebugEnabled(final String tag) {
        return isDebugLevelApplicable(tag);
    }

    private static boolean isVerboseEnabled() {
        return isLogLevelEnabled(LogLevel.VERBOSE);
    }

    private static boolean isLogLevelEnabled(final LogLevel desiredLevel) {
        if (logLevelNotSet()) {
            setLogLevel(DEFAULT_LOG_LEVEL);
        }

        return isLogLevelEnabled(desiredLevel, logLevel);
    }

    private static boolean isLogLevelEnabled(final LogLevel desiredLevel, final LogLevel actualLevel) {
        return desiredLevel.ordinal() <= actualLevel.ordinal();
    }

    private static boolean logLevelNotSet() {
        return logLevel == NO_LOG_LEVEL;
    }

    private static boolean isErrorLevelApplicable(final String tag) {
        return isErrorEnabled() || logLevelForced(tag, LogLevel.ERROR);
    }

    private static boolean isWarningLevelApplicable(final String tag) {
        return isWarningEnabled() || logLevelForced(tag, LogLevel.WARNING);
    }

    private static boolean isInfoLevelApplicable(final String tag) {
        return isInfoEnabled() || logLevelForced(tag, LogLevel.INFO);
    }

    private static boolean isDebugLevelApplicable(final String tag) {
        return isDebugEnabled() || logLevelForced(tag, LogLevel.DEBUG);
    }

    private static boolean isVerboseLevelApplicable(final String tag) {
        return isVerboseEnabled() || logLevelForced(tag, LogLevel.VERBOSE);
    }

    private static boolean logLevelForced(final String tag, final LogLevel level) {
        return forcedLoggingLevels.containsKey(tag) && isLogLevelEnabled(level, forcedLoggingLevels.get(tag));
    }

    /**
     * Use this method to warn developers of errors not important enough as to be logged as a complete stacktrace
     *
     * @param javaClass the class of the object having the exception
     * @param action    action that failed
     * @param e         exception occurred
     */
    public static void warnError(@NotNull Class<BaseActivity> javaClass, @NotNull String action, @NotNull Exception e) {
        error(javaClass.getSimpleName(), String.format("Failed to %s: %s", action, e.getLocalizedMessage()));
    }

    public enum LogLevel {
        ERROR, WARNING, INFO, DEBUG, VERBOSE
    }
}
