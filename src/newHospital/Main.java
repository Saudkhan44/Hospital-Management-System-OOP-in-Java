package newHospital;


public class Main {
    public static void main(String[] args) {
        HospitalData.patientList.add(new Patient("Saud","saud400@gmail.com","1234567890",
                "Male",21, "12345"));
        HospitalData.patientList.add(new Patient("Ali","ali400@gmail.com","1234567890",
                "Male",50, "12345"));
        HospitalData.doctorList.add(new Doctor("Saqib","saqib400@gmail.com","1234567890",
                "Male","Medicine", "12345"));
        HospitalData.doctorList.add(new Doctor("Amjad","amjad400@gmail.com","1234567890",
                "Male","Surgery", "12345"));

        new HomePageGUI();
    }
}