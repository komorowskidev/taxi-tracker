package pl.komorowskidev.taxitracker.infrastructure.mongodb

import org.springframework.stereotype.Component
import pl.komorowskidev.taxitracker.domain.model.TaxiLocation

@Component
class TaxiLocationMapper {
    fun toEntity(taxiLocation: TaxiLocation) =
        TaxiLocationEntity(
            taxiId = taxiLocation.taxiId,
            latitude = taxiLocation.latitude,
            longitude = taxiLocation.longitude,
            timestamp = taxiLocation.timestamp,
        )

    fun toDomain(taxiLocationEntity: TaxiLocationEntity) =
        TaxiLocation(
            taxiId = taxiLocationEntity.taxiId,
            latitude = taxiLocationEntity.latitude,
            longitude = taxiLocationEntity.longitude,
            timestamp = taxiLocationEntity.timestamp,
        )
}
