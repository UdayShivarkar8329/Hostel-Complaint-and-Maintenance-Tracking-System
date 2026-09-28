package hostel.complaint.and.maintenance.tracking.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class feedback extends JFrame {

    private JComboBox<String> feedbackType;
    private JTextArea feedbackMessage;

    private StarButton star1;
    private StarButton star2;
    private StarButton star3;
    private StarButton star4;
    private StarButton star5;

    private JButton submitButton;
    private JButton resetButton;

    String studentName;
    String studentUsername;

    private int selectedRating = 0;

    private final Color NAVY = new Color(8, 39, 83);
    private final Color NAVY_DARK = new Color(5, 29, 63);
    private final Color BLUE = new Color(27, 111, 213);
    private final Color TEXT = new Color(15, 45, 82);
    private final Color BORDER = new Color(218, 227, 240);
    private final Color BACKGROUND = new Color(246, 249, 253);
    private final Color STAR_YELLOW = new Color(255, 174, 0);
    private final Color MUTED = new Color(88, 105, 145);
    private final Color STAR_GRAY = new Color(205, 214, 228);

    public feedback(String name, String username) {

        super("Hostel Complaint & Maintenance Tracking System");

        this.studentName = name;
        this.studentUsername = username;


        setSize(1750, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        createUI();

        setVisible(true);
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(BACKGROUND);
        setContentPane(mainPanel);

        createTopBar(mainPanel);
        createSidebar(mainPanel);
        createFeedbackPage(mainPanel);
        createPageTitle();
    }

    private void createTopBar(JPanel parent) {

        JPanel topBar = new JPanel(null);
        topBar.setBackground(NAVY);
        topBar.setBounds(0, 0, 1750, 56);
        parent.add(topBar);

        JLabel homeIcon = new JLabel("⌂");
        homeIcon.setForeground(Color.WHITE);
        homeIcon.setFont(new Font("Arial", Font.PLAIN, 40));
        homeIcon.setBounds(25, 10, 45, 45);
        topBar.add(homeIcon);

        JLabel title = new JLabel("Hostel Management");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(80, 18, 250, 35);
        topBar.add(title);

        JLabel notification = new JLabel("🔔");
        notification.setForeground(Color.WHITE);
        notification.setFont(new Font("Arial", Font.BOLD, 24));
        notification.setBounds(1415, 18, 30, 30);
        topBar.add(notification);

        JLabel notificationCount = new JLabel("");
//        notificationCount.setOpaque(true);
//        notificationCount.setBackground(new Color(235, 60, 60));
//        notificationCount.setForeground(Color.WHITE);
        notificationCount.setFont(new Font("Arial", Font.BOLD, 11));
        notificationCount.setHorizontalAlignment(SwingConstants.CENTER);
        notificationCount.setBounds(1430, 8, 20, 20);
//        notificationCount.setBorder(
//                BorderFactory.createLineBorder(NAVY, 1)
//        );
        topBar.add(notificationCount);

        JLabel userIcon = new JLabel();
        userIcon.setForeground(new Color(235, 240, 248));
        userIcon.setFont(new Font("Arial", Font.PLAIN, 38));
        userIcon.setBounds(1475, 12, 40, 40);
        topBar.add(userIcon);

        JLabel username = new JLabel("WELCOME "+studentName);
        username.setForeground(Color.WHITE);
        username.setFont(new Font("Arial", Font.BOLD, 16));
        username.setBounds(1525, 18, 110, 30);
        topBar.add(username);

        JLabel arrow = new JLabel();
        arrow.setForeground(Color.WHITE);
        arrow.setFont(new Font("Arial", Font.BOLD, 20));
        arrow.setBounds(1635, 17, 30, 30);
        topBar.add(arrow);
    }

    private void createSidebar(JPanel parent) {

        JPanel sidebar = new JPanel(null);
        sidebar.setBackground(new Color(5, 21, 62));
        sidebar.setBounds(0, 56, 238, 968);
        parent.add(sidebar);

        JLabel logo = new JLabel("HOSTEL");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 22));
        logo.setBounds(55, 22, 150, 28);
        sidebar.add(logo);

        JLabel logoSub = new JLabel(
                "<html>COMPLAINT &amp; MAINTENANCE<br>TRACKING SYSTEM</html>"
        );
        logoSub.setForeground(Color.WHITE);
        logoSub.setFont(new Font("Arial", Font.PLAIN, 8));
        logoSub.setBounds(55, 48, 160, 28);
        sidebar.add(logoSub);

        JLabel homeIcon = new JLabel("⌂");
        homeIcon.setForeground(Color.WHITE);
        homeIcon.setFont(new Font("Arial", Font.BOLD, 28));
        homeIcon.setBounds(20, 25, 30, 30);
        sidebar.add(homeIcon);

        addSidebarButton(sidebar, "⌂", "Dashboard", 98, false);
        addSidebarButton(sidebar, "□", "My Complaints", 150, false);
        addSidebarButton(sidebar, "✎", "New Complaint", 202, false);
        addSidebarButton(sidebar, "⚑", "Announcements", 254, false);
        addSidebarButton(sidebar, "♟", "Notifications", 306, false);
        addSidebarButton(sidebar, "★", "Feedback", 358, true);
        addSidebarButton(sidebar, "⇥", "Logout", 410, false);

        JPanel helpPanel = new JPanel(null);
        helpPanel.setBackground(new Color(20, 62, 125));
        helpPanel.setBounds(14, 760, 210, 155);
        sidebar.add(helpPanel);

        JLabel helpIcon = new JLabel("○");
        helpIcon.setForeground(Color.WHITE);
        helpIcon.setFont(new Font("Arial", Font.BOLD, 25));
        helpIcon.setBounds(16, 14, 30, 30);
        helpPanel.add(helpIcon);

        JLabel helpTitle = new JLabel("Need Help?");
        helpTitle.setForeground(Color.WHITE);
        helpTitle.setFont(new Font("Arial", Font.BOLD, 15));
        helpTitle.setBounds(16, 48, 150, 25);
        helpPanel.add(helpTitle);

        JLabel helpText = new JLabel(
                "<html>Our support team is here<br>" +
                        "to assist you 24/7.</html>"
        );
        helpText.setForeground(Color.WHITE);
        helpText.setFont(new Font("Arial", Font.PLAIN, 11));
        helpText.setBounds(16, 73, 175, 35);
        helpPanel.add(helpText);

        JButton contactButton = new JButton("Contact Support");
        contactButton.setForeground(Color.WHITE);
        contactButton.setBackground(new Color(20, 62, 125));
        contactButton.setFont(new Font("Arial", Font.PLAIN, 12));
        contactButton.setBorderPainted(false);
        contactButton.setFocusPainted(false);
        contactButton.setBounds(16, 112, 175, 30);

        contactButton.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Contact Support\n\nOur support team is available 24/7."
                )
        );

        helpPanel.add(contactButton);
    }
    private JPanel createPageTitle() {

        JPanel panel = new JPanel();

        panel.setOpaque(false);

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel("Feekback");

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        32
                )
        );

        title.setForeground(TEXT);
        JLabel subtitle = new JLabel("Hostel Complaint & Maintenance Tracking System");

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(MUTED);

        panel.add(title);

        panel.add(
                Box.createVerticalStrut(3)
        );

        panel.add(subtitle);

        return panel;
    }


    private void addSidebarButton(
            JPanel sidebar,
            String icon,
            String text,
            int y,
            boolean selected
    ) {

        JButton button = new JButton();
        button.setBounds(13, y, 212, 47);
        button.setLayout(null);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);

        if (selected) {
            button.setBackground(new Color(113, 48, 239));
        } else {
            button.setBackground(new Color(5, 21, 62));
        }

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setFont(new Font("Arial", Font.BOLD, 20));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setBounds(17, 8, 30, 30);
        button.add(iconLabel);

        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.BOLD, 14));
        textLabel.setBounds(47, 8, 155, 30);
        button.add(textLabel);

        sidebar.add(button);

        if (text.equals("Dashboard")) {
            button.addActionListener(e -> {
                dispose();
                new dashboard("","","").setVisible(true);
            });
        }

        else if (text.equals("My Complaints")) {
            button.addActionListener(e -> {
                JOptionPane.showMessageDialog(
                        this,
                        "My Complaints"
                );
            });
        }

        else if (text.equals("New Complaint")) {
            button.addActionListener(e -> {
                JOptionPane.showMessageDialog(
                        this,
                        "New Complaint"
                );
            });
        }

        else if (text.equals("Announcements")) {
            button.addActionListener(e -> {
                JOptionPane.showMessageDialog(
                        this,
                        "Announcements"
                );
            });
        }

        else if (text.equals("Notifications")) {
            button.addActionListener(e -> {
                JOptionPane.showMessageDialog(
                        this,
                        "Notifications"
                );
            });
        }

        else if (text.equals("Feedback")) {
            button.addActionListener(e -> {
                JOptionPane.showMessageDialog(
                        this,
                        "You are already on the Feedback page."
                );
            });
        }

        else if (text.equals("Logout")) {
            button.addActionListener(e -> {

                int result = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

                if (result == JOptionPane.YES_OPTION) {
                    dispose();

                    try {
                        new login("", "").setVisible(true);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Unable to open login page."
                        );
                    }
                }
            });
        }
    }
    private void createFeedbackPage(JPanel parent) {

        JPanel content = new JPanel(null);
        content.setBackground(Color.WHITE);
        content.setBounds(260, 80, 1470, 800);

        content.setBorder(
                BorderFactory.createLineBorder(
                        new Color(222, 231, 242),
                        1
                )
        );

        parent.add(content);

        JLabel feedbackIcon = new JLabel("▣");
        feedbackIcon.setForeground(TEXT);
        feedbackIcon.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        38
                )
        );
        feedbackIcon.setBounds(20, 18, 50, 45);
        content.add(feedbackIcon);

        JLabel heading = new JLabel("Feedback");
        heading.setForeground(TEXT);
        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        30
                )
        );
        heading.setBounds(78, 20, 300, 40);
        content.add(heading);

        JLabel description = new JLabel(
                "Your feedback helps us improve the hostel experience for everyone. "
                        + "Share your thoughts, suggestions or report any issues."
        );

        description.setForeground(
                new Color(53, 83, 123)
        );

        description.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        description.setBounds(
                25,
                65,
                1200,
                35
        );

        content.add(description);

        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(Color.WHITE);

        formPanel.setBorder(
                BorderFactory.createLineBorder(
                        new Color(222, 231, 242),
                        1
                )
        );

        formPanel.setBounds(
                18,
                112,
                1298,
                605
        );

        content.add(formPanel);

        JLabel typeLabel = new JLabel("Type");

        typeLabel.setForeground(TEXT);
        typeLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        typeLabel.setBounds(
                18,
                18,
                100,
                30
        );

        formPanel.add(typeLabel);

        feedbackType =
                new JComboBox<>(
                        new String[]{
                                "Select Feedback Type",
                                "General Feedback",
                                "Suggestion",
                                "Complaint"
                        }
                );

        feedbackType.setBounds(
                18,
                50,
                570,
                42
        );

        feedbackType.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        feedbackType.setBackground(Color.WHITE);
        feedbackType.setForeground(TEXT);

        formPanel.add(feedbackType);

        JLabel ratingLabel =
                new JLabel("Rating");

        ratingLabel.setForeground(TEXT);
        ratingLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        ratingLabel.setBounds(
                18,
                115,
                100,
                30
        );

        formPanel.add(ratingLabel);

        star1 = new StarButton();
        star2 = new StarButton();
        star3 = new StarButton();
        star4 = new StarButton();
        star5 = new StarButton();

        star1.setBounds(18, 145, 42, 42);
        star2.setBounds(58, 145, 42, 42);
        star3.setBounds(98, 145, 42, 42);
        star4.setBounds(138, 145, 42, 42);
        star5.setBounds(178, 145, 42, 42);

        formPanel.add(star1);
        formPanel.add(star2);
        formPanel.add(star3);
        formPanel.add(star4);
        formPanel.add(star5);

        star1.addActionListener(e -> setRating(1));
        star2.addActionListener(e -> setRating(2));
        star3.addActionListener(e -> setRating(3));
        star4.addActionListener(e -> setRating(4));
        star5.addActionListener(e -> setRating(5));

        JLabel messageLabel =
                new JLabel("Your Feedback");

        messageLabel.setForeground(TEXT);
        messageLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        messageLabel.setBounds(
                18,
                205,
                200,
                30
        );

        formPanel.add(messageLabel);

        feedbackMessage =
                new JTextArea();

        feedbackMessage.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        feedbackMessage.setForeground(TEXT);
        feedbackMessage.setBackground(Color.WHITE);
        feedbackMessage.setLineWrap(true);
        feedbackMessage.setWrapStyleWord(true);

        feedbackMessage.setBorder(
                new EmptyBorder(
                        12,
                        12,
                        12,
                        12
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(feedbackMessage);

        scrollPane.setBounds(
                18,
                237,
                1255,
                255
        );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER,
                        1
                )
        );

        formPanel.add(scrollPane);

        resetButton =
                new JButton("Reset");

        resetButton.setBounds(
                900,
                525,
                110,
                50
        );

        resetButton.setBackground(Color.WHITE);
        resetButton.setForeground(TEXT);

        resetButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        resetButton.setFocusPainted(false);

        resetButton.setBorder(
                BorderFactory.createLineBorder(
                        BORDER,
                        1
                )
        );

        formPanel.add(resetButton);

        submitButton =
                new JButton("➤   Submit Feedback");

        submitButton.setBounds(
                1025,
                525,
                230,
                50
        );

        submitButton.setBackground(BLUE);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 15));
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setOpaque(true);
        submitButton.setContentAreaFilled(true);
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);
        formPanel.add(submitButton);

        resetButton.addActionListener(
                e -> resetForm()
        );

        submitButton.addActionListener(
                e -> submitFeedback()
        );
    }

    private void setRating(int rating) {

        selectedRating = rating;

        StarButton[] stars = {
                star1,
                star2,
                star3,
                star4,
                star5
        };

        for (int i = 0; i < stars.length; i++) {

            if (i < rating) {
                stars[i].setSelectedStar(true);
            } else {
                stars[i].setSelectedStar(false);
            }
        }
    }

    private void submitFeedback() {

        String type =
                (String) feedbackType.getSelectedItem();

        String message =
                feedbackMessage.getText().trim();

        if (type == null ||
                type.equals("Select Feedback Type")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a feedback type.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (selectedRating == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a rating from 1 to 5 stars.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (message.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your feedback.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            feedbackMessage.requestFocus();

            return;
        }

        String sql =
                "INSERT INTO feedback " +
                        "(feedback_type, rating, message) " +
                        "VALUES (?, ?, ?)";

        try {

            Connection con =
                    Con.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(
                    1,
                    type
            );

            pst.setInt(
                    2,
                    selectedRating
            );

            pst.setString(
                    3,
                    message
            );

            int result =
                    pst.executeUpdate();

            pst.close();
            con.close();

            if (result > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Feedback submitted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                resetForm();
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to submit feedback.\n\n"
                            + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }

    private void resetForm() {

        feedbackType.setSelectedIndex(0);

        feedbackMessage.setText("");

        selectedRating = 0;

        StarButton[] stars = {
                star1,
                star2,
                star3,
                star4,
                star5
        };

        for (StarButton star : stars) {
            star.setSelectedStar(false);
        }

        feedbackType.requestFocus();
    }

    private class StarButton extends JButton {

        private boolean selectedStar = false;

        public StarButton() {

            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );
        }

        public void setSelectedStar(
                boolean selected
        ) {

            selectedStar = selected;
            repaint();
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int centerX =
                    getWidth() / 2;

            int centerY =
                    getHeight() / 2;

            int outerRadius = 16;
            int innerRadius = 7;

            Polygon star =
                    new Polygon();

            for (int i = 0; i < 10; i++) {

                double angle =
                        -Math.PI / 2
                                + i * Math.PI / 5;

                int radius =
                        (i % 2 == 0)
                                ? outerRadius
                                : innerRadius;

                int x =
                        centerX
                                + (int)
                                (Math.cos(angle)
                                        * radius);

                int y =
                        centerY
                                + (int)
                                (Math.sin(angle)
                                        * radius);

                star.addPoint(x, y);
            }

            if (selectedStar) {
                g2.setColor(STAR_YELLOW);
            } else {
                g2.setColor(STAR_GRAY);
            }

            g2.fill(star);

            g2.dispose();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new feedback("","")
        );
    }
}
