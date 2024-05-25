package pl.komorowskidev.taxitracker.domain.ports

import pl.komorowskidev.taxitracker.domain.model.Taxi

interface TaxiPort {
    fun save(taxi: Taxi)
}
