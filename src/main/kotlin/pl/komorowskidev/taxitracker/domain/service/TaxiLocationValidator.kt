package pl.komorowskidev.taxitracker.domain.service

import org.springframework.stereotype.Component
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest

@Component
class TaxiLocationValidator {
    fun validate(taxiLocationRequest: TaxiLocationRequest): Validation {
        if (taxiLocationRequest.endTime.isBefore(taxiLocationRequest.startTime)) {
            return Validation.Invalid("endTime cannot be before startTime: $taxiLocationRequest")
        }
        return Validation.Valid
    }
}
