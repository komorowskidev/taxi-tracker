package pl.komorowskidev.taxitracker.interfaces.rest.taxilocation

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest
import pl.komorowskidev.taxitracker.domain.service.TaxiLocationService
import java.time.Instant

@Controller
@RequestMapping("/api")
class TaxiLocationController(
    private val taxiLocationService: TaxiLocationService,
    private val taxiLocationDtoMapper: TaxiLocationDtoMapper,
) {
    @GetMapping("/taxi-locations")
    fun getTaxiLocations(
        @RequestParam(name = "taxi-id") taxiId: String,
        @RequestParam(name = "start-time") startTime: Instant,
        @RequestParam(name = "end-time") endTime: Instant,
    ): ResponseEntity<List<TaxiLocationDto>> {
        val taxiLocations = taxiLocationService.getTaxiLocations(TaxiLocationRequest(taxiId, startTime, endTime))
        return ResponseEntity.ok(taxiLocations.map { taxiLocationDtoMapper.toDto(it) })
    }
}
