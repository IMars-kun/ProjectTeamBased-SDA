public class MedicalRecord {
    String patientID;
    String doctorID;
    String diagnosis;
    String treatment;
    String prescription;
    String date;

    // Constructor
    public MedicalRecord(String patientID, String doctorID, 
    String diagnosis, String treatment, String prescription, String date) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.prescription = prescription;
        this.date = date;
    }

    // Getter methods
    public String getPatientID() { return patientID; }
    public String getDoctorID() { return doctorID; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
    public String getPrescription() { return prescription; }
    public String getDate() { return date; }


    @Override
    public String toString() {
            return"Doctor ID\t: " + doctorID + "\n" +
                "Diagnosis\t: " + diagnosis + "\n" +
                "Treatment\t: " + treatment + "\n" +
                "Prescription\t: " + prescription + "\n" +
                "Date\t: " + date + "\n";
    }

}
