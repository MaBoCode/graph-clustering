package utils;

import core.graph.Graph;
import core.graph.GraphFactory;
import core.graph.components.Edge;
import utils.exceptions.GraphException;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Used to generate a specific type of graph
 * Types available:
 * <ul>
 * 	<li>A Complete graph contains the maximum number of edges</li>
 * 	<li>Simple, using the number of edges or the probability that an edge exists.</li>
 * 	<li>Bipartite</li>
 * 	<li>Path</li>
 * 	<li>Binary Tree</li>
 * 	<li>Cycle</li>
 * 	<li>Eurelian Circle</li>
 * 	<li>Eurelian Path</li>
 * 	<li>Star</li>
 * 	<li>Wheel</li>
 * 	<li>Regular</li>
 *
 * @author matthiasbrownmarie
 *
 */
public class GraphGenerator {

    /**
     * Generates a random simple graph containing verticesCount vertices and edgesCount edges
     * @param verticesCount
     * @param edgesCount
     * @return a random simple graph
     * @throws GraphException
     */
    public static Graph simple(int verticesCount, int edgesCount) throws GraphException {

        //Check edges count
        if(edgesCount > (long) verticesCount*(verticesCount - 1)/2) throw new GraphException("Too many edges");

        if(edgesCount < 0) throw new GraphException("Too few edges");

        Random random = new Random();

        Graph graph = getRandomAbstractGraph();


        ArrayList<Edge> edges = new ArrayList<>();


        while(edges.size() < edgesCount) {
            String srcLabel = String.valueOf(random.nextInt(verticesCount));
            String destLabel = String.valueOf(random.nextInt(verticesCount));
            double weight = getRandomWeight();

            Edge edge = new Edge(srcLabel, destLabel, weight);

            if(!srcLabel.contentEquals(destLabel) && !edges.contains(edge)) {
                edges.add(edge);
                graph.addEdge(edge);
            }
        }

        return graph;
    }

    /**
     * Generates a random simple graph with verticesCount vertices and with edges between any two vertices of probability p
     * @param verticesCount
     * @param p the probability of choosing an edge
     * @return a random simple graph
     * @throws GraphException
     */
    public static Graph simple(int verticesCount, double p) throws GraphException {
        checkProbability(p);

        Graph graph = getRandomAbstractGraph();

        for(int srcId = 0; srcId < verticesCount; srcId++) {
            for(int destId = srcId + 1; destId < verticesCount; destId++) {
                //Bernoulli probability: P(U < p) = p
                boolean result = bernoulli(p);

                if(result) {
                    String srcLabel = String.valueOf(srcId);
                    String destLabel = String.valueOf(destId);
                    double weight = getRandomWeight();

                    graph.addEdge(srcLabel, destLabel, weight);
                }
            }
        }

        return graph;

    }

    /**
     * Generates a complete graph of verticesCount vertices
     * @param verticesCount
     * @return the complete graph
     * @throws GraphException
     */
    public static Graph complete(int verticesCount) throws GraphException {
        return simple(verticesCount, 1.0);
    }

    /**
     * Generates a random bipartite graph
     * @param vCount1 the number of vertices in one partition
     * @param vCount2 the number of vertices in the other partition
     * @param eCount the number of edges
     * @return the random bipartite graph
     * @throws GraphException
     */
    public static Graph bipartite(int vCount1, int vCount2, int eCount) throws GraphException {

        if(eCount > (long) vCount1*vCount2) throw new GraphException("Too many edges");

        if(eCount < 0) throw new GraphException("Too few edges");

        Random random = new Random();

        Graph graph = getRandomAbstractGraph();

        ArrayList<String> vertices = getVertices(vCount1 + vCount2);

        ArrayList<Edge> edges = new ArrayList<>();

        while(graph.edgesCount() < eCount) {
            int i = random.nextInt(vCount1);
            int j = vCount1 + random.nextInt(vCount2);
            double weight = getRandomWeight();

            Edge edge = new Edge(vertices.get(i), vertices.get(j), weight);

            if(!edges.contains(edge)) {
                edges.add(edge);
                graph.addEdge(edge);
            }
        }

        return graph;

    }

    /**
     * Returns a complete bipartite graph
     * @param vCount1
     * @param vCount2
     * @return the complete bipartite graph
     * @throws GraphException
     */
    public static Graph completeBipartite(int vCount1, int vCount2) throws GraphException {
        return bipartite(vCount1, vCount2, vCount1*vCount2);
    }

