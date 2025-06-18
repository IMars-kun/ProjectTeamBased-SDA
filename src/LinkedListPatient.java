public class LinkedListPatient {
    private Node<Patient> head;

    public void addPatient(Patient patient) {
        Node<Patient> newNode = new Node<Patient>(patient);
        if (isEmpty()) {
            head = newNode;
        } else {
            Node<Patient> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public boolean removePatientByID(String targetID){
        if(isEmpty()){
            return false;
        }

        if((head.data).getID().equals(targetID)){
            head = head.next;
            return true;
        }

        Node<Patient> current = head;
        while (current.next != null){
            if ((current.next.data).getID().equals(targetID)){
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        } 
        return false;
    }

    public void findPatientByName(String targetString){
        if(isEmpty()){
            System.out.println("Patient list is empty");
            return;
        }
        Node<Patient> current = head;
        while (current != null){
            if((current.data).getName().contains(targetString)){
                System.out.println(current.data);
            }
            current = current.next;
        }

        return;
    }

    public void displayMedicalRecord(String patientID, BST binarySearch) {
        Patient patient = binarySearch.searchPatient(patientID);
        if (patient == null) {
            System.out.println("\nPatient with ID " + patientID + " not found.");
            return;
        }

        Stack medicalRecordStack = patient.getMedicalRecordStack();
        if (medicalRecordStack.isEmpty()) {
            System.out.println("\nNo medical records found for patient ID " + patientID);
            return;
        }
        System.out.println("\nMedical records for patient ID " + patientID + ":");
        medicalRecordStack.display();

    }

    public Node<Patient> getHead() {
        return head;
    }

    public boolean isEmpty() {
        return head == null;
    }

}
