package gui;

import core.graph.DirectedGraph;
import core.graph.Graph;
import core.graph.components.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Visualizer {
    private static Visualizer instance;

    private Visualizer() {}

    public static synchronized Visualizer getInstance() {
        if (instance == null) {
            instance = new Visualizer();
        }

        return instance;
    }

    public org.graphstream.graph.Graph newGraph(Graph graph) {
        org.graphstream.graph.Graph graphLayout = new SingleGraph("graph");
        Map<String, List<Edge>> vertexMap = graph.getVertexMap();

        boolean directed = graph instanceof DirectedGraph;

        build(graphLayout, vertexMap, directed);

        setStyle(graphLayout);

        return graphLayout;
    }

    public void build(org.graphstream.graph.Graph graphLayout, Map<String, List<Edge>> vertexMap, boolean directed) {
        //Adding nodes
        for(String label: vertexMap.keySet()) {
            graphLayout.addNode(label);
        }

        //Adding edges
        for(List<Edge> edges: vertexMap.values()) {
            for(Edge edge: edges) {
                String srcId = edge.getSrc().getLabel();
                String destId = edge.getDest().getLabel();
                double weight = edge.getWeight();

                if(graphLayout.getNode(srcId) == null) graphLayout.addNode(srcId);

                if(graphLayout.getNode(destId) == null) graphLayout.addNode(destId);

                if(graphLayout.getEdge(srcId +  destId) == null && graphLayout.getEdge(destId + srcId) == null) {
                    org.graphstream.graph.Edge edgeLayout = graphLayout.addEdge(srcId + destId, srcId, destId, directed);

                    //Setting weight
                    edgeLayout.setAttribute("weight", weight);
                    edgeLayout.addAttribute("ui.label", weight);
                }
            }
        }
    }

    public void traverse(ArrayList<String> traversal, org.graphstream.graph.Graph graphLayout) throws InterruptedException {

        graphLayout.display();

        for(String label: traversal) {
            Thread.sleep(1500);
            graphLayout.getNode(label).addAttribute("ui.class", "selected");
        }
    }


    public void setStyle(org.graphstream.graph.Graph graphLayout) {
        String workDir = System.getProperty("user.dir") + "/src/main/resources/stylesheet";

        graphLayout.addAttribute("ui.stylesheet", "url(" + workDir + ")");

        for (Node node : graphLayout) {
            node.addAttribute("ui.label", node.getId());
        }
    }

}