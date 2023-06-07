package example.solution;

import example.model.entity.City;

import java.util.*;

public class Graph {
    private Map<Integer, List<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addNode(int nodeId) {
        adjacencyList.put(nodeId, new ArrayList<>());
    }

    public void addEdge(City sourceId, City destinationId, int flightId) {
        if(!adjacencyList.containsKey(sourceId.getId()))
            adjacencyList.put(sourceId.getId(), new ArrayList<>());
        if(!adjacencyList.containsKey(destinationId.getId()))
            adjacencyList.put(destinationId.getId(), new ArrayList<>());
        adjacencyList.get(sourceId.getId()).add(new Edge(destinationId, flightId));
        adjacencyList.get(destinationId.getId()).add(new Edge(sourceId, flightId));
    }

    public List<Edge> getEdges(int nodeId) {
        return adjacencyList.get(nodeId);
    }

    public Set<Integer> getNodes() {
        return adjacencyList.keySet();
    }

    @Override
    public String toString() {
        return "Graph{" +
                "adjacencyList=" + adjacencyList +
                '}';
    }
}
