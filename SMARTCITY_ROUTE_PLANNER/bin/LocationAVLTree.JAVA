public class LocationAVLTree {
    private AVLNode root;

    private int height(AVLNode n) { return n == null ? 0 : n.height; }
    private int balance(AVLNode n) { return n == null ? 0 : height(n.left) - height(n.right); }

    public void insert(Location loc) { root = insertRec(root, loc); }

    private AVLNode insertRec(AVLNode node, Location loc) {
        if (node == null) return new AVLNode(loc);
        if (loc.id.compareTo(node.location.id) < 0) node.left = insertRec(node.left, loc);
        else if (loc.id.compareTo(node.location.id) > 0) node.right = insertRec(node.right, loc);
        else return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int bal = balance(node);
        if (bal > 1 && loc.id.compareTo(node.left.location.id) < 0) return rotateRight(node);
        if (bal < -1 && loc.id.compareTo(node.right.location.id) > 0) return rotateLeft(node);
        if (bal > 1 && loc.id.compareTo(node.left.location.id) > 0) { node.left = rotateLeft(node.left); return rotateRight(node); }
        if (bal < -1 && loc.id.compareTo(node.right.location.id) < 0) { node.right = rotateRight(node.right); return rotateLeft(node); }
        return node;
    }

    public void remove(String id) { root = removeRec(root, id); }

    private AVLNode removeRec(AVLNode node, String id) {
        if (node == null) return null;
        if (id.compareTo(node.location.id) < 0) node.left = removeRec(node.left, id);
        else if (id.compareTo(node.location.id) > 0) node.right = removeRec(node.right, id);
        else {
            if (node.left == null || node.right == null)
                node = (node.left != null) ? node.left : node.right;
            else {
                AVLNode min = minValueNode(node.right);
                node.location = min.location;
                node.right = removeRec(node.right, min.location.id);
            }
        }
        if (node == null) return null;
        node.height = 1 + Math.max(height(node.left), height(node.right));
        int bal = balance(node);
        if (bal > 1 && balance(node.left) >= 0) return rotateRight(node);
        if (bal > 1 && balance(node.left) < 0) { node.left = rotateLeft(node.left); return rotateRight(node); }
        if (bal < -1 && balance(node.right) <= 0) return rotateLeft(node);
        if (bal < -1 && balance(node.right) > 0) { node.right = rotateRight(node.right); return rotateLeft(node); }
        return node;
    }

    private AVLNode minValueNode(AVLNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left, T2 = x.right;
        x.right = y; y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right, T2 = y.left;
        y.left = x; x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    public Location search(String id) { return searchRec(root, id); }
    private Location searchRec(AVLNode node, String id) {
        if (node == null) return null;
        if (id.equals(node.location.id)) return node.location;
        if (id.compareTo(node.location.id) < 0) return searchRec(node.left, id);
        return searchRec(node.right, id);
    }

    public void displayInOrder() {
        System.out.println("\nLocations (sorted by ID):");
        displayRec(root);
        System.out.println();
    }

    private void displayRec(AVLNode node) {
        if (node != null) {
            displayRec(node.left);
            System.out.println("  " + node.location);
            displayRec(node.right);
        }
    }
}