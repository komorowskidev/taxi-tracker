package pl.komorowskidev.taxitracker.infrastructure.mongodb

import org.springframework.stereotype.Component
import pl.komorowskidev.taxitracker.domain.model.TaxiLocation
import pl.komorowskidev.taxitracker.domain.ports.TaxiLocationPort

@Component
class TaxiLocationAdapter(
    private val taxiLocationRepository: TaxiLocationRepository,
    private val taxiLocationMapper: TaxiLocationMapper,
) : TaxiLocationPort {
    override fun save(taxiLocation: TaxiLocation) {
        taxiLocationRepository.save(taxiLocationMapper.toEntity(taxiLocation))
    }
}
