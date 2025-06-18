public class Patient {
    private String ID;
    private String name;
    private int age;
    private String address;
    private String phone;
    private Stack medicalRecordStack;

    //Constructor
    public Patient(String ID, String name, int age, String address, String phone){
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.medicalRecordStack = new Stack();
    }

    //Getter
    public String getID() {return ID;}
    public String getName() {return name;}
    public int getAge() {return age;}
    public String getAddress() {return address;}
    public String getPhone() {return phone;}
    public Stack getMedicalRecordStack() {return medicalRecordStack;}

    public void addRecord(MedicalRecord record) {
        medicalRecordStack.push(record);
    }

    @Override
    public String toString(){
        return "\tID\t\t: " + ID + "\n" + "\tName\t\t: " + name + "\n" + "\tAge\t\t: " + age + "\n" + "\tAddress\t\t: " + address
        + "\n" + "\tNumber Phone\t: " + phone + "\n" ;
    }
}


