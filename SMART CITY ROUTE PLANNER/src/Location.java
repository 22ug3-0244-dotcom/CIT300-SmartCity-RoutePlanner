public class Location {
    String name;
    String id;

    public Location(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() { return name + " (" + id + ")"; }
}