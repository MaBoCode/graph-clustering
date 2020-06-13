package core.clustering;

import core.graph.components.Vertex;

import java.util.ArrayList;

/**
 * Represents a cluster
 * A cluster contains a centroid (vertex object) and members (list of vertex objects)
 * @author matthiasbrownmarie
 *
 */
public class Cluster {

    private Vertex centroid;
    private ArrayList<Vertex> members;

    public Cluster() {
        // TODO Auto-generated constructor stub
    }

    public Cluster(Vertex v) {
        this.centroid = v;
    }

    public void setCentroid(Vertex centroid) {
        this.centroid = centroid;
    }

    public void setMembers(ArrayList<Vertex> members) {
        this.members = members;
    }

    public Vertex getCentroid() {
        return centroid;
    }

    public ArrayList<Vertex> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(centroid.getLabel()).append(": ");

        for(Vertex vertex: getMembers()) {
            stringBuilder.append(vertex.getLabel()).append(", ");
        }

        return stringBuilder.toString();
    }
}
