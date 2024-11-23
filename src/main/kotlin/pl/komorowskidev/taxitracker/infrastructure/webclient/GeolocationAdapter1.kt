package pl.komorowskidev.taxitracker.infrastructure.webclient

import org.springframework.stereotype.Component
import pl.komorowskidev.taxitracker.domain.model.Geolocation
import pl.komorowskidev.taxitracker.domain.ports.GeolocationPort1

@Component
class GeolocationAdapter1(private val webClient: WebClient) : GeolocationPort1 {
    override fun read(address: String): Geolocation {
        return webClient.callForGeolocation(address)
    }
}