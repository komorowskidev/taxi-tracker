package pl.komorowskidev.taxitracker.domain.ports

import pl.komorowskidev.taxitracker.domain.model.GeolocationResult

interface GeolocationPort2 {
    fun read(address: String): GeolocationResult
}
