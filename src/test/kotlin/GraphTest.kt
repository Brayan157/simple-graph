import mock.PurposedGraphs.generateFirstPurposedGraph
import models.Edge
import models.Graph
import models.Vertex
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode.DOWN
import org.assertj.core.api.Assertions.assertThat

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
    inner class AdjacencyMatrixTests {

        @Test
        fun `should return an adjacency matrix of a graph`() {
            val v1 = Vertex("v1")
            val v2 = Vertex("v2")
            val e1 = Edge(Pair(v1, v2))

            val graph = Graph()
            graph.addVertex(v1)
            graph.addVertex(v2)
            graph.addEdge(e1)

            val adjacencyMatrix = graph.matrizAdjacency()

            assertAll (
                { assertThat(adjacencyMatrix.size).isEqualTo(graph.getVertices().size) },
                { assertThat(adjacencyMatrix.first().size).isEqualTo(graph.getVertices().size) },
                { assertThat(adjacencyMatrix[0][0]).isEqualTo(0) },
                { assertThat(adjacencyMatrix[1][1]).isEqualTo(0) },
                { assertThat(adjacencyMatrix[0][1]).isEqualTo(1) },
                { assertThat(adjacencyMatrix[1][0]).isEqualTo(1) }
            )
        }
    }

    @Nested
    inner class ExampleGraphsTest {

        @Test
        fun `should generate an adjacency matrix of first purposed graph`() {
            val graph = generateFirstPurposedGraph()
            val adjacencyMatrix = graph.matrizAdjacency()

            assertAll(
                { assertThat(graph.getVertices().size).isEqualTo(4) },
                {
                    assertThat(graph.getAverageDegree())
                        .isEqualTo(BigDecimal(7).divide(BigDecimal(2), 2, DOWN))
                },
                { assertThat(adjacencyMatrix.size).isEqualTo(graph.getVertices().size) },
                { assertThat(adjacencyMatrix.first().size).isEqualTo(graph.getVertices().size) },
                { assertThat(adjacencyMatrix[0][0]).isEqualTo(1) },
                { assertThat(adjacencyMatrix[1][1]).isEqualTo(0) },
                { assertThat(adjacencyMatrix[2][2]).isEqualTo(0) },
                { assertThat(adjacencyMatrix[3][3]).isEqualTo(0) },
                { assertThat(adjacencyMatrix[0][1]).isEqualTo(2) },
                { assertThat(adjacencyMatrix[0][2]).isEqualTo(0) },
                { assertThat(adjacencyMatrix[0][3]).isEqualTo(1) },
                { assertThat(adjacencyMatrix[1][0]).isEqualTo(2) },
                { assertThat(adjacencyMatrix[1][1]).isEqualTo(0) },
                { assertThat(adjacencyMatrix[1][2]).isEqualTo(1) },
                { assertThat(adjacencyMatrix[1][3]).isEqualTo(0) },
                { assertThat(adjacencyMatrix[2][0]).isEqualTo(0) },
                { assertThat(adjacencyMatrix[2][1]).isEqualTo(1) },
                { assertThat(adjacencyMatrix[2][2]).isEqualTo(0) },
                { assertThat(adjacencyMatrix[2][3]).isEqualTo(2) },
                { assertThat(adjacencyMatrix[3][0]).isEqualTo(1) },
                { assertThat(adjacencyMatrix[3][1]).isEqualTo(0) },
                { assertThat(adjacencyMatrix[3][2]).isEqualTo(2) },
                { assertThat(adjacencyMatrix[3][3]).isEqualTo(0) }
            )
        }
    }
}