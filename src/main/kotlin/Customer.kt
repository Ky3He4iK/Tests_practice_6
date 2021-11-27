import java.util.concurrent.Semaphore

import java.util.concurrent.atomic.AtomicInteger


class Customer(
    private val spaces: AtomicInteger,
    private val bavailable: Semaphore,
    private val cavailable: Semaphore
) : Runnable {
    override fun run() {
        try {
            cavailable.release()
            if (bavailable.hasQueuedThreads()) {
                spaces.decrementAndGet()
                println("Customer in waiting area")
                bavailable.acquire()
                spaces.incrementAndGet()
            } else {
                bavailable.acquire()
            }
        } catch (e: InterruptedException) {
        }
    }
}
