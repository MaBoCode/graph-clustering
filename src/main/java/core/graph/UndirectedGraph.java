package core.graph;

public class UndirectedGraph extends Graph {

    @Override
    public void addEdge(String src, String dest, double weight) {
        // TODO Auto-generated method stub
        super.addEdge(src, dest, weight);
        super.addEdge(dest, src, weight);
    }

    @Override
    public void removeEdge(String src, String dest) {
        // TODO Auto-generated method stub
        super.removeEdge(src, dest);
        super.removeEdge(dest, src);
    }
}
