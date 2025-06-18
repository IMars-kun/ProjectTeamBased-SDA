import java.time.LocalDateTime;

public class LinkedListDoctor {

    private Node<Doctor> head;

    public void addDoctor (Doctor doctor) {
        Node<Doctor> newNode = new Node<Doctor>(doctor);

        if (isEmpty()) {
            head = newNode;
        }
        else {
            Node<Doctor> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public boolean removeDoctor (String IDDoc) {
        if (isEmpty()) {
            return false;
        }

        if (head.data.getIDDoc().equals(IDDoc)) {
            head = head.next;
            return true;
        }
        
        Node<Doctor> current = head;
        while (current.next != null) {
            if ((current.next.data).getIDDoc().equals(IDDoc)) {
                current.next = current.next.next;
            }
            current = current.next;
        }
        return false;
    }

    public void allLoginDoctor() {
        if (isEmpty()) {
            System.out.println("No doctor in the list");
            return;
        }

        Node<Doctor> current = head;
        boolean found = false;
        int num = 1;
        System.out.println("=== List Logged In Doctors ===");
        while (current != null) {
            if (current.data.isLogin()) {
                System.out.println("[" + num + "] " + current.data.toString());
                found = true;
                num++;
            }
            current = current.next;
        }

        if(!found){
            System.out.println("Login Doctor is empty");
        }
    }

    public Doctor logDoctor(String IDDoc, boolean loginStatus, LocalDateTime loginTime, LocalDateTime logoutTime) {
        Node<Doctor> current = head;
        while (current != null) {
            if (current.data.getIDDoc().equals(IDDoc)) {
                current.data.setLogin(loginStatus);
                if (loginStatus) {
                    // Kalau login, update loginTime dan reset logoutTime
                    current.data.setLoginTime(loginTime);
                    current.data.setLogoutTime(null);
                } else {
                    // Kalau logout, jangan ubah loginTime, cuma set logoutTime
                    current.data.setLogoutTime(logoutTime);
                }
                return current.data;
            }
            current = current.next;
        }
        System.out.println("Doctor with ID " + IDDoc + " not found.");
        return null;
    }

    public Doctor findDoctorByID (String idDoc) {
        Node<Doctor> current = head;
        while (current != null) {
            if (current.data.getIDDoc().equals(idDoc)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public boolean isEmpty() {
        return head == null; 
    }

    public Node<Doctor> gethead() {
        return head;
    }
}
