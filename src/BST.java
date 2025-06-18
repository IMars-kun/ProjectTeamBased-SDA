public class BST {
    
    class Tree{
        Patient data;
        Tree left, right;

        Tree (Patient data) {
            this.data = data;
        }
    }
    Tree root;

    public void insertPatient (Patient p) {
        root = insertRecursive (root, p);
    }

    private Tree insertRecursive (Tree node, Patient p) {
        if (node == null) {
            return new Tree (p);
        }
        if (p.getID().compareToIgnoreCase(node.data.getID()) < 0) {
            node.left = insertRecursive (node.left, p);
        }
        else if (p.getID().compareToIgnoreCase(node.data.getID()) > 0) {
            node.right = insertRecursive (node.right, p);
        }
        else {
            System.out.println("Patient ID already exist");
        }
        return node;
    }

    public void removePatient (String id) {
        root = removeRecursive(root, id);
    }

    private Tree removeRecursive (Tree node, String id) {
        if (node == null) {
            System.out.println("Patient ID not found");
            return null;
        }

        if (id.compareToIgnoreCase(node.data.getID()) < 0) {
            node.left = removeRecursive(node.left, id);
        }
        else if (id.compareToIgnoreCase(node.data.getID()) > 0) {
            node.right = removeRecursive(node.right, id);
        }
        else {
            if (node.left == null && node.right == null) {
                return null;
            }
            else if (node.left == null) {
                return node.right;
            }
            else if (node.right == null) {
                return node.left;
            }
        }
        return node;
    }

    public Patient searchPatient (String id) {
        Tree node = searchRecursive (root, id);
        if (node != null) {
            return node.data;
        }
        else {
            return null;
        }
    }

    private Tree searchRecursive (Tree node, String id) {
        if (node == null || node.data.getID().equals(id)) {
            return node;
        }
        if (id.compareToIgnoreCase(node.data.getID()) < 0) {
            return searchRecursive (node.left, id);
        }
        else {
            return searchRecursive (node.right, id);
        }
    }

    public void inOrderDisplay() {
        System.out.println("============================= PATIENT LIST BST ===============================");
        System.out.println("------------------------------------------------------------------------------");
        System.out.printf("| %-2s | %-8s | %-15s | %-4s | %-15s | %-15s |\n" ,"No", "ID", "Name", "Age", "Address", "Number Phone");
        System.out.println("------------------------------------------------------------------------------");
        inOrderRecursive (root, 1);
        System.out.println("------------------------------------------------------------------------------");
    }

    private void inOrderRecursive (Tree node, int num) {
        if (node != null) {
            inOrderRecursive (node.left, num);
            System.out.printf("| %-2s | %-8s | %-15s | %-4s | %-15s | %-15s |\n", num, node.data.getID(),
            node.data.getName(), node.data.getAge(), node.data.getAddress(), node.data.getPhone());
            num++;
            inOrderRecursive (node.right, num);
        }
    }
}
