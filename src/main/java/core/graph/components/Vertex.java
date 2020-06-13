package core.graph.components;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a vertex with its label and its neighbors (i.e. its edges)
 * @author matthiasbrownmarie
 *
 */
public class Vertex {
    private String label;
    private List<Edge> edges;

    public Vertex(String label) {
        // TODO Auto-generated constructor stub
        this.label = label;
        this.edges = new ArrayList<>();
    }

    public Vertex(String label, List<Edge> edges) {
        this.label = label;
        this.edges = edges;
    }

    public void setNeighbors(List<Edge> edges) {
        this.edges = edges;
    }

    public String getLabel() {
        return label;
    }

    public List<Edge> getNeighbors() {
        return edges;
    }


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return label + ": " + edges.toString();
    }

}
