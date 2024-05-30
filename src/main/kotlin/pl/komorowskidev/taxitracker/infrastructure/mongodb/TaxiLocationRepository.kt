package pl.komorowskidev.taxitracker.infrastructure.mongodb

import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant

interface TaxiLocationRepository : MongoRepository<TaxiLocationEntity, String> {
    fun findByTaxiIdAndTimestampBetweenOrderByTimestamp(
        taxiId: String,
        start: Instant,
        end: Instant,
    ): List<TaxiLocationEntity>
}
