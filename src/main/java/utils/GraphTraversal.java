package utils;

import core.graph.Graph;
import core.graph.components.Edge;

import java.util.*;

public class GraphTraversal {

    /**
     * Performs a breadth first traversal
     * @param g
     * @param src
     * @return
     */
    public static ArrayList<String> breadthFirstTraversal(Graph g, String src) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> result = new ArrayList<String>();

        Map<String, List<Edge>> vertexMap = g.getVertexMap();

        Logs.debug(vertexMap.toString());

        Map<String, Boolean> visited = new HashMap<String, Boolean>();

        for(String label: vertexMap.keySet()) visited.put(label, false);

        Map<String, Integer> level = new HashMap<String, Integer>();
        for(String label: vertexMap.keySet()) level.put(label, null);

        Queue<String> queue = new LinkedList<String>();

        queue.add(src);
        visited.put(src, true);

        level.put(src, 0);

        while(!queue.isEmpty()) {
            String label = queue.poll();

            stringBuilder.append(label + " ");
            result.add(label);

            for (Edge edge : vertexMap.get(label)) {
                String nextLabel = edge.getDest().toString();

                if (!visited.get(nextLabel)) {
                    queue.add(nextLabel);
                    visited.put(nextLabel, true);
                    level.put(nextLabel, level.get(label) + 1);
                }
            }
        }

        Logs.debug(level.toString());

        System.out.println(stringBuilder.toString());

        return result;
    }

    /**
     * Performs a depth first traversal
     * @param g
     * @param label
     * @return
     */
    public static ArrayList<String> depthFirstTraversal(Graph g, String label) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> result = new ArrayList<String>();

        Map<String, List<Edge>> vertexMap = g.getVertexMap();

        Map<String, Boolean> visited = new HashMap<>();

        for(String l: vertexMap.keySet()) visited.put(l, false);

        Stack<String> stack = new Stack<>();

        stack.push(label);

        while(!stack.empty()) {

            label = stack.pop();

            if(!visited.get(label)) {
                stringBuilder.append(label + " ");
                visited.put(label, true);
                Logs.debug(label + " visited");
                result.add(label);
            }

            Iterator<Edge> edgesIterator = vertexMap.get(label).iterator();

            Logs.debug(vertexMap.get(label).toString());

            while(edgesIterator.hasNext()) {
                String nextLabel = edgesIterator.next().getDest().toString();

                if(!visited.get(nextLabel)) {
                    stack.push(nextLabel);
                }
            }
        }

        System.out.println(stringBuilder.toString());

        return result;
    }

    /**
     *
     * @param g
     * @return
     */
    public static ArrayList<String> breadthFirstTraversal(Graph g) {
        return breadthFirstTraversal(g, g.getVertexMap().keySet().stream().findFirst().get());
    }

    /**
     *
     * @param g
     * @return
     */
    public static ArrayList<String> depthFirstTraversal(Graph g) {
        return depthFirstTraversal(g, g.getVertexMap().keySet().stream().findFirst().get());
    }

}
