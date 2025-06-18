import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        LinkedListPatient patientList = new LinkedListPatient();
        LinkedListDoctor doctorList = new LinkedListDoctor();
        QueueAppointment appointmentQueue = new QueueAppointment();
        BST binarySearch = new BST();
        Map mapAppointment = new Map();
        MenuFunction menu = new MenuFunction(patientList, doctorList, appointmentQueue, sc, binarySearch, mapAppointment);
        boolean exit = false;
        FileHandler fileHandler = new FileHandler();
        fileHandler.readFromFile("ListPatient.txt", patientList, binarySearch);
        fileHandler.readFromFileDoctor("ListDoctor.txt", doctorList);
        fileHandler.loadMedicalRecords("MedicalRecord.txt", patientList, binarySearch);
        fileHandler.readFromFileAppointments("UpComingAppointments.txt", mapAppointment, binarySearch, doctorList);
        fileHandler.loadAppointment(mapAppointment, appointmentQueue);
        while(!exit){
                System.out.println("====================================");
                System.out.println("           DAISUKE CLINIC          ");
                System.out.println("====================================");
                System.out.println("        [1] Patient Menu            ");
                System.out.println("        [2] Doctor Menu             ");
                System.out.println("        [0] Exit                    ");
                System.out.println("------------------------------------");
                System.out.print("Input option: ");
                int menuControl = -1;
                try {
                    menuControl = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }
                
                switch (menuControl) {
                    case 1:
                        menu.menuPatient();
                        break;
                    case 2:
                        menu.menuDoctor();
                        break;
                    case 0:
                        exit = true;
                        System.out.println("Thank you for using the hospital management system. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }

        sc.close();
    }
}
