package pl.komorowskidev.taxitracker.infrastructure.mongodb

import org.springframework.stereotype.Component
import pl.komorowskidev.taxitracker.domain.model.TaxiLocation

@Component
class TaxiLocationMapper {
    fun toEntity(taxiLocation: TaxiLocation): TaxiLocationEntity {
        return TaxiLocationEntity(
            id = taxiLocation.id,
            latitude = taxiLocation.latitude,
            longitude = taxiLocation.longitude,
            timestamp = taxiLocation.timestamp,
        )
    }
}
