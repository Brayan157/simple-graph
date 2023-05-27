import mock.PurposedGraphs.generateFirstPurposedGraph
import models.Edge
import models.Graph
import models.Vertex
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode.DOWN

class GraphTest {

    @Nested
    inner class VertexTests {
        @Test
        fun `should add a vertex to a graph`() {
            val graph = Graph()
            val vertex = Vertex(name = "vertex1")
            graph.addVertex(vertex)

            assertTrue(graph.getVertices().size == 1)
        }
    }
//    @Nested
//    inner class MatrixAdjacency{
//        @Test
////        fun ``
//    }
    @Nested
    inner class DegreeTest{
        @Test
        fun `should count degree of vertex in a graph`(){
            val graph = Graph()
            val vertexList = listOf(Vertex(name="vertex1"), Vertex(name="vertex2"), Vertex(name = "vertex3"))
            val edge = Edge(Pair(vertexList[0], vertexList[1]))

            vertexList.forEach {
                graph.addVertex(it)
            }

            graph.addEdge(edge)

            assertTrue(graph.getVertexDegree(vertexList[0]) == 1)
            assertTrue(graph.getVertexDegree(vertexList[1]) == 1)
            assertTrue(graph.getVertexDegree(vertexList[2]) == 0)
        }
        @Test
        fun `should calculate average degree of a graph`(){
            val graph = Graph()
            val vertexList = listOf(Vertex(name="vertex1"), Vertex(name="vertex2"), Vertex(name = "vertex3"))
            val edge = Edge(Pair(vertexList[0], vertexList[1]))

            vertexList.forEach {
                graph.addVertex(it)
            }

            graph.addEdge(edge)

            val mathContext = MathContext(2, DOWN)

            assertEquals(
                graph.getAverageDegree(),
                BigDecimal(0.66).round(mathContext)
            )
        }
    }

    @Nested
    inner class ExampleGraphsTest {

        @Test
        fun `should generate an adjacency matrix of first purposed graph`() {
            val graph = generateFirstPurposedGraph()

            assertEquals(graph.getVertices().size, 4)
            assertEquals(graph.getAverageDegree(), BigDecimal(7).divide(BigDecimal(2), 2, DOWN))
        }
    }
}