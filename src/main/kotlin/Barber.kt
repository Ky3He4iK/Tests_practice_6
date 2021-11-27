import java.util.concurrent.Semaphore
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicInteger


class Barber(private val spaces: AtomicInteger, private val bavailable: Semaphore, private val cavailable: Semaphore) :
    Runnable {
    override fun run() {
        while (true) {
            try {
                cavailable.acquire()

                // Space freed up in waiting area
                println("Customer getting hair cut")
                Thread.sleep(
                    ThreadLocalRandom.current().nextInt(1000, 10000 + 1000).toLong()
                ) // Sleep to imitate length of time to cut hair
                println("Customer Pays and leaves")
                bavailable.release()
            } catch (e: InterruptedException) {
            }
        }
    }
}
