package models

import java.math.BigDecimal
import java.math.RoundingMode.DOWN

class Graph {
    val vertices: MutableSet<Vertex> = mutableSetOf()
    val edges: MutableSet<Edge> = mutableSetOf()

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
    fun listaAdjacenci():Map<Int, MutableList<Int>>{
        val listAdjacency = mutableMapOf<Int, MutableList<Int>>()
        val positions = mutableMapOf<Vertex, Int>()
        for ((numero, vertex) in vertices.withIndex()){
            positions[vertex] = numero
        }
        edges.forEach {
            val origin = positions[it.vertices.first]
            val destiny = positions[it.vertices.second]
            if (origin != null && destiny != null){
                listAdjacencyToAdd(listAdjacency, origin, destiny)
            }
        }
        return listAdjacency
    }

    fun listAdjacencyToAdd(listAdjacency: MutableMap<Int, MutableList<Int>>, origin: Int, destiny: Int) {
        listAdjacency.getOrPut(origin) { mutableListOf() }.add(destiny)
        if (origin != destiny){
            listAdjacency.getOrPut(destiny) { mutableListOf() }.add(origin)
        }
    }

    fun listIncidence(): Map<Int, MutableList<Int>>{
        val listIncidence = mutableMapOf<Int, MutableList<Int>>()
        val positions = mutableMapOf<Vertex, Int>()
        for ((numero, vertex) in vertices.withIndex()){
            positions[vertex] = numero
        }
        edges.forEachIndexed { edgeIndex, edge ->
            val origin = positions[edge.vertices.first]
            val destiny = positions[edge.vertices.second]

            if (origin != null && destiny != null) {
                listIncidenceToAdd(listIncidence, origin, destiny, edgeIndex)
            }
        }
        return listIncidence
    }

    fun listIncidenceToAdd(listIncidence: MutableMap<Int, MutableList<Int>>, origin: Int, destiny: Int, edgeIndex: Int) {
        listIncidence.getOrPut(origin) { mutableListOf() }.add(edgeIndex)
        if (origin != destiny){
            listIncidence.getOrPut(destiny) { mutableListOf() }.add(edgeIndex)
        }
    }
}