package hostel.complaint.and.maintenance.tracking.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Signup extends JFrame implements ActionListener {

    JTextField nameField;
    JTextField usernameField;
    JTextField roomNumberField;

    JPasswordField passwordField;
    JPasswordField confirmPasswordField;

    JLabel roomLabel;

    JLabel userTypeLabel,background;

    Choice userTypeChoice;

    JCheckBox showPassword;
    JCheckBox showConfirmPassword;

    JButton signupButton;
    JButton clearButton;
    JButton backButton;

    String studentname;
    String studentusername;

    Signup(String name, String username) {

        super("Sign Up");
        this.studentname = name;
        this.studentusername = username;


        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon(ClassLoader.getSystemResource("Icons/signup.png"));
        Image image1 = image.getImage().getScaledInstance(1750,1080,Image.SCALE_SMOOTH);
        background = new JLabel(new ImageIcon(image1));
        background.setBounds(0, 0, 1750, 1080);
        add(background);

        JLabel heading = new JLabel("Create Account");
        heading.setBounds(650, 60, 500, 60);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 45));
        heading.setForeground(new Color(25, 65, 120));
        background.add(heading);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(650, 150, 150, 35);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        background.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(820, 150, 320, 40);
        nameField.setFont(new Font("Arial", Font.PLAIN, 18));
        background.add(nameField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(650, 210, 150, 35);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        background.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(820, 210, 320, 40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(650, 270, 150, 35);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        background.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(820, 270, 320, 40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setEchoChar('•');
        background.add(passwordField);

        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(820, 310, 150, 25);
        showPassword.setBackground(Color.LIGHT_GRAY);
        showPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        showPassword.addActionListener(this);
        add(background);

        JLabel confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setBounds(650, 350, 180, 35);
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 18));
        background.add(confirmLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(820, 350, 320, 40);
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 18));
        confirmPasswordField.setEchoChar('•');
        background.add(confirmPasswordField);

        showConfirmPassword = new JCheckBox("Show Password");
        showConfirmPassword.setBounds(820, 390, 150, 25);
        showConfirmPassword.setBackground(Color.LIGHT_GRAY);
        showConfirmPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        showConfirmPassword.addActionListener(this);
        background.add(showConfirmPassword);

        roomLabel = new JLabel("Room Number:");
        roomLabel.setBounds(650, 430, 150, 35);
        roomLabel.setFont(new Font("Arial", Font.BOLD, 20));
        background.add(roomLabel);

        roomNumberField = new JTextField();
        roomNumberField.setBounds(820, 430, 320, 40);
        roomNumberField.setFont(new Font("Arial", Font.PLAIN, 18));
        roomNumberField.setToolTipText("Enter your hostel room number");
        background.add(roomNumberField);

        userTypeLabel = new JLabel("Login As:");
        userTypeLabel.setBounds(650, 500, 150, 35);
        userTypeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        background.add(userTypeLabel);

        userTypeChoice = new Choice();
        userTypeChoice.add("Student");
        userTypeChoice.add("Admin");
        userTypeChoice.setBounds(820, 500, 320, 30);
        userTypeChoice.addItemListener(e -> updateRoomField());
        background.add(userTypeChoice);

        signupButton = new JButton("SIGN UP");
        signupButton.setBounds(650, 550, 180, 45);
        signupButton.setFont(new Font("Arial", Font.BOLD, 18));
        signupButton.setBackground(new Color(0, 122, 255));
        signupButton.setForeground(Color.BLACK);
//        signupButton.setBackground(Color.WHITE);
        signupButton.setFocusPainted(false);
        signupButton.setOpaque(true);
        signupButton.setContentAreaFilled(true);
        signupButton.setBorderPainted(false);
        signupButton.addActionListener(this);
       background.add(signupButton);

        clearButton = new JButton("CLEAR");
        clearButton.setBounds(940, 550, 180, 45);
        clearButton.setFont(new Font("Arial", Font.BOLD, 18));
        clearButton.setBackground(new Color(0, 122, 255));
        clearButton.setForeground(Color.BLACK);
