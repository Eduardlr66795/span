package interview.span.infrastructure.processor.rest.standings.v1

import interview.span.infrastructure.processor.Processor
import interview.span.utils.logging.Logger
import interview.span.utils.metrics.DataDog
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class StandingsV1Controller(
    private val processor: Processor,
    private val datadog: DataDog
) {

    @PostMapping(path = ["test"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun standingResultsRequestV1() {

        Logger.info()

        datadog.metricCounter(DataDog.API_ENDPOINT)

        processor.processStandingResultsRequest()
    }
}
