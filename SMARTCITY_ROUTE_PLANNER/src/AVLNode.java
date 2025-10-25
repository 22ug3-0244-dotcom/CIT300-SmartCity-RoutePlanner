public class AVLNode {
    Location location;
    AVLNode left, right;
    int height;

    public AVLNode(Location loc) {
        this.location = loc;
        this.height = 1;
    }
}