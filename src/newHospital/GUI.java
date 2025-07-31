package newHospital;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

// HomePage:
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

class HomePageGUI extends JFrame implements ActionListener {

    private JButton doctorBtn, patientBtn, adminBtn;
    private JButton registerPatientBtn;

    public HomePageGUI() {
        setTitle("Sana Malik Hospital - Welcome");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // ===== Top Label =====
        JLabel welcomeLabel = new JLabel("Welcome to Sana Malik Hospital", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(0, 102, 204));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        add(welcomeLabel, BorderLayout.NORTH);

        // ===== Center Login Panel =====
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        JLabel loginTitle = new JLabel("Choose Your Role to Login", SwingConstants.CENTER);
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel roleButtonPanel = new JPanel(new GridLayout(1, 3, 20, 10));
        roleButtonPanel.setOpaque(false);
        roleButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        doctorBtn = new JButton("Doctor");
        patientBtn = new JButton("Patient");
        adminBtn = new JButton("Admin");

        styleButton(doctorBtn);
        styleButton(patientBtn);
        styleButton(adminBtn);

        roleButtonPanel.add(doctorBtn);
        roleButtonPanel.add(patientBtn);
        roleButtonPanel.add(adminBtn);

        centerPanel.add(loginTitle, BorderLayout.NORTH);
        centerPanel.add(roleButtonPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // ===== Bottom Register Section =====
        JPanel registerPanel = new JPanel(new BorderLayout());
        registerPanel.setOpaque(false);
        registerPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 30, 60));

        JLabel regLabel = new JLabel("New Patient? Register Below", SwingConstants.CENTER);
        regLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        registerPatientBtn = new JButton("Register as Patient");
        registerPatientBtn.setBackground(new Color(0, 153, 76));
        registerPatientBtn.setForeground(Color.WHITE);
        registerPatientBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerPatientBtn.setPreferredSize(new Dimension(180, 40));

        JPanel btnHolder = new JPanel();
        btnHolder.setOpaque(false);
        btnHolder.add(registerPatientBtn);

        registerPanel.add(regLabel, BorderLayout.NORTH);
        registerPanel.add(btnHolder, BorderLayout.CENTER);

        add(registerPanel, BorderLayout.SOUTH);

        // ==== Add Action Listeners ====
        doctorBtn.addActionListener(this);
        patientBtn.addActionListener(this);
        adminBtn.addActionListener(this);
        registerPatientBtn.addActionListener(this);

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        button.setBackground(new Color(0, 153, 204));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == doctorBtn) {
            new LoginGUI(Role.DOCTOR);
        } else if (e.getSource() == patientBtn) {
            new LoginGUI(Role.PATIENT);
        } else if (e.getSource() == adminBtn) {
            new LoginGUI(Role.ADMIN);
        } else if (e.getSource() == registerPatientBtn) {
            new UnifiedRegistrationGUI();
        }
    }
}

// Login Page:
class LoginGUI extends JFrame implements ActionListener {

    String adminName = "ALI", adminPass = "12345";

    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton loginBtn, backBtn;
    private Role userRole; // The role passed from HomePage

