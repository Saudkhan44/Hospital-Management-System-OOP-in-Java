package newHospital;

import java.util.ArrayList;
import java.util.Date;

// ================= ENUMS =================
enum Role {
    PATIENT, DOCTOR, ADMIN
}

enum AppointmentStatus {
    SCHEDULED, COMPLETED, CANCELLED
}

// ================= USER BASE CLASS =================
abstract class User {
    private static int idCounter = 0;
    private String id;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private Role role;
    private String password;

    public User(String name, String email, String phone, String gender, Role role, String password) {
        this.id = generateId(role);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.role = role;
        this.password = password;
    }

    private String generateId(Role role) {
        return role.name().substring(0, 3) + "-" + idCounter++;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getGender() { return gender; }
    public Role getRole() { return role; }
    public String getPassword() { return password; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setGender(String gender) { this.gender = gender; }
    public void setPassword(String password) { this.password = password; }

    public void display() {
        System.out.println("ID: " + id);
        System.out.println("Role: " + role);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Gender: " + gender);
    }
}

// ================= PATIENT CLASS =================
class Patient extends User {
    private int age;
    private ArrayList<Appointment> appointments = new ArrayList<>();
    private ArrayList<Prescription> prescriptions = new ArrayList<>();

    public Patient(String name, String email, String phone, String gender, int age, String password) {
        super(name, email, phone, gender, Role.PATIENT, password);
        this.age = age;
    }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public ArrayList<Appointment> getAppointments() { return appointments; }

    public ArrayList<Prescription> getPrescriptions() { return prescriptions; }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    public void bookAppointment(Doctor doctor, Date date) {
        Appointment appointment = new Appointment(date, this, doctor, AppointmentStatus.SCHEDULED);
        appointments.add(appointment);
        doctor.addAppointment(appointment);
    }
}

// ================= DOCTOR CLASS =================
class Doctor extends User {
    private String specialty;
    private ArrayList<Appointment> appointments = new ArrayList<>();
    private ArrayList<Prescription> writtenPrescriptions = new ArrayList<>();

    public Doctor(String name, String email, String phone, String gender, String specialty, String password) {
        super(name, email, phone, gender, Role.DOCTOR, password);
        this.specialty = specialty;
    }

    // ======= Getters and Setters =======
    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public ArrayList<Prescription> getWrittenPrescriptions() {
        return writtenPrescriptions;
    }

    // ======= Appointment Methods =======
    public void addAppointment(Appointment app) {
        appointments.add(app);
    }

    public void confirmAppointment(String appointmentId) {
        for (Appointment a : appointments) {
            if (a.getId().equals(appointmentId)) {
                a.setStatus(AppointmentStatus.COMPLETED);
                break;
            }
        }
    }

    public void cancelAppointment(String appointmentId) {
        for (Appointment a : appointments) {
            if (a.getId().equals(appointmentId)) {
                a.setStatus(AppointmentStatus.CANCELLED);
                break;
            }
        }
    }

    public void addWrittenPrescription(Prescription p) {
        writtenPrescriptions.add(p);
    }
}

// ================= APPOINTMENT CLASS =================
class Appointment {
    private static int counter = 1;
    private String id;
    private Date date;
    private Patient patient;
    private Doctor doctor;
    private AppointmentStatus status;

    public Appointment(Date date, Patient patient, Doctor doctor, AppointmentStatus status) {
        this.id = "APT-" + counter++;
        this.date = date;
        this.patient = patient;
        this.doctor = doctor;
        this.status = status;
    }

    public String getId() { return id; }
    public Date getDate() { return date; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public AppointmentStatus getStatus() { return status; }

    public void setDate(Date date) { this.date = date; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    public void display() {
        System.out.println("Appointment ID: " + id);
        System.out.println("Date: " + date);
        System.out.println("Patient: " + patient.getName());
        System.out.println("Doctor: " + doctor.getName());
        System.out.println("Status: " + status);
    }
}

// Prescription Class:
class Prescription {
    static int idCounter = 1;
    private String id;
    private Date dateIssued;
    private Doctor doctor;
    private Patient patient;
    private String diagnosis;
    private String medicationDetails;
    private String instructions;

    public Prescription(Doctor doctor, Patient patient, String diagnosis, String medicationDetails, String instructions) {
        this.id = generateId();
        this.dateIssued = new Date();  // current timestamp
        this.doctor = doctor;
        this.patient = patient;
        this.diagnosis = diagnosis;
        this.medicationDetails = medicationDetails;
        this.instructions = instructions;
    }

    private String generateId() {
        return "PR" + "-" + idCounter++;
    }


    // === Getters ===
    public String getId() { return id; }
    public Date getDateIssued() { return dateIssued; }
    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }
    public String getDiagnosis() { return diagnosis; }
    public String getMedicationDetails() { return medicationDetails; }
    public String getInstructions() { return instructions; }

    // === Setters ===
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setMedicationDetails(String medicationDetails) { this.medicationDetails = medicationDetails; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    @Override
    public String toString() {
        return "Prescription ID: " + id +
                "\nDate: " + dateIssued +
                "\nDoctor: " + doctor.getName() +
                "\nPatient: " + patient.getName() +
                "\nDiagnosis: " + diagnosis +
                "\nMedications: " + medicationDetails +
                "\nInstructions: " + instructions;
    }
}