    /**
     * Generates a random bipartite graph with each edge of probability p
     * @param vCount1
     * @param vCount2
     * @param p the probability that the graph contains an edge
     * @return
     * @throws GraphException
     */
    public static Graph bipartite(int vCount1, int vCount2, double p) throws GraphException {
        checkProbability(p);

        Graph graph = getRandomAbstractGraph();

        ArrayList<String> vertices = getVertices(vCount1 + vCount2);

        for(int i = 0; i < vCount1; i++) {
            for(int j = 0; j < vCount2; j++) {
                boolean result = bernoulli(p);

                if(result) {
                    String srcLabel = vertices.get(i);
                    String destLabel = vertices.get(vCount1 + j);
                    double weight = getRandomWeight();

                    graph.addEdge(srcLabel, destLabel, weight);
                }
            }
        }

        return graph;
    }

    /**
     * Generates a graph path
     * @param vCount
     * @return a graph path
     * @throws GraphException
     */
    public static Graph path(int vCount) throws GraphException {

        Graph graph = new GraphFactory().createGraph(GraphFactory.GraphTypes.UNDIRECTED);

        ArrayList<String> vertices = getVertices(vCount);

        for(int i = 0; i < vCount - 1; i++) {
            String srcLabel = vertices.get(i);
            String destLabel = vertices.get(i+1);
            double weight = getRandomWeight();

            graph.addEdge(srcLabel, destLabel, weight);
        }

        return graph;
    }

    /**
     * Generates a complete binary tree graph
     * @param vCount
     * @return a complete binary tree graph
     * @throws GraphException
     */
    public static Graph binaryTree(int vCount) throws GraphException {

        Graph graph = new GraphFactory().createGraph(GraphFactory.GraphTypes.UNDIRECTED);

        ArrayList<String> vertices = getVertices(vCount);

        for(int i = 1; i < vCount; i++) {
            String srcLabel = vertices.get(i);
            String destLabel = vertices.get((i-1)/2);
            double weight = getRandomWeight();

            graph.addEdge(srcLabel, destLabel, weight);
        }

        return graph;
    }

    /**
     * Generates a cycle graph
     * @param vCount
     * @return a cycle graph
     * @throws GraphException
     */
    public static Graph cycle(int vCount) throws GraphException {
        Graph graph = getRandomAbstractGraph();

        ArrayList<String> vertices = getVertices(vCount);

        for(int i = 0; i < vCount - 1; i++) {
            String srcLabel = vertices.get(i);
            String destLabel = vertices.get(i+1);
            double weight = getRandomWeight();

            graph.addEdge(srcLabel, destLabel, weight);
        }

        // Connects first and last vertex
        graph.addEdge(vertices.get(vCount-1), vertices.get(0), getRandomWeight());

        return graph;
    }

    /**
     * Generates an Eurelian cycle graph
     * @param vCount
     * @param eCount
     * @return a Eurelian cycle graph
     * @throws GraphException if edges or vertices are <= 0
     */
    public static Graph eulerianCycle(int vCount, int eCount) throws GraphException {
        if(eCount <= 0 ) throw new GraphException("Eurelian cycle must have at least one edge");

        if(vCount <= 0) throw new GraphException("Eurelian cycle must have at least one vertex");

        Graph graph = new GraphFactory().createGraph(GraphFactory.GraphTypes.UNDIRECTED);

        ArrayList<String> vertices = new ArrayList<String>();

        Random random = new Random();

        for(int i = 0; i < eCount; i++) {
            vertices.add(String.valueOf(random.nextInt(vCount)));
        }

        for(int i = 0; i < eCount - 1; i++) {
            String srcLabel = vertices.get(i);
            String destLabel = vertices.get(i+1);
            double weight = getRandomWeight();

            graph.addEdge(srcLabel, destLabel, weight);
        }

        graph.addEdge(vertices.get(eCount-1), vertices.get(0), getRandomWeight());

        return graph;
    }

    /**
     * Generates an Eurelian path graph
     * @param vCount
     * @param eCount
     * @return an Eurelian path graph
     * @throws GraphException if edges < 0 or vertices <=0
     */
    public static Graph eulerianPath(int vCount, int eCount) throws GraphException {

        if(eCount < 0) throw new GraphException("Edges count must be positive");

        if(vCount <= 0) throw new GraphException("Eurelian path must have at least one vertex");

        Graph graph = new GraphFactory().createGraph(GraphFactory.GraphTypes.UNDIRECTED);

        ArrayList<String> vertices = new ArrayList<String>();

        Random random = new Random();

        for(int i = 0; i < eCount + 1; i++) {
            vertices.add(String.valueOf(random.nextInt(vCount)));
        }

        for(int i = 0; i < eCount; i++) {
            String srcLabel = vertices.get(i);
            String destLabel = vertices.get(i+1);
            double weight = getRandomWeight();

            graph.addEdge(srcLabel, destLabel, weight);
        }

        return graph;
    }

