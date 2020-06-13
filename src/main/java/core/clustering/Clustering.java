package core.clustering;

import core.graph.Graph;
import core.graph.components.Edge;
import core.graph.components.Vertex;
import utils.Logs;

import java.util.*;

/**
 * Performs the clustering
 *
 * @author matthiasbrownmarie
 *
 */
public class Clustering {

    /**
     * Performs the clustering
     * Selects random centroids and convert them to clusters
     * Assign each vertex to its nearest cluster
     * Update the centroid of each cluster
     * @param graph
     * @param k
     */
    public static void cluster_2(Graph graph, int k) {
        //Select k centroids (vertex objects)
        Vertex[] centroids = selectCentroids(graph, k);

        //Create k clusters with those centroids
        Cluster[] clusters = convertToClusters(centroids);

    }

    /**
     * Assigns each vertex to its closest cluster
     * @param clusters
     * @param graph
     */
    public static void assignVertexToCluster(Cluster[] clusters, Graph graph) {
        Map<String, List<Edge>> vertexMap = graph.getVertexMap();

        for(Map.Entry<String, List<Edge>> entry: vertexMap.entrySet()) {

            for(Cluster cluster: clusters) {

                Vertex centroid = cluster.getCentroid();

                List<Edge> centroidEdges = vertexMap.get(centroid);
                String currentVertex = entry.getKey();


            }
        }
    }

    /**
     * Converts centroids into clusters
     * @param centroids
     * @return the clusters
     */
    public static Cluster[] convertToClusters(Vertex[] centroids) {
        Cluster[] clusters = new Cluster[centroids.length];

        for (int i = 0; i < centroids.length; i++) {
            Cluster cluster = new Cluster(centroids[i]);
            clusters[i] = cluster;
        }

        return clusters;
    }

    /**
     * Randomly select k centroids
     * @param graph
     * @param k
     * @return the centroids
     */
    public static Vertex[] selectCentroids(Graph graph, int k) {

        Map<String, List<Edge>> vertexMap = graph.getVertexMap();
        Random random = new Random();

        Vertex[] centroids = new Vertex[k];

        //Extract labels from the graph
        String[] keysArray = Arrays.asList(vertexMap.keySet().toArray()).toArray(new String[vertexMap.keySet().size()]);

        ArrayList<String> keys = new ArrayList<>(Arrays.asList(keysArray));

        for(int i = 0; i < k; i++) {

            int randomIndex = random.nextInt(keys.size());

            String randomVertexLabel = keys.get(randomIndex);

            keys.remove(randomIndex);

            List<Edge> edges = vertexMap.get(randomVertexLabel);

            Vertex centroid = null;

            if(edges != null) {
                centroid = new Vertex(randomVertexLabel, edges);
            } else {
                centroid = new Vertex(randomVertexLabel);
            }

            centroids[i] = centroid;
        }

        return centroids;
    }

    /**
     * First version of the algorithm
     * @param graph
     * @param k
     */
    public static void cluster(Graph graph, int k) {
        Map<String, List<Edge>> vertexMap = graph.getVertexMap();

        Random random = new Random();

        String[] centroids = new String[k];

        Map<String, String[]> centroidsMap = new HashMap<>();

        for(int i = 0; i < k; i++) {
            //Only selects a random vertex with a digit label (no letters)
            String randomVertexLabel = String.valueOf(random.nextInt(vertexMap.size()));

            centroids[i] = randomVertexLabel;
            centroidsMap.put(randomVertexLabel, new String[] {randomVertexLabel});
        }

        for(Map.Entry<String, List<Edge>> entry: vertexMap.entrySet()) {

            for(String centroid: centroids) {

                List<Edge> centroidEdges = vertexMap.get(centroid);
                String currentVertex = entry.getKey();

                if(vertexMap.get(currentVertex) == null) {
                    centroidsMap.put(currentVertex, new String[] {centroid});
                }

                for(Edge edge: centroidEdges) {
                    //Check if current vertex has edge with current centroid
                    if(currentVertex.contentEquals(edge.getDest().toString())) {

                        String[] currentVertexCluster = centroidsMap.get(currentVertex);

                        // Check if current vertex has a centroid assigned
                        if(currentVertexCluster == null) {
                            centroidsMap.put(currentVertex, new String[] {centroid});
                        } else {
                            //Get the weight of the centroid of the current vertex
                            double currentVertexClusterWeight = 0;

                            for(Edge e: vertexMap.get(currentVertexCluster[0])) {
                                if(e.getDest().toString().contentEquals(currentVertex)) {
                                    currentVertexClusterWeight = e.getWeight();
                                }
                            }

                            if(edge.getWeight() < currentVertexClusterWeight) {
                                centroidsMap.get(currentVertex)[0] = centroid;
                            }
                        }
                    }
                }
            }
        }

        for(Map.Entry<String, String[]> entry: centroidsMap.entrySet()) {
            Logs.debug(entry.getKey() + ":" + entry.getValue()[0]);
        }
    }

}
