package models

data class DepthSearch(
    var visited: Boolean?,
    var predecessor: Vertex?,
    var time: Int?,
    var finalDistance: Int?
)

