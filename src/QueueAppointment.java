public class QueueAppointment {

    private Node<Appointment> front, rear;

    public boolean isEmpty() {
        return front == null;
    }

    public Node<Appointment> getFront() {
        return front;
    }

    public Node<Appointment> getRear() {
        return rear;
    }

    //ENQUEUE
    public void enqueue (Appointment a) {
        Node<Appointment> newNode = new Node<>(a);
        if (isEmpty()) {
            front = rear = newNode;
            return;
        }

        if (front.data.getTime().compareTo(a.getTime()) > 0) {
            newNode.next = front;
            front = newNode;
            return;
        }

        Node<Appointment> current = front;
        while (current.next != null && current.next.data.getTime().compareTo(a.getTime()) <= 0) {
            current = current.next;
        }
        newNode.next = current.next;
        current.next = newNode;

        if (newNode.next == null) {
            rear = newNode;
        }
    }

    //DEQUEUE
    public void processNextAppointment() {
        if (isEmpty()) {
            System.out.println("There is no appointment to process");
            return;
        }

        Node<Appointment> current = front;

        front = front.next;
        if (front == null) rear = null; 
        
        }

    //VIEW ALL APPOINTMENTS
    public void displayUpcomingAppointments() {
        if (isEmpty()) {
            return;
        }

        Node<Appointment> current = front;
        int num = 1;
        while (current != null) {
            Appointment a = current.data;
            System.out.printf("%-5s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s\n",
            num++, a.getAppointmentID(), 
            a.getPatient().getID(), a.getPatient().getName(),
            a.getDoctor().getIDDoc(), a.getDoctor().getNameDoc(),
            a.getTime());
            
            current = current.next;
        }
    }
}
