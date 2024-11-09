package pl.komorowskidev.taxitracker.domain.service

import org.springframework.stereotype.Service
import pl.komorowskidev.taxitracker.domain.model.TaxiLocation
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest
import pl.komorowskidev.taxitracker.domain.ports.TaxiLocationPort

@Service
class TaxiLocationService(
    private val taxiLocationPort: TaxiLocationPort,
    private val taxiLocationValidator: TaxiLocationValidator,
) {
    fun saveTaxiLocation(taxiLocation: TaxiLocation) {
        taxiLocationPort.save(taxiLocation)
    }

    fun getTaxiLocations(taxiLocationRequest: TaxiLocationRequest): TaxiLocationsResponse =
        when (val validationResult = taxiLocationValidator.validate(taxiLocationRequest)) {
            Validation.Valid -> callToInfrastructure(taxiLocationRequest)
            is Validation.Invalid -> TaxiLocationsResponse.RequestNotValid(validationResult.message)
        }

    private fun callToInfrastructure(taxiLocationRequest: TaxiLocationRequest): TaxiLocationsResponse =
        TaxiLocationsResponse.Success(taxiLocationPort.getTaxiLocations(taxiLocationRequest))
}
