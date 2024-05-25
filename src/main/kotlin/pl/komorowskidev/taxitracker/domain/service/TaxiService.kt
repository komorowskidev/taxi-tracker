package pl.komorowskidev.taxitracker.domain.service

import org.springframework.stereotype.Service
import pl.komorowskidev.taxitracker.domain.model.Taxi
import pl.komorowskidev.taxitracker.domain.ports.TaxiPort

@Service
class TaxiService(private val taxiPort: TaxiPort) {
    fun saveTaxiLocation(taxi: Taxi) {
        taxiPort.save(taxi)
    }
}
