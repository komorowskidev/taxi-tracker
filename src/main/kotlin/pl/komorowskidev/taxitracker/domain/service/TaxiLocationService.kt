package pl.komorowskidev.taxitracker.domain.service

import org.springframework.stereotype.Service
import pl.komorowskidev.taxitracker.domain.model.TaxiLocation
import pl.komorowskidev.taxitracker.domain.ports.TaxiLocationPort

@Service
class TaxiLocationService(private val taxiLocationPort: TaxiLocationPort) {
    fun saveTaxiLocation(taxiLocation: TaxiLocation) {
        taxiLocationPort.save(taxiLocation)
    }
}
