package engine

import kotlinx.browser.window

class Game {
    val startedAt: Double = now()
    var fps = 60
    var frameIntervalMS = (1000 / fps)
    var tickMS = frameIntervalMS / 2
    var debug = false
    var seeElapsed = false
    var stop = false
    var client: Client = DefaultClient()

    private class DefaultClient : Client
    private var changed = false
    private var animating = false
    private var previous = startedAt
    private var lastRender = startedAt

    init {
        if(debug) debugInit()
    }

    fun loop() {
        val current: Double = now()
        val elapsed: Double = current - previous
        previous = current
        if(debug && seeElapsed) debugLoop(elapsed)

        changed = client.update(elapsed)

        val shouldRender = (current - lastRender) >= frameIntervalMS
        if(changed && shouldRender) render()

        if(!stop) window.setTimeout(::loop, tickMS)
    }

    private fun now() : Double {
        return window.performance.now()
    }

    private fun render() {
        lastRender = now()
        if(debug) console.log("changes were made, we should render")

        if(!animating) {
            if(debug) console.log("requesting animation frame")
            animating = true
            window.requestAnimationFrame {
                client.render(it)
                animating = false
            }
        }

        changed = false
    }

    private fun debugInit() {
        console.log("startedAt $startedAt")
        console.log("fps cap: $fps, frameIntervalMS: $frameIntervalMS")
        console.log("ticking at: $tickMS")
    }

    private fun debugLoop(elapsed: Double) {
        console.log("  changed: $changed")
        console.log("  elapsed: $elapsed")
    }
}