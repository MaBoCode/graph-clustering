import core.graph.Graph;
import core.graph.GraphFactory;
import utils.Logs;
import utils.exceptions.GraphException;

public class Main {

    public static void main(String[] args) throws GraphException {

        Logs.debug("test");
        Graph graph = new GraphFactory().createGraph(GraphFactory.GraphTypes.UNDIRECTED);


        graph.addEdge("1", "3", 4);
        graph.addEdge("1", "4", 1);
        graph.addEdge("1", "5", 2);
        graph.addEdge("2", "4", 3);
        graph.addEdge("2", "6", 1);
        graph.addEdge("4", "6", 6);
        graph.addEdge("5", "6", 1);

        graph.show();

        graph.generateLayout().display();
    }
}
