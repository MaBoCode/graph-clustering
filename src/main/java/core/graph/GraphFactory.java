package core.graph;

import utils.exceptions.GraphException;

public class GraphFactory {

    public enum GraphTypes {
        DIRECTED,
        UNDIRECTED
    }

    public Graph createGraph(GraphTypes graphType) throws GraphException {
        switch (graphType) {
            case DIRECTED:
                return new DirectedGraph();
            case UNDIRECTED:
                return new UndirectedGraph();
            default:
                throw new GraphException("Unknown graph type.");
        }
    }
}
