package pl.komorowskidev.taxitracker.domain.service

import org.springframework.stereotype.Component
import pl.komorowskidev.taxitracker.common.exceptions.InvalidDateRangeException
import pl.komorowskidev.taxitracker.domain.model.TaxiLocationRequest

@Component
class TaxiLocationValidator() {
    fun validate(taxiLocationRequest: TaxiLocationRequest) {
        if (taxiLocationRequest.startTime.isAfter(
                taxiLocationRequest.endTime,
            )
        ) {
            throw InvalidDateRangeException("startTime cannot be after endTime")
        }
    }
}
