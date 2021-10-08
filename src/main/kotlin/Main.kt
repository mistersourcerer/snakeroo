import engine.Game
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement

// Doesn't seem to me that this is "the" way to do this,
// But I couldn't find anything different from that =((
external fun require(o: String)

val game = Snakeroo()
fun main() {
    require("css/snakeroo.scss")

    val canvas = document.getElementById("snakeroo") as HTMLCanvasElement
    val context = canvas.getContext("2d") as CanvasRenderingContext2D
    game.apply { ctx = context }.run()
}