    public LoginGUI(Role role) {
        this.userRole = role;

        setTitle(role + " Login - Malik Hospital");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(role + " Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 20));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        nameField.setBounds(10,10,10,5);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        formPanel.add(nameLabel); formPanel.add(nameField);
        formPanel.add(passwordLabel); formPanel.add(passwordField);
        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        loginBtn = new JButton("Login");
        backBtn = new JButton("Back");
        loginBtn.setBackground(new Color(0, 153, 76));
        loginBtn.setForeground(Color.WHITE);
        backBtn.setBackground(new Color(153, 0, 0));
        backBtn.setForeground(Color.WHITE);

        buttonPanel.add(loginBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        loginBtn.addActionListener(this);
        backBtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String name = nameField.getText();
            String password = new String(passwordField.getPassword());

            if (name.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userRole == Role.PATIENT) {
                boolean found = false;
                for (Patient p : HospitalData.patientList) {
                    if (p.getName().equalsIgnoreCase(name) && p.getPassword().equalsIgnoreCase(password)) {
                        found = true;
                        new PatientDashboardGUI(p);
                        break;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(this, "Invalid name or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if (userRole == Role.DOCTOR) {
                boolean found = false;
                for (Doctor d : HospitalData.doctorList) {
                    if (d.getName().equalsIgnoreCase(name) && d.getPassword().equalsIgnoreCase(password)) {
                        found = true;
                        new DoctorDashboardGUI(d);
                        break;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(this, "Invalid name or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(userRole == Role.ADMIN){
                    if (name.equalsIgnoreCase(adminName) && password.equalsIgnoreCase(adminPass)){
                        new AdminDashboardGUI(adminName);
                    }
                else {
                    JOptionPane.showMessageDialog(this, "Invalid name or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        else if (e.getSource() == backBtn) {
            this.dispose();
        }
    }
}

// Registration From:
class UnifiedRegistrationGUI extends JFrame implements ActionListener, ItemListener {
    private JTextField nameField, emailField, phoneField, genderField, ageField, specialtyField;
    private JPasswordField passwordField;
    private JComboBox<String> roleDropdown;
    private JLabel ageLabel, specialtyLabel;
    private JButton submitBtn, backBtn;

    public UnifiedRegistrationGUI() {
        setTitle("User Registration - Malik Hospital");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Register New User", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 12));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // === Fields ===
        roleDropdown = new JComboBox<>(new String[]{"Select", "Patient", "Doctor"});
        roleDropdown.addItemListener(this);

        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        genderField = new JTextField();
        passwordField = new JPasswordField();
        ageField = new JTextField();
        specialtyField = new JTextField();

        // === Labels ===
        ageLabel = new JLabel("Age:");
        specialtyLabel = new JLabel("Specialty:");

        formPanel.add(new JLabel("Select Role:")); formPanel.add(roleDropdown);
        formPanel.add(new JLabel("Full Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Email:")); formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:")); formPanel.add(phoneField);
        formPanel.add(new JLabel("Gender:")); formPanel.add(genderField);
        formPanel.add(new JLabel("Password:")); formPanel.add(passwordField);
        formPanel.add(ageLabel); formPanel.add(ageField);
        formPanel.add(specialtyLabel); formPanel.add(specialtyField);

        add(formPanel, BorderLayout.CENTER);

        // === Buttons ===
        JPanel buttonPanel = new JPanel();
        submitBtn = new JButton("Register");
        backBtn = new JButton("Back");

        submitBtn.setBackground(new Color(0, 153, 76));
        submitBtn.setForeground(Color.WHITE);
        backBtn.setBackground(new Color(153, 0, 0));
        backBtn.setForeground(Color.WHITE);

        buttonPanel.add(submitBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        submitBtn.addActionListener(this);
        backBtn.addActionListener(this);

        updateVisibility();  // Show/hide fields on first load
        setVisible(true);
    }

    private void updateVisibility() {
        String selectedRole = (String) roleDropdown.getSelectedItem();
        boolean isPatient = selectedRole.equals("Patient");
        boolean isDoctor = selectedRole.equals("Doctor");

        ageLabel.setVisible(isPatient);
        ageField.setVisible(isPatient);

        specialtyLabel.setVisible(isDoctor);
        specialtyField.setVisible(isDoctor);
    }

    //“If the event came from the role dropdown and the user selected a new item,
    // then update the form fields (e.g., show age for patient, show specialty for doctor).”
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == roleDropdown && e.getStateChange() == ItemEvent.SELECTED) {
            updateVisibility();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitBtn) {
            String role = (String) roleDropdown.getSelectedItem();
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String gender = genderField.getText();
            String password = new String(passwordField.getPassword());

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || gender.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (role.equals("Patient")) {
                String ageStr = ageField.getText();
                if (ageStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter age.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    int age = Integer.parseInt(ageStr);
                    HospitalData.patientList.add(new Patient(name, email, phone, gender, age, password));
                    JOptionPane.showMessageDialog(this, "✅ Patient registered successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Age must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else if (role.equals("Doctor")) {
                String specialty = specialtyField.getText();
                if (specialty.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter specialty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                HospitalData.doctorList.add(new Doctor(name, email, phone, gender, specialty, password));
                JOptionPane.showMessageDialog(this, "✅ Doctor registered successfully!");

            }

            // Clear fields
            nameField.setText(""); emailField.setText(""); phoneField.setText("");
            genderField.setText(""); passwordField.setText(""); ageField.setText("");
            specialtyField.setText("");

        } else if (e.getSource() == backBtn) {
            dispose();
        }
    }
}

// Doctor Dashboard:
class DoctorDashboardGUI extends JFrame implements ActionListener {

    private JButton viewAppointmentsBtn, confirmBtn, cancelBtn, logoutBtn;
    private Doctor loggedInDoctor;

    public DoctorDashboardGUI(Doctor doctor) {
        this.loggedInDoctor = doctor;

        setTitle("Doctor Dashboard - Malik Hospital");
        setSize(768, 768); // 8 x 8 inch
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // ==== Title ====
        JLabel titleLabel = new JLabel("Welcome Dr. " + doctor.getName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(titleLabel, BorderLayout.NORTH);

        // ==== Buttons ====
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));
        buttonPanel.setBackground(Color.WHITE);

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 18);

        viewAppointmentsBtn = createStyledButton("View Appointments", btnFont);
        confirmBtn = createStyledButton("✅ Confirm Appointment", btnFont);
        cancelBtn = createStyledButton("❌ Cancel Appointment", btnFont);
        logoutBtn = createStyledButton("Logout", btnFont);

        buttonPanel.add(viewAppointmentsBtn);
        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);
        buttonPanel.add(logoutBtn);

        add(buttonPanel, BorderLayout.CENTER);

        // Action listeners
        viewAppointmentsBtn.addActionListener(this);
        confirmBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        logoutBtn.addActionListener(this);

        setVisible(true);
    }

    private JButton createStyledButton(String text, Font font) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(230, 240, 255));
        btn.setForeground(Color.DARK_GRAY);
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(180, 220, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(230, 240, 255));
            }
        });

        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewAppointmentsBtn) {
            new DoctorAppointmentsGUI(loggedInDoctor); // Implement this
        } else if (e.getSource() == confirmBtn) {
            new DoctorAppointmentsGUI(loggedInDoctor); // Implement this
        } else if (e.getSource() == cancelBtn) {
            new DoctorAppointmentsGUI(loggedInDoctor); // Implement this
        } else if (e.getSource() == logoutBtn) {
            dispose();
            new HomePageGUI();
        }
    }
}



// Patient dashboard:
class PatientDashboardGUI extends JFrame implements ActionListener {
    private Patient patient;
    private JButton viewAppointmentsBtn, bookAppointmentBtn, viewPrescriptionsBtn, logoutBtn;

    public PatientDashboardGUI(Patient patient) {
        this.patient = patient;

        setTitle("Patient Dashboard - Malik Hospital");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JLabel title = new JLabel("Welcome, " + patient.getName(), SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(0, 102, 204));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 180, 40, 180));
        buttonPanel.setBackground(Color.WHITE);

        viewAppointmentsBtn = createButton("View Appointments");
        bookAppointmentBtn = createButton("Book Appointment");
        viewPrescriptionsBtn = createButton("View Prescriptions");
        logoutBtn = createButton("Logout");

        buttonPanel.add(viewAppointmentsBtn);
        buttonPanel.add(bookAppointmentBtn);
        buttonPanel.add(viewPrescriptionsBtn);
        buttonPanel.add(logoutBtn);

        add(buttonPanel, BorderLayout.CENTER);

        viewAppointmentsBtn.addActionListener(this);
        bookAppointmentBtn.addActionListener(this);
        viewPrescriptionsBtn.addActionListener(this);
        logoutBtn.addActionListener(this);

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(230, 240, 255));
        btn.setForeground(Color.DARK_GRAY);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewAppointmentsBtn) {
            new ViewAppointmentsGUI(patient);
        } else if (e.getSource() == bookAppointmentBtn) {
            new BookAppointmentGUI(patient);
        } else if (e.getSource() == viewPrescriptionsBtn) {
            showPrescriptionsGUI();
        } else if (e.getSource() == logoutBtn) {
            dispose();
            new HomePageGUI();
        }
    }

    // ==================== View Prescriptions Logic ====================
    private void showPrescriptionsGUI() {
        JFrame frame = new JFrame("Your Prescriptions");
        frame.setSize(750, 400);
        frame.setLocationRelativeTo(this);
        frame.setLayout(new BorderLayout());

        JLabel heading = new JLabel("Prescriptions", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        frame.add(heading, BorderLayout.NORTH);

        String[] cols = { "ID", "Doctor", "Date", "Diagnosis", "Medications", "Instructions" };
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-yyyy");

        ArrayList<Prescription> prescriptions = patient.getPrescriptions();
        for (Prescription p : prescriptions) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getDoctor().getName(),
                    sdf.format(p.getDateIssued()),
                    p.getDiagnosis(),
                    p.getMedicationDetails(),
                    p.getInstructions()
            });
        }

        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> frame.dispose());
        JPanel panel = new JPanel();
        panel.add(closeBtn);
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}


