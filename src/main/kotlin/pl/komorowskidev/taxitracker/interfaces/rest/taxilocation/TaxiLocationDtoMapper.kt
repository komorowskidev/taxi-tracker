package pl.komorowskidev.taxitracker.interfaces.rest.taxilocation

import org.springframework.stereotype.Component
import pl.komorowskidev.taxitracker.domain.model.TaxiLocation

@Component
class TaxiLocationDtoMapper {
    fun toDto(taxiLocation: TaxiLocation): TaxiLocationDto =
        TaxiLocationDto(
            taxiId = taxiLocation.taxiId,
            latitude = taxiLocation.latitude.toPlainString(),
            longitude = taxiLocation.longitude.toPlainString(),
            timestamp = taxiLocation.timestamp.toString(),
        )
}
