package models

class Edge (
    val vertices: Pair<Vertex, Vertex>
) {

    fun existsVertex(vertex: Vertex): Boolean {
        return vertices.first.name == vertex.name || vertices.second.name == vertex.name
    }

    fun isLoop() = vertices.first == vertices.second
}
