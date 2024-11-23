package pl.komorowskidev.taxitracker.domain.ports

import pl.komorowskidev.taxitracker.domain.model.GeolocationResultSealed

interface GeolocationPort3 {
    fun read(address: String): GeolocationResultSealed
}
