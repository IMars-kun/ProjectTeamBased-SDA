import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class MenuFunction {
    LinkedListPatient patientList;
    LinkedListDoctor doctorList;
    QueueAppointment appointmentQueue;
    Scanner sc;
    FileHandler fileHandler;
    BST binarySearch;
    Map mapAppointment;

    public static String RESET = "\u001B[0m";
    public static String RED = "\u001B[31m";
    public static String GREEN = "\u001B[32m";

    public MenuFunction (LinkedListPatient patientList, LinkedListDoctor doctorList, QueueAppointment appointmentQueue, Scanner sc, BST binarySearch, Map mapAppointment) {
        this.patientList = patientList;
        this.doctorList = doctorList;
        this.appointmentQueue = appointmentQueue;
        this.sc = sc;
        this.fileHandler = new FileHandler();
        this.binarySearch = binarySearch;
        this.mapAppointment = mapAppointment;
    }

    public void addNewPatient() {
        System.out.println("====================================");
        System.out.println("           Patient Identity         ");
        System.out.println("====================================");
        System.out.print("ID\t: ");
        String id = sc.nextLine();
        if (id.isEmpty()) {
        System.out.println(RED + "ID cannot be empty" + RESET);
        return;
        }
        System.out.print("Name\t: ");
        String name = sc.nextLine();
        if (name.isEmpty()) {
        System.out.println(RED + "Name cannot be empty" + RESET);
        return; 
        }
        System.out.print("Age\t: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Address\t: ");
        String address = sc.nextLine();
        if (address.isEmpty()) {
        System.out.println(RED + "Address cannot be empty" + RESET);
        return;
        }
        System.out.print("Phone number: ");
        String numberPhone = sc.nextLine();
        if (numberPhone.isEmpty()) {
        System.out.println(RED + "Phone number cannot be empty" + RESET);
        return;
        }

        Patient newPatient = new Patient(id, name, age, address, numberPhone);
        patientList.addPatient(newPatient);
        fileHandler.writeToFile("ListPatient.txt", newPatient);
        binarySearch.insertPatient(newPatient);
        System.out.println(GREEN + "Successfully added patient" + RESET);
    }

    public void removePatientByID() {
        System.out.println("------------------------------------");
        System.out.print("Patient ID: ");
        String remove = sc.nextLine();
        if (patientList.removePatientByID(remove)) {
            System.out.println(GREEN + "Patient removed successfully." + RESET);
            binarySearch.removePatient(remove);
        } else {
            System.out.println(RED + "Patient ID not found." + RESET);
        }
    }

    public void searchPatientByName() {
        System.out.println("------------------------------------");
        System.out.print("Patient Name: ");
        String search = sc.nextLine();
        if (search == null) {
            System.out.println(RED + "Patient not found" + RESET);
        }
        patientList.findPatientByName(search);
    }

    public void displayAllPatients(LinkedListPatient patientList) {
        Node<Patient> current = patientList.getHead();
        if (current == null) {
            System.out.println(RED + "Patient list is empty" + RESET);
            return;
        }
        int num = 1;
        System.out.println("=============================== PATIENT LIST =================================");
        System.out.println("------------------------------------------------------------------------------");
        System.out.printf("| %-2s | %-8s | %-15s | %-4s | %-15s | %-15s |\n" ,"No", "ID", "Name", "Age", "Address", "Number Phone");
        System.out.println("------------------------------------------------------------------------------");

        while (current != null) {
            Patient p = current.data;
            System.out.printf("| %-2s | %-8s | %-15s | %-4s | %-15s | %-15s |\n", num++, p.getID(), p.getName(), p.getAge(), p.getAddress(), p.getPhone());
            current = current.next;
        }
        System.out.println("------------------------------------------------------------------------------");
    }

    public void displayMedicalRecords(String patientID){
        patientList.displayMedicalRecord(patientID, binarySearch);
    }

    public void registDoctor() {
        System.out.println("====================================");
        System.out.println("           Doctor Identity          ");
        System.out.println("====================================");
        System.out.print("Doctor ID\t: ");
        String IdDoc = sc.nextLine();
        System.out.print("Doctor Name\t: ");
        String name = sc.nextLine();
        System.out.print("Specialist\t: ");
        String specialty = sc.nextLine();
        
        boolean login = false; // Default value for login
        LocalDateTime loginTime = null;
        LocalDateTime logoutTime = null;

        Doctor doctor = new Doctor(login, IdDoc, name, specialty, loginTime, logoutTime);
        doctorList.addDoctor(doctor);
        fileHandler.writeToFileDoctor("ListDoctor.txt", doctor);
        System.out.println(GREEN + "Successfully registered doctor with ID: " + IdDoc + RESET);
    }

    public void doctorLogin() {
        System.out.println("------------------------------------");
        System.out.print("Doctor ID: ");
        String id = sc.nextLine();
        LocalDateTime loginTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedLoginTime = loginTime.format(formatter);
        
        Doctor doctor = doctorList.findDoctorByID(id);
        if (doctor == null) {
            System.out.println(RED + "Doctor with ID : " + id + " not found. Login failed." + RESET);
            return;
        }
        if (doctor.isLogin()) {
            System.out.println(RED + "Doctor with ID : " + id + " is already logged in."+ RESET);
            return;
        }
        doctorList.logDoctor(id, true, loginTime, null);
        fileHandler.overwriteDoctor("ListDoctor.txt", doctor);
        System.out.println(GREEN + "Doctor with name : " + doctor.getNameDoc() + " has logged in at " + formattedLoginTime + RESET);
    }

    public void doctorLogout() {
        System.out.println("------------------------------------");
        System.out.print("Doctor ID: ");
        String id = sc.nextLine();
        if(id.isEmpty()) {
            System.out.println(RED + "Doctor ID cannot be empty." + RESET);
            return;
        }
        LocalDateTime logoutTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedLogoutTime = logoutTime.format(formatter);
        
        Doctor doctor = doctorList.findDoctorByID(id);
        if (doctor == null) {
            System.out.println(RED + "Doctor with ID : " + id + " not found. Logout failed." + RESET);
            return;
        }
        if (!doctor.isLogin()) {
            System.out.println(RED + "Doctor with ID : " + id + " is not logged in." + RESET);
            return;
        }
        doctorList.logDoctor(id, false, doctor.getLoginTime(), logoutTime);
        fileHandler.overwriteDoctor("ListDoctor.txt", doctor);
        System.out.println(GREEN + "Doctor with name : " + doctor.getNameDoc() + " has logged out at " + formattedLogoutTime + RESET);
    }

    public void viewAllDoctor(LinkedListDoctor doctorList) {
        final String RESET = "\u001B[0m";
        final String RED = "\u001B[31m";
        final String GREEN = "\u001B[32m";

        Node<Doctor> current = doctorList.gethead();
        if (current == null) {
            System.out.println(RED + "No doctor in the list" + RESET);
            return;
        }

        int num = 1;
        System.out.println("========================================= DOCTOR LIST ============================================");
        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.printf("| %-2s | %-8s | %-15s | %-12s | %-19s | %-19s |\n",
                        "No", "ID", "Name", "Specialist", "Logged-in", "Logged-out");
        System.out.println("---------------------------------------------------------------------------------------------------");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm");

        while (current != null) {
            Doctor d = current.data;

            String loginStr = (d.getLoginTime() != null) ? d.getLoginTime().format(formatter) : "Absent";
            String logoutStr = (d.getLogoutTime() != null) ? d.getLogoutTime().format(formatter) : "";

            String row = String.format("| %-2d | %-8s | %-15s | %-12s | %-19s | %-19s |",
                        num++, d.getIDDoc(), d.getNameDoc(), d.getSpecialty(), loginStr, logoutStr);

            if (d.isLogin()) {
                System.out.println(GREEN + row + RESET);
            } else {
                System.out.println(RED + row + RESET);
            }

            current = current.next;
        }

        System.out.println("---------------------------------------------------------------------------------------------------");
    }


    public void scheduleAppointment() {
        System.out.println("=== Schedule Appointment ===");

        System.out.print("Doctor ID\t:");
        String doctorID = sc.nextLine();
        Doctor doctor = doctorList.findDoctorByID(doctorID);
        if (doctor == null) {
            System.out.println(RED + "Doctor not found" + RESET);
            return;
        }
        if (!doctor.isLogin()) {
            System.out.println(RED + "Doctor not logged in" + RESET);
            return;
        }
        showDoctorSchedule(doctor, mapAppointment);

        System.out.print("Appointment ID\t: ");
        String appointmentID = sc.nextLine();
        if (appointmentID.isEmpty()) {
            System.out.println(RED + "Appointment ID cannot be empty" + RESET);
            return;
        }

        System.out.print("Patient ID\t:");
        String patientID = sc.nextLine();
        Patient patient = binarySearch.searchPatient(patientID);
        if (patient == null) {
            System.out.println(RED + "Patient not found" + RESET);
            return;
        }

        //tanggal appointment
        String date = LocalDate.now().toString();

        // Pilih sesi
        String[] schedule = doctor.getSchedule();
        int session = -1;
        while (true) {
            System.out.print("Choose Session (1-" + (schedule.length - 1) + "): ");
            String sessionInput = sc.nextLine();
            try {
                session = Integer.parseInt(sessionInput);
                if (session < 1 || session > schedule.length - 1) {
                    System.out.println(RED + "Invalid session number." + RESET);
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(RED + "Please enter a valid session number." + RESET);
            }
        }

        String time = schedule[session - 1];
        String appointmentTime = date + " " + time;

        // Cek apakah sesi sudah terbooking di tanggal itu
        QueueAppointment queue = mapAppointment.getQueueByDoctorID(doctorID);
        if (queue != null) {
            Node<Appointment> current = queue.getFront();
            while (current != null) {
                String bookedDate = current.data.getTime().substring(0, 10);
                String bookedTime = current.data.getTime().substring(11, 16);
                if (bookedDate.equals(date) && bookedTime.equals(time)) {
                    System.out.println(RED + "That session is already booked on that date." + RESET);
                    return;
                }
                current = current.next;
            }
        }

        Appointment appointment = new Appointment(appointmentID, patient, doctor, appointmentTime);
        if (queue == null) {
            queue = new QueueAppointment();
            queue.enqueue(appointment);
            mapAppointment.put(doctorID, appointment);
        } else {
            queue.enqueue(appointment);
        }
        fileHandler.writeToFileAppointments("UpComingAppointments.txt", mapAppointment);
        System.out.println(GREEN + "Appointment scheduled successfully" + RESET);
    }

    public void viewUpcomingAppointments (Map mapAppointment) {
        Map.Mapnode current = mapAppointment.getHead();
        if (current == null) {
            System.out.println(RED + "No appointments available" + RESET);
            return;
        }
        
        int num = 1;
        System.out.println("============================================= UPCOMING APPOINTMENTS ==============================================");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s\n",
                    "No" ,"Appintment ID", "Patient ID", "Patient Name" ,"Doctor ID", "Doctor Name", "Time");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        
        while (current != null) {
            Node<Appointment> node = current.queue.getFront();
            while (node != null) {
                Appointment a = node.data;
                System.out.printf("%-5s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s\n",
                num++, a.getAppointmentID(), 
                a.getPatient().getID(), a.getPatient().getName(),
                a.getDoctor().getIDDoc(), a.getDoctor().getNameDoc(),
                a.getTime());

                node = node.next;
            }
            current = current.next;
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------");
    }

    public void processAppointmentAddRecord() {
    Appointment appointment = mapAppointment.getFirstAppointmentFromLoggedInDoctors(doctorList);
    QueueAppointment queue = mapAppointment.getQueueOfFirstAppointmentFromLoggedInDoctors(doctorList);

    if (appointment == null || queue == null) {
        System.out.println(RED + "No appointments to process for logged-in doctors." + RESET);
        return;
    }

    String patientID = appointment.getPatient().getID();
    String doctorID = appointment.getDoctor().getIDDoc();
    String date = appointment.getTime();
    System.out.println(appointment);
    
    System.out.println("=== Input Medical Record ===");
    System.out.println("Patient ID      : " + patientID);
    System.out.println("Doctor ID       : " + doctorID);
    System.out.print("Diagnosis       : ");
    String diagnosis = sc.nextLine();
    System.out.print("Treatment       : ");
    String treatment = sc.nextLine();
    System.out.print("Prescription    : ");
    String prescription = sc.nextLine();

    if (diagnosis.isEmpty() || treatment.isEmpty() || prescription.isEmpty()) {
        System.out.println(RED + "All fields must be filled!" + RESET);
        return;
    }

    MedicalRecord record = new MedicalRecord(patientID, doctorID, diagnosis, treatment, prescription, date);
    fileHandler.writeMedicalRecord("MedicalRecord.txt", record);
    fileHandler.loadMedicalRecords("MedicalRecord.txt", patientList, binarySearch);

    //DEQUEUE
    queue.processNextAppointment();
    fileHandler.writeToFileAppointments("UpComingAppointments.txt", mapAppointment);

    System.out.println(GREEN + "Medical record added successfully" + RESET);
}

    public void searchPatientByID() {
        System.out.println("------------------------------------");
        System.out.print("Patient ID: ");
        String searchID = sc.nextLine();

        Patient found = binarySearch.searchPatient(searchID);
        if (found == null) {
            System.out.println(RED + "The patient's ID is not found" + RESET);
        }
        else {
            System.out.println("------------------------------------");
            System.out.println(found);
            recordsDisplay(searchID);
        }
    }

    public void showDoctorSchedule (Doctor doctor, Map mapAppointment) {
        DateTimeFormatter slotFormat = DateTimeFormatter.ofPattern("HH.mm");
        DateTimeFormatter apptFormat = DateTimeFormatter.ofPattern("HH:mm");

        System.out.println("--------------------------------------------------------------------");
        System.out.printf("| %-10s | %-15s | %-15s | %-15s |\n", 
            doctor.getIDDoc(), doctor.getNameDoc(), doctor.getSpecialty(), "Practice Hours");
        System.out.println("--------------------------------------------------------------------");

        String[] times = doctor.getSchedule();
        QueueAppointment queue = mapAppointment.getQueueByDoctorID(doctor.getIDDoc());

        for (int i = 0; i < times.length - 1; i++) {
            String slot = times[i] + " - " + times[i + 1];
            boolean booked = false;

            if (queue != null) {
                Node<Appointment> current = queue.getFront();
                while (current != null) {
                    try {
                        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH.mm");
                        LocalDateTime apptTime = LocalDateTime.parse(current.data.getTime(), inputFormat);
                        LocalTime bookedTime = apptTime.toLocalTime();

                        LocalTime slotStart = LocalTime.parse(times[i], slotFormat);
                        if (bookedTime.equals(slotStart)) {
                            booked = true;
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Time format is error: " + current.data.getTime());
                    }

                    current = current.next;
                }
            }

            String display = booked ? RED + slot + RESET : slot;
            System.out.printf("| %-10s | %-15s | %-15s | %-15s |\n", "", "", "", display);
        }
        System.out.println("------------------------------------------------------------------------------------");
    }

    public void menuPatient() {
        boolean exit = false;
        while (!exit) {
            System.out.println("======================================");
            System.out.println("            MANAGE PATIENT            ");
            System.out.println("======================================");
            System.out.println("        [1] Add New Patient           ");
            System.out.println("        [2] Remove Patient by ID      ");
            System.out.println("        [3] Search Patient by Name    ");
            System.out.println("        [4] Display All Patients      ");
            System.out.println("        [5] Search Patient by ID      ");
            System.out.println("        [6] Display All Patients BST  ");
            System.out.println("        [7] Make An Appointment       ");
            System.out.println("        [8] Process An Appointment    ");
            System.out.println("        [9] Upcoming Appointments     ");
            System.out.println("        [0] Exit                      ");
            System.out.println("--------------------------------------");
            System.out.print("Input option: ");

            int menuControl = -1;
            try {
                menuControl = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please try again.");
                continue;
            }

            switch (menuControl) {
                case 1:
                    addNewPatient();
                    break;
                case 2:
                    removePatientByID();
                    break;
                case 3:
                    searchPatientByName();
                    break;
                case 4:
                    displayAllPatients(patientList);
                    break;
                case 5:
                    searchPatientByID();
                    break;
                case 6:
                    binarySearch.inOrderDisplay();
                    break;
                case 7:
                    scheduleAppointment();
                    break;
                case 8:
                    processAppointmentAddRecord();
                    break;
                case 9:
                    viewUpcomingAppointments(mapAppointment);
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void menuDoctor() {
        boolean exit = false;
        while (!exit) {
            System.out.println("========================================");
            System.out.println("             MANAGE DOCTOR              ");
            System.out.println("========================================");
            System.out.println("        [1] Register New Doctor     ");
            System.out.println("        [2] Doctor Log-in           ");
            System.out.println("        [3] Doctor Log-out          ");
            System.out.println("        [4] View All Doctors        ");
            System.out.println("        [5] View All Logged In Doctors");
            System.out.println("        [0] Exit                    ");
            System.out.println("----------------------------------------");
            System.out.print("Input option: ");

            int menuControl = -1;
            try {
                menuControl = Integer.parseInt(sc.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid option. Please try again.");
                continue;
            }

            switch (menuControl) {
                case 1:
                    registDoctor();
                    break;
                case 2:
                    doctorLogin();
                    break;
                case 3:
                    doctorLogout();
                    break;
                case 4:
                    viewAllDoctor(doctorList);
                    break;
                case 5:
                    doctorList.allLoginDoctor();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void recordsDisplay(String patientID) {
        System.out.println("====================================");
        System.out.println("1. Display Medical Records");
        System.out.println("0. Exit to Main Menu");
        System.out.println("------------------------------------");
        System.out.printf("Input option : ");
        int value = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        switch (value) {
            case 1:
                displayMedicalRecords(patientID);
                break;
            case 0:
                System.out.println("Exiting to main menu...");
                return; // Exit the method to return to the main menu
        
            default:
                break;
        }
    }
}
