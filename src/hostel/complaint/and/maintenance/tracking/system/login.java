package hostel.complaint.and.maintenance.tracking.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class login extends JFrame implements ActionListener {

    JTextField textField1;
    Choice loginChoice;
    JButton b1, b2, b3;
    JPasswordField passwordField;
    JCheckBox showPassword;

    String studentname;
    String studentusername;

    login(String name, String username) {

        super("Login");

        this.studentname = name;
        this.studentusername = username;

        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon(
                ClassLoader.getSystemResource("Icons/login.jpg")
        );

        Image image1 = image.getImage().getScaledInstance(
                1750,
                1080,
                Image.SCALE_SMOOTH
        );

        JLabel background = new JLabel(new ImageIcon(image1));
        background.setBounds(0, 0, 1750, 1080);

        add(background);

        JLabel heading = new JLabel("Welcome Back!");
        heading.setBounds(650, 90, 500, 70);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 50));
        heading.setForeground(new Color(25, 65, 120));
        background.add(heading);

        JLabel subHeading = new JLabel("Sign in to your account");
        subHeading.setBounds(650, 170, 350, 35);
        subHeading.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        subHeading.setForeground(Color.DARK_GRAY);
        background.add(subHeading);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(650, 280, 150, 35);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        background.add(usernameLabel);

        textField1 = new JTextField();
        textField1.setBounds(820, 280, 320, 40);
        textField1.setFont(new Font("Arial", Font.PLAIN, 18));
        background.add(textField1);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(650, 350, 150, 35);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        background.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(820, 350, 320, 40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setEchoChar('•');
        background.add(passwordField);

        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(820, 400, 180, 30);
        showPassword.setOpaque(false);
        showPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        showPassword.addActionListener(this);
        background.add(showPassword);

        JLabel loginLabel = new JLabel("Login As:");
        loginLabel.setBounds(650, 450, 150, 35);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 20));
        background.add(loginLabel);

        loginChoice = new Choice();
        loginChoice.add("Student");
        loginChoice.add("Admin");
        loginChoice.setBounds(820, 450, 320, 30);
        background.add(loginChoice);

        b1 = new JButton("LOGIN");
        b1.setBounds(820, 600, 180, 45);
        b1.setFont(new Font("Arial", Font.BOLD, 20));
        b1.setBackground(new Color(40, 180, 90));
        b1.setForeground(Color.BLACK);
        b1.setOpaque(true);
        b1.setContentAreaFilled(true);
        b1.setBorderPainted(false);
        b1.setFocusPainted(false);
        b1.addActionListener(this);
        background.add(b1);

        b2 = new JButton("CLEAR");
        b2.setBounds(940, 530, 180, 45);
        b2.setFont(new Font("Arial", Font.BOLD, 20));
        b2.setBackground(new Color(0, 122, 255));
        b2.setForeground(Color.WHITE);
        b2.setOpaque(true);
        b2.setContentAreaFilled(true);
        b2.setBorderPainted(false);
        b2.setFocusPainted(false);
        b2.addActionListener(this);
        background.add(b2);

        b3 = new JButton("SIGN UP");
        b3.setBounds(700, 530, 180, 45);
        b3.setFont(new Font("Arial", Font.BOLD, 20));
        b3.setBackground(new Color(0, 122, 255));
        b3.setForeground(Color.WHITE);
        b3.setOpaque(true);
        b3.setContentAreaFilled(true);
        b3.setBorderPainted(false);
        b3.setFocusPainted(false);
        b3.addActionListener(this);
        background.add(b3);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == showPassword) {

            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }

        } else if (e.getSource() == b1) {

            String username = textField1.getText().trim();

            String password =
                    new String(passwordField.getPassword()).trim();

            String usertype =
                    loginChoice.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all information.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {

                con = Con.getConnection();

                if (con == null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Database connection failed.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    return;
                }

                String query;

                if (usertype.equals("Student")) {

                    query =
                            "SELECT id, name, username, usertype, room_number " +
                                    "FROM Signup " +
                                    "WHERE username = ? " +
                                    "AND password = ? " +
                                    "AND usertype = ?";

                } else {

                    query =
                            "SELECT id, name, username, usertype " +
                                    "FROM Signup " +
                                    "WHERE username = ? " +
                                    "AND password = ? " +
                                    "AND usertype = ?";
                }

                ps = con.prepareStatement(query);

                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, usertype);

                rs = ps.executeQuery();

                if (rs.next()) {

                    String loggedInName = rs.getString("name");
                    String loggedInUsername = rs.getString("username");

                    if (usertype.equals("Admin")) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Admin Login Successful!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        dispose();

                        new adminDash(
                                loggedInName,
                                loggedInUsername
                        );

                    } else {

                        String roomNumber = rs.getString("room_number");

                        if (roomNumber == null) {
                            roomNumber = "";
                        }

                        JOptionPane.showMessageDialog(
                                this,
                                "Login Successful!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        dispose();

                        new dashboard(loggedInName, loggedInUsername,roomNumber);
                    }
                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid Username, Password or User Type.",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Database Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

            } finally {

                try {
                    if (rs != null) {
                        rs.close();
                    }
                } catch (Exception ignored) {
                }

                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (Exception ignored) {
                }

                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception ignored) {
                }
            }

        } else if (e.getSource() == b2) {

            textField1.setText("");
            passwordField.setText("");

            loginChoice.select("Student");

            showPassword.setSelected(false);
            passwordField.setEchoChar('•');

            textField1.requestFocus();

        } else if (e.getSource() == b3) {

            dispose();

            new Signup();
        }
    }

    public static void main(String[] args) {

        new login("", "");
    }
}