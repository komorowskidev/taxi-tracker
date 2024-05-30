package pl.komorowskidev.taxitracker.domain.service

import org.springframework.stereotype.Service
import pl.komorowskidev.taxitracker.domain.model.TaxiLocation
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest
import pl.komorowskidev.taxitracker.domain.ports.TaxiLocationPort

@Service
class TaxiLocationService(private val taxiLocationPort: TaxiLocationPort, private val taxiLocationValidator: TaxiLocationValidator) {
    fun saveTaxiLocation(taxiLocation: TaxiLocation) {
        taxiLocationPort.save(taxiLocation)
    }

    fun getTaxiLocations(taxiLocationRequest: TaxiLocationRequest): List<TaxiLocation> {
        taxiLocationValidator.validate(taxiLocationRequest)
        return taxiLocationPort.getTaxiLocations(taxiLocationRequest)
    }
}
