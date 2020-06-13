package core.graph.components;

/**
 * Represents a graph edge which contains the source vertex, the destination vertex and the weight between them.
 * @author matthiasbrownmarie
 *
 */
public class Edge implements Comparable<Edge> {
    private Vertex src, dest;
    private double weight;

    public Edge(String srcLabel, String destLabel, double weight) {
        this.src = new Vertex(srcLabel);
        this.dest = new Vertex(destLabel);
        this.weight = weight;
    }

    public Edge(Vertex src, Vertex dest, double weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof Edge) {
            Edge edge = (Edge) obj;

            return edge.getSrc().toString().contentEquals(src.toString()) && edge.getDest().toString().contentEquals(dest.toString());
        } else if (obj instanceof String) {
            String destLabel = (String) obj;

            return destLabel.contentEquals(dest.toString());
        }

        return false;
    }

    public Vertex getSrc() {
        return src;
    }

    public Vertex getDest() {
        return dest;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "(" + src.toString() + ", " + dest.toString() + ", " + String.valueOf(weight) + ")";
    }

    @Override
    public int compareTo(Edge o) {
        // TODO Auto-generated method stub
        return this.getDest().toString().compareTo(o.getDest().toString());
    }

}
