package engine

import kotlinx.browser.window

interface Client {
    fun update(elapsed: Double) : Boolean = true
    fun render(timestamp: Double) = console.log("rendering ${timestamp}...")
}