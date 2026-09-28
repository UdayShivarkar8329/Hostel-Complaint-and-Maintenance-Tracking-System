package hostel.complaint.and.maintenance.tracking.system;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class hostelRecords extends JFrame {

    // =========================
    // COLORS
    // =========================

    private static final Color NAVY = new Color(8, 39, 83);
    private static final Color BLUE = new Color(25, 112, 232);
    private static final Color LIGHT_BG = new Color(245, 249, 254);
    private static final Color TEXT = new Color(18, 48, 96);
    private static final Color BORDER = new Color(215, 226, 240);

    private static final Color GREEN = new Color(0, 145, 105);
    private static final Color GREEN_BG = new Color(220, 244, 236);

    private static final Color ORANGE = new Color(225, 125, 20);
    private static final Color ORANGE_BG = new Color(255, 239, 215);

    // =========================
    // MYSQL DATABASE
    // =========================

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/Hostel_Complaint_system";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD = "Uday@8888";

    // =========================
    // UI VARIABLES
    // =========================

    private JTextField searchField;

    private JComboBox<String> hostelBox;
    private JComboBox<String> roomTypeBox;
    private JComboBox<String> statusBox;

    private JTable recordsTable;

    private JLabel totalStudentsLabel;
    private JLabel occupiedRoomsLabel;
    private JLabel availableRoomsLabel;
    private JLabel pendingLabel;

    private JLabel showingLabel;
    private JLabel pageLabel;

    private JPanel sidebar;
    private JPanel mainContent;

    private String adminName;
    private String adminUsername;

    // =========================
    // DATA
    // =========================

    private final List<Object[]> allRecords =
            new ArrayList<>();

    private List<Object[]> filteredRecords =
            new ArrayList<>();

    private int currentPage = 1;

    private final int pageSize = 8;

    // =========================
    // CONSTRUCTOR
    // =========================

    public hostelRecords(String name, String username) {

        adminName = name == null ? "" : name;
        adminUsername = username == null ? "" : username;

        setTitle("Hostel Records - Admin Panel");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(1200, 750));

        setLayout(new BorderLayout());

        getContentPane().setBackground(LIGHT_BG);

        // Database
        initializeDatabase();

        // UI
        createSidebar();
        createMainArea();

        // Load records
        loadRecordsFromDatabase();

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLocationRelativeTo(null);

        setVisible(true);
    }

    // =========================================================
    // DATABASE
    // =========================================================

    private void initializeDatabase() {

        String createTable =
                "CREATE TABLE IF NOT EXISTS hostel_records (" +
                        "record_id VARCHAR(10) PRIMARY KEY," +
                        "student_name VARCHAR(50) NOT NULL," +
                        "student_id VARCHAR(20) UNIQUE," +
                        "room_no VARCHAR(10) NOT NULL," +
                        "block VARCHAR(20) NOT NULL," +
                        "room_type VARCHAR(20) NOT NULL," +
                        "contact VARCHAR(15)," +
                        "status VARCHAR(20) NOT NULL" +
                        ")";

        try (Connection con = getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate(createTable);

            // Add sample records only when table is empty
            try (ResultSet rs =
                         st.executeQuery(
                                 "SELECT COUNT(*) FROM hostel_records")) {

                rs.next();

                if (rs.getInt(1) == 0) {

                    insertInitialRecords(con);
                }
            }

        } catch (SQLException ex) {

            showDatabaseError(ex);
        }
    }

    private void insertInitialRecords(Connection con)
            throws SQLException {

        String sql =
                "INSERT INTO hostel_records " +
                        "(record_id, student_name, student_id, room_no, " +
                        "block, room_type, contact, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps =
                     con.prepareStatement(sql)) {

            /*
             * IMPORTANT:
             * Vacant rooms use empty strings instead of NULL
             * because your database has:
             *
             * student_name VARCHAR(50) NOT NULL
             */

            addBatch(
                    ps,
                    "ST001",
                    "Rohit Sharma",
                    "CSE24001",
                    "B-101",
                    "Block B",
                    "2 Sharing",
                    "9876543210",
                    "Occupied"
            );

            addBatch(
                    ps,
                    "ST002",
                    "Priya Singh",
                    "CSE24002",
                    "A-203",
                    "Block A",
                    "3 Sharing",
                    "8765432109",
                    "Occupied"
            );

            addBatch(
                    ps,
                    "ST003",
                    "Amit Kumar",
                    "CSE24003",
                    "C-302",
                    "Block C",
                    "2 Sharing",
                    "9123456789",
                    "Occupied"
            );

            addBatch(
                    ps,
                    "ST004",
                    "",
                    null,
                    "A-104",
                    "Block A",
                    "4 Sharing",
                    null,
                    "Vacant"
            );

            addBatch(
                    ps,
                    "ST005",
                    "Sahil Verma",
                    "CSE24005",
                    "B-205",
                    "Block B",
                    "2 Sharing",
                    "8877665543",
                    "Occupied"
            );

            addBatch(
                    ps,
                    "ST006",
                    "Isha Patil",
                    "CSE24006",
                    "C-108",
                    "Block C",
                    "3 Sharing",
                    "7766554432",
                    "Occupied"
            );

            addBatch(
                    ps,
                    "ST007",
                    "Karan Mehta",
                    "CSE24007",
                    "D-201",
                    "Block D",
                    "2 Sharing",
                    "6655443322",
                    "Occupied"
            );

            addBatch(
                    ps,
                    "ST008",
                    "",
                    null,
                    "B-304",
                    "Block B",
                    "4 Sharing",
                    null,
                    "Vacant"
            );

            ps.executeBatch();
        }
    }

    private void addBatch(
            PreparedStatement ps,
            String recordId,
            String name,
            String studentId,
            String room,
            String block,
            String roomType,
            String contact,
            String status)
            throws SQLException {

        ps.setString(1, recordId);

        // student_name cannot be NULL in your database
        ps.setString(
                2,
                name == null ? "" : name
        );

        if (studentId == null ||
                studentId.trim().isEmpty()) {

            ps.setNull(3, Types.VARCHAR);

        } else {

            ps.setString(3, studentId);
        }

        ps.setString(4, room);
        ps.setString(5, block);
        ps.setString(6, roomType);

        if (contact == null ||
                contact.trim().isEmpty()) {

            ps.setNull(7, Types.VARCHAR);

        } else {

            ps.setString(7, contact);
        }

        ps.setString(8, status);

        ps.addBatch();
    }

    private Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASSWORD
        );
    }

    private void showDatabaseError(SQLException ex) {

        JOptionPane.showMessageDialog(
                this,
                "Database operation failed.\n\n" +
                        "Please check:\n" +
                        "• MySQL Server is running\n" +
                        "• Database name is correct\n" +
                        "• Username and password are correct\n" +
                        "• MySQL Connector/J is added\n\n" +
                        "Error:\n" +
                        ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

    private void createSidebar() {

        sidebar = new JPanel(new BorderLayout());

        sidebar.setBackground(NAVY);

        sidebar.setPreferredSize(
                new Dimension(264, 0)
        );

        add(sidebar, BorderLayout.WEST);

        JPanel top = new JPanel(null);

        top.setOpaque(false);

        top.setPreferredSize(
                new Dimension(264, 500)
        );

        JLabel homeIcon = new JLabel("⌂");

        homeIcon.setForeground(Color.WHITE);

        homeIcon.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        43
                )
        );

        homeIcon.setBounds(20, 18, 45, 45);

        top.add(homeIcon);

        JLabel hostel = new JLabel("HOSTEL");

        hostel.setForeground(Color.WHITE);

        hostel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        21
                )
        );

        hostel.setBounds(78, 18, 150, 28);

        top.add(hostel);

        JLabel line1 =
                new JLabel("Complaint & Maintenance");

        line1.setForeground(Color.WHITE);

        line1.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        line1.setBounds(78, 44, 180, 20);

        top.add(line1);

        JLabel line2 =
                new JLabel("Tracking System");

        line2.setForeground(Color.WHITE);

        line2.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        line2.setBounds(78, 62, 160, 20);

        top.add(line2);

        JLabel adminPanel =
                new JLabel("ADMIN PANEL");

        adminPanel.setForeground(Color.WHITE);

        adminPanel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        adminPanel.setBounds(22, 116, 150, 25);

        top.add(adminPanel);

        JSeparator separator =
                new JSeparator();

        separator.setForeground(
                new Color(62, 91, 128)
        );

        separator.setBounds(22, 143, 218, 1);

        top.add(separator);

        createSidebarButton(
                top,
                "⌂",
                "Admin Home",
                155,
                false
        );

        createSidebarButton(
                top,
                "▤",
                "Complaint Center",
                203,
                false
        );

        createSidebarButton(
                top,
                "⚒",
                "Maintenance Team",
                251,
                false
        );

        createSidebarButton(
                top,
                "♜",
                "Hostel Records",
                299,
                true
        );

        createSidebarButton(
                top,
                "⚑",
                "Communication",
                347,
                false
        );

        JSeparator separator2 =
                new JSeparator();

        separator2.setForeground(
                new Color(62, 91, 128)
        );

        separator2.setBounds(22, 417, 218, 1);

        top.add(separator2);

        createSidebarButton(
                top,
                "⇥",
                "Logout",
                437,
                false
        );

        sidebar.add(
                top,
                BorderLayout.NORTH
        );

        JPanel bottom = new JPanel();

        bottom.setOpaque(false);

        bottom.setLayout(
                new BoxLayout(
                        bottom,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel building =
                new JLabel("▥");

        building.setForeground(
                new Color(91, 132, 180)
        );

        building.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        85
                )
        );

        building.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel better =
                new JLabel("Better Hostel");

        better.setForeground(
                new Color(155, 196, 239)
        );

        better.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        better.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel happier =
                new JLabel("Happier Students");

        happier.setForeground(
                new Color(155, 196, 239)
        );

        happier.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        happier.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        bottom.add(building);

        bottom.add(
                Box.createVerticalStrut(5)
        );

        bottom.add(better);

        bottom.add(happier);

        bottom.add(
                Box.createVerticalStrut(85)
        );

        sidebar.add(
                bottom,
                BorderLayout.SOUTH
        );
    }

    private void createSidebarButton(
            JPanel parent,
            String icon,
            String text,
            int y,
            boolean selected) {

        JPanel button =
                new JPanel(null);

        button.setBackground(
                selected ? BLUE : NAVY
        );

        button.setBounds(
                10,
                y,
                240,
                44
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        JLabel iconLabel =
                new JLabel(icon);

        iconLabel.setForeground(
                Color.WHITE
        );

        iconLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        21
                )
        );

        iconLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        iconLabel.setBounds(
                12,
                4,
                32,
                34
        );

        button.add(iconLabel);

        JLabel textLabel =
                new JLabel(text);

        textLabel.setForeground(
                Color.WHITE
        );

        textLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        textLabel.setBounds(
                53,
                4,
                175,
                34
        );

        button.add(textLabel);

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e) {

                        if (!selected) {
                            button.setBackground(
                                    new Color(
                                            17,
                                            61,
                                            116
                                    )
                            );
                        }
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e) {

                        if (!selected) {
                            button.setBackground(
                                    NAVY
                            );
                        }
                    }

                    @Override
                    public void mouseClicked(
                            MouseEvent e) {

                        handleSidebarAction(
                                text
                        );
                    }
                }
        );

        parent.add(button);
    }

    private void handleSidebarAction(
            String text) {

        if ("Admin Home".equals(text)) {

            dispose();

            new adminDash(
                    adminName,
                    adminUsername
            );

        } else if ("Complaint Center".equals(text)) {

            dispose();

            complaintcenter frame =
                    new complaintcenter(
                            adminName,
                            adminUsername
                    );

            frame.setVisible(true);

        } else if ("Maintenance Team".equals(text)) {

            dispose();

            new maintenance(
                    adminName,
                    adminUsername
            );

        } else if ("Communication".equals(text)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Communication panel is ready for integration.",
                    "Communication",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else if ("Logout".equals(text)) {

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to logout?",
                            "Logout",
                            JOptionPane.YES_NO_OPTION
                    );

            if (result ==
                    JOptionPane.YES_OPTION) {

                dispose();

                new login("", "");
            }
        }
    }

    // =========================================================
    // MAIN AREA
    // =========================================================

    private void createMainArea() {

        JPanel mainArea =
                new JPanel(
                        new BorderLayout()
                );

        mainArea.setBackground(
                LIGHT_BG
        );

        add(
                mainArea,
                BorderLayout.CENTER
        );

        createTopBar(mainArea);

        mainContent =
                new JPanel();

        mainContent.setBackground(
                LIGHT_BG
        );

        mainContent.setLayout(
                new BoxLayout(
                        mainContent,
                        BoxLayout.Y_AXIS
                )
        );

        mainContent.setBorder(
                new EmptyBorder(
                        18,
                        20,
                        20,
                        20
                )
        );

        JPanel headingPanel =
                new JPanel(
                        new BorderLayout()
                );

        headingPanel.setOpaque(false);

        headingPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        72
                )
        );

        JPanel headingText =
                new JPanel();

        headingText.setOpaque(false);

        headingText.setLayout(
                new BoxLayout(
                        headingText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel heading =
                new JLabel(
                        "Hostel Records"
                );

        heading.setForeground(TEXT);

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        31
                )
        );

        JLabel description =
                new JLabel(
                        "View and manage student, room and hostel allocation records."
                );

        description.setForeground(
                new Color(
                        40,
                        72,
                        123
                )
        );

        description.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        headingText.add(heading);

        headingText.add(
                Box.createVerticalStrut(2)
        );

        headingText.add(description);

        RoundedButton addStudent =
                new RoundedButton(
                        "⊕  Add Student",
                        BLUE,
                        Color.WHITE
                );

        addStudent.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        addStudent.setPreferredSize(
                new Dimension(
                        145,
                        46
                )
        );

        addStudent.addActionListener(
                e -> showAddStudentDialog()
        );

        headingPanel.add(
                headingText,
                BorderLayout.WEST
        );

        headingPanel.add(
                addStudent,
                BorderLayout.EAST
        );

        mainContent.add(headingPanel);

        mainContent.add(
                Box.createVerticalStrut(10)
        );

        JPanel filter =
                createFilterPanel();

        filter.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        92
                )
        );

        mainContent.add(filter);

        mainContent.add(
                Box.createVerticalStrut(15)
        );

        JPanel cards =
                createSummaryCards();

        cards.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        126
                )
        );

        mainContent.add(cards);

        mainContent.add(
                Box.createVerticalStrut(15)
        );

        JPanel table =
                createRecordsTable();

        table.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        485
                )
        );

        mainContent.add(table);

        JScrollPane scrollPane =
                new JScrollPane(
                        mainContent,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        mainArea.add(
                scrollPane,
                BorderLayout.CENTER
        );
    }

    // =========================================================
    // TOP BAR
    // =========================================================

    private void createTopBar(
            JPanel mainArea) {

        JPanel topBar =
                new JPanel(
                        new BorderLayout()
                );

        topBar.setBackground(Color.WHITE);

        topBar.setPreferredSize(
                new Dimension(
                        0,
                        68
                )
        );

        topBar.setBorder(
                BorderFactory.createMatteBorder(
                        0,
                        0,
                        1,
                        0,
                        new Color(
                                224,
                                232,
                                243
                        )
                )
        );

        JButton menu =
                new JButton("☰");

        menu.setForeground(NAVY);

        menu.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        28
                )
        );

        menu.setBorder(
                new EmptyBorder(
                        0,
                        27,
                        0,
                        0
                )
        );

        menu.setContentAreaFilled(false);

        menu.setBorderPainted(false);

        menu.setFocusPainted(false);

        menu.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        menu.addActionListener(
                e -> {

                    sidebar.setVisible(
                            !sidebar.isVisible()
                    );

                    revalidate();

                    repaint();
                }
        );

        JPanel right =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                14,
                                12
                        )
                );

        right.setOpaque(false);

        JButton notification =
                new JButton("🔔");

        notification.setForeground(NAVY);

        notification.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        27
                )
        );

        notification.setBorderPainted(false);

        notification.setContentAreaFilled(false);

        notification.setFocusPainted(false);

        notification.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        notification.addActionListener(
                e ->
                        JOptionPane.showMessageDialog(
                                this,
                                "No new hostel record notifications.",
                                "Notifications",
                                JOptionPane.INFORMATION_MESSAGE
                        )
        );

        JLabel count =
                new JLabel();

        count.setForeground(Color.WHITE);

