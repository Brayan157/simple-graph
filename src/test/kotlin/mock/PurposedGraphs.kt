package mock

import models.Edge
import models.Graph
import models.Vertex

object PurposedGraphs {

    private const val FIRST_INDEX = 0
    private const val SECOND_INDEX = 1
    private const val THIRD_INDEX = 2
    private const val FOURTH_INDEX = 3

    fun generateFirstPurposedGraph(): Graph {
        val graph = Graph()
        val vertexList = listOf(
            Vertex("v1"),
            Vertex("v2"),
            Vertex("v3"),
            Vertex("v4")
        )
        val edgeList = listOf(
            Edge(Pair(vertexList[FIRST_INDEX], vertexList[SECOND_INDEX])),
            Edge(Pair(vertexList[FIRST_INDEX], vertexList[SECOND_INDEX])),
            Edge(Pair(vertexList[SECOND_INDEX], vertexList[THIRD_INDEX])),
            Edge(Pair(vertexList[FIRST_INDEX], vertexList[FOURTH_INDEX])),
            Edge(Pair(vertexList[THIRD_INDEX], vertexList[FOURTH_INDEX])),
            Edge(Pair(vertexList[THIRD_INDEX], vertexList[FOURTH_INDEX])),
            Edge(Pair(vertexList[FIRST_INDEX], vertexList[FIRST_INDEX])),
        )

        vertexList.forEach { graph.addVertex(it) }
        edgeList.forEach { graph.addEdge(it) }

        return graph
    }
}