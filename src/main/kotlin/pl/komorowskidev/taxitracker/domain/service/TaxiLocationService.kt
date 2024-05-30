package pl.komorowskidev.taxitracker.domain.service

import org.springframework.stereotype.Service
import pl.komorowskidev.taxitracker.domain.exceptions.InvalidDateRangeException
import pl.komorowskidev.taxitracker.domain.model.TaxiLocation
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest
import pl.komorowskidev.taxitracker.domain.ports.TaxiLocationPort

@Service
class TaxiLocationService(private val taxiLocationPort: TaxiLocationPort) {
    fun saveTaxiLocation(taxiLocation: TaxiLocation) {
        taxiLocationPort.save(taxiLocation)
    }

    fun getTaxiLocations(taxiLocationRequest: TaxiLocationRequest): List<TaxiLocation> {
        validate(taxiLocationRequest)
        return taxiLocationPort.getTaxiLocations(taxiLocationRequest)
    }

    private fun validate(taxiLocationRequest: TaxiLocationRequest) {
        if (taxiLocationRequest.startTime.isAfter(
                taxiLocationRequest.endTime,
            )
        ) {
            throw InvalidDateRangeException("startTime cannot be after endTime")
        }
    }
}
