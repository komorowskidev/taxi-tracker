package pl.komorowskidev.taxitracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaxiTrackerApplication

fun main(args: Array<String>) {
    runApplication<TaxiTrackerApplication>(*args)
}
