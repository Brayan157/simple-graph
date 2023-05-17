package models

import java.math.BigDecimal
import java.math.RoundingMode
import java.math.RoundingMode.DOWN

class Graph {
    private val vertices: MutableSet<Vertex> = mutableSetOf()
    private val edges: MutableSet<Edge> = mutableSetOf()

    fun addVertex(vertex: Vertex) {
        if (vertices.none { it.name == vertex.name }) {
            vertices.add(vertex)
        }
    }
    fun addEdge(edge: Edge) = edges.add(edge)
    fun getVertexDegree(vertex: Vertex) = edges.filter {
        it.existsVertex(vertex)
    }.size

    fun getAverageDegree(): BigDecimal {
        val degreeSum =  vertices.sumOf { getVertexDegree(it) }

        return degreeSum.toBigDecimal().divide(
            vertices.size.toBigDecimal(),
            2,
            DOWN
        )
    }

    fun getVertices() = vertices
}