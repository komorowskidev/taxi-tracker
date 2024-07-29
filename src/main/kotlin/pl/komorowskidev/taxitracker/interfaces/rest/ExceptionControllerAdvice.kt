package pl.komorowskidev.taxitracker.interfaces.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import pl.komorowskidev.taxitracker.common.exceptions.InvalidDateRangeException

@RestControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler(value = [InvalidDateRangeException::class])
    fun handleInvalidDataRangeException(e: InvalidDateRangeException): ResponseEntity<ErrorMessageModel> {
        return ResponseEntity.badRequest().body(ErrorMessageModel(400, e.message))
    }
}
