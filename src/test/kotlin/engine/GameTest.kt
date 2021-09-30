package engine

import kotlin.test.Test
import kotlin.test.assertTrue

class GameTest {
    private val game = Game()

    @Test
    fun knowsTheCurrentTimestamp() {
        assertTrue(game.startedAt != null)
    }
}