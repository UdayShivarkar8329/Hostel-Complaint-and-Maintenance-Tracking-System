package hostel.complaint.and.maintenance.tracking.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class complaints extends JFrame implements ActionListener {

    JComboBox<String> categoryBox;
    JComboBox<String> locationBox;

    JTextField titleField;
    JTextArea descriptionArea;

    JRadioButton morningButton;
    JRadioButton afternoonButton;
    JRadioButton eveningButton;
    JRadioButton anytimeButton;

    JButton submitButton;
    JButton resetButton;
    JButton guidelinesButton;
    JButton feedbackButton;

    String studentname;
    String studentusername;
    String roomNumber;

    complaints(String name, String username,String roomNumber) {

        super("Hostel Complaint");

        this.studentname = name;
        this.studentusername = username;
        this.roomNumber = roomNumber;


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);

        ImageIcon backgroundIcon =
                new ImageIcon(ClassLoader.getSystemResource("Icons/login.jpg"));

        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(
                1750,
                1080,
                Image.SCALE_SMOOTH
        );

        JLabel background = new JLabel(new ImageIcon(backgroundImage));
        background.setBounds(0, 0, 1750, 1080);

        add(background);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(null);
        sidebar.setBackground(new Color(8, 39, 83));
        sidebar.setBounds(0, 0, 290, 1080);
        background.add(sidebar);

        JLabel hostelIcon = new JLabel("⌂");
        hostelIcon.setBounds(28, 30, 55, 55);
        hostelIcon.setFont(new Font("Arial", Font.BOLD, 48));
        hostelIcon.setForeground(Color.WHITE);
        sidebar.add(hostelIcon);

        JLabel hostelTitle = new JLabel("Hostel");
        hostelTitle.setBounds(92, 25, 180, 35);
        hostelTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        hostelTitle.setForeground(Color.WHITE);
        sidebar.add(hostelTitle);

        JLabel hostelSubTitle = new JLabel("Complaint & Maintenance");
        hostelSubTitle.setBounds(92, 60, 190, 22);
        hostelSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        hostelSubTitle.setForeground(Color.WHITE);
        sidebar.add(hostelSubTitle);

        JLabel hostelSubTitle2 = new JLabel("Tracking System");
        hostelSubTitle2.setBounds(92, 82, 180, 22);
        hostelSubTitle2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        hostelSubTitle2.setForeground(Color.WHITE);
        sidebar.add(hostelSubTitle2);

        JButton dashboardButton =
                createSideButton("▦   Dashboard", 145);
        sidebar.add(dashboardButton);

        JButton complaintMenuButton =
                createSideButton("✎   New Complaint", 215);
        sidebar.add(complaintMenuButton);

        complaintMenuButton.setBackground(new Color(76, 111, 255));

        JButton myComplaintsButton =
                createSideButton("▤   My Complaints", 285);
        sidebar.add(myComplaintsButton);

        JButton maintenanceButton =
                createSideButton("🔧   Maintenance Requests", 355);
        sidebar.add(maintenanceButton);

        JButton announcementButton =
                createSideButton("⚑   Announcements", 425);
        sidebar.add(announcementButton);

        announcementButton.addActionListener(ActiveEvent ->{
           dispose();
           new announcements(studentname,studentusername,roomNumber);
        });

//        JButton profileButton =
//                createSideButton("♙   Profile", 495);
//        sidebar.add(profileButton);

//        JButton helpButton =
//                createSideButton("?   Help & Support", 565);
//        sidebar.add(helpButton);
        myComplaintsButton.addActionListener(e -> {
            dispose();
            new mycomplaints(studentname, studentusername,roomNumber);
        });


        JButton logoutButton =
                createSideButton("⇥   Logout", 495);
        sidebar.add(logoutButton);

        JPanel topBar = new JPanel();
        topBar.setLayout(null);
        topBar.setBackground(new Color(255, 255, 255, 245));
        topBar.setBounds(290, 0, 1460, 90);
        background.add(topBar);

        JLabel menuIcon = new JLabel("☰");
        menuIcon.setBounds(30, 25, 40, 40);
        menuIcon.setFont(new Font("Arial", Font.PLAIN, 28));
        menuIcon.setForeground(new Color(20, 42, 90));
        topBar.add(menuIcon);

        JLabel pageTitle = new JLabel("Complaint");
        pageTitle.setBounds(75, 22, 300, 45);
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        pageTitle.setForeground(new Color(15, 40, 90));
        topBar.add(pageTitle);

        JLabel userIcon = new JLabel("●");
        userIcon.setHorizontalAlignment(SwingConstants.CENTER);
        userIcon.setBounds(1060, 18, 48, 48);
        userIcon.setFont(new Font("Arial", Font.BOLD, 35));
        userIcon.setForeground(new Color(25, 65, 120));
        topBar.add(userIcon);

//        JLabel username = new JLabel(studentname);
//        username.setBounds(1120, 22, 150, 40);
//        username.setFont(new Font("Segoe UI", Font.BOLD, 17));
//        username.setForeground(new Color(20, 35, 70));
//        topBar.add(username);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(248, 250, 255, 245));
        mainPanel.setBounds(290, 90, 1460, 990);
        background.add(mainPanel);

        JPanel banner = new JPanel();
        banner.setLayout(null);
        banner.setBackground(new Color(239, 242, 255));
        banner.setBounds(25, 25, 1405, 150);
        mainPanel.add(banner);

        JLabel bannerIcon = new JLabel("☑");
        bannerIcon.setBounds(45, 30, 80, 70);
        bannerIcon.setFont(new Font("Arial", Font.BOLD, 60));
        bannerIcon.setForeground(new Color(75, 76, 235));
        banner.add(bannerIcon);

        JLabel bannerTitle = new JLabel("We're here to help!");
        bannerTitle.setBounds(180, 30, 500, 45);
        bannerTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        bannerTitle.setForeground(new Color(15, 40, 90));
        banner.add(bannerTitle);

        JLabel bannerText = new JLabel(
                "<html>Submit your complaint and our team will<br>" +
                        "take care of it as quickly as possible.</html>"
        );

        bannerText.setBounds(180, 78, 500, 60);
        bannerText.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        bannerText.setForeground(new Color(50, 65, 100));
        banner.add(bannerText);

        JLabel betterHostel = new JLabel(
                "<html><center>Better<br>Hostel<br>Life</center></html>"
        );

        betterHostel.setBounds(1150, 20, 120, 110);
        betterHostel.setFont(
                new Font("Segoe UI", Font.ITALIC | Font.BOLD, 22)
        );
        betterHostel.setForeground(new Color(80, 65, 230));
        banner.add(betterHostel);

        JPanel complaintPanel = new JPanel();
        complaintPanel.setLayout(null);
        complaintPanel.setBackground(Color.WHITE);
        complaintPanel.setBounds(25, 195, 650, 760);
        mainPanel.add(complaintPanel);

        JLabel complaintHeading =
                new JLabel("✎   Submit a New Complaint");

        complaintHeading.setBounds(25, 20, 450, 40);
        complaintHeading.setFont(
                new Font("Segoe UI", Font.BOLD, 22)
        );
        complaintHeading.setForeground(new Color(15, 45, 100));
        complaintPanel.add(complaintHeading);

        JLabel categoryLabel = new JLabel("Category");
        categoryLabel.setBounds(25, 80, 250, 30);
        categoryLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );
        categoryLabel.setForeground(new Color(20, 35, 70));
        complaintPanel.add(categoryLabel);

        categoryBox = new JComboBox<>();

        categoryBox.addItem("Select Category");
        categoryBox.addItem("Electrical");
        categoryBox.addItem("Plumbing");
        categoryBox.addItem("Furniture");
        categoryBox.addItem("Room Cleaning");
        categoryBox.addItem("Internet / Wi-Fi");
        categoryBox.addItem("Water Supply");
        categoryBox.addItem("Security");
        categoryBox.addItem("Other");

        categoryBox.setBounds(25, 115, 285, 45);
        categoryBox.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );
        complaintPanel.add(categoryBox);

        JLabel locationLabel = new JLabel("Problem Location");
        locationLabel.setBounds(330, 80, 250, 30);
        locationLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );
        locationLabel.setForeground(new Color(20, 35, 70));
        complaintPanel.add(locationLabel);

        locationBox = new JComboBox<>();

        locationBox.addItem("Select Location");
        locationBox.addItem("My Room");
        locationBox.addItem("Bathroom");
        locationBox.addItem("Mess");
        locationBox.addItem("Corridor");
        locationBox.addItem("Study Room");
        locationBox.addItem("Common Area");
        locationBox.addItem("Hostel Entrance");
        locationBox.addItem("Other");

        locationBox.setBounds(330, 115, 285, 45);
        locationBox.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );
        complaintPanel.add(locationBox);

        JLabel titleLabel = new JLabel("Complaint Title");
        titleLabel.setBounds(25, 180, 250, 30);
        titleLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );
        complaintPanel.add(titleLabel);

        titleField = new JTextField();
        titleField.setBounds(25, 215, 590, 45);
        titleField.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );
        complaintPanel.add(titleField);

        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setBounds(25, 280, 250, 30);
        descriptionLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );
        complaintPanel.add(descriptionLabel);

        descriptionArea = new JTextArea();
        descriptionArea.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane descriptionScroll =
                new JScrollPane(descriptionArea);

        descriptionScroll.setBounds(25, 315, 590, 105);
        complaintPanel.add(descriptionScroll);

        JLabel visitLabel =
                new JLabel("Preferred Visit Time");

        visitLabel.setBounds(25, 440, 300, 30);
        visitLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );
        complaintPanel.add(visitLabel);

        JLabel visitSubLabel =
                new JLabel(
                        "Select the time when it is convenient for maintenance staff to visit."
                );

        visitSubLabel.setBounds(25, 465, 580, 25);
        visitSubLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        visitSubLabel.setForeground(Color.GRAY);
        complaintPanel.add(visitSubLabel);

        morningButton =
                new JRadioButton(
                        "<html><center>☀<br><b>Morning</b><br>" +
                                "8 AM – 12 PM</center></html>"
                );

        afternoonButton =
                new JRadioButton(
                        "<html><center>☀<br><b>Afternoon</b><br>" +
                                "12 PM – 4 PM</center></html>"
                );

        eveningButton =
                new JRadioButton(
                        "<html><center>☀<br><b>Evening</b><br>" +
                                "4 PM – 8 PM</center></html>"
                );

        anytimeButton =
                new JRadioButton(
                        "<html><center>◷<br><b>Anytime</b><br>" +
                                "As per team</center></html>"
                );

        setupRadioButton(morningButton, 25);
        setupRadioButton(afternoonButton, 175);
        setupRadioButton(eveningButton, 325);
        setupRadioButton(anytimeButton, 475);

        ButtonGroup timeGroup = new ButtonGroup();

        timeGroup.add(morningButton);
        timeGroup.add(afternoonButton);
        timeGroup.add(eveningButton);
        timeGroup.add(anytimeButton);

        complaintPanel.add(morningButton);
        complaintPanel.add(afternoonButton);
        complaintPanel.add(eveningButton);
        complaintPanel.add(anytimeButton);

        resetButton = new JButton("↻   Reset");
        resetButton.setBounds(320, 650, 120, 45);
        styleWhiteButton(resetButton);
        resetButton.addActionListener(this);
        complaintPanel.add(resetButton);

        submitButton = new JButton("➤   Submit Complaint");
        submitButton.setBounds(450, 650, 165, 45);
        submitButton.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );
        submitButton.setBackground(new Color(88, 75, 235));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setOpaque(true);
        submitButton.addActionListener(this);
        complaintPanel.add(submitButton);

        JPanel quickPanel = new JPanel();
        quickPanel.setLayout(null);
        quickPanel.setBackground(new Color(241, 250, 248));
        quickPanel.setBounds(700, 195, 730, 265);
        mainPanel.add(quickPanel);

        JLabel quickTitle =
                new JLabel("⚡  Quick Actions");

        quickTitle.setBounds(25, 20, 300, 40);
        quickTitle.setFont(
                new Font("Segoe UI", Font.BOLD, 21)
        );
        quickTitle.setForeground(new Color(15, 50, 90));
        quickPanel.add(quickTitle);

        JLabel quickSubtitle =
                new JLabel("Need help with something else?");

        quickSubtitle.setBounds(25, 55, 350, 25);
        quickSubtitle.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        quickSubtitle.setForeground(Color.GRAY);
        quickPanel.add(quickSubtitle);

        guidelinesButton = new JButton(
                "<html><center><font size='5'>📖</font><br>" +
                        "<b>Guidelines</b><br>" +
                        "<font size='3'>Learn how to raise a complaint<br>" +
                        "and track the process.</font></center></html>"
        );

        guidelinesButton.setBounds(25, 95, 320, 135);
        styleActionButton(guidelinesButton);
        guidelinesButton.addActionListener(this);
        quickPanel.add(guidelinesButton);

        feedbackButton = new JButton(
                "<html><center><font size='5'>💬</font><br>" +
                        "<b>Feedback</b><br>" +
                        "<font size='3'>Share your experience<br>" +
                        "and suggestions.</font></center></html>"
        );

        feedbackButton.setBounds(370, 95, 320, 135);
        styleActionButton(feedbackButton);
        feedbackButton.addActionListener(this);
        quickPanel.add(feedbackButton);

        JPanel recentPanel = new JPanel();
        recentPanel.setLayout(null);
        recentPanel.setBackground(Color.WHITE);
        recentPanel.setBounds(700, 480, 730, 475);
        mainPanel.add(recentPanel);

        JLabel recentTitle =
                new JLabel("▤   Recent Complaints");

        recentTitle.setBounds(25, 20, 350, 35);
        recentTitle.setFont(
                new Font("Segoe UI", Font.BOLD, 21)
        );
        recentTitle.setForeground(new Color(15, 45, 100));
        recentPanel.add(recentTitle);

        JButton viewAll = new JButton("View All");

        viewAll.setBounds(620, 20, 85, 30);
        viewAll.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );
        viewAll.setForeground(new Color(75, 65, 230));
        viewAll.setBackground(Color.WHITE);
        viewAll.setBorderPainted(false);
        viewAll.setFocusPainted(false);
        recentPanel.add(viewAll);

        addComplaintRow(
                recentPanel,
                "💧",
                "Water Leakage in Bathroom",
                "Room 204  •  Plumbing",
                "Pending",
                "24 Aug 2026",
                70
        );

        addComplaintRow(
                recentPanel,
                "💡",
                "Tube Light Not Working",
                "Room 204  •  Electrical",
                "In Progress",
                "22 Aug 2026",
                145
        );

        addComplaintRow(
                recentPanel,
                "🌀",
                "Fan Not Working",
                "Room 204  •  Electrical",
                "Resolved",
                "18 Aug 2026",
                220
        );

        addComplaintRow(
                recentPanel,
                "🚽",
                "Toilet Flush Not Working",
                "Room 202  •  Plumbing",
                "Resolved",
                "15 Aug 2026",
                295
        );

        addComplaintRow(
                recentPanel,
                "🚪",
                "Door Lock Issue",
                "Room 205  •  Maintenance",
                "Pending",
                "10 Aug 2026",
                370
        );

        dashboardButton.addActionListener(e -> {
            dispose();
            new dashboard(studentname,studentusername,roomNumber);
        });

        complaintMenuButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "You are already on the New Complaint page."
            );
        });

        logoutButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                new login("","");
            }
        });

        setVisible(true);
    }

    private JButton createSideButton(String text, int y) {

        JButton button = new JButton(text);

        button.setBounds(12, y, 265, 52);
        button.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );
        button.setForeground(Color.WHITE);
        button.setBackground(
                new Color(8, 39, 83)
        );
        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);

        return button;
    }

    private void setupRadioButton(
            JRadioButton button,
            int x
    ) {

        button.setBounds(x, 500, 135, 125);
        button.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(
                SwingConstants.CENTER
        );
        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 225, 235)
                )
        );
        button.setOpaque(true);

        complaintPanelFix(button);
    }

    private void complaintPanelFix(JRadioButton button) {

        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 225, 235)
                )
        );

        button.setOpaque(true);
    }

    private void styleWhiteButton(JButton button) {

        button.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );
        button.setBackground(Color.WHITE);
        button.setForeground(
                new Color(20, 40, 80)
        );
        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 220, 230)
                )
        );
        button.setOpaque(true);
    }

    private void styleActionButton(JButton button) {

        button.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );
        button.setBackground(Color.WHITE);
        button.setForeground(
                new Color(20, 40, 90)
        );
        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 225, 235)
                )
        );
        button.setHorizontalAlignment(
                SwingConstants.CENTER
        );
        button.setOpaque(true);
    }

    private void addComplaintRow(
            JPanel panel,
            String icon,
            String title,
            String details,
            String status,
            String date,
            int y
    ) {

        JPanel row = new JPanel();
        row.setLayout(null);
        row.setBackground(Color.WHITE);

        row.setBounds(
                15,
                y,
                700,
                70
        );

        panel.add(row);

        JLabel complaintIcon =
                new JLabel(icon);

        complaintIcon.setBounds(
                10,
                10,
                45,
                45
        );

        complaintIcon.setFont(
                new Font("Segoe UI Emoji", Font.PLAIN, 25)
        );

        row.add(complaintIcon);

        JLabel complaintTitle =
                new JLabel(title);

        complaintTitle.setBounds(
                65,
                10,
                330,
                25
        );

        complaintTitle.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        complaintTitle.setForeground(
                new Color(20, 35, 70)
        );

        row.add(complaintTitle);

        JLabel complaintDetails =
                new JLabel(details);

        complaintDetails.setBounds(
                65,
                38,
                330,
                22
        );

        complaintDetails.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        complaintDetails.setForeground(Color.GRAY);

        row.add(complaintDetails);

        JLabel statusLabel =
                new JLabel(status);

        statusLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        statusLabel.setBounds(
                475,
                10,
                100,
                28
        );

        statusLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );

        if (status.equals("Resolved")) {

            statusLabel.setForeground(
                    new Color(20, 140, 70)
            );

            statusLabel.setBackground(
                    new Color(225, 248, 235)
            );

        } else if (status.equals("In Progress")) {

            statusLabel.setForeground(
                    new Color(30, 100, 210)
            );

            statusLabel.setBackground(
                    new Color(225, 240, 255)
            );

        } else {

            statusLabel.setForeground(
                    new Color(220, 125, 20)
            );

            statusLabel.setBackground(
                    new Color(255, 243, 220)
            );
        }

        statusLabel.setOpaque(true);

        row.add(statusLabel);

        JLabel dateLabel =
                new JLabel(date);

        dateLabel.setBounds(
                580,
                38,
                105,
                22
        );

        dateLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 11)
        );

        dateLabel.setForeground(Color.GRAY);

        row.add(dateLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submitButton) {

            String category =
                    (String) categoryBox.getSelectedItem();

            String location =
                    (String) locationBox.getSelectedItem();

            String title =
                    titleField.getText().trim();

            String description =
                    descriptionArea.getText().trim();

            if (category.equals("Select Category") ||
                    location.equals("Select Location") ||
                    title.isEmpty() ||
                    description.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all complaint information!",
                        "Incomplete Information",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (!morningButton.isSelected() &&
                    !afternoonButton.isSelected() &&
                    !eveningButton.isSelected() &&
                    !anytimeButton.isSelected()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select your preferred visit time.",
                        "Select Visit Time",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String visitTime = "";

            if (morningButton.isSelected()) {

                visitTime = "Morning";

            } else if (afternoonButton.isSelected()) {

                visitTime = "Afternoon";

            } else if (eveningButton.isSelected()) {

                visitTime = "Evening";

            } else if (anytimeButton.isSelected()) {

                visitTime = "Anytime";
            }

            String query =
                    "INSERT INTO complaints " +
                            "(name, username, category, location, title, description, visit_time) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";

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

                PreparedStatement ps =
                        con.prepareStatement(query);

                ps.setString(1, studentname);
                ps.setString(2, studentusername);
                ps.setString(3, category);
                ps.setString(4, location);
                ps.setString(5, title);
                ps.setString(6, description);
                ps.setString(7, visitTime);

                ps.executeUpdate();

                ps.close();
                con.close();

                JOptionPane.showMessageDialog(
                        this,
                        "Complaint submitted successfully!\n\n" +
                                "Category: " + category +
                                "\nLocation: " + location +
                                "\nPreferred Visit Time: " + visitTime,
                        "Complaint Submitted",
                        JOptionPane.INFORMATION_MESSAGE
                );

                resetForm();

            } catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Database Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } else if (e.getSource() == resetButton) {

            resetForm();

        } else if (e.getSource() == guidelinesButton) {

            JOptionPane.showMessageDialog(
                    this,
                    "HOSTEL COMPLAINT GUIDELINES\n\n" +
                            "1. Select the correct complaint category.\n\n" +
                            "2. Select the exact location of the problem.\n\n" +
                            "3. Give your complaint a short and clear title.\n\n" +
                            "4. Explain the problem properly in the description.\n\n" +
                            "5. Select a convenient visit time for maintenance staff.\n\n" +
                            "6. Avoid submitting the same complaint multiple times.",
                    "Complaint Guidelines",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else if (e.getSource() == feedbackButton) {

            JTextArea feedbackArea =
                    new JTextArea();

            feedbackArea.setLineWrap(true);
            feedbackArea.setWrapStyleWord(true);

            JScrollPane scrollPane =
                    new JScrollPane(feedbackArea);

            scrollPane.setPreferredSize(
                    new Dimension(400, 150)
            );

            int result = JOptionPane.showConfirmDialog(
                    this,
                    scrollPane,
                    "Share Your Feedback",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {

                if (feedbackArea.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter your feedback.",
                            "Feedback",
                            JOptionPane.WARNING_MESSAGE
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Thank you for your feedback!",
                            "Feedback Submitted",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        }
    }

    private void resetForm() {

        categoryBox.setSelectedIndex(0);
        locationBox.setSelectedIndex(0);
        titleField.setText("");
        descriptionArea.setText("");

        morningButton.setSelected(false);
        afternoonButton.setSelected(false);
        eveningButton.setSelected(false);
        anytimeButton.setSelected(false);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new complaints("Test Student", "test","");
        });
    }
}