package pl.komorowskidev.taxitracker.domain.service

import org.springframework.stereotype.Service
import pl.komorowskidev.taxitracker.domain.model.Geolocation
import pl.komorowskidev.taxitracker.domain.model.GeolocationResultError
import pl.komorowskidev.taxitracker.domain.model.GeolocationResultSuccess
import pl.komorowskidev.taxitracker.domain.model.GeolocationResultSealed
import pl.komorowskidev.taxitracker.domain.ports.GeolocationPort1
import pl.komorowskidev.taxitracker.domain.ports.GeolocationPort2
import pl.komorowskidev.taxitracker.domain.ports.GeolocationPort3

@Service
class GeolocationService(
    private val geolocationPort1: GeolocationPort1,
    private val geolocationPort2: GeolocationPort2,
    private val geolocationPort3: GeolocationPort3,
    ) {

    fun resolveGeolocation1(address: String) {
        val geolocation = geolocationPort1.read(address)
    }









    fun resolveGeolocation2(address: String) {
        when(val geolocationResult = geolocationPort2.read(address)) {
            is GeolocationResultSuccess -> process(geolocationResult.geolocation)
            is GeolocationResultError -> handleError(geolocationResult.message)
        }
    }








    fun resolveGeolocation3(address: String) {
        when(val geolocationResult = geolocationPort3.read(address)) {
            is GeolocationResultSealed.Success -> process(geolocationResult.geolocation)
            is GeolocationResultSealed.Error -> handleError(geolocationResult.message)
//            else -> handleErrors()
        }
    }




    private fun process(geolocation: Geolocation) {
        // do something
    }

    private fun handleError(message: String) {
        // handle error
    }

    private fun handleErrors() {

    }
}