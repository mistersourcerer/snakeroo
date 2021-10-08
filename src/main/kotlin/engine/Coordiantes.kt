package engine

import org.w3c.dom.CanvasRenderingContext2D

data class Vector2d(val x: Int, val y: Int)
data class Rect(val position: Vector2d, val width: Int, val height: Int)