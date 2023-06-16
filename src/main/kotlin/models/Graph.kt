package models

import java.math.BigDecimal
import java.math.RoundingMode.DOWN
import java.util.LinkedList
import java.util.Queue

class Graph {
    val vertices: MutableSet<Vertex> = mutableSetOf()
    val edges: MutableSet<Edge> = mutableSetOf()
    val breadthSearch: MutableSet<BreadthSearch> = mutableSetOf()

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
    fun adjacencyMatrix(): Array<IntArray> {
        val matrix = Array(vertices.size){IntArray(vertices.size)}

        val positions = mutableMapOf<Vertex, Int>()
        for ((numero, vertex) in vertices.withIndex()){
            positions[vertex] = numero
        }
        edges.forEach {
            adjacencyMatrixEdge(matrix, positions[it.vertices.first]!!, positions[it.vertices.second]!!)
        }
        return matrix
    }
    private fun adjacencyMatrixEdge(matrizAdjacency: Array<IntArray>, origin:Int, destiny:Int){
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
            incidenceMatrixToAdd(matrix, positions[edge.vertices.first]!!, positions[edge.vertices.second]!!, index)
        }
        return matrix

    }
    private fun incidenceMatrixToAdd(matrizIncidence: Array<IntArray>, origin:Int, destiny:Int, column:Int){
        if (origin == destiny){
            matrizIncidence[origin][column] = 2
        }
        else{
            matrizIncidence[origin][column] = 1
            matrizIncidence[destiny][column] = 1
        }
    }
    fun adjacencyList():Map<Int, MutableList<Int>>{
        val adjacencyList = mutableMapOf<Int, MutableList<Int>>()
        val positions = mutableMapOf<Vertex, Int>()
        for ((numero, vertex) in vertices.withIndex()){
            positions[vertex] = numero
        }
        edges.forEach {
            val origin = positions[it.vertices.first]
            val destiny = positions[it.vertices.second]
            if (origin != null && destiny != null){
                adjacencyListToAdd(adjacencyList, origin, destiny)
            }
        }
        return adjacencyList
    }

    private fun adjacencyListToAdd(listAdjacency: MutableMap<Int, MutableList<Int>>, origin: Int, destiny: Int) {
        listAdjacency.getOrPut(origin) { mutableListOf() }.add(destiny)
        if (origin != destiny){
            listAdjacency.getOrPut(destiny) { mutableListOf() }.add(origin)
        }
    }

    fun incidenceList(): Map<Int, MutableList<Int>>{
        val incidenceList = mutableMapOf<Int, MutableList<Int>>()
        val positions = mutableMapOf<Vertex, Int>()
        for ((numero, vertex) in vertices.withIndex()){
            positions[vertex] = numero
        }
        edges.forEachIndexed { edgeIndex, edge ->
            val origin = positions[edge.vertices.first]
            val destiny = positions[edge.vertices.second]

            if (origin != null && destiny != null) {
                incidenceListToAdd(incidenceList, origin, destiny, edgeIndex)
            }
        }
        return incidenceList
    }

    private fun incidenceListToAdd(incidenceList: MutableMap<Int, MutableList<Int>>, origin: Int, destiny: Int, edgeIndex: Int) {
        incidenceList.getOrPut(origin) { mutableListOf() }.add(edgeIndex)
        if (origin != destiny){

            incidenceList.getOrPut(destiny) { mutableListOf() }.add(edgeIndex)
        }
    }

    private fun getAdjacentVertices(currentVertex: Vertex): List<Vertex> {
        val adjacenctVertices = mutableListOf<Vertex>()
        edges.forEach {
            if (currentVertex == it.vertices.first){
                adjacenctVertices.add(it.vertices.second)
            }else if (currentVertex == it.vertices.second){
                adjacenctVertices.add(it.vertices.first)
            }
        }
        return adjacenctVertices
    }

    fun breadthSearch(startVertex: Vertex): MutableMap<Vertex, BreadthSearch> {
        val breadthSearch = mutableMapOf<Vertex, BreadthSearch>()
        val queueOfVertices:Queue<Vertex> = LinkedList()
        val visited = mutableMapOf<Vertex, Boolean>()
        val distance = mutableMapOf<Vertex, Int>()
        vertices.forEach {
            visited[it] = false
            distance[it] = Int.MAX_VALUE
        }
        queueOfVertices.offer(startVertex)
        visited[startVertex] = true
        distance[startVertex] = 0
        breadthSearch[startVertex] = BreadthSearch(
            visited = visited[startVertex]!!,
            predecessor = null,
            distance = distance[startVertex]!!
        )
        while (queueOfVertices.isNotEmpty()){
            val currentVertex = queueOfVertices.poll()
            processBreadthSearch(currentVertex, queueOfVertices, visited, distance, breadthSearch)
        }
        return breadthSearch
    }
    private fun processBreadthSearch(
        currentVertex: Vertex,
        queueOfVertices: Queue<Vertex>,
        visited: MutableMap<Vertex, Boolean>,
        distance: MutableMap<Vertex, Int>,
        breadthSearch: MutableMap<Vertex, BreadthSearch>
    ) {
        for (neighbor in getAdjacentVertices(currentVertex)) {
            if (!visited[neighbor]!!) {
                queueOfVertices.offer(neighbor)
                visited[neighbor] = true
                distance[neighbor] = distance[currentVertex]!! + 1
                breadthSearch[neighbor] = BreadthSearch(
                    visited = visited[neighbor]!!,
                    predecessor = currentVertex,
                    distance = distance[neighbor]!!
                )
            }
        }
    }


    fun depthSearch(startVertex: Vertex): MutableMap<Vertex, DepthSearch> {
        val depthSearch = mutableMapOf<Vertex, DepthSearch>()
        val visited = mutableMapOf<Vertex, Boolean>()
        val distance = mutableMapOf<Vertex, Int>()
        vertices.forEach {
            visited[it] = false
        }
        val predecessor:Vertex? = null
        depthSearchVisit(startVertex, predecessor, visited, distance, depthSearch)

        return depthSearch
    }

    private fun depthSearchVisit(
        vertex: Vertex,
        predecessor: Vertex?,
        visited: MutableMap<Vertex, Boolean>,
        distance: MutableMap<Vertex, Int>,
        depthSearch: MutableMap<Vertex, DepthSearch>
    ) {
        visited[vertex] = true
        depthSearch[vertex] = DepthSearch(
            visited = visited[vertex]!!,
            distance = distance[vertex]!!,
            predecessor = predecessor
        )

        for (neighbor in getAdjacentVertices(vertex)) {
            if (!visited[neighbor]!!) {
                distance[neighbor] = distance[vertex]!!+1
                depthSearchVisit(neighbor, vertex, visited, distance, depthSearch)
            }
        }
    }
}