import engine.Client
import kotlinx.browser.window
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.Image

class Snakeroo(canvas: HTMLCanvasElement) : Client {
    // grid w32, h32
    private var last: Double = 0.0

    private val atlas = Image

    override fun update(elapsed: Double): Boolean {
        last += elapsed
        if(last >= 1000) {
            last = 0.0
            return true
        }

        return false
    }
}