package pl.komorowskidev.taxitracker.infrastructure.webclient

import org.springframework.stereotype.Component
import pl.komorowskidev.taxitracker.common.WebClientTimeoutException
import pl.komorowskidev.taxitracker.domain.model.GeolocationResultError
import pl.komorowskidev.taxitracker.domain.model.GeolocationResultSuccess
import pl.komorowskidev.taxitracker.domain.model.GeolocationResult
import pl.komorowskidev.taxitracker.domain.ports.GeolocationPort2

@Component
class GeolocationAdapter2(private val webClient: WebClient) : GeolocationPort2 {
    override fun read(address: String): GeolocationResult {
        return try {
            GeolocationResultSuccess(webClient.callForGeolocation(address))
        } catch(e: WebClientTimeoutException) {
            GeolocationResultError("timeout")
        }
    }
}