package interview.span.utils.logging.objects

import interview.span.utils.logging.objects.Constants.LOGGER_EVENT_TYPE

class LogEntry {

    // Indicates the log type that we are working with: Event / Message
    private var logType: String

    // A searchable high level overview of the event that occurred.
    private var tag: String

    // A short description of the event that will provide additional information when debugging
    private var description: String

    // Searchable term that will be used to uniquely identify the resource associated with the given event
    private var attributes: Map<String, Any>? = null


    constructor(tag: String, description: String, mdc: Map<String, Any>? = null, attributes: Map<String, Any>? = null) {
        this.logType = LOGGER_EVENT_TYPE
        this.description = description
        this.tag = tag

        setAttributes(attributes)
    }

    constructor(logTagObject: LogTagObject, attributes: Map<String, Any>? = null) {
        this.logType = LOGGER_EVENT_TYPE
        this.description = logTagObject.description
        this.tag = logTagObject.logTag

        setAttributes(attributes)
    }


    /**
     * This method is used to append additional attributes io the logEntry
     */
    fun appendAttributes(attributes: Map<String, Any>) {
        if (this.attributes.isNullOrEmpty()) {
            this.attributes = attributes
        } else {
            this.attributes = this.attributes!!.plus(attributes)
        }
    }

    private fun setAttributes(attributes: Map<String, Any>? = null) {
        if (!attributes.isNullOrEmpty()) {
            this.attributes = attributes
        }
    }

    /**
     * This method is used to check if a given logEntry has attributes associated with it.
     * True - If it has attributes associated with it.
     * False - If no attributes are associated with it.
     */
    fun hasAttributes(): Boolean {
        return !attributes.isNullOrEmpty()
    }

    fun getLogType(): String {
        return this.logType
    }

    fun getLogTag(): String {
        return this.tag
    }

    fun getDescription(): String {
        return this.description
    }

    fun getAttributes(): Map<String, Any>? {
        return this.attributes
    }
}