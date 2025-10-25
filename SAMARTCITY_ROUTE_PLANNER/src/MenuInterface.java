import java.util.*;

public class MenuInterface {
    private CityGraph cityGraph;
    private Scanner scanner;

    public MenuInterface() {
        cityGraph = new CityGraph();
        scanner = new Scanner(System.in);
        initializeSampleData();
    }

    private void initializeSampleData() {
        cityGraph.addLocation("Central Station", "CS001");
        cityGraph.addLocation("City Mall", "CM002");
        cityGraph.addLocation("University", "UN003");
        cityGraph.addLocation("Hospital", "HP004");
        cityGraph.addLocation("Park", "PK005");

        cityGraph.addRoad("CS001", "CM002", 5);
        cityGraph.addRoad("CM002", "UN003", 3);
        cityGraph.addRoad("UN003", "HP004", 4);
        cityGraph.addRoad("HP004", "PK005", 2);
        cityGraph.addRoad("PK005", "CS001", 6);
    }

    public void start() {
        System.out.println("Welcome to Smart City Route Planner!");
        while (true) {
            displayMenu();
            int choice = getValidatedInt("Enter your choice (1–7): ", 1, 7);
            switch (choice) {
                case 1 -> addLocation();
                case 2 -> removeLocation();
                case 3 -> addRoad();
                case 4 -> removeRoad();
                case 5 -> cityGraph.displayAllConnections();
                case 6 -> cityGraph.displayAllLocationsFromTree();
                case 7 -> { System.out.println("Goodbye!"); return; }
            }
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private void displayMenu() {
        System.out.println("\n--- Smart City Route Planner ---");
        System.out.println("1. Add a new location");
        System.out.println("2. Remove a location");
        System.out.println("3. Add a road between locations");
        System.out.println("4. Remove a road between locations");
        System.out.println("5. Display all connections");
        System.out.println("6. Display all locations (AVL Tree)");
        System.out.println("7. Exit");
    }

    private int getValidatedInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int n = scanner.nextInt();
                scanner.nextLine();
                if (n >= min && n <= max) return n;
                System.out.println("Enter a number between " + min + " and " + max + ".");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Try again.");
                scanner.nextLine();
            }
        }
    }

    private String getValidatedString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Input cannot be empty!");
        }
    }

    private void addLocation() {
        System.out.println("\n--- Add New Location ---");
        String name = getValidatedString("Enter location name: ");
        String id = getValidatedString("Enter location ID: ");
        if (cityGraph.addLocation(name, id))
            System.out.println("Location added successfully!");
        else
            System.out.println("Location already exists!");
    }

    private void removeLocation() {
        System.out.println("\n--- Remove Location ---");
        cityGraph.displayAllLocationsFromTree();
        String id = getValidatedString("Enter ID to remove: ");
        if (cityGraph.removeLocation(id))
            System.out.println("Location removed!");
        else
            System.out.println("Location not found!");
    }

    private void addRoad() {
        System.out.println("\n--- Add Road ---");
        cityGraph.displayAllLocationsFromTree();
        String s = getValidatedString("Enter source ID: ");
        String d = getValidatedString("Enter destination ID: ");
        int dist = getValidatedInt("Enter distance (km): ", 1, 1000);
        if (cityGraph.addRoad(s, d, dist))
            System.out.println("Road added successfully!");
        else
            System.out.println("Failed: check IDs or road already exists.");
    }

    private void removeRoad() {
        System.out.println("\n--- Remove Road ---");
        String s = getValidatedString("Enter source ID: ");
        String d = getValidatedString("Enter destination ID: ");
        if (cityGraph.removeRoad(s, d))
            System.out.println("Road removed successfully!");
        else
            System.out.println("Road not found!");
    }
}