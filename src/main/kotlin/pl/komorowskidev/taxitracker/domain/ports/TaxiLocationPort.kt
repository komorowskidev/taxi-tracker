package pl.komorowskidev.taxitracker.domain.ports

import pl.komorowskidev.taxitracker.domain.model.TaxiLocation
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest

interface TaxiLocationPort {
    fun save(taxiLocation: TaxiLocation)

    fun getTaxiLocations(taxiLocationRequest: TaxiLocationRequest): List<TaxiLocation>
}
