package pl.komorowskidev.taxitracker.domain.ports

import pl.komorowskidev.taxitracker.domain.model.TaxiLocation

interface TaxiLocationPort {
    fun save(taxiLocation: TaxiLocation)
}
