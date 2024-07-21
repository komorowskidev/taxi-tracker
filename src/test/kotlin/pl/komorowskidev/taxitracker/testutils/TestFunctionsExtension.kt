package pl.komorowskidev.taxitracker.testutils

import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextClosedEvent

fun ConfigurableApplicationContext.onClose(function: () -> Unit) =
    addApplicationListener { event ->
        if (event is ContextClosedEvent) {
            function()
        }
    }
