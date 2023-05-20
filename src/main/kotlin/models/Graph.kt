package models

import java.math.BigDecimal
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
    fun getVertexDegree(vertex: Vertex): Int {
        var count = 0
        edges.forEach {
            if (it.existsVertex(vertex)) {
                count++
                if (it.isLoop()) count++
            }
        }

        return count
    }

    fun getAverageDegree(): BigDecimal {
        val degreeSum =  vertices.sumOf {
            getVertexDegree(it)
        }

        return degreeSum.toBigDecimal().divide(
            vertices.size.toBigDecimal(),
            2,
            DOWN
        )
    }

    fun getVertices() = vertices
}