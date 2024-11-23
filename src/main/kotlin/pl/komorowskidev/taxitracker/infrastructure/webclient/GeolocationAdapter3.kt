package pl.komorowskidev.taxitracker.infrastructure.webclient

import org.springframework.stereotype.Component
import pl.komorowskidev.taxitracker.common.WebClientTimeoutException
import pl.komorowskidev.taxitracker.domain.model.GeolocationResultSealed
import pl.komorowskidev.taxitracker.domain.ports.GeolocationPort3

@Component
class GeolocationAdapter3(private val webClient: WebClient) : GeolocationPort3 {
    override fun read(address: String): GeolocationResultSealed {
        return try {
            GeolocationResultSealed.Success(webClient.callForGeolocation(address))
        } catch(e: WebClientTimeoutException) {
            GeolocationResultSealed.Error("timeout")
        }
    }
}