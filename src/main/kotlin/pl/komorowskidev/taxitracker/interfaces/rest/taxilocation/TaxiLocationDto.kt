package pl.komorowskidev.taxitracker.interfaces.rest.taxilocation

data class TaxiLocationDto(
    val taxiId: String,
    val latitude: String,
    val longitude: String,
    val timestamp: String,
)
