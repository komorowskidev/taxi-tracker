package pl.komorowskidev.taxitracker.interfaces.sqs.taxievent

import org.springframework.stereotype.Component
import pl.komorowskidev.taxitracker.domain.model.TaxiLocation

@Component
class TaxiEventMapper {
    fun toDomain(taxiEventDto: TaxiEventDto): TaxiLocation =
        TaxiLocation(
            taxiId = taxiEventDto.taxiId,
            latitude = taxiEventDto.latitude,
            longitude = taxiEventDto.longitude,
            timestamp = taxiEventDto.timestamp,
        )
}
