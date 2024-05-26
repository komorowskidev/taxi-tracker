package pl.komorowskidev.taxitracker.infrastructure.mongodb

import org.springframework.data.mongodb.repository.MongoRepository

interface TaxiLocationRepository : MongoRepository<TaxiLocationEntity, String>
