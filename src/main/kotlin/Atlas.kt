import engine.Vector2d
import org.w3c.dom.Image
import kotlin.js.Promise

data class Atlas(val size: Int = 32, val path: String = "sprite.png") {
    val headDown = tileAt(0, 0)
    val headLeft = tileAt(6, 1)
    val headUp = tileAt(7,0)
    val headRight = tileAt(7, 1)

    val bodyHor = tileAt(0, 1)
    val bodyVer = tileAt(1, 1)
    val bodyDownRight = tileAt(2, 0)
    val bodyLeftDown = tileAt(3, 0)
    val bodyUpRight = tileAt(2, 1)
    val bodyLeftUp = tileAt(3, 1)

    val tailHor = tileAt(1, 0)
    val tailRight = tileAt(4, 0)
    val tailLeft = tileAt(4, 1)
    val tailUp = tileAt(5, 0)

    val bottle = tileAt(5,1)
    val apple = tileAt(6, 0)

    private val soilMap = listOf(
        tileAt(8, 0),
        tileAt(8, 1)
    )

    fun load(): Promise<Image> {
        val img = Image().apply { src = path }
        return Promise<Image> { resolve, reject ->
            img.onload = { resolve(img) }
            img.onerror = { _, _, _, _, _ -> reject(Exception("Fail to load $path")) }
        }
    }

    fun getSoil(idx: Int): Vector2d {
        return soilMap[idx]
    }

    private fun tileAt(col: Int, row: Int): Vector2d {
        return Vector2d(col * size, row * size)
    }
}