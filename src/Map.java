public class Map {
    private Mapnode head;

    public Map() {
        head = null;
    }

    public static class Mapnode {
        String DoctorID;
        QueueAppointment queue;
        Mapnode next;
        LinkedListDoctor doctorList;
        

        public Mapnode (String DoctorID, QueueAppointment queue) {
        this.DoctorID = DoctorID;
        this.queue = queue;
        this.next = null;
        }
    }

    public Mapnode getHead() {
        return head;
    }

    public QueueAppointment getQueueByDoctorID(String doctorID) {
        Mapnode current = head;
        while (current != null) {
            if (current.DoctorID.equals(doctorID)) {
                return current.queue;
            }
            current = current.next;
        }
        return null;
    }

    public void put (String DoctorID, Appointment appointment) {
        
        if (head == null ) {
            QueueAppointment queue = new QueueAppointment();
            queue.enqueue(appointment);
            head = new Mapnode(DoctorID, queue);
            return;
        }

        if (head.DoctorID.equals(DoctorID)) {
            head.queue.enqueue(appointment);
            return;
        }

        if (DoctorID.compareTo(head.DoctorID) < 0) {
            QueueAppointment queue = new QueueAppointment();
            queue.enqueue(appointment);
            Mapnode nwNode = new Mapnode(DoctorID, queue);
            nwNode.next = head;
            head = nwNode;
            return;
        }

        Mapnode current = head;
        while (current.next != null && current.next.DoctorID.compareTo(DoctorID) < 0) {
            current = current.next;
        }

        if (current.next != null && current.next.DoctorID.equals(DoctorID)) {
            current.next.queue.enqueue(appointment);
            return;
        }
        
        QueueAppointment queue = new QueueAppointment();
        queue.enqueue(appointment);
        Mapnode nwNode = new Mapnode(DoctorID, queue);
        nwNode.next = current.next;
        current.next = nwNode;
    }

    // Ambil appointment pertama dari dokter yang sedang login
    public Appointment getFirstAppointmentFromLoggedInDoctors(LinkedListDoctor doctorList) {
        Mapnode current = head;
        Appointment earliest = null;

        while (current != null) {
            if (!current.queue.isEmpty()) {
                Doctor doctor = doctorList.findDoctorByID(current.DoctorID);
                if (doctor != null && doctor.isLogin()) {
                    Appointment a = current.queue.getFront().data;
                    if (earliest == null || a.getTime().compareTo(earliest.getTime()) < 0) {
                        earliest = a;
                    }
                }
            }
            current = current.next;
        }
        return earliest;
    }

    // Ambil queue appointment dari dokter login yang punya appointment terdekat
    public QueueAppointment getQueueOfFirstAppointmentFromLoggedInDoctors(LinkedListDoctor doctorList) {
        Mapnode current = head;
        Appointment earliest = null;
        QueueAppointment resultQueue = null;

        while (current != null) {
            if (!current.queue.isEmpty()) {
                Doctor doctor = doctorList.findDoctorByID(current.DoctorID);
                if (doctor != null && doctor.isLogin()) {
                    Appointment a = current.queue.getFront().data;
                    if (earliest == null || a.getTime().compareTo(earliest.getTime()) < 0) {
                        earliest = a;
                        resultQueue = current.queue;
                    }
                }
            }
            current = current.next;
        }
        return resultQueue;
    }
}
