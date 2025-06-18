import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FileHandler {
    public void readFromFile(String fileName, LinkedListPatient Linkedlist, BST bst){
        LinkedListPatient linkPatient = Linkedlist;
        BST binarySearch = bst;
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    int age = Integer.parseInt(parts[2].trim());
                    String address = parts[3].trim();
                    String phone = parts[4].trim();
                    
                    Patient patient = new Patient(id, name, age, address, phone);
                    linkPatient.addPatient(patient);
                    binarySearch.insertPatient(patient);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Error: Null value encountered in file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void writeToFile(String fileName, Patient patient) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String line = patient.getID() + " | " + patient.getName() + " | " + patient.getAge() + " | "
                    + patient.getAddress() + " | " + patient.getPhone();
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void writeToFileDoctor(String fileName, Doctor doctor) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
        String loginTimeStr = (doctor.getLoginTime() != null) ? doctor.getLoginTime().format(formatter) : "";
        String logoutTimeStr = (doctor.getLogoutTime() != null) ? doctor.getLogoutTime().format(formatter) : "";

        String line = doctor.isLogin() + " | " + doctor.getIDDoc() + " | " + doctor.getNameDoc() + " | " + 
            doctor.getSpecialty() + " | " + loginTimeStr + " | " + logoutTimeStr;

        writer.write(line);
        writer.newLine();
    } catch (IOException e) {
        System.out.println("Error writing to file: " + e.getMessage());
    }
}

    public void readFromFileDoctor(String fileName, LinkedListDoctor linkedList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    try {
                        boolean login = Boolean.parseBoolean(parts[0].trim());
                        String idDoc = parts[1].trim();
                        String nameDoc = parts[2].trim();
                        String specialty = parts[3].trim();

                        LocalDateTime loginTime = null;
                        LocalDateTime logoutTime = null;

                        if (!parts[4].trim().isEmpty()) {
                            loginTime = LocalDateTime.parse(parts[4].trim(), formatter);
                        }

                        if (!parts[5].trim().isEmpty()) {
                            logoutTime = LocalDateTime.parse(parts[5].trim(), formatter);
                        }

                        Doctor doctor = new Doctor(login, idDoc, nameDoc, specialty, loginTime, logoutTime);
                        linkedList.addDoctor(doctor);

                    } catch (NumberFormatException | DateTimeParseException e) {
                        System.out.println("Skipping invalid line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void overwriteDoctor(String fileName, Doctor updatedDoctor) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String loginTimeStr = updatedDoctor.getLoginTime() != null ? updatedDoctor.getLoginTime().format(formatter) : "";
        String logoutTimeStr = updatedDoctor.getLogoutTime() != null ? updatedDoctor.getLogoutTime().format(formatter) : "";
        
        String[] tempLines = new String[1000]; // Anggap maksimal 1000 baris
        int count = 0;

        // Baca file dan simpan ke array
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null && count < tempLines.length) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    String id = parts[1].trim();
                    if (id.equals(updatedDoctor.getIDDoc())) {
                        // Ganti baris lama dengan baris baru
                            tempLines[count++] = updatedDoctor.isLogin() + " | " +
                                                updatedDoctor.getIDDoc() + " | " +
                                                updatedDoctor.getNameDoc() + " | " +
                                                updatedDoctor.getSpecialty() + " | " +
                                                loginTimeStr + " | " +
                                                logoutTimeStr;
                        continue;
                    }
                }
                tempLines[count++] = line;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Tulis kembali isi array ke file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            for (int i = 0; i < count; i++) {
                writer.write(tempLines[i]);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Menyimpan rekam medis ke file (semua pasien dalam satu file)
    public void writeMedicalRecord(String filename, MedicalRecord record) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(record.getPatientID() + " | " +
                        record.getDoctorID() + " | " +
                        record.getDiagnosis() + " | " +
                        record.getTreatment() + " | " +
                        record.getPrescription() + " | " +
                        record.getDate());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Membaca rekam medis dan mengaitkannya dengan pasien berdasarkan ID
    public void loadMedicalRecords(String filename, LinkedListPatient patientList, BST bst) {
        BST binarySearch = bst;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    String patientId = parts[0].trim();
                    String doctorId = parts[1].trim();
                    String diagnosis = parts[2].trim();
                    String treatment = parts[3].trim();
                    String prescription = parts[4].trim();
                    String date = parts[5].trim();

                    Patient patient = binarySearch.searchPatient(patientId);
                    if (patient != null) {
                        MedicalRecord record = new MedicalRecord(patientId, doctorId, diagnosis, treatment, prescription, date);
                        patient.addRecord(record);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void writeToFileAppointments (String fileName, Map mapAppointment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Map.Mapnode current = mapAppointment.getHead();
            String lastPrefix = null;
            
            while (current != null) {
                Node<Appointment> appoint = current.queue.getFront();
                
                while (appoint != null) {
                Appointment app = appoint.data;
                String currPrefix = app.getDoctor().getIDDoc().substring(0, 1); //problem

                    if (lastPrefix != null && !lastPrefix.equals(currPrefix)) {
                        writer.newLine();
                    }
                    lastPrefix = currPrefix;
                
                String line = app.getAppointmentID() + " | " + app.getPatient().getID() + " | " +
                app.getDoctor().getIDDoc() + " | " + app.getTime();
                writer.write(line);
                writer.newLine();
                appoint = appoint.next;
                }
                current = current.next;
            }
        
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void readFromFileAppointments (String fileName, Map mapAppointment, BST patientList, LinkedListDoctor doctorList) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length != 4) {
                    continue;
                }
                    try {
                        String appointmentID = parts[0].trim();
                        String patientID = parts[1].trim();
                        String doctorID = parts[2].trim();
                        String time = parts[3].trim();

                        Patient patient = patientList.searchPatient(patientID);
                        Doctor doctor = doctorList.findDoctorByID(doctorID);

                        if (patient != null && doctor != null) {
                            Appointment appointments = new Appointment(appointmentID, patient, doctor, time);
                            mapAppointment.put(doctorID, appointments);
                        }
 
                    } catch (NumberFormatException | DateTimeParseException e) {
                        System.out.println("Skipping invalid line: " + line);
                    }
            }
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void loadAppointment(Map mapAppointment, QueueAppointment queAppointment) {
        Map.Mapnode current = mapAppointment.getHead();
        while (current != null) {
            Node<Appointment> node = current.queue.getFront();
            while (node != null) {
                queAppointment.enqueue(node.data);
                node = node.next;
            }
            current = current.next;
        }
    }
}
