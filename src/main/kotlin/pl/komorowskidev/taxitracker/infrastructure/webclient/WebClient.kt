package pl.komorowskidev.taxitracker.infrastructure.webclient

import org.springframework.stereotype.Component
import pl.komorowskidev.taxitracker.common.WebClientTimeoutException
import pl.komorowskidev.taxitracker.domain.model.Geolocation

@Component
class WebClient {
    fun callForGeolocation(address: String): Geolocation {
        throw WebClientTimeoutException("timeout")
    }
}