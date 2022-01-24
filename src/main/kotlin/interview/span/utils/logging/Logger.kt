package interview.span.utils.logging

import interview.span.utils.logging.objects.Constants
import interview.span.utils.logging.objects.LogEntry
import interview.span.utils.logging.objects.LogLevel
import org.slf4j.LoggerFactory
import java.lang.StringBuilder

object Logger {

    private val logFactory = LoggerFactory.getLogger(Logger::class.java)

    fun info(logEntry: LogEntry) {
        processLogEntry(LogLevel.INFO, logEntry)
    }

    fun displayResult(entry: String) {
        logFactory.info(entry)
    }

    private fun processLogEntry(logLevel: LogLevel, logEntry: LogEntry) {
        when (logLevel) {
            LogLevel.INFO -> {
                logFactory.info(getLogString(logEntry))
            }

            LogLevel.WARN -> {
                logFactory.warn(getLogString(logEntry))
            }

            LogLevel.ERROR -> {
                logFactory.error(getLogString(logEntry))
            }

            LogLevel.DEBUG -> {
                logFactory.debug(getLogString(logEntry))
            }
        }
    }

    private fun getLogString(logEntry: LogEntry): String {
        val builder = StringBuilder()
        val logType = logTypeFormatted(logEntry)
        val logTag = logTagFormatted(logEntry)
        val description = descriptionFormatted(logEntry)

        // Append the basic properties that are common across logEvents & logMessages
        builder.append("$logType, $logTag, $description")

        if (logEntry.hasAttributes()) {
            builder.append(", ${attributesFormatted(logEntry)}")
        }

        return builder.toString()
    }

    private fun logTypeFormatted(logEntry: LogEntry): String {
        return "${Constants.LOGGER_LOG_TYPE}: ${logEntry.getLogType()}"
    }

    private fun logTagFormatted(logEntry: LogEntry): String {
        return "${Constants.LOGGER_LOG_TAG}: ${logEntry.getLogTag()}"
    }

    private fun descriptionFormatted(logEntry: LogEntry): String {
        return "${Constants.LOGGER_DESCRIPTION}: ${logEntry.getDescription()}"
    }

    private fun attributesFormatted(logEntry: LogEntry): String {
        return "${Constants.LOGGER_ATTRIBUTES}: ${logEntry.getAttributes()}"
    }
}
