package interview.span.utils.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig {

    @Value("\${application.file.delimiter}")
    private lateinit var fileDelimiter: String

    fun getFileDelimiter(): String {
        return fileDelimiter
    }
}
