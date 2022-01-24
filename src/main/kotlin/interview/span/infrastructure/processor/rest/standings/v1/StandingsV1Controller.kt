package interview.span.infrastructure.processor.rest.standings.v1

import interview.span.infrastructure.processor.Processor
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class StandingsV1Controller(
    private val processor: Processor
) {

    @PostMapping(path = ["process-file"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun standingResultsRequestV1(
        @RequestParam fileName: String
    ): ResponseEntity<Any> {
        return processor.processStandingResultsRequest(fileName)
    }

    @PostMapping(path = ["process-hardcoded-list"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun standingResultsRequestV1(): ResponseEntity<Any> {
        return processor.processStandingResultsRequest()
    }
}