//        count.setBackground(
//                new Color(
//                        235,
//                        55,
//                        55
//                )
//        );

        count.setOpaque(true);

        count.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        count.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        count.setPreferredSize(
                new Dimension(
                        16,
                        16
                )
        );

        JPanel notificationPanel =
                new JPanel(
                        new BorderLayout()
                );

        notificationPanel.setOpaque(false);

        notificationPanel.add(
                notification,
                BorderLayout.CENTER
        );

        notificationPanel.add(
                count,
                BorderLayout.NORTH
        );

        JButton profile =
                new JButton("●");

        profile.setForeground(NAVY);

        profile.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );

        profile.setBackground(
                new Color(
                        232,
                        240,
                        252
                )
        );

        profile.setPreferredSize(
                new Dimension(
                        42,
                        42
                )
        );

        profile.setBorderPainted(false);

        profile.setFocusPainted(false);

        profile.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        profile.addActionListener(
                e ->
                        JOptionPane.showMessageDialog(
                                this,
                                "Admin: " +
                                        (
                                                adminName.isBlank()
                                                        ? "Admin"
                                                        : adminName
                                        ) +
                                        "\nUsername: " +
                                        (
                                                adminUsername.isBlank()
                                                        ? "Not available"
                                                        : adminUsername
                                        ),
                                "Admin Profile",
                                JOptionPane.INFORMATION_MESSAGE
                        )
        );

        JLabel admin =
                new JLabel("Admin");

        admin.setForeground(TEXT);

        admin.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        JLabel arrow =
                new JLabel("⌄");

        arrow.setForeground(NAVY);

        arrow.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        right.add(notificationPanel);

        right.add(profile);

        right.add(admin);

        right.add(arrow);

        right.add(
                Box.createHorizontalStrut(15)
        );

        topBar.add(
                menu,
                BorderLayout.WEST
        );

        topBar.add(
                right,
                BorderLayout.EAST
        );

        mainArea.add(
                topBar,
                BorderLayout.NORTH
        );
    }

    // =========================================================
    // FILTER
    // =========================================================

    private JPanel createFilterPanel() {

        RoundedPanel panel =
                new RoundedPanel(
                        10,
                        Color.WHITE,
                        BORDER
                );

        panel.setLayout(
                new GridBagLayout()
        );

        panel.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        0,
                        5,
                        0,
                        5
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        searchField =
                new JTextField();

        searchField.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        searchField.setForeground(
                new Color(
                        86,
                        112,
                        155
                )
        );

        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        new RoundedBorder(
                                BORDER,
                                8
                        ),
                        new EmptyBorder(
                                0,
                                38,
                                0,
                                8
                        )
                )
        );

        searchField.setToolTipText(
                "Search by student name, student ID or room number"
        );

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout()
                );

        searchPanel.setOpaque(false);

        JLabel searchIcon =
                new JLabel("⌕");

        searchIcon.setForeground(NAVY);

        searchIcon.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        searchIcon.setBorder(
                new EmptyBorder(
                        0,
                        10,
                        0,
                        5
                )
        );

        searchPanel.add(
                searchIcon,
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2.5;

        panel.add(
                searchPanel,
                gbc
        );

        hostelBox =
                createComboBox(
                        new String[]{
                                "All Hostels",
                                "Block A",
                                "Block B",
                                "Block C",
                                "Block D"
                        }
                );

        roomTypeBox =
                createComboBox(
                        new String[]{
                                "All Room Types",
                                "2 Sharing",
                                "3 Sharing",
                                "4 Sharing"
                        }
                );

        statusBox =
                createComboBox(
                        new String[]{
                                "All Status",
                                "Vacant",
                                "Occupied"
                        }
                );

        gbc.gridx = 1;
        gbc.weightx = 1;

        panel.add(
                createFilterGroup(
                        "Hostel / Block",
                        hostelBox
                ),
                gbc
        );

        gbc.gridx = 2;

        panel.add(
                createFilterGroup(
                        "Room Type",
                        roomTypeBox
                ),
                gbc
        );

        gbc.gridx = 3;

        panel.add(
                createFilterGroup(
                        "Status",
                        statusBox
                ),
                gbc
        );

        RoundedButton searchButton =
                new RoundedButton(
                        "⌕  Search",
                        BLUE,
                        Color.WHITE
                );

        searchButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        searchButton.setPreferredSize(
                new Dimension(
                        120,
                        40
                )
        );

        searchButton.addActionListener(
                e -> filterRecords()
        );

        gbc.gridx = 4;
        gbc.weightx = 0.6;

        panel.add(
                searchButton,
                gbc
        );

        RoundedButton resetButton =
                new RoundedButton(
                        "⟳  Reset",
                        Color.WHITE,
                        NAVY
                );

        resetButton.setBorderColor(BORDER);

        resetButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        resetButton.setPreferredSize(
                new Dimension(
                        105,
                        40
                )
        );

        resetButton.addActionListener(
                e -> {

                    searchField.setText("");

                    hostelBox.setSelectedIndex(0);

                    roomTypeBox.setSelectedIndex(0);

                    statusBox.setSelectedIndex(0);

                    currentPage = 1;

                    applyFilters();
                }
        );

        gbc.gridx = 5;
        gbc.weightx = 0.5;

        panel.add(
                resetButton,
                gbc
        );

        return panel;
    }

    private JPanel createFilterGroup(
            String title,
            JComboBox<String> combo) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                4
                        )
                );

        panel.setOpaque(false);

        JLabel label =
                new JLabel(title);

        label.setForeground(TEXT);

        label.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        panel.add(
                label,
                BorderLayout.NORTH
        );

        panel.add(
                combo,
                BorderLayout.CENTER
        );

        return panel;
    }

    private JComboBox<String> createComboBox(
            String[] items) {

        JComboBox<String> combo =
                new JComboBox<>(items);

        combo.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        combo.setForeground(TEXT);

        combo.setBackground(Color.WHITE);

        combo.setBorder(
                new RoundedBorder(
                        BORDER,
                        8
                )
        );

        combo.setPreferredSize(
                new Dimension(
                        145,
                        40
                )
        );

        return combo;
    }

    // =========================================================
    // SUMMARY CARDS
    // =========================================================

    private JPanel createSummaryCards() {

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                16,
                                0
                        )
                );

        cards.setOpaque(false);

        JPanel totalCard =
                createSummaryCard(
                        "♟",
                        "Total Students",
                        "0",
                        "Database records",
                        new Color(
                                231,
                                243,
                                255
                        ),
                        new Color(
                                71,
                                145,
                                232
                        )
                );

        totalStudentsLabel =
                findValueLabel(totalCard);

        JPanel occupiedCard =
                createSummaryCard(
                        "▣",
                        "Occupied Rooms",
                        "0",
                        "Currently occupied",
                        new Color(
                                255,
                                247,
                                237
                        ),
                        new Color(
                                244,
                                174,
                                82
                        )
                );

        occupiedRoomsLabel =
                findValueLabel(occupiedCard);

        JPanel availableCard =
                createSummaryCard(
                        "⌂",
                        "Available Rooms",
                        "0",
                        "Currently vacant",
                        new Color(
                                237,
                                250,
                                246
                        ),
                        new Color(
                                63,
                                184,
                                153
                        )
                );

        availableRoomsLabel =
                findValueLabel(availableCard);

        JPanel pendingCard =
                createSummaryCard(
                        "◷",
                        "Pending Allocations",
                        "0",
                        "Vacant rooms",
                        new Color(
                                246,
                                241,
                                255
                        ),
                        new Color(
                                153,
                                121,
                                226
                        )
                );

        pendingLabel =
                findValueLabel(pendingCard);

        cards.add(totalCard);
        cards.add(occupiedCard);
        cards.add(availableCard);
        cards.add(pendingCard);

        return cards;
    }

    private JLabel findValueLabel(
            JPanel card) {

        for (Component c :
                card.getComponents()) {

            if (c instanceof JPanel) {

                JPanel panel =
                        (JPanel) c;

                for (Component inner :
                        panel.getComponents()) {

                    if (inner instanceof JLabel) {

                        JLabel label =
                                (JLabel) inner;

                        if (label.getFont()
                                .getSize() >= 25) {

                            return label;
                        }
                    }
                }
            }
        }

        return new JLabel("0");
    }

    private JPanel createSummaryCard(
            String icon,
            String title,
            String value,
            String growth,
            Color background,
            Color iconColor) {

        RoundedPanel card =
                new RoundedPanel(
                        10,
                        background,
                        new Color(
                                205,
                                220,
                                238
                        )
                );

        card.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        0,
                        10,
                        0,
                        10
                );

        JPanel iconPanel =
                new JPanel(
                        new BorderLayout()
                );

        iconPanel.setBackground(
                iconColor
        );

        iconPanel.setPreferredSize(
                new Dimension(
                        50,
                        50
                )
        );

        JLabel iconLabel =
                new JLabel(icon);

        iconLabel.setForeground(
                Color.WHITE
        );

        iconLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );

        iconLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        iconPanel.add(
                iconLabel,
                BorderLayout.CENTER
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.CENTER;

        card.add(
                iconPanel,
                gbc
        );

        JPanel text =
                new JPanel();

        text.setOpaque(false);

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(TEXT);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setForeground(TEXT);

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        29
                )
        );

        JLabel growthLabel =
                new JLabel(growth);

        growthLabel.setForeground(GREEN);

        growthLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        text.add(titleLabel);

        text.add(
                Box.createVerticalStrut(2)
        );

        text.add(valueLabel);

        text.add(
                Box.createVerticalStrut(2)
        );

        text.add(growthLabel);

        gbc.gridx = 1;

        gbc.gridheight = 1;

        gbc.weightx = 1;

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        card.add(
                text,
                gbc
        );

        return card;
    }

    // =========================================================
    // TABLE
    // =========================================================

    private JPanel createRecordsTable() {

        RoundedPanel panel =
                new RoundedPanel(
                        10,
                        Color.WHITE,
                        BORDER
                );

        panel.setLayout(
                new BorderLayout(
                        0,
                        8
                )
        );

        panel.setBorder(
                new EmptyBorder(
                        14,
                        10,
                        10,
                        10
                )
        );

        JLabel heading =
                new JLabel(
                        "All Hostel Records"
                );

        heading.setForeground(TEXT);

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        19
                )
        );

        panel.add(
                heading,
                BorderLayout.NORTH
        );

        String[] columns = {
                "ID",
                "Student Name",
                "Student ID",
                "Room No.",
                "Block",
                "Room Type",
                "Contact",
                "Status",
                "Action"
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        new Object[0][columns.length],
                        columns
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        recordsTable =
                new JTable(model);

        recordsTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        recordsTable.setForeground(TEXT);

        recordsTable.setRowHeight(42);

        recordsTable.setShowGrid(true);

        recordsTable.setGridColor(
                new Color(
                        232,
                        238,
                        247
                )
        );

        recordsTable.setIntercellSpacing(
                new Dimension(0, 0)
        );

        recordsTable.setSelectionBackground(
                new Color(
                        235,
                        243,
                        255
                )
        );

        recordsTable.getTableHeader()
                .setReorderingAllowed(false);

        recordsTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                11
                        )
                );

        recordsTable.getTableHeader()
                .setForeground(TEXT);

        recordsTable.getTableHeader()
                .setBackground(
                        new Color(
                                245,
                                248,
                                253
                        )
                );

        recordsTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                38
                        )
                );

        // Increased Action width so all 3 buttons fit
        int[] widths = {
                60,
                145,
                110,
                85,
                90,
                105,
                125,
                90,
                260
        };

        for (int i = 0;
             i < widths.length;
             i++) {

            recordsTable
                    .getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(
                            widths[i]
                    );
        }

        DefaultTableCellRenderer normalRenderer =
                new DefaultTableCellRenderer() {

                    @Override
                    public Component
                    getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean selected,
                            boolean focused,
                            int row,
                            int column) {

                        Component component =
                                super
                                        .getTableCellRendererComponent(
                                                table,
                                                value,
                                                selected,
                                                focused,
                                                row,
                                                column
                                        );

                        setBorder(
                                new EmptyBorder(
                                        0,
                                        8,
                                        0,
                                        5
                                )
                        );

                        setHorizontalAlignment(
                                SwingConstants.LEFT
                        );

                        component.setBackground(
                                selected
                                        ? new Color(
                                        235,
                                        243,
                                        255
                                )
                                        : Color.WHITE
                        );

                        component.setForeground(
                                TEXT
                        );

                        return component;
                    }
                };

        for (int i = 0; i < 7; i++) {

            recordsTable
                    .getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(
                            normalRenderer
                    );
        }

        recordsTable
                .getColumnModel()
                .getColumn(7)
                .setCellRenderer(
                        new StatusRenderer()
                );

        recordsTable
                .getColumnModel()
                .getColumn(8)
                .setCellRenderer(
                        new ActionRenderer()
                );

        /*
         * IMPORTANT:
         *
         * JTable renderer buttons are visual components.
         * We therefore handle clicks directly from the JTable.
         *
         * This prevents:
         *
         * IllegalComponentStateException:
         * component must be showing on the screen
         *
         * especially for the ⋯ popup.
         */

        recordsTable.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e) {

                        handleActionColumnClick(e);
                    }
                }
        );

        JScrollPane scrollPane =
                new JScrollPane(recordsTable);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        JPanel bottom =
                new JPanel(
                        new BorderLayout()
                );

        bottom.setOpaque(false);

        bottom.setBorder(
                new EmptyBorder(
                        7,
                        5,
                        0,
                        0
                )
        );

        showingLabel =
                new JLabel(
                        "Showing 0 records"
                );

        showingLabel.setForeground(
                new Color(
                        54,
                        82,
                        126
                )
        );

        showingLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        JPanel pagination =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                7,
                                0
                        )
                );

        pagination.setOpaque(false);

        RoundedButton previous =
                createPageButton("‹");

        previous.addActionListener(
                e -> changePage(-1)
        );

        RoundedButton next =
                createPageButton("›");

        next.addActionListener(
                e -> changePage(1)
        );

        pageLabel =
                new JLabel("1");

        pageLabel.setForeground(NAVY);

        pageLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        pageLabel.setBorder(
                new EmptyBorder(
                        8,
                        8,
                        8,
                        8
                )
        );

        pagination.add(previous);

        pagination.add(pageLabel);

        pagination.add(next);

        bottom.add(
                showingLabel,
                BorderLayout.WEST
        );

        bottom.add(
                pagination,
                BorderLayout.EAST
        );

        panel.add(
                bottom,
                BorderLayout.SOUTH
        );

        return panel;
    }

    // =========================================================
    // HANDLE ACTION BUTTON CLICK
    // =========================================================

    private void handleActionColumnClick(
            MouseEvent e) {

        if (recordsTable == null) {
            return;
        }

        int viewRow =
                recordsTable.rowAtPoint(
                        e.getPoint()
                );

        int viewColumn =
                recordsTable.columnAtPoint(
                        e.getPoint()
                );

        // Action column is column 8
        if (viewRow < 0 ||
                viewColumn != 8) {

            return;
        }

        Rectangle cellRect =
                recordsTable.getCellRect(
                        viewRow,
                        viewColumn,
                        true
                );

        int relativeX =
                e.getX() - cellRect.x;

        /*
         * Action layout:
         *
         * View  = 95 px
         * Gap   = 7 px
         * Edit  = 95 px
         * Gap   = 7 px
         * More  = 40 px
         *
         * We use slightly larger click areas
         * for easier clicking.
         */

        int modelRow =
                recordsTable.convertRowIndexToModel(
                        viewRow
                );

        if (relativeX < 105) {

            // VIEW
            showStudentDetails(modelRow);

        } else if (relativeX < 210) {

            // EDIT
            showEditOptions(modelRow);

        } else {

            // MORE
            showMoreMenu(
                    recordsTable,
                    viewRow,
                    modelRow
            );
        }
    }

    // =========================================================
    // STATUS RENDERER
    // =========================================================

    private class StatusRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component
        getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focused,
                int row,
                int column) {

            String status =
                    value == null
                            ? ""
                            : value.toString();

            JLabel label =
                    new JLabel(status);

            label.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            label.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            11
                    )
            );

            label.setOpaque(true);

            label.setBorder(
                    new EmptyBorder(
                            5,
                            10,
                            5,
                            10
                    )
            );

            if ("Vacant".equalsIgnoreCase(
                    status)) {

                label.setForeground(GREEN);

                label.setBackground(
                        GREEN_BG
                );

            } else {

                label.setForeground(ORANGE);

                label.setBackground(
                        ORANGE_BG
                );
            }

            JPanel wrapper =
                    new JPanel(
                            new GridBagLayout()
                    );

            wrapper.setBackground(
                    selected
                            ? new Color(
                            235,
                            243,
                            255
                    )
                            : Color.WHITE
            );

            wrapper.add(label);

            return wrapper;
        }
    }

    // =========================================================
    // ACTION RENDERER
    // =========================================================

    private class ActionRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component
        getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focused,
                int row,
                int column) {

            JPanel panel =
                    new JPanel(
                            new FlowLayout(
                                    FlowLayout.LEFT,
                                    7,
                                    5
                            )
                    );

            panel.setBackground(
                    selected
                            ? new Color(
                            235,
                            243,
                            255
                    )
                            : Color.WHITE
            );

            // VIEW
            RoundedButton view =
                    new RoundedButton(
                            "◉  View",
                            Color.WHITE,
                            BLUE
                    );

            view.setBorderColor(
                    new Color(
                            123,
                            181,
                            255
                    )
            );

            view.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            11
                    )
            );

            view.setPreferredSize(
                    new Dimension(
                            95,
                            30
                    )
            );

            // EDIT
            RoundedButton edit =
                    new RoundedButton(
                            "✎  Edit",
                            Color.WHITE,
                            BLUE
                    );

            edit.setBorderColor(
                    new Color(
                            123,
                            181,
                            255
                    )
            );

            edit.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            11
                    )
            );

            edit.setPreferredSize(
                    new Dimension(
                            95,
                            30
                    )
            );

            // MORE
            RoundedButton more =
                    new RoundedButton(
                            "⋯",
                            Color.WHITE,
                            NAVY
                    );

            more.setBorderColor(BORDER);

            more.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            17
                    )
            );

            more.setPreferredSize(
                    new Dimension(
                            40,
                            30
                    )
            );

            /*
             * NO ActionListeners here.
             *
             * JTable handles the actual click.
             */

            panel.add(view);

            panel.add(edit);

            panel.add(more);

            return panel;
        }
    }

    // =========================================================
    // MORE MENU
    // =========================================================

    private void showMoreMenu(
            JTable table,
            int viewRow,
            int modelRow) {

        if (table == null) {
            return;
        }

        if (viewRow < 0 ||
                viewRow >= table.getRowCount()) {

            return;
        }

        if (!validRow(modelRow)) {
            return;
        }

        JPopupMenu popup =
                createMoreMenu(modelRow);

        Rectangle cellRect =
                table.getCellRect(
                        viewRow,
                        8,
                        true
                );

        /*
         * IMPORTANT FIX:
         *
         * Do NOT use:
         *
         * popup.show(more, ...)
         *
         * because "more" is only a JTable
         * renderer component.
         *
         * The JTable itself is a real visible
         * component, so we show the popup
         * relative to the JTable.
         */

        int popupWidth =
                popup.getPreferredSize().width;

        int x =
                cellRect.x +
                        cellRect.width -
                        popupWidth;

        if (x < 0) {
            x = cellRect.x;
        }

        int y =
                cellRect.y +
                        cellRect.height;

        popup.show(
                table,
                x,
                y
        );
    }

    private JPopupMenu createMoreMenu(
            int row) {

        JPopupMenu popup =
                new JPopupMenu();

        popup.setBackground(
                Color.WHITE
        );

        JMenuItem view =
                createMenuItem(
                        "◉  View Details"
                );

        JMenuItem edit =
                createMenuItem(
                        "✎  Edit Record"
                );

        JMenuItem room =
                createMenuItem(
                        "▣  Room Details"
                );

        JMenuItem remove =
                createMenuItem(
                        "▣  Remove Record"
                );

        remove.setForeground(
                new Color(
                        225,
                        55,
                        55
                )
        );

        popup.add(view);

        popup.add(edit);

        popup.add(room);

        popup.addSeparator();

        popup.add(remove);

        view.addActionListener(
                e -> showStudentDetails(row)
        );

        edit.addActionListener(
                e -> showEditOptions(row)
        );

        room.addActionListener(
                e -> showRoomDetails(row)
        );

        remove.addActionListener(
                e -> removeRecord(row)
        );

        return popup;
    }

    private JMenuItem createMenuItem(
            String text) {

        JMenuItem item =
                new JMenuItem(text);

        item.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        item.setForeground(TEXT);

        item.setBackground(
                Color.WHITE
        );

        item.setPreferredSize(
                new Dimension(
                        190,
                        36
                )
        );

        return item;
    }

    // =========================================================
    // EDIT OPTIONS
    // =========================================================

    private void showEditOptions(
            int row) {

        if (!validRow(row)) {
            return;
        }

        /*
         * EXACTLY THREE EDIT OPTIONS
         *
         * No fourth "Cancel" option.
         * Closing the dialog cancels.
         */

        Object[] options = {
                "Edit Student Details",
                "Edit Room Details",
                "Change Status"
        };

        int choice =
                JOptionPane.showOptionDialog(
                        this,
                        "Choose what you want to edit:",
                        "Edit Hostel Record",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]
                );

        if (choice == 0) {

            editStudentDetails(row);

        } else if (choice == 1) {

            editRoomDetails(row);

        } else if (choice == 2) {

            changeStatus(row);
        }
    }

    // =========================================================
    // VIEW STUDENT DETAILS
    // =========================================================

    private void showStudentDetails(
            int row) {

        if (!validRow(row)) {
            return;
        }

        String studentName =
                value(row, 1);

        String studentId =
                value(row, 2);

        String room =
                value(row, 3);

        String block =
                value(row, 4);

        String roomType =
                value(row, 5);

        String contact =
                value(row, 6);

        String status =
                value(row, 7);

        JDialog dialog =
                new JDialog(
                        this,
                        "Hostel Record Details",
                        true
                );

        dialog.setSize(
                640,
                360
        );

        dialog.setLocationRelativeTo(this);

        dialog.setLayout(
                new BorderLayout()
        );

        JPanel main =
                new JPanel(
                        new BorderLayout(
                                20,
                                0
                        )
                );

        main.setBackground(
                Color.WHITE
        );

        main.setBorder(
                new EmptyBorder(
                        18,
                        18,
                        12,
                        18
                )
        );

        JPanel studentPanel =
                new JPanel();

        studentPanel.setOpaque(false);

        studentPanel.setPreferredSize(
                new Dimension(
                        165,
                        230
                )
        );

        studentPanel.setLayout(
                new BoxLayout(
                        studentPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JPanel avatar =
                new JPanel(
                        new BorderLayout()
                );

        avatar.setBackground(
                new Color(
                        226,
                        237,
                        252
                )
        );

        avatar.setPreferredSize(
                new Dimension(
                        65,
                        65
                )
        );

        avatar.setMaximumSize(
                new Dimension(
                        65,
                        65
                )
        );

        JLabel person =
                new JLabel("●");

        person.setForeground(NAVY);

        person.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        38
                )
        );

        person.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        avatar.add(
                person,
                BorderLayout.CENTER
        );

        JLabel name =
                new JLabel(
                        studentName.isBlank()
                                ? "Vacant Room"
                                : studentName
                );

        name.setForeground(TEXT);

        name.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        JLabel id =
                new JLabel(
                        studentId.isBlank()
                                ? "No student assigned"
                                : studentId
                );

        id.setForeground(
                new Color(
                        75,
                        105,
                        145
                )
        );

        id.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        JLabel statusLabel =
                new JLabel(
                        status
                );

        statusLabel.setOpaque(true);

        statusLabel.setBorder(
                new EmptyBorder(
                        4,
                        10,
                        4,
                        10
                )
        );

        boolean vacant =
                "Vacant".equalsIgnoreCase(
                        status
                );

        statusLabel.setForeground(
                vacant ? GREEN : ORANGE
        );

        statusLabel.setBackground(
                vacant
                        ? GREEN_BG
                        : ORANGE_BG
        );

        statusLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        studentPanel.add(avatar);

        studentPanel.add(
                Box.createVerticalStrut(10)
        );

        studentPanel.add(name);

        studentPanel.add(
                Box.createVerticalStrut(3)
        );

        studentPanel.add(id);

        studentPanel.add(
                Box.createVerticalStrut(8)
        );

        studentPanel.add(statusLabel);

        JPanel details =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                12,
                                12
                        )
                );

        details.setOpaque(false);

        addDetail(
                details,
                "Record ID",
                value(row, 0)
        );

        addDetail(
                details,
                "Student Name",
                studentName
        );

        addDetail(
                details,
                "Student ID",
                studentId
        );

        addDetail(
                details,
                "Contact",
                contact
        );

        addDetail(
                details,
                "Room No.",
                room
        );

        addDetail(
                details,
                "Block",
                block
        );

        addDetail(
                details,
                "Room Type",
                roomType
        );

        addDetail(
                details,
                "Status",
                status
        );

        main.add(
                studentPanel,
                BorderLayout.WEST
        );

        main.add(
                details,
                BorderLayout.CENTER
        );

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        bottom.setBackground(
                Color.WHITE
        );

        RoundedButton close =
                new RoundedButton(
                        "Close",
                        Color.WHITE,
                        BLUE
                );

        close.setBorderColor(
                new Color(
                        123,
                        181,
                        255
                )
        );

        close.setPreferredSize(
                new Dimension(
                        75,
                        34
                )
        );

        close.addActionListener(
                e -> dialog.dispose()
        );

        bottom.add(close);

        dialog.add(
                main,
                BorderLayout.CENTER
        );

        dialog.add(
                bottom,
                BorderLayout.SOUTH
        );

        dialog.setVisible(true);
    }

    private void showRoomDetails(
            int row) {

        if (!validRow(row)) {
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Room: " +
                        value(row, 3) +
                        "\n\nBlock: " +
                        value(row, 4) +
                        "\nRoom Type: " +
                        value(row, 5) +
                        "\nStatus: " +
                        value(row, 7),
                "Room Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void addDetail(
            JPanel panel,
            String title,
            String value) {

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(
                new Color(
                        80,
                        108,
                        145
                )
        );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        JLabel valueLabel =
                new JLabel(
                        value == null ||
                                value.isBlank()
                                ? "-"
                                : value
                );

        valueLabel.setForeground(TEXT);

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );

        panel.add(titleLabel);

        panel.add(valueLabel);
    }

    // =========================================================
    // EDIT STUDENT
    // =========================================================

    private void editStudentDetails(
            int row) {

        if (!validRow(row)) {
            return;
        }

        String oldName =
                value(row, 1);

        String oldStudentId =
                value(row, 2);

        String oldContact =
                value(row, 6);

        JTextField name =
                new JTextField(oldName);

        JTextField studentId =
                new JTextField(oldStudentId);

        JTextField contact =
                new JTextField(oldContact);

        JPanel panel =
                formPanel(
                        new String[]{
                                "Student Name:",
                                "Student ID:",
                                "Contact:"
                        },
                        new JComponent[]{
                                name,
                                studentId,
                                contact
                        }
                );

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Edit Student Details",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result !=
                JOptionPane.OK_OPTION) {

            return;
        }

        String newName =
                name.getText().trim();

        String newStudentId =
                studentId.getText().trim();

        String newContact =
                contact.getText().trim();

        if (newName.isEmpty()) {

            showWarning(
                    "Student name cannot be empty."
            );

            return;
        }

        String recordId =
                value(row, 0);

        String sql =
                "UPDATE hostel_records " +
                        "SET student_name=?, " +
                        "student_id=?, " +
                        "contact=?, " +
                        "status='Occupied' " +
                        "WHERE record_id=?";

        try (Connection con =
                     getConnection();

             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    newName
            );

            setNullableString(
                    ps,
                    2,
                    newStudentId
            );

            setNullableString(
                    ps,
                    3,
                    newContact
            );

            ps.setString(
                    4,
                    recordId
            );

            int updated =
                    ps.executeUpdate();

            if (updated > 0) {

                loadRecordsFromDatabase();

                showSuccess(
                        "Student details updated successfully."
                );
            }

        } catch (SQLException ex) {

            showDatabaseError(ex);
        }
    }

    // =========================================================
    // EDIT ROOM
    // =========================================================

    private void editRoomDetails(
            int row) {

        if (!validRow(row)) {
            return;
        }

        JTextField room =
                new JTextField(
                        value(row, 3)
                );

        JComboBox<String> block =
                createComboBox(
                        new String[]{
                                "Block A",
                                "Block B",
                                "Block C",
                                "Block D"
                        }
                );

        block.setSelectedItem(
                value(row, 4)
        );

        JComboBox<String> roomType =
                createComboBox(
                        new String[]{
                                "2 Sharing",
                                "3 Sharing",
                                "4 Sharing"
                        }
                );

        roomType.setSelectedItem(
                value(row, 5)
        );

        JPanel panel =
                formPanel(
                        new String[]{
                                "Room No.:",
                                "Block:",
                                "Room Type:"
                        },
                        new JComponent[]{
                                room,
                                block,
                                roomType
                        }
                );

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Edit Room Details",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result !=
                JOptionPane.OK_OPTION) {

            return;
        }

        String newRoom =
                room.getText().trim();

        if (newRoom.isEmpty()) {

            showWarning(
                    "Room number cannot be empty."
            );

            return;
        }

        String sql =
                "UPDATE hostel_records " +
                        "SET room_no=?, " +
                        "block=?, " +
                        "room_type=? " +
                        "WHERE record_id=?";

        try (Connection con =
                     getConnection();

             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    newRoom
            );

            ps.setString(
                    2,
                    block.getSelectedItem().toString()
            );

            ps.setString(
                    3,
                    roomType.getSelectedItem().toString()
            );

            ps.setString(
                    4,
                    value(row, 0)
            );

            int updated =
                    ps.executeUpdate();

            if (updated > 0) {

                loadRecordsFromDatabase();

                showSuccess(
                        "Room details updated successfully."
                );
            }

        } catch (SQLException ex) {

            showDatabaseError(ex);
        }
    }

    // =========================================================
    // CHANGE STATUS
    // =========================================================

    private void changeStatus(
            int row) {

        if (!validRow(row)) {
            return;
        }

        JComboBox<String> status =
                createComboBox(
                        new String[]{
                                "Vacant",
                                "Occupied"
                        }
                );

        status.setSelectedItem(
                value(row, 7)
        );

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                10,
                                10
                        )
                );

        panel.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        panel.add(
                new JLabel("Status:")
        );

        panel.add(status);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Change Status",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result !=
                JOptionPane.OK_OPTION) {

            return;
        }

        String newStatus =
                status.getSelectedItem()
                        .toString();

        String recordId =
                value(row, 0);

        String sql =
                "UPDATE hostel_records " +
                        "SET status=? " +
                        "WHERE record_id=?";

        try (Connection con =
                     getConnection();

             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    newStatus
            );

            ps.setString(
                    2,
                    recordId
            );

            ps.executeUpdate();

            /*
             * When room becomes vacant,
             * clear student information.
             *
             * student_name gets an empty string
             * instead of NULL because your database
             * has student_name NOT NULL.
             */

            if ("Vacant".equalsIgnoreCase(
                    newStatus)) {

                String clearStudent =
                        "UPDATE hostel_records " +
                                "SET student_name='', " +
                                "student_id=NULL, " +
                                "contact=NULL " +
                                "WHERE record_id=?";

                try (PreparedStatement clear =
                             con.prepareStatement(
                                     clearStudent
                             )) {

                    clear.setString(
                            1,
                            recordId
                    );

                    clear.executeUpdate();
                }
            }

            loadRecordsFromDatabase();

            showSuccess(
                    "Status updated successfully."
            );

        } catch (SQLException ex) {

            showDatabaseError(ex);
        }
    }

    // =========================================================
    // FORM PANEL
    // =========================================================

    private JPanel formPanel(
            String[] labels,
            JComponent[] fields) {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                labels.length,
                                2,
                                10,
                                10
                        )
                );

        panel.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        for (int i = 0;
             i < labels.length;
             i++) {

            panel.add(
                    new JLabel(
                            labels[i]
                    )
            );

            panel.add(
                    fields[i]
            );
        }

        return panel;
    }

    // =========================================================
    // ADD STUDENT
    // =========================================================

    private void showAddStudentDialog() {

        JTextField name =
                new JTextField();

        JTextField studentId =
                new JTextField();

        JTextField room =
                new JTextField();

        JTextField contact =
                new JTextField();

        JComboBox<String> block =
                createComboBox(
                        new String[]{
                                "Block A",
                                "Block B",
                                "Block C",
                                "Block D"
                        }
                );

        JComboBox<String> roomType =
                createComboBox(
                        new String[]{
                                "2 Sharing",
                                "3 Sharing",
                                "4 Sharing"
                        }
                );

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                6,
                                2,
                                10,
                                10
                        )
                );

        panel.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        panel.add(
                new JLabel("Student Name:")
        );

        panel.add(name);

        panel.add(
                new JLabel("Student ID:")
        );

        panel.add(studentId);

        panel.add(
                new JLabel("Room No.:")
        );

        panel.add(room);

        panel.add(
                new JLabel("Block:")
        );

        panel.add(block);

        panel.add(
                new JLabel("Room Type:")
        );

        panel.add(roomType);

        panel.add(
                new JLabel("Contact:")
        );

        panel.add(contact);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add Student",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result !=
                JOptionPane.OK_OPTION) {

            return;
        }

        if (name.getText()
                .trim()
                .isEmpty()
                ||
                studentId.getText()
                        .trim()
                        .isEmpty()
                ||
                room.getText()
                        .trim()
                        .isEmpty()) {

            showWarning(
                    "Student name, student ID and room number are required."
            );

            return;
        }

        String recordId =
                generateRecordId();

        String sql =
                "INSERT INTO hostel_records " +
                        "(record_id, student_name, student_id, " +
                        "room_no, block, room_type, contact, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, 'Occupied')";

        try (Connection con =
                     getConnection();

             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    recordId
            );

            ps.setString(
                    2,
                    name.getText().trim()
            );

            ps.setString(
                    3,
                    studentId.getText().trim()
            );

            ps.setString(
                    4,
                    room.getText().trim()
            );

            ps.setString(
                    5,
                    block.getSelectedItem()
                            .toString()
            );

            ps.setString(
                    6,
                    roomType.getSelectedItem()
                            .toString()
            );

            setNullableString(
                    ps,
                    7,
                    contact.getText().trim()
            );

            ps.executeUpdate();

            loadRecordsFromDatabase();

            showSuccess(
                    "Student added successfully.\n\n" +
                            "Record ID: " +
                            recordId
            );

        } catch (SQLException ex) {

            showDatabaseError(ex);
        }
    }

    // =========================================================
    // GENERATE RECORD ID
    // =========================================================

    private String generateRecordId() {

        int max = 0;

        for (Object[] row :
                allRecords) {

            String id =
                    value(row, 0);

            if (id.startsWith("ST")) {

                try {

                    max =
                            Math.max(
                                    max,
                                    Integer.parseInt(
                                            id.substring(2)
                                    )
                            );

                } catch (NumberFormatException ignored) {
                }
            }
        }

        return String.format(
                "ST%03d",
                max + 1
        );
    }

    // =========================================================
    // REMOVE RECORD
    // =========================================================

    private void removeRecord(
            int row) {

        if (!validRow(row)) {
            return;
        }

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to remove record " +
                                value(row, 0) +
                                "?",
                        "Remove Record",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (result !=
                JOptionPane.YES_OPTION) {

            return;
        }

        String sql =
                "DELETE FROM hostel_records " +
                        "WHERE record_id=?";

        try (Connection con =
                     getConnection();

             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    value(row, 0)
            );

            int deleted =
                    ps.executeUpdate();

            if (deleted > 0) {

                loadRecordsFromDatabase();

                showSuccess(
                        "Hostel record removed successfully."
                );
            }

        } catch (SQLException ex) {

            showDatabaseError(ex);
        }
    }

    // =========================================================
    // LOAD DATABASE RECORDS
    // =========================================================

    private void loadRecordsFromDatabase() {

        allRecords.clear();

        String sql =
                "SELECT " +
                        "record_id, " +
                        "student_name, " +
                        "student_id, " +
                        "room_no, " +
                        "block, " +
                        "room_type, " +
                        "contact, " +
                        "status " +
                        "FROM hostel_records " +
                        "ORDER BY record_id";

        try (Connection con =
                     getConnection();

             PreparedStatement ps =
                     con.prepareStatement(sql);

             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                allRecords.add(
                        new Object[]{
                                safe(
                                        rs.getString(
                                                "record_id"
                                        )
                                ),

                                safe(
                                        rs.getString(
                                                "student_name"
                                        )
                                ),

                                safe(
                                        rs.getString(
                                                "student_id"
                                        )
                                ),

                                safe(
                                        rs.getString(
                                                "room_no"
                                        )
                                ),

                                safe(
                                        rs.getString(
                                                "block"
                                        )
                                ),

                                safe(
                                        rs.getString(
                                                "room_type"
                                        )
                                ),

                                safe(
                                        rs.getString(
                                                "contact"
                                        )
                                ),

                                safe(
                                        rs.getString(
                                                "status"
                                        )
                                ),

                                ""
                        }
                );
            }

            applyFilters();

        } catch (SQLException ex) {

            showDatabaseError(ex);
        }
    }

    // =========================================================
    // FILTER
    // =========================================================

    private void filterRecords() {

        currentPage = 1;

        applyFilters();
    }

    private void applyFilters() {

        String search =
                searchField == null
                        ? ""
                        : searchField
                        .getText()
                        .trim()
                        .toLowerCase();

        String block =
                hostelBox == null
                        ? "All Hostels"
                        : hostelBox
                        .getSelectedItem()
                        .toString();

        String roomType =
                roomTypeBox == null
                        ? "All Room Types"
                        : roomTypeBox
                        .getSelectedItem()
                        .toString();

        String status =
                statusBox == null
                        ? "All Status"
                        : statusBox
                        .getSelectedItem()
                        .toString();

        filteredRecords =
                new ArrayList<>();

        for (Object[] row :
                allRecords) {

            String name =
                    value(row, 1)
                            .toLowerCase();

            String id =
                    value(row, 2)
                            .toLowerCase();

            String room =
                    value(row, 3)
                            .toLowerCase();

            boolean searchMatch =
                    search.isEmpty()
                            ||
                            name.contains(search)
                            ||
                            id.contains(search)
                            ||
                            room.contains(search);

            boolean blockMatch =
                    block.equals(
                            "All Hostels"
                    )
                            ||
                            value(row, 4)
                                    .equals(block);

            boolean roomMatch =
                    roomType.equals(
                            "All Room Types"
                    )
                            ||
                            value(row, 5)
                                    .equals(roomType);

            boolean statusMatch =
                    status.equals(
                            "All Status"
                    )
                            ||
                            value(row, 7)
                                    .equals(status);

            if (searchMatch
                    &&
                    blockMatch
                    &&
                    roomMatch
                    &&
                    statusMatch) {

                filteredRecords.add(row);
            }
        }

        updateSummaryCards();

        currentPage =
                Math.max(
                        1,
                        Math.min(
                                currentPage,
                                getTotalPages()
                        )
                );

        updateTablePage();
    }

    // =========================================================
    // UPDATE TABLE
    // =========================================================

    private void updateTablePage() {

        if (recordsTable == null) {
            return;
        }

        DefaultTableModel model =
                (DefaultTableModel)
                        recordsTable.getModel();

        model.setRowCount(0);

        int total =
                filteredRecords.size();

        int totalPages =
                getTotalPages();

        if (total == 0) {

            currentPage = 1;

        } else if (currentPage > totalPages) {

            currentPage = totalPages;
        }

        int from =
                total == 0
                        ? 0
                        : (currentPage - 1)
                        * pageSize;

        int to =
                Math.min(
                        from + pageSize,
                        total
                );

        for (int i = from;
             i < to;
             i++) {

            model.addRow(
                    filteredRecords.get(i)
            );
        }

        pageLabel.setText(
                totalPages == 0
                        ? "0"
                        : currentPage +
                        " / " +
                        totalPages
        );

        if (total == 0) {

            showingLabel.setText(
                    "Showing 0 records"
            );

        } else {

            showingLabel.setText(
                    "Showing " +
                            (from + 1) +
                            " - " +
                            to +
                            " of " +
                            total +
                            " records"
            );
        }
    }

    private int getTotalPages() {

        return Math.max(
                1,
                (int) Math.ceil(
                        filteredRecords.size()
                                /
                                (double) pageSize
                )
        );
    }

    private void changePage(
            int direction) {

        int newPage =
                currentPage +
                        direction;

        if (newPage >= 1 &&
                newPage <= getTotalPages()) {

            currentPage =
                    newPage;

            updateTablePage();
        }
    }

    // =========================================================
    // SUMMARY
    // =========================================================

    private void updateSummaryCards() {

        int totalStudents = 0;

        int occupied = 0;

        int vacant = 0;

        for (Object[] row :
                allRecords) {

            if ("Occupied".equalsIgnoreCase(
                    value(row, 7))) {

                occupied++;

                totalStudents++;

            } else if (
                    "Vacant".equalsIgnoreCase(
                            value(row, 7))) {

                vacant++;
            }
        }

        if (totalStudentsLabel != null) {

            totalStudentsLabel.setText(
                    String.valueOf(
                            totalStudents
                    )
            );
        }

        if (occupiedRoomsLabel != null) {

            occupiedRoomsLabel.setText(
                    String.valueOf(
                            occupied
                    )
            );
        }

        if (availableRoomsLabel != null) {

            availableRoomsLabel.setText(
                    String.valueOf(
                            vacant
                    )
            );
        }

        if (pendingLabel != null) {

            pendingLabel.setText(
                    String.valueOf(
                            vacant
                    )
            );
        }
    }

    // =========================================================
    // ROW VALIDATION
    // =========================================================

    private boolean validRow(
            int row) {

        return recordsTable != null
                &&
                row >= 0
                &&
                row <
                        recordsTable
                                .getModel()
                                .getRowCount();
    }

    // =========================================================
    // TABLE VALUE
    // =========================================================

    private String value(
            int row,
            int column) {

        if (!validRow(row)) {
            return "";
        }

        Object value =
                recordsTable
                        .getModel()
                        .getValueAt(
                                row,
                                column
                        );

        return value == null
                ? ""
                : value.toString();
    }

    private String value(
            Object[] row,
            int column) {

        if (row == null ||
                column < 0 ||
                column >= row.length) {

            return "";
        }

        Object value =
                row[column];

        return value == null
                ? ""
                : value.toString();
    }

    // =========================================================
    // UTILITY
    // =========================================================

    private String safe(
            String value) {

        return value == null
                ? ""
                : value;
    }

    private void setNullableString(
            PreparedStatement ps,
            int index,
            String value)
            throws SQLException {

        if (value == null ||
                value.isBlank()) {

            ps.setNull(
                    index,
                    Types.VARCHAR
            );

        } else {

            ps.setString(
                    index,
                    value
            );
        }
    }

    private void showWarning(
            String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showSuccess(
            String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // PAGE BUTTON
    // =========================================================

    private RoundedButton createPageButton(
            String text) {

        RoundedButton button =
                new RoundedButton(
                        text,
                        Color.WHITE,
                        NAVY
                );

        button.setBorderColor(
                BORDER
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        15
                )
        );

        button.setPreferredSize(
                new Dimension(
                        34,
                        34
                )
        );

        return button;
    }

    // =========================================================
    // ROUNDED PANEL
    // =========================================================

    private class RoundedPanel
            extends JPanel {

        private final int radius;

        private final Color background;

        private final Color borderColor;

        public RoundedPanel(
                int radius,
                Color background,
                Color borderColor) {

            this.radius = radius;

            this.background = background;

            this.borderColor = borderColor;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                    background
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radius,
                    radius
            );

            g2.setColor(
                    borderColor
            );

            g2.drawRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radius,
                    radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // ROUNDED BUTTON
    // =========================================================

    private class RoundedButton
            extends JButton {

        private Color backgroundColor;

        private Color borderColor;

        public RoundedButton(
                String text,
                Color background,
                Color foreground) {

            super(text);

            backgroundColor =
                    background;

            setForeground(
                    foreground
            );

            setBackground(
                    background
            );

            setFocusPainted(false);

            setBorderPainted(false);

            setContentAreaFilled(false);

            setOpaque(false);

            setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );
        }

        public void setBorderColor(
                Color color) {

            borderColor = color;
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            Color fill =
                    backgroundColor;

            if (getModel()
                    .isRollover()) {

                fill =
                        backgroundColor
                                .equals(BLUE)
                                ? new Color(
                                15,
                                92,
                                205
                        )
                                : new Color(
                                244,
                                248,
                                253
                        );
            }

            g2.setColor(fill);

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    8,
                    8
            );

            if (borderColor != null) {

                g2.setColor(
                        borderColor
                );

                g2.drawRoundRect(
                        0,
                        0,
                        getWidth() - 1,
                        getHeight() - 1,
                        8,
                        8
                );
            }

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // ROUNDED BORDER
    // =========================================================

    private class RoundedBorder
            extends AbstractBorder {

        private final Color color;

        private final int radius;

        public RoundedBorder(
                Color color,
                int radius) {

            this.color = color;

            this.radius = radius;
        }

        @Override
        public void paintBorder(
                Component c,
                Graphics g,
                int x,
                int y,
                int width,
                int height) {

            Graphics2D g2 =
                    (Graphics2D)
                            g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);

            g2.drawRoundRect(
                    x,
                    y,
                    width - 1,
                    height - 1,
                    radius,
                    radius
            );

            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(
                Component c) {

            return new Insets(
                    8,
                    10,
                    8,
                    10
            );
        }
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    try {

                        UIManager
                                .setLookAndFeel(
                                        UIManager
                                                .getSystemLookAndFeelClassName()
                                );

                    } catch (Exception ignored) {
                    }

                    new hostelRecords(
                            "",
                            ""
                    );
                }
        );
    }
}