package pl.komorowskidev.taxitracker.domain.ports

import pl.komorowskidev.taxitracker.domain.model.Geolocation

interface GeolocationPort1 {
    fun read(address: String): Geolocation
}
