package org.dronix.knsamples

import kotlinx.cinterop.staticCFunction
import org.dronix.knsamples.timer.TimerTask
import platform.posix.pause

fun main(args: Array<String>) {

    println("START")

    TimerTask.setIntervalSeconds(0, 1, staticCFunction(::handler))

    while (true){
        pause()
    }
}

fun handler(signum: Int) {
    initRuntimeIfNeeded()
    println("received signal: $signum")
}

