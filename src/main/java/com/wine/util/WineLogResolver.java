package com.wine.util;

import static java.util.Objects.requireNonNull;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * Created by erickang on 10/22/16.
 */
public final class WineLogResolver {
    private WineLogResolver() {}

    public static synchronized void addConsoleAppender(String appenderName) {
        requireNonNull(appenderName, "appenderName is null");

        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        Layout layout = PatternLayout.createLayout(PatternLayout.SIMPLE_CONVERSION_PATTERN,
                null, config, null, null, false, false, null, null);
        ConsoleAppender appender = ConsoleAppender.createDefaultAppenderForLayout(layout).newBuilder().
                withName(appenderName).setTarget(ConsoleAppender.Target.SYSTEM_OUT).build();
        appender.start();
        config.addAppender(appender);
    }

    public static synchronized void addLogger(String loggerName, String... appenderNames) {
        requireNonNull(loggerName, "loggerName is null");
        requireNonNull(appenderNames, "appenderNames is null");

        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();

        LoggerConfig loggerConfig = LoggerConfig.createLogger(true, Level.OFF, loggerName, null,
                getAppenderRefs(appenderNames), null, config, null);

        for (String appenderName : appenderNames) {
            Appender appender = requireNonNull(config.getAppenders().get(appenderName),
                    "cannot find appender " + appenderName);
            loggerConfig.addAppender(appender, Level.TRACE, null);
        }

        config.addLogger(loggerName, loggerConfig);
        ctx.updateLoggers();
    }

    public static synchronized void setLogLevel(String loggerName, String logLevel) {
        requireNonNull(loggerName, "loggerName is null");
        requireNonNull(logLevel, "logLevel is null");

        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();

        LoggerConfig loggerConfig;
        if ((loggerConfig = config.getLoggers().get(loggerName)) == null) {
            throw new IllegalArgumentException("cannot find logger: " + loggerName);
        }

        Level level = requireNonNull(Level.getLevel(logLevel), "invalid log level");
        loggerConfig.setLevel(level);
        ctx.updateLoggers();
    }

    private static AppenderRef[] getAppenderRefs(String... appenders) {
        AppenderRef[] refs = new AppenderRef[appenders.length];

        for (int i = 0; i < appenders.length; i++) {
            refs[i] = AppenderRef.createAppenderRef(appenders[i], Level.TRACE, null);
        }
        return refs;
    }

}
