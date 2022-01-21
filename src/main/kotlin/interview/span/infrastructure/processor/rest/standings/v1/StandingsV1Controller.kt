package interview.span.infrastructure.processor.rest.standings.v1

import interview.span.infrastructure.processor.Processor
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class StandingsV1Controller(
    private val processor: Processor
) {

    @PostMapping(path = ["test"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun standingResultsRequestV1() {
        processor.processStandingResultsRequest()
    }
}
