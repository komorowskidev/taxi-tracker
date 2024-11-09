package pl.komorowskidev.taxitracker.interfaces.rest

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
sealed class Response<T>(
    val data: T? = null,
    val errorMessage: String? = null,
) {
    class Success<T>(
        data: T,
    ) : Response<T>(data = data)

    class Error<T>(
        errorMessage: String,
    ) : Response<T>(errorMessage = errorMessage)
}
