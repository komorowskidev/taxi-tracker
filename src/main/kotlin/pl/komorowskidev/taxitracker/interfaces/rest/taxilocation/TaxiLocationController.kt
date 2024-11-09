package pl.komorowskidev.taxitracker.interfaces.rest.taxilocation

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest
import pl.komorowskidev.taxitracker.domain.service.TaxiLocationService
import pl.komorowskidev.taxitracker.domain.service.TaxiLocationsResponse
import pl.komorowskidev.taxitracker.interfaces.rest.Response
import java.time.Instant

@Controller
@RequestMapping("/api")
class TaxiLocationController(
    private val taxiLocationService: TaxiLocationService,
    private val taxiLocationDtoMapper: TaxiLocationDtoMapper,
) {
    @GetMapping("/taxi-locations", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getTaxiLocations(
        @RequestParam(name = "taxi-id") taxiId: String,
        @RequestParam(name = "start-time") startTime: Instant,
        @RequestParam(name = "end-time") endTime: Instant,
    ): ResponseEntity<Response<List<TaxiLocationDto>>> {
        val taxiLocationsResponse = taxiLocationService.getTaxiLocations(TaxiLocationRequest(taxiId, startTime, endTime))
        when (taxiLocationsResponse) {
            is TaxiLocationsResponse.Success -> {
                return ResponseEntity.ok(
                    Response.Success(taxiLocationsResponse.taxiLocations.map { taxiLocationDtoMapper.toDto(it) }),
                )
            }
            is TaxiLocationsResponse.RequestNotValid -> {
                val error = Response.Error<List<TaxiLocationDto>>(taxiLocationsResponse.message)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
            }
        }
    }
}
