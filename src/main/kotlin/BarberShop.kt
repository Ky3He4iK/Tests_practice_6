import java.util.concurrent.Executors
import java.util.concurrent.Semaphore
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicInteger


internal object BarberShop {
    @JvmStatic
    fun main(args: Array<String>) {
        val spaces = AtomicInteger(15)
        val barbers = Semaphore(3, true)
        val customers = Semaphore(0, true)
        val openUp = Executors.newFixedThreadPool(3)

        println("Opening up shop")
        val employees: Array<Barber> = Array(3) { Barber(spaces, barbers, customers) }
        employees.forEach {
            openUp.execute(it)
        }
        while (true) {
            try {
                Thread.sleep(
                    ThreadLocalRandom.current().nextInt(100, 1000 + 100).toLong()
                ) // Sleep until next person gets in
            } catch (e: InterruptedException) {
            }
            println("Customer walks in")
            if (spaces.get() >= 0) {
                Thread(Customer(spaces, barbers, customers)).start()
            } else {
                println("Customer walks out, as no seats are available")
            }
        }
    }
}
