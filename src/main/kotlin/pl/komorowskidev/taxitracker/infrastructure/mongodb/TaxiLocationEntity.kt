package pl.komorowskidev.taxitracker.infrastructure.mongodb

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.Instant

@Document(collection = "taxi_locations")
data class TaxiLocationEntity(
    @Id val id: ObjectId = ObjectId.get(),
    val taxiId: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val timestamp: Instant,
)
