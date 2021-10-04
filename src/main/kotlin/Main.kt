import engine.Game
import kotlinx.browser.document
import kotlinx.browser.window

// Doesn't seem to me that this is "the" way to do this,
// But I couldn't find anything different from that =((
external fun require(o: String)

val game = Game()
fun main() {
    require("css/snakeroo.scss")

    val canvas = document.getElementById("canvas")

    game.client = Snakeroo(canvas)
    game.loop()
}