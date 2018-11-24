package org.dronix.knsamples.timer

import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr
import platform.posix.*

object TimerTask{
    fun setIntervalSeconds(initialDelay: Long, period: Long, handler: platform.posix.__sighandler_t?) {

            val sa: sigaction = nativeHeap.alloc()
            val timer: itimerval = nativeHeap.alloc()

            sa.__sigaction_handler.sa_handler = handler
            sigaction(SIGALRM, sa.ptr, null)

            timer.it_value.tv_usec = 0
            timer.it_value.tv_sec = if(initialDelay == 0L) period else initialDelay

            timer.it_interval.tv_usec = 0
            timer.it_interval.tv_sec = period

            setitimer(ITIMER_REAL, timer.ptr, null)

    }

    fun setIntervalMicro(initialDelay: Long, period: Long, handler: platform.posix.__sighandler_t?) {
        memScoped {
            val sa: sigaction = alloc()
            val timer: itimerval = alloc()

            sa.__sigaction_handler.sa_handler = handler
            sigaction(SIGALRM, sa.ptr, null)

            timer.it_value.tv_sec = 0
            timer.it_value.tv_usec = if(initialDelay == 0L) period else initialDelay

            timer.it_interval.tv_sec = 0
            timer.it_interval.tv_usec = period

            setitimer(ITIMER_REAL, timer.ptr, null)
        }
    }
}