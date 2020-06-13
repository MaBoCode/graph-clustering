package core.graph;

import core.graph.components.Edge;
import gui.Visualizer;
import utils.GraphTraversal;
import utils.exceptions.GraphException;

import java.util.*;

/**
 * Represents a graph
 * @author matt
 *
 */
public abstract class Graph {
    private Map<String, List<Edge>> vertexMap = new HashMap<String, List<Edge>>();

    private org.graphstream.graph.Graph layout;

    /**
     * Adds a vertex to the graph
     * @param label
     */
    public void addVertex(String label) {

        if(!vertexMap.containsKey(label))
            vertexMap.put(label, new LinkedList<Edge>());
    }

    /**
     * Adds an edge to the graph
     * @param src
     * @param dest
     * @param weight
     */
    public void addEdge(String src, String dest, double weight) {

        if(!vertexMap.containsKey(src)) {
            addVertex(src);
        }

        if (!vertexMap.containsKey(dest)) {
            addVertex(dest);
        }

        Edge edge = new Edge(src, dest, weight);

        if(!vertexMap.get(src).contains(edge)) {
            vertexMap.get(src).add(edge);
        }

        sortVertices();
    }

    /**
     * Adds an edge to the graph
     * @param edge
     */
    public void addEdge(Edge edge) {
        String src = edge.getSrc().toString();
        String dest = edge.getDest().toString();

        if(!vertexMap.containsKey(src)) {
            addVertex(src);
        }

        if (!vertexMap.containsKey(dest)) {
            addVertex(dest);
        }

        if(!vertexMap.get(src).contains(edge)) vertexMap.get(src).add(edge);

        sortVertices();
    }

    /**
     * Removes a vertex from the graph
     * @param label
     */
    public void removeVertex(String label) {
        if(vertexMap.containsKey(label)) {
            vertexMap.remove(label);

            for(List<Edge> edges: vertexMap.values()) {
                for(Edge edge: edges) {
                    if(edge.getDest().toString() == label) edges.remove(edge);
                }
            }
        }
    }

    /**
     * Removes an edge from the graph
     * @param src
     * @param dest
     */
    @SuppressWarnings("unlikely-arg-type")
    public void removeEdge(String src, String dest) {
        List<Edge> edges = vertexMap.get(src);

        if(edges != null) {
            for(Edge e: edges) {
                if(e.equals(dest)) {
                    edges.remove(e);
                    break;
                }
            }
        }
    }

    /**
     * Generates the layout in order to visualize the graph with GraphStream
     * @return the graph layout
     */
    public org.graphstream.graph.Graph generateLayout() {

        org.graphstream.graph.Graph graphLayout = Visualizer.getInstance().newGraph(this);

        setLayout(graphLayout);

        return graphLayout;
    }

    /**
     * Traverses the graph from the first vertex using the specified type
     * @param type BF(breadth first) or DF(depth first)
     * @throws InterruptedException
     * @throws GraphException
     */
    public void traverse(String type) throws InterruptedException, GraphException {
        ArrayList<String> result;

        switch (type) {
            case "BF":
                result = GraphTraversal.breadthFirstTraversal(this);
                generateLayout();
                Visualizer.getInstance().traverse(result, layout);
                break;
            case "DF":
                result = GraphTraversal.depthFirstTraversal(this);
                generateLayout();
                Visualizer.getInstance().traverse(result, layout);
                break;
            default:
                throw new GraphException("Unrecognized traversal algorithm");
        }
    }

    /**
     * Traverses the graph from src using the specified type
     * @param type BF(breadth first) or DF(depth first)
     * @param src the vertex to start the traversal
     * @throws GraphException
     * @throws InterruptedException
     */
    public void traverse(String type, String src) throws GraphException, InterruptedException {
        ArrayList<String> result;

        switch (type) {
            case "BF":
                result = GraphTraversal.breadthFirstTraversal(this, src);
                generateLayout();
                Visualizer.getInstance().traverse(result, layout);
                break;
            case "DF":
                result = GraphTraversal.depthFirstTraversal(this, src);
                generateLayout();
                Visualizer.getInstance().traverse(result, layout);
                break;
            default:
                throw new GraphException("Unrecognized traversal algorithm");
        }
    }

    /**
     * Clears the graph
     * @return
     */
    public boolean clear() {
        vertexMap.clear();

        return vertexMap.size() == 0;
    }

    /**
     *
     * @return
     */
    public Map<String, List<Edge>> getVertexMap() {
        return vertexMap;
    }

    /**
     *
     * @param vertexMap
     */
    public void setVertexMap(Map<String, List<Edge>> vertexMap) {
        this.vertexMap = vertexMap;
    }

    /**
     * Prints the graph to the console
     */
    public void show() {
        System.out.println(this.toString());
        //layout.display();
    }

    /**
     *
     * @param graphLayout
     */
    public void setLayout(org.graphstream.graph.Graph graphLayout) {
        layout = graphLayout;
    }

    /**
     *
     * @return
     */
    public org.graphstream.graph.Graph getLayout() {
        return layout;
    }

    /**
     *
     * @return the number of edges
     */
    public int edgesCount() {
        int count = 0;

        for(List<Edge> edges: vertexMap.values()) {
            count += edges.size();
        }

        return count;
    }

    /**
     *
     * @return
     */
    public Map<String, List<Edge>> sortVertices() {
        Map<String, List<Edge>> sortedVertexMap = new HashMap<String, List<Edge>>();

        vertexMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(elem -> sortedVertexMap.put(elem.getKey(), elem.getValue()));


        for(Map.Entry<String, List<Edge>> entry: sortedVertexMap.entrySet()) {
            Collections.sort(entry.getValue(), Collections.reverseOrder());
        }

        setVertexMap(sortedVertexMap);

        return sortedVertexMap;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        StringBuilder stringBuilder = new StringBuilder();

        for(Map.Entry<String, List<Edge>> entry: vertexMap.entrySet()) {
            stringBuilder.append(entry.getKey() + ": ");

            for(Edge e: entry.getValue()) {
                stringBuilder.append("(" + e.getDest().getLabel() + ", " + String.valueOf(e.getWeight()) + ") ");
            }

            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

}