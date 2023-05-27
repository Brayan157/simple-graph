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
    fun matrizAdjacency(): Array<IntArray> {
        val matrix = Array(vertices.size){IntArray(vertices.size)}

        val positions = mutableMapOf<Vertex, Int>()
        for ((numero, vertex) in vertices.withIndex()){
            positions[vertex] = numero
        }
        edges.forEach {
            matrixAdjacencyEdge(matrix, positions[it.vertices.first]!!, positions[it.vertices.second]!!)
        }
        return matrix
    }
    fun matrixAdjacencyEdge(matrizAdjacency: Array<IntArray>, origin:Int, destiny:Int){
        if (origin >=0 && origin < vertices.size && destiny>= 0 && destiny < vertices.size) {
            if (origin == destiny){
                matrizAdjacency[origin][destiny] += 1
            }
            else{
                matrizAdjacency[origin][destiny] += 1
                matrizAdjacency[destiny][origin] += 1
            }
        }
    }
    fun matrixIncidence():Array<IntArray>{
        val matrix = Array(vertices.size){IntArray(edges.size)}

        val positions = mutableMapOf<Vertex, Int>()
        for ((numero, vertex) in vertices.withIndex()){
            positions[vertex] = numero
        }
        edges.forEachIndexed { index, edge ->
            matrixIncidenceToAdd(matrix, positions[edge.vertices.first]!!, positions[edge.vertices.second]!!, index)
        }
        return matrix

    }
    fun matrixIncidenceToAdd(matrizIncidence: Array<IntArray>, origin:Int, destiny:Int, column:Int){
        if (origin == destiny){
            matrizIncidence[origin][column] = 2
        }
        else{
            matrizIncidence[origin][column] = 1
            matrizIncidence[destiny][column] = 1
        }

    }

    fun getVertices() = vertices
}