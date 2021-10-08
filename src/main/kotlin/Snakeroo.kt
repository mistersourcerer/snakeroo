import engine.*
import kotlinx.browser.document
import org.w3c.dom.CanvasImageSource
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.svg.SVGAngle
import kotlin.random.Random

class Snakeroo() : Client {
    // grid w32, h32
    lateinit var ctx: CanvasRenderingContext2D
    var velocity = 2 // pixels per frame

    private lateinit var sprite: CanvasImageSource

    private var shouldStop: Boolean = false
    private val game = Game(this)
    private val atlas = Atlas()
    private val loaded = mutableMapOf("atlas" to false)
    private val soilMap = mutableListOf<MutableList<Int>>()

    private enum class Direction { NORTH, SOUTH, WEST, EAST }
    private data class Snake(var size: Int, var position: Vector2d, var direction: Direction = Direction.SOUTH)
    private val snake = Snake(3, Vector2d(0, 0))

    private val keyDirections = mutableMapOf(
        "ArrowUp" to ::moveUp,
        "ArrowDown" to ::moveDown,
        "ArrowLeft" to ::moveWest,
        "ArrowRight" to ::moveEast,

        "w" to ::moveUp,
        "s" to ::moveDown,
        "a" to ::moveWest,
        "d" to ::moveEast,
    )

    private val commands: ArrayDeque<() -> Unit> = ArrayDeque()

    private fun moveUp() {
        snake.direction = Direction.NORTH
        snake.position = Vector2d(snake.position.x, snake.position.y - velocity)
    }

    private fun moveDown() {
        snake.direction = Direction.SOUTH
        snake.position = Vector2d(snake.position.x, snake.position.y + velocity)
    }

    private fun moveWest() {
        snake.direction = Direction.WEST
        snake.position = Vector2d(snake.position.x - velocity, snake.position.y)
    }

    private fun moveEast() {
        snake.direction = Direction.EAST
        snake.position = Vector2d(snake.position.x + velocity, snake.position.y)
    }

    override fun run() {
        resizeCanvas()
        createMap()
        registerHandlers()

        // snake.position = map.getCoord(0, 0)

        atlas.load().then {
            console.log("image loaded: ${it.src}")
            loaded.put("atlas", true)
            sprite = it.unsafeCast<CanvasImageSource>()
            game.loop()
        }
    }

    private var lastKey: String = ""
    private fun registerHandlers() {
        document.addEventListener("keydown", { e ->
            val event = e as KeyboardEvent
            if(lastKey != event.key) {
                keyDirections[event.key]?.let { commands.add(it) }
                lastKey = event.key
            }
        })
    }

    private fun updateSnake(elapsed: Double) {
        commands.removeFirstOrNull()?.invoke()
        snake.position = when(snake.direction) {
            Direction.NORTH -> Vector2d(snake.position.x, snake.position.y - velocity)
            Direction.SOUTH -> Vector2d(snake.position.x, snake.position.y + velocity)
            Direction.WEST -> Vector2d(snake.position.x - velocity, snake.position.y)
            Direction.EAST -> Vector2d(snake.position.x + velocity, snake.position.y)
        }
    }

    override fun update(elapsed: Double): Boolean {
        if(shouldStop) return false
        if(!loaded.values.all { it }) return false
        updateSnake(elapsed)

        // last += elapsed
        // if(last >= 1000) {
        //     last = 0.0
        //     return true
        // }

        // return false

        return true
    }

    override fun render(timestamp: Double) {
        ctx.clearRect(0.0, 0.0, ctx.canvas.width.toDouble(), ctx.canvas.height.toDouble());
        // renderSoil(ctx)
        renderSnake(ctx)
    }

    fun stop() {
        shouldStop = true
    }

    fun start() {
        shouldStop = false
    }

    private fun resizeCanvas() {
        // change size accordingly with screen size
        ctx.canvas.apply {
            width = 640
            height = 480
        }
    }

    private fun createMap() {


        // repeat(map.cols) { col ->
        //     if(soilMap.size <= col) {
        //         soilMap.add(MutableList<Int>(map.rows) { Random.nextInt(0, 2) })
        //     }
        // }
    }

    private fun renderTile(ctx: CanvasRenderingContext2D, spritePosition: Vector2d, mapPosition: Vector2d) {
        ctx.drawImage(
            sprite,
            spritePosition.x.toDouble(),
            spritePosition.y.toDouble(),
            atlas.size.toDouble(),
            atlas.size.toDouble(),
            mapPosition.x.toDouble(),
            mapPosition.y.toDouble(),
            atlas.size.toDouble(),
            atlas.size.toDouble()
        )
    }

    private fun renderSnake(ctx: CanvasRenderingContext2D) {
        val head = when(snake.direction) {
            Direction.NORTH -> atlas.headUp
            Direction.SOUTH -> atlas.headDown
            Direction.WEST -> atlas.headLeft
            Direction.EAST -> atlas.headRight
        }
        renderTile(ctx, head, snake.position)
    }
}