    /**
     * Generates a wheel graph
     * @param vCount
     * @return a wheel graph
     * @throws GraphException if vertices <= 1
     */
    public static Graph wheel(int vCount) throws GraphException {
        if(vCount <= 1) throw new GraphException("Wheel graph must have at least 2 vertices");

        Graph graph = new GraphFactory().createGraph(GraphFactory.GraphTypes.UNDIRECTED);

        ArrayList<String> vertices = getVertices(vCount);

        // Creates a cycle
        for(int i = 1; i < vCount - 1; i++) {
            String srcLabel = vertices.get(i);
            String destLabel = vertices.get(i+1);
            double weight = getRandomWeight();

            graph.addEdge(srcLabel, destLabel, weight);
        }

        graph.addEdge(vertices.get(vCount-1), vertices.get(1), getRandomWeight());

        //Links first vertex to the other ones
        for(int i = 1; i < vCount; i++) {
            String srcLabel = vertices.get(0);
            String destLabel = vertices.get(i);
            double weight = getRandomWeight();

            graph.addEdge(srcLabel, destLabel, weight);
        }

        return graph;
    }

    /**
     * Generates a star graph
     * @param vCount
     * @return a star graph
     * @throws GraphException vertices <= 0
     */
    public static Graph star(int vCount) throws GraphException {
        if(vCount <= 0) throw new GraphException("Star graph must have at least one vertex");

        Graph graph = new GraphFactory().createGraph(GraphFactory.GraphTypes.UNDIRECTED);

        ArrayList<String> vertices = getVertices(vCount);

        for(int i = 1; i < vCount; i++) {
            String srcLabel = vertices.get(0);
            String destLabel = vertices.get(i);
            double weight = getRandomWeight();

            graph.addEdge(srcLabel, destLabel, weight);
        }

        return graph;
    }

    /**
     * Generates a uniformly random k-regular graph
     * @param vCount
     * @param k
     * @return a uniformly random k-regular graph
     * @throws GraphException if vCount * k is not even
     */
    public static Graph regular(int vCount, int k) throws GraphException {
        if(vCount*k % 2 != 0) throw new GraphException("Number of vertices * k must be even");

        Graph graph = new GraphFactory().createGraph(GraphFactory.GraphTypes.UNDIRECTED);

        String[] verticesArray = new String[vCount * k];

        for(int i = 0; i < vCount; i++) {
            for(int j = 0; j < k; j++) {
                verticesArray[i + vCount*j] = String.valueOf(i);
            }
        }

        List<String> vertices = Arrays.asList(verticesArray);

        Collections.shuffle(vertices);

        for(int i = 0; i < vCount*k/2; i++) {
            graph.addEdge(vertices.get(2*i), vertices.get(2*i + 1), getRandomWeight());
        }

        return graph;
    }

    /**
     * Generates a random graph
     * @return a directed or undirected graph object
     * @throws GraphException
     */
    public static Graph getRandomAbstractGraph() throws GraphException {
        Random random = new Random();

        GraphFactory factory = new GraphFactory();

        GraphFactory.GraphTypes[] graphTypes = GraphFactory.GraphTypes.values();

        GraphFactory.GraphTypes graphType = graphTypes[random.nextInt(graphTypes.length)];

        return factory.createGraph(graphType);
    }

    /**
     * Creates a new list of vertices and shuffles it
     * @param vCount
     * @return the list of vertices
     */
    public static ArrayList<String> getVertices(int vCount) {
        ArrayList<String> vertices = new ArrayList<>();

        for(int i = 0; i < vCount; i++) {
            vertices.add(String.valueOf(i));
        }

        Collections.shuffle(vertices);

        return vertices;
    }

    /**
     * Generates a random weight between 0 and 100
     * @return the weight
     */
    public static double getRandomWeight() {
        Random random = new Random();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);

        return Double.parseDouble(decimalFormat.format(random.nextDouble()));
    }

    /**
     * Checks if the given probability is in bound
     * @param p
     * @throws GraphException
     */
    public static void checkProbability(double p) throws GraphException {
        if(p < 0.0 || p > 1.0) throw new GraphException("Probability must be between 0 and 1");
    }

    /**
     * Computes Bernoulli probability: P(U < p) = p
     * @param p
     * @return true or false
     */
    public static boolean bernoulli(double p) {
        return new Random().nextDouble() < p;
    }
}

