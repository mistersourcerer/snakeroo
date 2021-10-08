package engine

import kotlinx.browser.window
import org.w3c.dom.Image
import kotlin.js.Promise

interface Client {
    fun update(elapsed: Double) : Boolean = true
    fun render(timestamp: Double) = console.log("rendering ${timestamp}...")
    fun run() = console.log("running")

    fun loadImage(path: String) : Promise<Image> {
        val img = Image()
        val p = Promise<Image> { resolve, reject ->
            img.onload = { resolve(img) }
            img.onerror = { _, _, _, _, _ -> reject(Exception("Fail to load $path")) }
        }
        img.src = path

        return p
    }
}