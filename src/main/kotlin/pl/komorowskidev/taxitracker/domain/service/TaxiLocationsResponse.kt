package pl.komorowskidev.taxitracker.domain.service

import pl.komorowskidev.taxitracker.domain.model.TaxiLocation

sealed class TaxiLocationsResponse {
    class Success(
        val taxiLocations: List<TaxiLocation>,
    ) : TaxiLocationsResponse()

    class RequestNotValid(
        val message: String,
    ) : TaxiLocationsResponse()
}
