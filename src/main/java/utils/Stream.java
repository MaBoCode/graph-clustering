package utils;

import core.graph.DirectedGraph;
import core.graph.Graph;
import core.graph.UndirectedGraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Used to import/export a graph from/to a csv file
 * @author matt
 *
 */
public class Stream {
    /**
     * Retrieves a graph from file with pattern src, dest, weight
     * @param file
     * @return
     */
    public static Graph importGraph(String file, boolean directed) {
        BufferedReader bufferedReader = null;

        Graph graph = null;

        try {
            String line = "";
            bufferedReader = new BufferedReader(new FileReader(file));

            line = bufferedReader.readLine();

            graph = directed ? new DirectedGraph() : new UndirectedGraph();

            while(line != null){
                String[] graph_data = line.split(",");

                String srcString = graph_data[0];
                String destString = graph_data[1];
                double weight = Double.parseDouble(graph_data[2]);

                graph.addEdge(srcString, destString, weight);

                line = bufferedReader.readLine();

            }

            return graph;
        } catch (FileNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }

        return null;
    }
}
