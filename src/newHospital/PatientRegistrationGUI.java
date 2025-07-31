package newHospital;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PatientRegistrationGUI extends JFrame implements ActionListener {
    private JTextField nameField, emailField, phoneField, genderField, ageField;
    private JPasswordField passwordField;
    private JButton submitBtn, backBtn;

    public PatientRegistrationGUI() {
        setTitle("Patient Registration - Khan Hospital");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("📝 Patient Registration Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        JLabel nameLabel = new JLabel("Full Name:");
        nameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField();

        JLabel genderLabel = new JLabel("Gender:");
        genderField = new JTextField();

        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        formPanel.add(nameLabel); formPanel.add(nameField);
        formPanel.add(emailLabel); formPanel.add(emailField);
        formPanel.add(phoneLabel); formPanel.add(phoneField);
        formPanel.add(genderLabel); formPanel.add(genderField);
        formPanel.add(ageLabel); formPanel.add(ageField);
        formPanel.add(passwordLabel); formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        submitBtn = new JButton("Submit");
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

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitBtn) {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String gender = genderField.getText();
            String ageStr = ageField.getText();
            String password = new String(passwordField.getPassword());

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || gender.isEmpty() || ageStr.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            int age = 0;
            try {
                age = Integer.parseInt(ageStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Age must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }



            // Here you can add the patient to the patient list or database (to be implemented)
            // Add to list
            HospitalData.patientList.add(new Patient(name, email, phone, gender, age, password));
            JOptionPane.showMessageDialog(this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Reset fields
            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            genderField.setText("");
            ageField.setText("");
            passwordField.setText("");

        } else if (e.getSource() == backBtn) {
            this.dispose();
            new HomePageGUI();
        }
    }
}
