package hostel.complaint.and.maintenance.tracking.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class adminDash extends JFrame {

    private final Color NAVY = new Color(8, 43, 78);
    private final Color BLUE = new Color(31, 105, 205);
    private final Color LIGHT_BG = new Color(245, 248, 252);
    private final Color TEXT = new Color(30, 43, 60);
    private final Color MUTED = new Color(105, 120, 140);
    private final Color GREEN = new Color(35, 170, 110);
    private final Color ORANGE = new Color(240, 155, 45);
    private final Color PURPLE = new Color(125, 90, 210);

    String adminName;
    String adminUsername;

    private JLabel totalLabel;
    private JLabel pendingLabel;
    private JLabel progressLabel;
    private JLabel resolvedLabel;
    private JLabel staffLabel;

    public adminDash(String name, String username) {

        super("Admin Dashboard");

        adminName = name;
        adminUsername = username;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(LIGHT_BG);

        createSidebar();
        createMainPanel();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

    private void createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(265, 1080));
        sidebar.setLayout(null);

        JLabel homeIcon = new JLabel("⌂");
        homeIcon.setBounds(25, 25, 45, 45);
        homeIcon.setFont(new Font("Segoe UI Symbol", Font.BOLD, 40));
        homeIcon.setForeground(Color.WHITE);
        sidebar.add(homeIcon);

        JLabel hostel = new JLabel("HOSTEL");
        hostel.setBounds(80, 20, 150, 30);
        hostel.setFont(new Font("Segoe UI", Font.BOLD, 23));
        hostel.setForeground(Color.WHITE);
        sidebar.add(hostel);

        JLabel system = new JLabel("Complaint & Maintenance");
        system.setBounds(80, 47, 180, 22);
        system.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        system.setForeground(Color.WHITE);
        sidebar.add(system);

        JLabel tracking = new JLabel("Tracking System");
        tracking.setBounds(80, 67, 180, 22);
        tracking.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tracking.setForeground(Color.WHITE);
        sidebar.add(tracking);

        JLabel panel = new JLabel("ADMIN PANEL");
        panel.setBounds(25, 120, 200, 30);
        panel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        panel.setForeground(Color.WHITE);
        sidebar.add(panel);

        addMenuButton(sidebar, "⌂", "Admin Home", 165, true);
        addMenuButton(sidebar, "▣", "Complaint Center", 225, false);
        addMenuButton(sidebar, "♟", "Maintenance Team", 285, false);
        addMenuButton(sidebar, "⌂", "Hostel Records", 345, false);
        addMenuButton(sidebar, "⚑", "Communication", 405, false);

        JSeparator separator = new JSeparator();
        separator.setBounds(25, 475, 215, 1);
        separator.setForeground(new Color(80, 110, 140));
        sidebar.add(separator);

        JButton logout = new JButton("↪   Logout");

        logout.setBounds(20, 505, 220, 48);
        logout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        logout.setForeground(Color.WHITE);
        logout.setBackground(NAVY);
        logout.setBorderPainted(false);
        logout.setFocusPainted(false);
        logout.setHorizontalAlignment(SwingConstants.LEFT);
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        logout.addActionListener(e -> {

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {

                dispose();

                new login("", "");
            }
        });

        sidebar.add(logout);

        JLabel bottom = new JLabel(
                "<html><center>Better Hostel<br>Happier Students</center></html>"
        );

        bottom.setBounds(25, 850, 215, 60);
        bottom.setHorizontalAlignment(SwingConstants.CENTER);
        bottom.setFont(new Font("Segoe UI", Font.BOLD, 15));
        bottom.setForeground(new Color(170, 195, 220));

        sidebar.add(bottom);

        add(sidebar, BorderLayout.WEST);
    }

    // =========================================================
    // SIDEBAR BUTTONS
    // =========================================================

    private void addMenuButton(
            JPanel sidebar,
            String icon,
            String text,
            int y,
            boolean selected
    ) {

        JButton button = new JButton(icon + "   " + text);

        button.setBounds(12, y, 235, 48);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (selected) {

            button.setBackground(BLUE);
            button.setForeground(Color.WHITE);

        } else {

            button.setBackground(NAVY);
            button.setForeground(Color.WHITE);
        }

        button.addActionListener(e -> {

            // Admin Home
            if (text.equals("Admin Home")) {
                return;
            }

            if (text.equals("Complaint Center")) {

                dispose();

                complaintcenter frame =
                        new complaintcenter(
                                adminName,
                                adminUsername
                        );

                frame.setVisible(true);

                return;
            }

            // Maintenance Team
            if (text.equals("Maintenance Team")) {

            dispose();
            maintenance frame = new maintenance("","");
            frame.setVisible(true);
                return;
            }

            if (text.equals("Hostel Records")) {

//                JOptionPane.showMessageDialog(
//                        this,
//                        "Hostel Records page will open here.",
//                        "Hostel Records",
//                        JOptionPane.INFORMATION_MESSAGE
                dispose();
                new hostelRecords(adminName, adminUsername);

//                dispose();
//                new hostelRecords(adminName, adminUsername);



                return;
            }

            // Communication
            if (text.equals("Communication")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Communication page will open here.",
                        "Communication",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        sidebar.add(button);
    }

    // =========================================================
    // MAIN PANEL
    // =========================================================

    private void createMainPanel() {

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(LIGHT_BG);

        // -----------------------------------------------------
        // TOP BAR
        // -----------------------------------------------------

        JPanel topBar = new JPanel(null);

        topBar.setPreferredSize(
                new Dimension(1400, 70)
        );

        topBar.setBackground(Color.WHITE);

        JLabel menu = new JLabel("☰");

        menu.setBounds(28, 18, 40, 35);
        menu.setFont(
                new Font(
                        "Segoe UI Symbol",
                        Font.BOLD,
                        27
                )
        );

        menu.setForeground(NAVY);
        topBar.add(menu);

        JLabel notification = new JLabel("🔔");

        notification.setBounds(1050, 17, 40, 35);
        notification.setFont(
                new Font(
                        "Segoe UI Symbol",
                        Font.BOLD,
                        25
                )
        );

        notification.setForeground(NAVY);
        topBar.add(notification);

        JLabel count = new JLabel("3");

        count.setBounds(1070, 10, 18, 18);
        count.setHorizontalAlignment(SwingConstants.CENTER);
        count.setFont(new Font("Arial", Font.BOLD, 11));
        count.setForeground(Color.WHITE);
        count.setBackground(new Color(220, 55, 55));
        count.setOpaque(true);

        topBar.add(count);

        JLabel profile = new JLabel("●");

        profile.setBounds(1110, 15, 40, 40);
        profile.setHorizontalAlignment(SwingConstants.CENTER);
        profile.setFont(new Font("Arial", Font.BOLD, 30));
        profile.setForeground(NAVY);

        topBar.add(profile);

        JLabel admin = new JLabel(
                adminName == null || adminName.isEmpty()
                        ? "Admin"
                        : adminName
        );

        admin.setBounds(1155, 18, 180, 35);
        admin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        admin.setForeground(TEXT);

        topBar.add(admin);

        main.add(topBar, BorderLayout.NORTH);

        // -----------------------------------------------------
        // CONTENT
        // -----------------------------------------------------

        JPanel content = new JPanel(null);
        content.setBackground(LIGHT_BG);

        JLabel title = new JLabel(
                "Welcome, " +
                        (
                                adminUsername == null ||
                                        adminUsername.isEmpty()
                                        ? "Admin"
                                        : adminUsername
                        ) +
                        "!"
        );

        title.setBounds(30, 25, 500, 45);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(NAVY);

        content.add(title);

        JLabel subtitle = new JLabel(
                "Here's what's happening in your hostel today."
        );

        subtitle.setBounds(30, 68, 600, 30);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(MUTED);

        content.add(subtitle);

        JLabel date = new JLabel("Admin Control Panel");

        date.setBounds(1050, 35, 250, 30);
        date.setHorizontalAlignment(SwingConstants.RIGHT);
        date.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        date.setForeground(MUTED);

        content.add(date);

        // -----------------------------------------------------
        // STAT LABELS
        // -----------------------------------------------------

        totalLabel = new JLabel("0");
        pendingLabel = new JLabel("0");
        progressLabel = new JLabel("0");
        resolvedLabel = new JLabel("0");
        staffLabel = new JLabel("0");

        createStatCard(
                content,
                "Total Complaints",
                totalLabel,
                "▣",
                30,
                BLUE
        );

        createStatCard(
                content,
                "Pending Complaints",
                pendingLabel,
                "◷",
                310,
                ORANGE
        );

        createStatCard(
                content,
                "In Progress",
                progressLabel,
                "⚙",
                590,
                PURPLE
        );

        createStatCard(
                content,
                "Resolved Complaints",
                resolvedLabel,
                "✓",
                870,
                GREEN
        );

        // -----------------------------------------------------
        // STATUS
        // -----------------------------------------------------

        JPanel statusPanel = createPanel(
                "Complaint Status Overview",
                30,
                245,
                500,
                300
        );

        createStatusContent(statusPanel);
        content.add(statusPanel);

        // -----------------------------------------------------
        // PRIORITY
        // -----------------------------------------------------

        JPanel priorityPanel = createPanel(
                "Priority Complaints",
                550,
                245,
                350,
                300
        );

        createPriorityContent(priorityPanel);
        content.add(priorityPanel);

        // -----------------------------------------------------
        // QUICK ACTIONS
        // -----------------------------------------------------

        JPanel quickPanel = createPanel(
                "Quick Actions",
                920,
                245,
                390,
                300
        );

        createQuickActions(quickPanel);
        content.add(quickPanel);

        // -----------------------------------------------------
        // RECENT COMPLAINTS
        // -----------------------------------------------------

        JPanel recentPanel = createPanel(
                "Recent Complaints",
                30,
                570,
                1280,
                330
        );

        createRecentComplaints(recentPanel);
        content.add(recentPanel);

        JScrollPane scrollPane =
                new JScrollPane(content);

        scrollPane.setBorder(null);
        scrollPane
                .getVerticalScrollBar()
                .setUnitIncrement(16);

        main.add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(
                main,
                BorderLayout.CENTER
        );

        loadDashboardData();
    }

    // =========================================================
    // STAT CARD
    // =========================================================

    private void createStatCard(
            JPanel parent,
            String title,
            JLabel value,
            String icon,
            int x,
            Color iconColor
    ) {

        JPanel card = new JPanel(null);

        card.setBounds(
                x,
                115,
                260,
                110
        );

        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 228, 238)
                )
        );

        JLabel iconLabel =
                new JLabel(icon);

        iconLabel.setBounds(
                18,
                22,
                55,
                55
        );

        iconLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        iconLabel.setFont(
                new Font(
                        "Segoe UI Symbol",
                        Font.BOLD,
                        25
                )
        );

        iconLabel.setForeground(Color.WHITE);
        iconLabel.setBackground(iconColor);
        iconLabel.setOpaque(true);

        card.add(iconLabel);

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setBounds(
                88,
                20,
                155,
                25
        );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        titleLabel.setForeground(NAVY);

        card.add(titleLabel);

        value.setBounds(
                88,
                45,
                150,
                40
        );

        value.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        30
                )
        );

        value.setForeground(TEXT);

        card.add(value);

        parent.add(card);
    }

    // =========================================================
    // COMMON PANEL
    // =========================================================

    private JPanel createPanel(
            String title,
            int x,
            int y,
            int width,
            int height
    ) {

        JPanel panel = new JPanel(null);

        panel.setBounds(
                x,
                y,
                width,
                height
        );

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 228, 238)
                )
        );

        JLabel heading =
                new JLabel(title);

        heading.setBounds(
                20,
                15,
                width - 40,
                30
        );

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        heading.setForeground(NAVY);

        panel.add(heading);

        return panel;
    }

    // =========================================================
    // STATUS
    // =========================================================

    private void createStatusContent(
            JPanel panel
    ) {

        JLabel pending =
                new JLabel("Pending");

        pending.setBounds(
                35,
                80,
                100,
                30
        );

        pending.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        pending.setForeground(ORANGE);
        panel.add(pending);

        JLabel pendingValue =
                new JLabel("0");

        pendingValue.setBounds(
                390,
                80,
                60,
                30
        );

        pendingValue.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        pendingValue.setForeground(TEXT);

        panel.add(pendingValue);

        JLabel progress =
                new JLabel("In Progress");

        progress.setBounds(
                35,
                135,
                120,
                30
        );

        progress.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        progress.setForeground(BLUE);

        panel.add(progress);

        JLabel progressValue =
                new JLabel("0");

        progressValue.setBounds(
                390,
                135,
                60,
                30
        );

        progressValue.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        progressValue.setForeground(TEXT);

        panel.add(progressValue);

        JLabel resolved =
                new JLabel("Resolved");

        resolved.setBounds(
                35,
                190,
                120,
                30
        );

        resolved.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        resolved.setForeground(GREEN);

        panel.add(resolved);

        JLabel resolvedValue =
                new JLabel("0");

        resolvedValue.setBounds(
                390,
                190,
                60,
                30
        );

        resolvedValue.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        resolvedValue.setForeground(TEXT);

        panel.add(resolvedValue);

        JPanel line1 = new JPanel();

        line1.setBounds(
                35,
                110,
                330,
                3
        );

        line1.setBackground(ORANGE);
        panel.add(line1);

        JPanel line2 = new JPanel();

        line2.setBounds(
                35,
                165,
                330,
                3
        );

        line2.setBackground(BLUE);
        panel.add(line2);

        JPanel line3 = new JPanel();

        line3.setBounds(
                35,
                220,
                330,
                3
        );

        line3.setBackground(GREEN);
        panel.add(line3);

        loadStatusValues(
                pendingValue,
                progressValue,
                resolvedValue
        );
    }

    // =========================================================
    // STATUS DATABASE
    // =========================================================

    private void loadStatusValues(
            JLabel pending,
            JLabel progress,
            JLabel resolved
    ) {

        try (Connection con =
                     Con.getConnection()) {

            String sql =
                    "SELECT status, COUNT(*) AS total " +
                            "FROM complaints GROUP BY status";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                String status =
                        rs.getString("status");

                int total =
                        rs.getInt("total");

                if ("Pending".equalsIgnoreCase(status)) {

                    pending.setText(
                            String.valueOf(total)
                    );
                }

                if ("In Progress".equalsIgnoreCase(status)) {

                    progress.setText(
                            String.valueOf(total)
                    );
                }

                if ("Resolved".equalsIgnoreCase(status)) {

                    resolved.setText(
                            String.valueOf(total)
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =========================================================
    // PRIORITY
    // =========================================================

    private void createPriorityContent(
            JPanel panel
    ) {

        JLabel high =
                new JLabel("High Priority");

        high.setBounds(
                30,
                80,
                180,
                35
        );

        high.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        high.setForeground(
                new Color(210, 70, 70)
        );

        panel.add(high);

        JLabel medium =
                new JLabel("Medium Priority");

        medium.setBounds(
                30,
                135,
                180,
                35
        );

        medium.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        medium.setForeground(ORANGE);

        panel.add(medium);

        JLabel low =
                new JLabel("Low Priority");

        low.setBounds(
                30,
                190,
                180,
                35
        );

        low.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        low.setForeground(BLUE);

        panel.add(low);

        JLabel highValue =
                new JLabel("0");

        highValue.setBounds(
                280,
                80,
                40,
                35
        );

        highValue.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        panel.add(highValue);

        JLabel mediumValue =
                new JLabel("0");

        mediumValue.setBounds(
                280,
                135,
                40,
                35
        );

        mediumValue.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        panel.add(mediumValue);

        JLabel lowValue =
                new JLabel("0");

        lowValue.setBounds(
                280,
                190,
                40,
                35
        );

        lowValue.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        panel.add(lowValue);

        loadPriorityValues(
                highValue,
                mediumValue,
                lowValue
        );
    }

    // =========================================================
    // PRIORITY DATABASE
    // =========================================================

    private void loadPriorityValues(
            JLabel high,
            JLabel medium,
            JLabel low
    ) {

        try (Connection con =
                     Con.getConnection()) {

            String sql =
                    "SELECT priority, COUNT(*) AS total " +
                            "FROM complaints GROUP BY priority";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                String priority =
                        rs.getString("priority");

                int total =
                        rs.getInt("total");

                if ("High".equalsIgnoreCase(priority)) {

                    high.setText(
                            String.valueOf(total)
                    );
                }

                if ("Medium".equalsIgnoreCase(priority)) {

                    medium.setText(
                            String.valueOf(total)
                    );
                }

                if ("Low".equalsIgnoreCase(priority)) {

                    low.setText(
                            String.valueOf(total)
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =========================================================
    // QUICK ACTIONS
    // =========================================================

    private void createQuickActions(
            JPanel panel
    ) {

        // VIEW ALL COMPLAINTS
        JButton view =
                createActionButton(
                        "View All Complaints",
                        BLUE
                );

        view.setBounds(
                20,
                65,
                350,
                45
        );

        view.addActionListener(e -> {

            dispose();

            complaintcenter frame =
                    new complaintcenter(
                            adminName,
                            adminUsername
                    );

            frame.setVisible(true);
        });

        panel.add(view);

        // MANAGE MAINTENANCE TEAM
        JButton staff =
                createActionButton(
                        "Manage Maintenance Team",
                        GREEN
                );

        staff.setBounds(
                20,
                120,
                350,
                45
        );

        staff.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Maintenance Team page will open here.",
                        "Maintenance Team",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        panel.add(staff);

        // CREATE ANNOUNCEMENT
        JButton announcement =
                createActionButton(
                        "Create Announcement",
                        PURPLE
                );

        announcement.setBounds(
                20,
                175,
                350,
                45
        );

        announcement.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Communication page will open here.",
                        "Communication",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        panel.add(announcement);

        // REFRESH
        JButton refresh =
                createActionButton(
                        "Refresh Dashboard",
                        new Color(70, 145, 180)
                );

        refresh.setBounds(
                20,
                230,
                350,
                45
        );

        refresh.addActionListener(e -> {

            loadDashboardData();

            JOptionPane.showMessageDialog(
                    this,
                    "Dashboard updated successfully.",
                    "Refresh",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        panel.add(refresh);
    }

    // =========================================================
    // ACTION BUTTON
    // =========================================================

    private JButton createActionButton(
            String text,
            Color color
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =========================================================
    // RECENT COMPLAINTS
    // =========================================================

    private void createRecentComplaints(
            JPanel panel
    ) {

        String[] columns = {

                "ID",
                "Student",
                "Room",
                "Category",
                "Title",
                "Priority",
                "Status",
                "Date"
        };

        JTable table =
                new JTable(
                        new Object[0][columns.length],
                        columns
                );

        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        table.setRowHeight(35);

        table.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                13
                        )
                );

        table.getTableHeader()
                .setBackground(
                        new Color(242, 246, 251)
                );

        table.getTableHeader()
                .setForeground(NAVY);

        JScrollPane scroll =
                new JScrollPane(table);

        scroll.setBounds(
                20,
                55,
                1240,
                245
        );

        scroll.setBorder(null);

        panel.add(scroll);

        loadRecentComplaints(table);
    }

    // =========================================================
    // LOAD RECENT COMPLAINTS
    // =========================================================

    private void loadRecentComplaints(
            JTable table
    ) {

        try (Connection con =
                     Con.getConnection()) {

            String sql =
                    "SELECT complaint_id, name, room_no, category, " +
                            "title, priority, status, complaint_date " +
                            "FROM complaints " +
                            "ORDER BY complaint_date DESC LIMIT 5";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            java.util.List<Object[]> rows =
                    new java.util.ArrayList<>();

            while (rs.next()) {

                rows.add(
                        new Object[]{

                                "#" +
                                        rs.getInt(
                                                "complaint_id"
                                        ),

                                rs.getString(
                                        "name"
                                ),

                                rs.getString(
                                        "room_no"
                                ),

                                rs.getString(
                                        "category"
                                ),

                                rs.getString(
                                        "title"
                                ),

                                rs.getString(
                                        "priority"
                                ),

                                rs.getString(
                                        "status"
                                ),

                                rs.getTimestamp(
                                        "complaint_date"
                                )
                        }
                );
            }

            Object[][] data =
                    rows.toArray(
                            new Object[0][]
                    );

            String[] columns = {

                    "ID",
                    "Student",
                    "Room",
                    "Category",
                    "Title",
                    "Priority",
                    "Status",
                    "Date"
            };

            table.setModel(
                    new javax.swing.table.DefaultTableModel(
                            data,
                            columns
                    ) {

                        @Override
                        public boolean isCellEditable(
                                int row,
                                int column
                        ) {
                            return false;
                        }
                    }
            );

            table.setRowHeight(35);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =========================================================
    // DASHBOARD DATA
    // =========================================================

    private void loadDashboardData() {

        try (Connection con =
                     Con.getConnection()) {

            String totalQuery =
                    "SELECT COUNT(*) FROM complaints";

            String pendingQuery =
                    "SELECT COUNT(*) FROM complaints " +
                            "WHERE status = 'Pending'";

            String progressQuery =
                    "SELECT COUNT(*) FROM complaints " +
                            "WHERE status = 'In Progress'";

            String resolvedQuery =
                    "SELECT COUNT(*) FROM complaints " +
                            "WHERE status = 'Resolved'";

            String staffQuery =
                    "SELECT COUNT(*) FROM maintenance_team";

            totalLabel.setText(
                    String.valueOf(
                            getCount(
                                    con,
                                    totalQuery
                            )
                    )
            );

            pendingLabel.setText(
                    String.valueOf(
                            getCount(
                                    con,
                                    pendingQuery
                            )
                    )
            );

            progressLabel.setText(
                    String.valueOf(
                            getCount(
                                    con,
                                    progressQuery
                            )
                    )
            );

            resolvedLabel.setText(
                    String.valueOf(
                            getCount(
                                    con,
                                    resolvedQuery
                            )
                    )
            );

            staffLabel.setText(
                    String.valueOf(
                            getCount(
                                    con,
                                    staffQuery
                            )
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // =========================================================
    // GET COUNT
    // =========================================================

    private int getCount(
            Connection con,
            String query
    ) throws SQLException {

        try (
                PreparedStatement ps =
                        con.prepareStatement(query);

                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);
            }
        }

        return 0;
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(() ->
                new adminDash(
                        "",
                        ""
                )
        );
    }
}