// Admin Dashboard:
class AdminDashboardGUI extends JFrame implements ActionListener {
    private JButton addDoctorBtn, addLabTechBtn, viewDoctorsBtn, viewPatientsBtn, logoutBtn;

    public AdminDashboardGUI(String adminName) {
        setTitle("Admin Dashboard - Malik Hospital");
        setSize(768, 768); // 8x8 inches approx.
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // ===== Title =====
        JLabel titleLabel = new JLabel("Welcome Admin: " + adminName, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(titleLabel, BorderLayout.NORTH);

        // ===== Button Panel =====
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 180, 40, 180));

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 18);
        Color normalColor = new Color(230, 240, 255);
        Color hoverColor = new Color(180, 220, 255);
        Color logoutColor = new Color(255, 230, 230);

        addDoctorBtn = createStyledButton("➕ Add Doctor", btnFont, normalColor, hoverColor);
        viewDoctorsBtn = createStyledButton("📋 View Doctor List", btnFont, normalColor, hoverColor);
        viewPatientsBtn = createStyledButton("📋 View Patient List", btnFont, normalColor, hoverColor);
        logoutBtn = createStyledButton("🔙 Logout", btnFont, logoutColor, new Color(255, 200, 200));

        // Add buttons
        buttonPanel.add(addDoctorBtn);
        buttonPanel.add(viewDoctorsBtn);
        buttonPanel.add(viewPatientsBtn);
        buttonPanel.add(logoutBtn);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);

        // Attach buttons to panel & listeners
        JButton[] buttons = { addDoctorBtn, viewDoctorsBtn, viewPatientsBtn, logoutBtn };
        for (JButton btn : buttons) {
            btn.addActionListener(this);
        }

    }

    private JButton createStyledButton(String text, Font font, Color baseColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setBackground(baseColor);
        button.setForeground(Color.DARK_GRAY);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addDoctorBtn) {
            new UnifiedRegistrationGUI();
        } else if (e.getSource() == viewDoctorsBtn) {
             showDoctorList();
        } else if (e.getSource() == viewPatientsBtn) {
             showPatientList();
        } else if (e.getSource() == logoutBtn) {
            dispose();
            new HomePageGUI();
        }
    }

    // Show Doctor List method:
    private void showDoctorList() {
        JDialog dialog = new JDialog(this, "📋 Doctor List", true);
        dialog.setSize(700, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel title = new JLabel("Registered Doctors", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        dialog.add(title, BorderLayout.NORTH);

        String[] columns = { "Name", "Email", "Phone", "Gender", "Specialty" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        for (Doctor d : HospitalData.doctorList) {
            model.addRow(new Object[]{d.getName(), d.getEmail(), d.getPhone(), d.getGender(), d.getSpecialty()
            });
        }

        dialog.add(new JScrollPane(table), BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    // Show Patient List:
    private void showPatientList() {
        JDialog dialog = new JDialog(this, "📋 Patient List", true);
        dialog.setSize(700, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel title = new JLabel("Registered Patients", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        dialog.add(title, BorderLayout.NORTH);

        String[] columns = { "Name", "Email", "Phone", "Gender", "Age" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        for (Patient p : HospitalData.patientList) {
            model.addRow(new Object[]{
                    p.getName(), p.getEmail(), p.getPhone(), p.getGender(), p.getAge()
            });
        }

        dialog.add(new JScrollPane(table), BorderLayout.CENTER);
        dialog.setVisible(true);
    }
}


// Doctor Dashboard and Functions:
class DoctorAppointmentsGUI extends JFrame implements ActionListener {
    private Doctor doctor;
    private JTable table;
    private DefaultTableModel model;
    private JButton confirmBtn, cancelBtn, backBtn, writePrescriptionBtn;

    public DoctorAppointmentsGUI(Doctor doctor) {
        this.doctor = doctor;

        setTitle("Doctor Appointments - Khan Hospital");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Title =====
        JLabel title = new JLabel("🗓️ My Appointments", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // ===== Table Setup =====
        String[] columns = { "Appointment ID", "Patient Name", "Date", "Status" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        loadAppointments();
        add(scrollPane, BorderLayout.CENTER);

        // ===== Button Panel =====
        JPanel btnPanel = new JPanel();

        confirmBtn = new JButton("✅ Confirm");
        cancelBtn = new JButton("❌ Cancel");
        writePrescriptionBtn = new JButton("✍️ Write Prescription");
        backBtn = new JButton("🔙 Back");

        confirmBtn.setBackground(new Color(0, 153, 76));
        confirmBtn.setForeground(Color.WHITE);

        cancelBtn.setBackground(new Color(204, 0, 0));
        cancelBtn.setForeground(Color.WHITE);

        writePrescriptionBtn.setBackground(new Color(102, 102, 255));
        writePrescriptionBtn.setForeground(Color.WHITE);

        backBtn.setBackground(Color.GRAY);
        backBtn.setForeground(Color.WHITE);

        btnPanel.add(confirmBtn);
        btnPanel.add(cancelBtn);
        btnPanel.add(writePrescriptionBtn);
        btnPanel.add(backBtn);

        add(btnPanel, BorderLayout.SOUTH);

        // Listeners
        confirmBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        writePrescriptionBtn.addActionListener(this);
        backBtn.addActionListener(this);

        setVisible(true);
    }

    private void loadAppointments() {
        model.setRowCount(0); // Clear previous data
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        for (Appointment app : doctor.getAppointments()) {
            String[] row = {
                    app.getId(),
                    app.getPatient().getName(),
                    sdf.format(app.getDate()),
                    app.getStatus().toString()
            };
            model.addRow(row);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();

        if (e.getSource() == confirmBtn) {
            if (selectedRow != -1) {
                String appId = (String) model.getValueAt(selectedRow, 0);
                doctor.confirmAppointment(appId);
                JOptionPane.showMessageDialog(this, "Appointment confirmed!");
                loadAppointments();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an appointment to confirm.");
            }
        }

        else if (e.getSource() == cancelBtn) {
            if (selectedRow != -1) {
                String appId = (String) model.getValueAt(selectedRow, 0);
                doctor.cancelAppointment(appId);
                JOptionPane.showMessageDialog(this, "Appointment cancelled!");
                loadAppointments();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an appointment to cancel.");
            }
        }

        else if (e.getSource() == writePrescriptionBtn) {
            if (selectedRow != -1) {
                String appId = (String) model.getValueAt(selectedRow, 0);
                Appointment selectedApp = null;

                for (Appointment a : doctor.getAppointments()) {
                    if (a.getId().equals(appId)) {
                        selectedApp = a;
                        break;
                    }
                }

                if (selectedApp != null && selectedApp.getStatus() == AppointmentStatus.COMPLETED) {
                    showPrescriptionForm(selectedApp);
                } else {
                    JOptionPane.showMessageDialog(this, "You can only prescribe for completed appointments.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an appointment.");
            }
        }

        else if (e.getSource() == backBtn) {
            dispose();
            new DoctorDashboardGUI(doctor);
        }
    }

    private void showPrescriptionForm(Appointment appointment) {
        // Form Fields
        JTextField diagnosisField = new JTextField();
        JTextArea medicationArea = new JTextArea(3, 20);
        JTextArea instructionArea = new JTextArea(3, 20);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setPreferredSize(new Dimension(350, 250));
        panel.add(new JLabel("Diagnosis:"));
        panel.add(diagnosisField);
        panel.add(new JLabel("Medications:"));
        panel.add(new JScrollPane(medicationArea));
        panel.add(new JLabel("Instructions:"));
        panel.add(new JScrollPane(instructionArea));

        int result = JOptionPane.showConfirmDialog(this, panel, "Write Prescription",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String diagnosis = diagnosisField.getText().trim();
            String medications = medicationArea.getText().trim();
            String instructions = instructionArea.getText().trim();

            if (diagnosis.isEmpty() || medications.isEmpty() || instructions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Prescription p = new Prescription(doctor, appointment.getPatient(), diagnosis, medications, instructions);
            appointment.getPatient().addPrescription(p);

            JOptionPane.showMessageDialog(this, "Prescription added successfully!");
        }
    }
}


class BookAppointmentGUI extends JFrame implements ActionListener {
    private JComboBox<String> doctorDropdown;
    private JTextField dateField;
    private JButton bookBtn, backBtn;
    private Patient loggedInPatient;  // pass current logged-in patient

    public BookAppointmentGUI(Patient patient) {
        this.loggedInPatient = patient;
        setTitle("Book Appointment - Malik Hospital");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Book an Appointment", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        formPanel.add(new JLabel("Select Doctor:"));
        doctorDropdown = new JComboBox<>();
        for (Doctor d : HospitalData.doctorList) {
            doctorDropdown.addItem(d.getName());// show name
        }
        formPanel.add(doctorDropdown);

        formPanel.add(new JLabel("Appointment Date (DD-MM-yyyy):"));
        dateField = new JTextField();
        formPanel.add(dateField);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        bookBtn = new JButton("Book");
        backBtn = new JButton("Back");

        bookBtn.setBackground(new Color(0, 153, 76));
        bookBtn.setForeground(Color.WHITE);
        backBtn.setBackground(new Color(153, 0, 0));
        backBtn.setForeground(Color.WHITE);

        buttonPanel.add(bookBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        bookBtn.addActionListener(this);
        backBtn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookBtn) {
            String selectedDoctorName = (String) doctorDropdown.getSelectedItem();
            String dateStr = dateField.getText();

            if (dateStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Parse date string
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date appointmentDate = sdf.parse(dateStr);

                // Find the Doctor object by name
                Doctor selectedDoctor = null;
                for (Doctor d : HospitalData.doctorList) {
                    if (d.getName().equalsIgnoreCase(selectedDoctorName)) {
                        selectedDoctor = d;
                        break;
                    }
                }

                if (selectedDoctor == null) {
                    JOptionPane.showMessageDialog(this, "Selected doctor not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Book appointment
                loggedInPatient.bookAppointment(selectedDoctor, appointmentDate);
                JOptionPane.showMessageDialog(this, "Appointment booked successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format! Use dd-MM-yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == backBtn) {
            dispose();
        }
    }
}


// VIEW APPOINTMENTS:
class ViewAppointmentsGUI extends JFrame {

    public ViewAppointmentsGUI(Patient loggedInPatient) {
        setTitle("My Appointments - Malik Hospital");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("📋 My Appointments", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Table columns
        String[] columns = {"Doctor", "Date", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        // Populate table from patient appointments
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (Appointment appt : loggedInPatient.getAppointments()) {
            String doctorName = appt.getDoctor().getName();
            String dateStr = sdf.format(appt.getDate());
            String status = appt.getStatus().toString();
            model.addRow(new Object[]{doctorName, dateStr, status});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}