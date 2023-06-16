package models

data class DepthSearch(
    var visited: Boolean?,
    var predecessor: Vertex?,
    var distance: Int?,
    var finalDistance: Int?
)

