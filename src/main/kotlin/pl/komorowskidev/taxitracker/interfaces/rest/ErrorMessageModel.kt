package pl.komorowskidev.taxitracker.interfaces.rest

data class ErrorMessageModel(
    val status: Int,
    val message: String?,
)
