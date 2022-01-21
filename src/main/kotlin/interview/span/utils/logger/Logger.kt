package interview.span.utils.logger

import org.slf4j.LoggerFactory

object Logger {

    private val logFactory = LoggerFactory.getLogger(Logger::class.java)

    fun info() {
        logFactory.info("THIS IS A LOG LINE")
        logFactory.info("THIS IS A LOG LINE")
        logFactory.info("THIS IS A LOG LINE")
        logFactory.info("THIS IS A LOG LINE")
        logFactory.info("THIS IS A LOG LINE")
        logFactory.info("THIS IS A LOG LINE")
        logFactory.info("THIS IS A LOG LINE")
        logFactory.info("THIS IS A LOG LINE")
    }
}
