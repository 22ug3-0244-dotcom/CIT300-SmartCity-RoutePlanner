import java.util.*;

public class CityGraph {
    private Map<String, List<Edge>> adjacencyList;
    private LocationAVLTree locationTree;

    public CityGraph() {
        this.adjacencyList = new HashMap<>();
        this.locationTree = new LocationAVLTree();
    }

    public static class Edge {
        String source;
        String destination;
        int distance;

        public Edge(String source, String destination, int distance) {
            this.source = source;
            this.destination = destination;
            this.distance = distance;
        }
    }

    public boolean addLocation(String name, String id) {
        if (adjacencyList.containsKey(id)) return false;
        Location location = new Location(name, id);
        adjacencyList.put(id, new ArrayList<>());
        locationTree.insert(location);
        return true;
    }

    public boolean removeLocation(String id) {
        if (!adjacencyList.containsKey(id)) return false;
        for (List<Edge> edges : adjacencyList.values())
            edges.removeIf(e -> e.source.equals(id) || e.destination.equals(id));
        adjacencyList.remove(id);
        locationTree.remove(id);
        return true;
    }

    public boolean addRoad(String sourceId, String destinationId, int distance) {
        if (!adjacencyList.containsKey(sourceId) || !adjacencyList.containsKey(destinationId))
            return false;

        for (Edge edge : adjacencyList.get(sourceId))
            if (edge.destination.equals(destinationId)) return false;

        adjacencyList.get(sourceId).add(new Edge(sourceId, destinationId, distance));
        adjacencyList.get(destinationId).add(new Edge(destinationId, sourceId, distance));
        return true;
    }

    public boolean removeRoad(String sourceId, String destinationId) {
        if (!adjacencyList.containsKey(sourceId) || !adjacencyList.containsKey(destinationId))
            return false;

        boolean removed1 = adjacencyList.get(sourceId).removeIf(e -> e.destination.equals(destinationId));
        boolean removed2 = adjacencyList.get(destinationId).removeIf(e -> e.destination.equals(sourceId));
        return removed1 || removed2;
    }

    public void displayAllConnections() {
        System.out.println("\n=== ALL CITY CONNECTIONS ===");
        if (adjacencyList.isEmpty()) {
            System.out.println("No locations available!");
            return;
        }
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        String start = adjacencyList.keySet().iterator().next();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            Location loc = locationTree.search(current);
            System.out.println("\n" + loc.name + " (" + current + ") connects to:");
            for (Edge edge : adjacencyList.get(current)) {
                Location dest = locationTree.search(edge.destination);
                System.out.println("  -> " + dest.name + " (" + edge.distance + "km)");
                if (visited.add(edge.destination)) queue.add(edge.destination);
            }
        }
        System.out.println("============================\n");
    }

    public void displayAllLocationsFromTree() { locationTree.displayInOrder(); }
    public boolean remove(String id) { return removeLocation(id); }
}