public class Stack {
    private class Node {
        MedicalRecord data;
        Node next;

        Node(MedicalRecord data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;

    public Stack() {
        top = null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void push(MedicalRecord record) {
        Node newNode = new Node(record);
        newNode.next = top;
        top = newNode;
    }

    public MedicalRecord pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty. No medical records to remove");
            return null;
        }
        MedicalRecord record = top.data;
        top = top.next;
        return record;
    }

    public MedicalRecord peek() {
        if (isEmpty()) {
            return null;
        }
        return top.data;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("No medical records in history");
            return;
        }
        Node current = top;
        while (current != null) {
            System.out.println(current.data.toString());
            current = current.next;
        }
    }
}