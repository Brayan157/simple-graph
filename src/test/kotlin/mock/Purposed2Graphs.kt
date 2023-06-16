package mock

import models.Edge
import models.Graph
import models.Vertex

object Purposed2Graphs {
    private const val u = 0
    private const val v = 1
    private const val x = 2
    private const val y = 3
    private const val w = 4
    private const val z = 5

    fun generateFirstPurposed2Graph(): Graph {
        val graph = Graph()
        val vertexList = listOf(
            Vertex("u"),
            Vertex("v"),
            Vertex("x"),
            Vertex("y"),
            Vertex("w"),
            Vertex("z")
        )
        val edgeList = listOf(
            Edge(Pair(vertexList[Purposed2Graphs.u], vertexList[Purposed2Graphs.v])),
            Edge(Pair(vertexList[Purposed2Graphs.u], vertexList[Purposed2Graphs.x])),
            Edge(Pair(vertexList[Purposed2Graphs.x], vertexList[Purposed2Graphs.v])),
            Edge(Pair(vertexList[Purposed2Graphs.y], vertexList[Purposed2Graphs.x])),
            Edge(Pair(vertexList[Purposed2Graphs.w], vertexList[Purposed2Graphs.y])),
            Edge(Pair(vertexList[Purposed2Graphs.w], vertexList[Purposed2Graphs.z])),
            Edge(Pair(vertexList[Purposed2Graphs.z], vertexList[Purposed2Graphs.z])),
        )
        vertexList.forEach { graph.addVertex(it) }
        edgeList.forEach { graph.addEdge(it) }

        return graph

    }
}