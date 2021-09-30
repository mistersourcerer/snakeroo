import engine.Client
import kotlinx.browser.window

class Snakeroo : Client {
    private var last: Double = 0.0

    override fun update(elapsed: Double): Boolean {
        last += elapsed
        if(last >= 1000) {
            last = 0.0
            return true
        }

        return false
    }
}