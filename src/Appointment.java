public class Appointment {
    String appointmentID;
    Patient patient;
    Doctor doctor;
    String time;

    // Constructor
    public Appointment(String appointmentID, Patient patient, Doctor doctor, String time) {
        this.appointmentID = appointmentID;
        this.patient = patient;
        this.doctor = doctor;
        this.time = time;
    }

    // Getter methods
    public String getAppointmentID() {
        return appointmentID;
    }
    public Patient getPatient() {
        return patient;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return  "Appointment ID\t: " + appointmentID + "\n" +
                "Patient ID\t: " + patient.getID() + "\n" +
                "Doctor ID\t: " + doctor.getIDDoc() + "\n" +
                "Time\t: " + time + "\n";
    }
}
