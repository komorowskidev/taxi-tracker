package pl.komorowskidev.taxitracker.domain.service

sealed class Validation {
    data object Valid : Validation()

    class Invalid(
        val message: String,
    ) : Validation()
}
