import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Doctor {
    // Attributes
    private String IDDoc;
    private String nameDoc;
    private String specialty;
    private boolean login;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private String[] schedule;

    // ANSI escape codes for colored output
    public static String RESET = "\u001B[0m";
    public static String RED = "\u001B[31m";
    public static String GREEN = "\u001B[32m";

    // Constructor
    public Doctor(boolean login, String IDDoc, String nameDoc, String specialty, LocalDateTime loginTime, LocalDateTime logoutTime) {
        this.IDDoc = IDDoc;
        this.nameDoc = nameDoc;
        this.specialty = specialty;
        this.login = login;
        this.loginTime =  loginTime;
        this.logoutTime =  logoutTime;
        this.schedule = generateSchedule();
    }

    //setter
    public void setLogin(boolean login) {
        this.login = login;
    }
    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }
    public void setLogoutTime(LocalDateTime logoutTime) {
        this.logoutTime = logoutTime;
    }

    // Getter methods
    public boolean isLogin() { return login; }
    public String getIDDoc() { return IDDoc; }
    public String getNameDoc() { return nameDoc; }
    public String getSpecialty() { return specialty; }
    public LocalDateTime getLoginTime() { return loginTime; }
    public LocalDateTime getLogoutTime() { return logoutTime; }
    public String[] getSchedule() {return schedule;} 

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        StringBuilder sb = new StringBuilder();
        sb.append(login ? GREEN : RED);
        sb.append("Doctor ID\t: ").append(IDDoc).append("\n");
        sb.append("Doctor Name\t: ").append(nameDoc).append("\n");
        sb.append("Specialist\t: ").append(specialty).append("\n");

        String loginStr = (loginTime != null) ? loginTime.format(formatter) : "-";
        sb.append("Logged-in\t: ").append(loginStr).append("\n");

        if (!login) {
            String logoutStr = (logoutTime != null) ? logoutTime.format(formatter) : "-";
            sb.append("Logged-out\t: ").append(logoutStr).append("\n");
        }

        sb.append(RESET);
        return sb.toString();
    }

    private String[] generateSchedule() {
        String[] time = new String[7];
        time[0] = "09.00"; time[1] = "09.30"; time[2] = "10.00"; time[3] = "10.30";
        time[4] = "11.00"; time[5] = "11.30"; time[6] = "12.00";
        return time;
    }

}

