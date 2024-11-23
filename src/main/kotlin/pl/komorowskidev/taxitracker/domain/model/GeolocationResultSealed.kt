package pl.komorowskidev.taxitracker.domain.model

sealed class GeolocationResultSealed {
    data class Success(val geolocation: Geolocation) : GeolocationResultSealed()
    data class Error(val message: String) : GeolocationResultSealed()
}