//        clearButton.setBackground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setOpaque(true);
        clearButton.setContentAreaFilled(true);
        clearButton.setBorderPainted(false);
        clearButton.addActionListener(this);
        background.add(clearButton);

        backButton = new JButton("BACK TO LOGIN");
        backButton.setBounds(790, 620, 220, 45);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(0, 122, 255));
        backButton.setForeground(Color.BLACK);
//        backButton.setBackground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setContentAreaFilled(true);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(this);
        background.add(backButton);

        updateRoomField();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void updateRoomField() {

        boolean isStudent =
                userTypeChoice.getSelectedItem().equals("Student");

        roomLabel.setVisible(isStudent);
        roomNumberField.setVisible(isStudent);

        if (isStudent) {

            userTypeLabel.setBounds(650, 500, 150, 35);
            userTypeChoice.setBounds(820, 500, 320, 30);

            signupButton.setBounds(650, 550, 180, 45);
            clearButton.setBounds(940, 550, 180, 45);
            backButton.setBounds(790, 620, 220, 45);

        } else {

            roomNumberField.setText("");

            userTypeLabel.setBounds(650, 430, 150, 35);
            userTypeChoice.setBounds(820, 430, 320, 30);

            signupButton.setBounds(650, 490, 180, 45);
            clearButton.setBounds(940, 490, 180, 45);
            backButton.setBounds(790, 560, 220, 45);
        }




        revalidate();
        repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == showPassword) {

            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }

        } else if (e.getSource() == showConfirmPassword) {

            if (showConfirmPassword.isSelected()) {
                confirmPasswordField.setEchoChar((char) 0);
            } else {
                confirmPasswordField.setEchoChar('•');
            }

        } else if (e.getSource() == signupButton) {

            String name = nameField.getText().trim();

            String username = usernameField.getText().trim();

            String password =
                    new String(passwordField.getPassword()).trim();

            String confirmPassword =
                    new String(confirmPasswordField.getPassword()).trim();

            String roomNumber =
                    roomNumberField.getText().trim();

            String usertype =
                    userTypeChoice.getSelectedItem();

            if (name.isEmpty() ||
                    username.isEmpty() ||
                    password.isEmpty() ||
                    confirmPassword.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all the information!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (usertype.equals("Student") &&
                    roomNumber.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter your room number!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );

                roomNumberField.requestFocus();
                return;
            }

            if (!password.equals(confirmPassword)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Password does not match!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            try {

                Connection con = Con.getConnection();

                if (con == null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Database connection failed!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                String query =
                        "INSERT INTO Signup " +
                                "(name, username, password, usertype, room_number) " +
                                "VALUES (?, ?, ?, ?, ?)";

                PreparedStatement ps =
                        con.prepareStatement(query);

                ps.setString(1, name);
                ps.setString(2, username);
                ps.setString(3, password);
                ps.setString(4, usertype);

                if (usertype.equals("Student")) {
                    ps.setString(5, roomNumber);
                } else {
                    ps.setNull(5, java.sql.Types.VARCHAR);
                }

                ps.executeUpdate();

                ps.close();
                con.close();

                JOptionPane.showMessageDialog(
                        this,
                        "Account created successfully!\nPlease login.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();
                new login("", "");

            } catch (Exception ex) {

                ex.printStackTrace();

                if (ex.getMessage() != null &&
                        ex.getMessage().toLowerCase().contains("duplicate")) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Username already exists!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Database Error: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

        } else if (e.getSource() == clearButton) {

            nameField.setText("");
            usernameField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");
            roomNumberField.setText("");

            showPassword.setSelected(false);
            showConfirmPassword.setSelected(false);

            passwordField.setEchoChar('•');
            confirmPasswordField.setEchoChar('•');

            userTypeChoice.select("Student");

//            updateRoomField();

            nameField.requestFocus();

        } else if (e.getSource() == backButton) {

            dispose();
            new login("", "");
        }
    }

    public static void main(String[] args) {

        new Signup("","");
    }
}