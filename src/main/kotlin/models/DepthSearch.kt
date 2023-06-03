package models

data class DepthSearch(
    val visited: Boolean,
    val predecessor: Vertex?,
    val distance: Int
)

