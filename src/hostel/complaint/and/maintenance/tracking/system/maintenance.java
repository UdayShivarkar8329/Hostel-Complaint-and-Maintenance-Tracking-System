package hostel.complaint.and.maintenance.tracking.system;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.EventObject;



public class maintenance extends JFrame {


    private final Color NAVY = new Color(8, 39, 83);
    private final Color BLUE = new Color(25, 112, 232);
    private final Color LIGHT_BLUE = new Color(245, 249, 254);
    private final Color TEXT = new Color(18, 48, 96);
    private final Color BORDER = new Color(215, 226, 240);

    private final Color GREEN = new Color(0, 145, 105);
    private final Color GREEN_BG = new Color(220, 244, 236);

    private final Color RED = new Color(225, 55, 55);
    private final Color RED_BG = new Color(255, 225, 225);

    private JTextField searchField;
    private JComboBox<String> roleBox;
    private JComboBox<String> statusBox;

    private JTable teamTable;
    private DefaultTableModel model;

    private JLabel totalMembersLabel;
    private JLabel activeMembersLabel;
    private JLabel onDutyLabel;
    private JLabel completedTasksLabel;

    private String adminName;
    private String adminUsername;

    public maintenance(String name, String username) {

       this.adminName = name;
        this.adminUsername = username;

        setTitle("Maintenance Team - Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1200, 750));
        setLayout(new BorderLayout());

        getContentPane().setBackground(LIGHT_BLUE);

        createSidebar();
        createMainArea();

        loadTeamMembers();

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
        sidebar.setPreferredSize(new Dimension(258, 0));
        sidebar.setLayout(new BorderLayout());

        add(sidebar, BorderLayout.WEST);

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(null);
        top.setPreferredSize(new Dimension(258, 500));

        JLabel homeIcon = new JLabel("⌂");
        homeIcon.setForeground(Color.WHITE);
        homeIcon.setFont(new Font("Arial", Font.PLAIN, 42));
        homeIcon.setBounds(20, 20, 40, 45);
        top.add(homeIcon);

        JLabel hostel = new JLabel("HOSTEL");
        hostel.setForeground(Color.WHITE);
        hostel.setFont(new Font("Arial", Font.BOLD, 21));
        hostel.setBounds(70, 18, 150, 28);
        top.add(hostel);

        JLabel line1 = new JLabel("Complaint & Maintenance");
        line1.setForeground(Color.WHITE);
        line1.setFont(new Font("Arial", Font.PLAIN, 12));
        line1.setBounds(70, 44, 175, 20);
        top.add(line1);

        JLabel line2 = new JLabel("Tracking System");
        line2.setForeground(Color.WHITE);
        line2.setFont(new Font("Arial", Font.PLAIN, 12));
        line2.setBounds(70, 62, 160, 20);
        top.add(line2);

        JLabel adminPanel = new JLabel("ADMIN PANEL");
        adminPanel.setForeground(Color.WHITE);
        adminPanel.setFont(new Font("Arial", Font.BOLD, 13));
        adminPanel.setBounds(20, 125, 150, 25);
        top.add(adminPanel);

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(62, 91, 128));
        separator.setBounds(20, 151, 218, 1);
        top.add(separator);

        createSidebarButton(top, "⌂", "Admin Home", 163, false);
        createSidebarButton(top, "▤", "Complaint Center", 211, false);
        createSidebarButton(top, "⚒", "Maintenance Team", 259, true);
        createSidebarButton(top, "♜", "Hostel Records", 307, false);
        createSidebarButton(top, "⚑", "Communication", 355, false);

        JSeparator separator2 = new JSeparator();
        separator2.setForeground(new Color(62, 91, 128));
        separator2.setBounds(20, 425, 218, 1);
        top.add(separator2);

        createSidebarButton(top, "⇥", "Logout", 445, false);

        sidebar.add(top, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JLabel building = new JLabel("▥");
        building.setForeground(new Color(91, 132, 180));
        building.setFont(new Font("Arial", Font.BOLD, 85));
        building.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel better = new JLabel("Better Hostel");
        better.setForeground(new Color(155, 196, 239));
        better.setFont(new Font("Arial", Font.PLAIN, 15));
        better.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel happier = new JLabel("Happier Students");
        happier.setForeground(new Color(155, 196, 239));
        happier.setFont(new Font("Arial", Font.PLAIN, 15));
        happier.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottom.add(building);
        bottom.add(Box.createVerticalStrut(5));
        bottom.add(better);
        bottom.add(happier);
        bottom.add(Box.createVerticalStrut(90));

        sidebar.add(bottom, BorderLayout.SOUTH);
    }

    private void createSidebarButton(
            JPanel parent,
            String icon,
            String text,
            int y,
            boolean selected) {

        JPanel button = new JPanel(null);

        button.setBackground(selected ? BLUE : NAVY);
        button.setBounds(10, y, 238, 44);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setFont(new Font("Arial", Font.BOLD, 21));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setBounds(12, 4, 32, 34);

        button.add(iconLabel);

        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        textLabel.setBounds(53, 4, 175, 34);

        button.add(textLabel);

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!selected)
                    button.setBackground(new Color(17, 61, 116));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!selected)
                    button.setBackground(NAVY);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                if (text.equals("Admin Home")) {
                    dispose();
                    new adminDash(adminName, adminUsername);
                } else if (text.equals("Complaint Center")) {
                    dispose();
                    new complaintcenter(adminName, adminUsername).setVisible(true);
                } else if (text.equals("Hostel Records")) {
                    dispose();
                    new hostelRecords(adminName,adminUsername);

                }  else if (text.equals("Logout")) {

                    int result = JOptionPane.showConfirmDialog(
                            maintenance.this,
                            "Are you sure you want to logout?",
                            "Logout",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (result == JOptionPane.YES_OPTION) {
                        dispose();
                        new login("", "").setVisible(true);
                    }
                }
            }
        });

        parent.add(button);
    }


    private void createMainArea() {

        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setBackground(LIGHT_BLUE);

        add(mainArea, BorderLayout.CENTER);

        createTopBar(mainArea);

        JPanel content = new JPanel();

        content.setBackground(LIGHT_BLUE);

        content.setLayout(
                new BoxLayout(content, BoxLayout.Y_AXIS)
        );

        content.setBorder(
                new EmptyBorder(20, 18, 20, 18)
        );

        JPanel headingPanel = new JPanel(new BorderLayout());
        headingPanel.setOpaque(false);
        headingPanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 70)
        );

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(
                new BoxLayout(
                        headingText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel heading = new JLabel("Maintenance Team");

        heading.setForeground(TEXT);
        heading.setFont(
                new Font("Arial", Font.BOLD, 31)
        );

        JLabel description = new JLabel(
                "View and manage hostel maintenance team members and their assignments."
        );

        description.setForeground(
                new Color(40, 72, 123)
        );

        description.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        headingText.add(heading);
        headingText.add(Box.createVerticalStrut(2));
        headingText.add(description);

        RoundedButton addTopButton =
                new RoundedButton(
                        "⊕  Add Team Member",
                        BLUE,
                        Color.WHITE
                );

        addTopButton.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        addTopButton.setPreferredSize(
                new Dimension(190, 52)
        );

        addTopButton.addActionListener(
                e -> showAddMemberDialog()
        );

        headingPanel.add(
                headingText,
                BorderLayout.WEST
        );

        headingPanel.add(
                addTopButton,
                BorderLayout.EAST
        );

        content.add(headingPanel);
        content.add(Box.createVerticalStrut(12));

        JPanel cards = createSummaryCards();

        cards.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 125)
        );

        content.add(cards);
        content.add(Box.createVerticalStrut(16));

        JPanel filter = createFilterPanel();

        filter.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 96)
        );

        content.add(filter);
        content.add(Box.createVerticalStrut(16));

        JPanel table = createTeamTable();

        table.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 440)
        );

        content.add(table);
        content.add(Box.createVerticalStrut(12));

        JPanel addMember = createAddMemberSection();

        addMember.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 90)
        );

        content.add(addMember);

        JScrollPane scrollPane =
                new JScrollPane(
                        content,
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

    private void createTopBar(JPanel mainArea) {

        JPanel topBar = new JPanel(new BorderLayout());

        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(0, 64));

        topBar.setBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 1, 0,
                        new Color(224, 232, 243)
                )
        );

        JLabel menu = new JLabel("☰");

        menu.setForeground(NAVY);
        menu.setFont(
                new Font("Arial", Font.PLAIN, 28)
        );

        menu.setBorder(
                new EmptyBorder(0, 27, 0, 0)
        );

        JPanel right =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                15,
                                12
                        )
                );

        right.setOpaque(false);

        JLabel notification = new JLabel("♧");

        notification.setForeground(NAVY);
        notification.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        JLabel count = new JLabel("3");

        count.setForeground(Color.WHITE);
        count.setBackground(new Color(235, 55, 55));
        count.setOpaque(true);
        count.setHorizontalAlignment(SwingConstants.CENTER);

        count.setFont(
                new Font("Arial", Font.BOLD, 10)
        );

        count.setPreferredSize(
                new Dimension(16, 16)
        );

        JPanel notificationPanel =
                new JPanel(new BorderLayout());

        notificationPanel.setOpaque(false);

        notificationPanel.add(
                notification,
                BorderLayout.CENTER
        );

        notificationPanel.add(
                count,
                BorderLayout.NORTH
        );

        JPanel profile =
                new JPanel(new BorderLayout());

        profile.setBackground(
                new Color(232, 240, 252)
        );

        profile.setPreferredSize(
                new Dimension(40, 40)
        );

        JLabel person = new JLabel("●");

        person.setForeground(NAVY);
        person.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        person.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        profile.add(person, BorderLayout.CENTER);

        JLabel admin = new JLabel("Admin");

        admin.setForeground(TEXT);
        admin.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        JLabel arrow = new JLabel("⌄");

        arrow.setForeground(NAVY);
        arrow.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        right.add(notificationPanel);
        right.add(profile);
        right.add(admin);
        right.add(arrow);
        right.add(Box.createHorizontalStrut(15));

        topBar.add(menu, BorderLayout.WEST);
        topBar.add(right, BorderLayout.EAST);

        mainArea.add(topBar, BorderLayout.NORTH);
    }

    // =========================================================
    // SUMMARY CARDS
    // =========================================================

    private JPanel createSummaryCards() {

        JPanel cards =
                new JPanel(
                        new GridLayout(1, 4, 16, 0)
                );

        cards.setOpaque(false);

        JPanel c1 = createSummaryCard(
                "♟",
                "Total Team Members",
                "0",
                "Database records",
                new Color(231, 243, 255),
                new Color(71, 145, 232)
        );

        JPanel c2 = createSummaryCard(
                "⚒",
                "Active Members",
                "0",
                "Currently active",
                new Color(255, 247, 237),
                new Color(244, 174, 82)
        );

        JPanel c3 = createSummaryCard(
                "◷",
                "On Duty Today",
                "0",
                "Currently on duty",
                new Color(246, 241, 255),
                new Color(153, 121, 226)
        );

        JPanel c4 = createSummaryCard(
                "✓",
                "Completed Tasks",
                "0",
                "Completed assignments",
                new Color(237, 250, 246),
                new Color(63, 184, 153)
        );

        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);

        return cards;
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
                        iconColor
                );

        card.setLayout(new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(0, 10, 0, 10);

        JPanel iconPanel =
                new JPanel(new BorderLayout());

        iconPanel.setBackground(iconColor);

        iconPanel.setPreferredSize(
                new Dimension(48, 48)
        );

        JLabel iconLabel = new JLabel(icon);

        iconLabel.setForeground(Color.WHITE);
        iconLabel.setFont(
                new Font("Arial", Font.BOLD, 25)
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

        card.add(iconPanel, gbc);

        JPanel text = new JPanel();

        text.setOpaque(false);

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel = new JLabel(title);

        titleLabel.setForeground(TEXT);
        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        JLabel valueLabel = new JLabel(value);

        valueLabel.setForeground(TEXT);
        valueLabel.setFont(
                new Font("Arial", Font.BOLD, 29)
        );

        JLabel growthLabel = new JLabel(growth);

        growthLabel.setForeground(GREEN);
        growthLabel.setFont(
                new Font("Arial", Font.PLAIN, 11)
        );

        text.add(titleLabel);
        text.add(Box.createVerticalStrut(2));
        text.add(valueLabel);
        text.add(Box.createVerticalStrut(2));
        text.add(growthLabel);

        if (title.equals("Total Team Members"))
            totalMembersLabel = valueLabel;

        if (title.equals("Active Members"))
            activeMembersLabel = valueLabel;

        if (title.equals("On Duty Today"))
            onDutyLabel = valueLabel;

        if (title.equals("Completed Tasks"))
            completedTasksLabel = valueLabel;

        gbc.gridx = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        card.add(text, gbc);

        return card;
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

        panel.setLayout(new GridBagLayout());

        panel.setBorder(
                new EmptyBorder(12, 10, 12, 10)
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(0, 5, 0, 5);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        searchField = new JTextField();

        searchField.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        new RoundedBorder(BORDER, 8),
                        new EmptyBorder(0, 12, 0, 8)
                )
        );

        searchField.setToolTipText(
                "Search by name, role or phone"
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2.7;

        panel.add(searchField, gbc);

        roleBox =
                createComboBox(
                        new String[]{
                                "All Roles",
                                "Electrician",
                                "Plumber",
                                "Carpenter",
                                "Cleaner",
                                "General Worker",
                                "Technician"
                        }
                );

        statusBox =
                createComboBox(
                        new String[]{
                                "All Status",
                                "Active",
                                "Inactive"
                        }
                );

        gbc.gridx = 1;
        gbc.weightx = 1.1;

        panel.add(
                createFilterGroup("Role", roleBox),
                gbc
        );

        gbc.gridx = 2;

        panel.add(
                createFilterGroup("Status", statusBox),
                gbc
        );

        RoundedButton searchButton =
                new RoundedButton(
                        "⌕  Search",
                        BLUE,
                        Color.WHITE
                );

        searchButton.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        searchButton.setPreferredSize(
                new Dimension(130, 40)
        );

        searchButton.addActionListener(
                e -> searchTeamMembers()
        );

        gbc.gridx = 3;
        gbc.weightx = 0.7;

        panel.add(searchButton, gbc);

        RoundedButton resetButton =
                new RoundedButton(
                        "⟳  Reset",
                        Color.WHITE,
                        NAVY
                );

        resetButton.setBorderColor(BORDER);

        resetButton.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        resetButton.setPreferredSize(
                new Dimension(110, 40)
        );

        resetButton.addActionListener(e -> {

            searchField.setText("");
            roleBox.setSelectedIndex(0);
            statusBox.setSelectedIndex(0);

            loadTeamMembers();
        });

        gbc.gridx = 4;
        gbc.weightx = 0.6;

        panel.add(resetButton, gbc);

        return panel;
    }

    private JPanel createFilterGroup(
            String title,
            JComboBox<String> combo) {

        JPanel panel = new JPanel();

        panel.setOpaque(false);

        panel.setLayout(
                new BorderLayout(0, 5)
        );

        JLabel label = new JLabel(title);

        label.setForeground(TEXT);
        label.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        panel.add(label, BorderLayout.NORTH);
        panel.add(combo, BorderLayout.CENTER);

        return panel;
    }

    private JComboBox<String> createComboBox(
            String[] items) {

        JComboBox<String> combo =
                new JComboBox<>(items);

        combo.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        combo.setForeground(TEXT);
        combo.setBackground(Color.WHITE);

        combo.setBorder(
                new RoundedBorder(BORDER, 8)
        );

        combo.setPreferredSize(
                new Dimension(140, 40)
        );

        return combo;
    }

    // =========================================================
    // TEAM TABLE
    // =========================================================

    private JPanel createTeamTable() {

        RoundedPanel panel =
                new RoundedPanel(
                        10,
                        Color.WHITE,
                        BORDER
                );

        panel.setLayout(
                new BorderLayout(0, 8)
        );

        panel.setBorder(
                new EmptyBorder(14, 10, 10, 10)
        );

        JLabel heading =
                new JLabel("Team Members");

        heading.setForeground(TEXT);

        heading.setFont(
                new Font("Arial", Font.BOLD, 19)
        );

        panel.add(heading, BorderLayout.NORTH);

        String[] columns = {
                "ID",
                "Name",
                "Role",
                "Department",
                "Phone",
                "Email",
                "Status",
                "Availability",
                "Actions"
        };

        model =
                new DefaultTableModel(
                        new Object[0][columns.length],
                        columns
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return column == 8;
                    }
                };

        teamTable = new JTable(model);

        teamTable.setFont(
                new Font("Arial", Font.PLAIN, 11)
        );

        teamTable.setForeground(TEXT);
        teamTable.setRowHeight(50);
        teamTable.setShowGrid(true);

        teamTable.setGridColor(
                new Color(232, 238, 247)
        );

        teamTable.setIntercellSpacing(
                new Dimension(0, 0)
        );

        teamTable.setSelectionBackground(
                new Color(235, 243, 255)
        );

        teamTable.getTableHeader()
                .setReorderingAllowed(false);

        teamTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                11
                        )
                );

        teamTable.getTableHeader()
                .setForeground(TEXT);

        teamTable.getTableHeader()
                .setBackground(
                        new Color(245, 248, 253)
                );

        teamTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(0, 38)
                );

        int[] widths = {
                60, 150, 130, 130, 135,
                160, 90, 105, 235
        };

        for (int i = 0; i < widths.length; i++) {

            teamTable
                    .getColumnModel()
                    .getColumn(i)
                    .setPreferredWidth(widths[i]);
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
                                super.getTableCellRendererComponent(
                                        table,
                                        value,
                                        selected,
                                        focused,
                                        row,
                                        column
                                );

                        setBorder(
                                new EmptyBorder(
                                        0, 8, 0, 5
                                )
                        );

                        setHorizontalAlignment(
                                SwingConstants.LEFT
                        );

                        if (selected) {
                            component.setBackground(
                                    new Color(235, 243, 255)
                            );
                            component.setForeground(TEXT);
                        }
                        else {
                            component.setBackground(
                                    Color.WHITE
                            );
                            component.setForeground(TEXT);
                        }

                        return component;
                    }
                };

        for (int i = 0; i < 6; i++) {

            teamTable
                    .getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(normalRenderer);
        }

        teamTable
                .getColumnModel()
                .getColumn(6)
                .setCellRenderer(
                        new TeamStatusRenderer()
                );

        teamTable
                .getColumnModel()
                .getColumn(7)
                .setCellRenderer(
                        new AvailabilityRenderer()
                );

        teamTable
                .getColumnModel()
                .getColumn(8)
                .setCellRenderer(
                        new ActionRenderer()
                );

        teamTable
                .getColumnModel()
                .getColumn(8)
                .setCellEditor(
                        new ActionEditor()
                );

        JScrollPane scrollPane =
                new JScrollPane(teamTable);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // LOAD DATA FROM MYSQL
    // =========================================================

    private void loadTeamMembers() {

        if (model == null)
            return;

        model.setRowCount(0);

        String sql =
                "SELECT staff_id, staff_name, staff_type, " +
                        "department, staff_number, email, status, availability " +
                        "FROM maintenance_team ORDER BY staff_id";

        try (
                Connection con = Con.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                model.addRow(
                        new Object[]{
                                "MT" +
                                        String.format(
                                                "%03d",
                                                rs.getInt("staff_id")
                                        ),

                                rs.getString("staff_name"),

                                rs.getString("staff_type"),

                                rs.getString("department"),

                                rs.getString("staff_number"),

                                rs.getString("email"),

                                rs.getString("status"),

                                rs.getString("availability"),

                                ""
                        }
                );
            }

            updateSummaryCards();

        }
        catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load maintenance team.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void searchTeamMembers() {

        model.setRowCount(0);

        String search =
                searchField.getText()
                        .trim();

        String role =
                roleBox.getSelectedItem()
                        .toString();

        String status =
                statusBox.getSelectedItem()
                        .toString();

        StringBuilder sql =
                new StringBuilder(
                        "SELECT staff_id, staff_name, staff_type, " +
                                "department, staff_number, email, status, availability " +
                                "FROM maintenance_team WHERE 1=1 "
                );

        if (!search.isEmpty()) {

            sql.append(
                    "AND (staff_name LIKE ? " +
                            "OR staff_type LIKE ? " +
                            "OR staff_number LIKE ?) "
            );
        }

        if (!role.equals("All Roles"))
            sql.append("AND staff_type = ? ");

        if (!status.equals("All Status"))
            sql.append("AND status = ? ");

        sql.append("ORDER BY staff_id");

        try (
                Connection con = Con.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql.toString())
        ) {

            int index = 1;

            if (!search.isEmpty()) {

                String value =
                        "%" + search + "%";

                ps.setString(index++, value);
                ps.setString(index++, value);
                ps.setString(index++, value);
            }

            if (!role.equals("All Roles"))
                ps.setString(index++, role);

            if (!status.equals("All Status"))
                ps.setString(index++, status);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                model.addRow(
                        new Object[]{
                                "MT" +
                                        String.format(
                                                "%03d",
                                                rs.getInt("staff_id")
                                        ),

                                rs.getString("staff_name"),
                                rs.getString("staff_type"),
                                rs.getString("department"),
                                rs.getString("staff_number"),
                                rs.getString("email"),
                                rs.getString("status"),
                                rs.getString("availability"),
                                ""
                        }
                );
            }

        }
        catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Search Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // SUMMARY
    // =========================================================

    private void updateSummaryCards() {

        String sql =
                "SELECT " +
                        "(SELECT COUNT(*) FROM maintenance_team), " +
                        "(SELECT COUNT(*) FROM maintenance_team WHERE status='Active'), " +
                        "(SELECT COUNT(*) FROM maintenance_team WHERE availability='On Duty'), " +
                        "(SELECT COUNT(*) FROM maintenance_assignments WHERE task_status='Completed')";

        try (
                Connection con = Con.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql);
                ResultSet rs =
                        ps.executeQuery()
        ) {

            if (rs.next()) {

                totalMembersLabel.setText(
                        rs.getString(1)
                );

                activeMembersLabel.setText(
                        rs.getString(2)
                );

                onDutyLabel.setText(
                        rs.getString(3)
                );

                completedTasksLabel.setText(
                        rs.getString(4)
                );
            }

        }
        catch (SQLException e) {

            totalMembersLabel.setText("0");
            activeMembersLabel.setText("0");
            onDutyLabel.setText("0");
            completedTasksLabel.setText("0");
        }
    }

    // =========================================================
    // ADD MEMBER
    // =========================================================

    private void showAddMemberDialog() {

        JTextField name =
                new JTextField();

        JComboBox<String> role =
                new JComboBox<>(
                        new String[]{
                                "Electrician",
                                "Plumber",
                                "Carpenter",
                                "Cleaner",
                                "General Worker",
                                "Technician"
                        }
                );

        JTextField department =
                new JTextField();

        JTextField phone =
                new JTextField();

        JTextField email =
                new JTextField();

        JComboBox<String> availability =
                new JComboBox<>(
                        new String[]{
                                "On Duty",
                                "Off Duty"
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
                        10, 10, 10, 10
                )
        );

        panel.add(new JLabel("Name:"));
        panel.add(name);

        panel.add(new JLabel("Role:"));
        panel.add(role);

        panel.add(new JLabel("Department:"));
        panel.add(department);

        panel.add(new JLabel("Phone:"));
        panel.add(phone);

        panel.add(new JLabel("Email:"));
        panel.add(email);

        panel.add(new JLabel("Availability:"));
        panel.add(availability);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add Maintenance Team Member",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION)
            return;

        if (name.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter member name.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String sql =
                "INSERT INTO maintenance_team " +
                        "(staff_name, staff_type, department, " +
                        "staff_number, email, status, availability) " +
                        "VALUES (?, ?, ?, ?, ?, 'Active', ?)";

        try (
                Connection con = Con.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    name.getText().trim()
            );

            ps.setString(
                    2,
                    role.getSelectedItem().toString()
            );

            ps.setString(
                    3,
                    department.getText().trim()
            );

            ps.setString(
                    4,
                    phone.getText().trim()
            );

            ps.setString(
                    5,
                    email.getText().trim()
            );

            ps.setString(
                    6,
                    availability.getSelectedItem().toString()
            );

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "New member added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadTeamMembers();

        }
        catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to add member.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // VIEW MEMBER
    // =========================================================

    private void showMemberDetails(int row) {

        int staffId =
                getStaffId(row);

        String sql =
                "SELECT * FROM maintenance_team " +
                        "WHERE staff_id = ?";

        try (
                Connection con = Con.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, staffId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                String message =
                        "ID: MT" +
                                String.format(
                                        "%03d",
                                        rs.getInt("staff_id")
                                ) +

                                "\nName: " +
                                rs.getString("staff_name") +

                                "\nRole: " +
                                rs.getString("staff_type") +

                                "\nDepartment: " +
                                rs.getString("department") +

                                "\nPhone: " +
                                rs.getString("staff_number") +

                                "\nEmail: " +
                                rs.getString("email") +

                                "\nStatus: " +
                                rs.getString("status") +

                                "\nAvailability: " +
                                rs.getString("availability");

                JOptionPane.showMessageDialog(
                        this,
                        message,
                        "Member Details",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        }
        catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // EDIT MEMBER
    // =========================================================

    private void editMember(int row) {

        int staffId =
                getStaffId(row);

        JTextField name =
                new JTextField(
                        teamTable
                                .getValueAt(row, 1)
                                .toString()
                );

        JComboBox<String> role =
                new JComboBox<>(
                        new String[]{
                                "Electrician",
                                "Plumber",
                                "Carpenter",
                                "Cleaner",
                                "General Worker",
                                "Technician"
                        }
                );

        role.setSelectedItem(
                teamTable
                        .getValueAt(row, 2)
                        .toString()
        );

        JTextField department =
                new JTextField(
                        teamTable
                                .getValueAt(row, 3)
                                .toString()
                );

        JTextField phone =
                new JTextField(
                        teamTable
                                .getValueAt(row, 4)
                                .toString()
                );

        JTextField email =
                new JTextField(
                        teamTable
                                .getValueAt(row, 5)
                                .toString()
                );

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                5,
                                2,
                                10,
                                10
                        )
                );

        panel.setBorder(
                new EmptyBorder(
                        10, 10, 10, 10
                )
        );

        panel.add(new JLabel("Name:"));
        panel.add(name);

        panel.add(new JLabel("Role:"));
        panel.add(role);

        panel.add(new JLabel("Department:"));
        panel.add(department);

        panel.add(new JLabel("Phone:"));
        panel.add(phone);

        panel.add(new JLabel("Email:"));
        panel.add(email);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Edit Member",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (result != JOptionPane.OK_OPTION)
            return;

        String sql =
                "UPDATE maintenance_team SET " +
                        "staff_name=?, staff_type=?, department=?, " +
                        "staff_number=?, email=? " +
                        "WHERE staff_id=?";

        try (
                Connection con = Con.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(
                    1,
                    name.getText().trim()
            );

            ps.setString(
                    2,
                    role.getSelectedItem().toString()
            );

            ps.setString(
                    3,
                    department.getText().trim()
            );

            ps.setString(
                    4,
                    phone.getText().trim()
            );

            ps.setString(
                    5,
                    email.getText().trim()
            );

            ps.setInt(6, staffId);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Member updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadTeamMembers();

        }
        catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // CHANGE STATUS
    // =========================================================

    private void changeStatus(int row) {

        int staffId =
                getStaffId(row);

        String current =
                teamTable
                        .getValueAt(row, 6)
                        .toString();

        String newStatus =
                current.equals("Active")
                        ? "Inactive"
                        : "Active";

        String sql =
                "UPDATE maintenance_team " +
                        "SET status=? WHERE staff_id=?";

        try (
                Connection con = Con.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setString(1, newStatus);
            ps.setInt(2, staffId);

            ps.executeUpdate();

            loadTeamMembers();

            JOptionPane.showMessageDialog(
                    this,
                    "Member status changed to "
                            + newStatus + ".",
                    "Status Updated",
                    JOptionPane.INFORMATION_MESSAGE
            );

        }
        catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // REMOVE MEMBER
    // =========================================================

    private void removeMember(int row) {

        int staffId =
                getStaffId(row);

        String memberName =
                teamTable
                        .getValueAt(row, 1)
                        .toString();

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to remove\n"
                                + memberName
                                + " from the maintenance team?",
                        "Remove Member",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (result != JOptionPane.YES_OPTION)
            return;

        String deleteAssignments =
                "DELETE FROM maintenance_assignments " +
                        "WHERE staff_id=?";

        String deleteMember =
                "DELETE FROM maintenance_team " +
                        "WHERE staff_id=?";

        try (
                Connection con = Con.getConnection()
        ) {

            con.setAutoCommit(false);

            try (
                    PreparedStatement ps1 =
                            con.prepareStatement(
                                    deleteAssignments
                            );

                    PreparedStatement ps2 =
                            con.prepareStatement(
                                    deleteMember
                            )
            ) {

                ps1.setInt(1, staffId);
                ps1.executeUpdate();

                ps2.setInt(1, staffId);
                ps2.executeUpdate();

                con.commit();

                JOptionPane.showMessageDialog(
                        this,
                        "Member removed successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                loadTeamMembers();
            }

        }
        catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // ASSIGN TASK
    // =========================================================

    private void showAssignDialog(
            int row) {

        int staffId =
                getStaffId(row);

        String memberName =
                teamTable
                        .getValueAt(row, 1)
                        .toString();

        JTextField complaintID =
                new JTextField();

        JTextArea description =
                new JTextArea(4, 20);

        description.setLineWrap(true);
        description.setWrapStyleWord(true);

        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBorder(
                new EmptyBorder(
                        10, 10, 10, 10
                )
        );

        panel.add(
                new JLabel(
                        "Assign task to: "
                                + memberName
                )
        );

        panel.add(
                Box.createVerticalStrut(10)
        );

        panel.add(
                new JLabel("Complaint ID:")
        );

        panel.add(complaintID);

        panel.add(
                Box.createVerticalStrut(10)
        );

        panel.add(
                new JLabel("Task Description:")
        );

        panel.add(
                new JScrollPane(description)
        );

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Assign Maintenance Task",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION)
            return;

        if (description.getText()
                .trim()
                .isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter task description.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Integer complaintId = null;

        if (!complaintID.getText()
                .trim()
                .isEmpty()) {

            try {

                complaintId =
                        Integer.parseInt(
                                complaintID.getText()
                                        .trim()
                        );

            }
            catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Complaint ID must be a number.",
                        "Validation",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }

        String sql =
                "INSERT INTO maintenance_assignments " +
                        "(staff_id, complaint_id, task_description, task_status) " +
                        "VALUES (?, ?, ?, 'Assigned')";

        try (
                Connection con = Con.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, staffId);

            if (complaintId == null)
                ps.setNull(2, Types.INTEGER);
            else
                ps.setInt(2, complaintId);

            ps.setString(
                    3,
                    description.getText().trim()
            );

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Task assigned successfully to "
                            + memberName + ".",
                    "Assignment Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            updateSummaryCards();

        }
        catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // TASK HISTORY
    // =========================================================

    private void showTaskHistory(int row) {

        int staffId =
                getStaffId(row);

        String memberName =
                teamTable
                        .getValueAt(row, 1)
                        .toString();

        String sql =
                "SELECT assignment_id, complaint_id, " +
                        "task_description, task_status, assigned_at, completed_at " +
                        "FROM maintenance_assignments " +
                        "WHERE staff_id=? " +
                        "ORDER BY assigned_at DESC";

        StringBuilder history =
                new StringBuilder();

        try (
                Connection con = Con.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, staffId);

            ResultSet rs =
                    ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                history.append(
                                "Assignment #"
                        )
                        .append(
                                rs.getInt("assignment_id")
                        )
                        .append("\n");

                history.append(
                                "Complaint ID: "
                        )
                        .append(
                                rs.getObject("complaint_id")
                        )
                        .append("\n");

                history.append(
                                "Task: "
                        )
                        .append(
                                rs.getString(
                                        "task_description"
                                )
                        )
                        .append("\n");

                history.append(
                                "Status: "
                        )
                        .append(
                                rs.getString(
                                        "task_status"
                                )
                        )
                        .append("\n");

                history.append(
                                "Assigned: "
                        )
                        .append(
                                rs.getTimestamp(
                                        "assigned_at"
                                )
                        )
                        .append("\n\n");
            }

            if (!found)
                history.append(
                        "No task history found."
                );

            JTextArea area =
                    new JTextArea(
                            history.toString()
                    );

            area.setEditable(false);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            13
                    )
            );

            JScrollPane scroll =
                    new JScrollPane(area);

            scroll.setPreferredSize(
                    new Dimension(
                            500,
                            350
                    )
            );

            JOptionPane.showMessageDialog(
                    this,
                    scroll,
                    "Task History - "
                            + memberName,
                    JOptionPane.INFORMATION_MESSAGE
            );

        }
        catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // PERFORMANCE
    // =========================================================

    private void showPerformance(int row) {

        int staffId =
                getStaffId(row);

        String memberName =
                teamTable
                        .getValueAt(row, 1)
                        .toString();

        String sql =
                "SELECT " +
                        "COUNT(*) AS total, " +
                        "SUM(CASE WHEN task_status='Completed' " +
                        "THEN 1 ELSE 0 END) AS completed, " +
                        "SUM(CASE WHEN task_status='Assigned' " +
                        "THEN 1 ELSE 0 END) AS assigned " +
                        "FROM maintenance_assignments " +
                        "WHERE staff_id=?";

        try (
                Connection con = Con.getConnection();
                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {

            ps.setInt(1, staffId);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                int total =
                        rs.getInt("total");

                int completed =
                        rs.getInt("completed");

                int assigned =
                        rs.getInt("assigned");

                JOptionPane.showMessageDialog(
                        this,
                        "Member: " + memberName +
                                "\n\nTotal Tasks: " + total +
                                "\nCompleted Tasks: " + completed +
                                "\nAssigned Tasks: " + assigned,
                        "Member Performance",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        }
        catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private int getStaffId(int row) {

        String id =
                teamTable
                        .getValueAt(row, 0)
                        .toString();

        return Integer.parseInt(
                id.replace("MT", "")
        );
    }

    // =========================================================
    // MORE MENU
    // =========================================================

    private JPopupMenu createMoreMenu(int row) {

        JPopupMenu popup =
                new JPopupMenu();

        popup.setBorder(
                BorderFactory.createCompoundBorder(
                        new RoundedBorder(BORDER, 8),
                        new EmptyBorder(5, 5, 5, 5)
                )
        );

        popup.setBackground(Color.WHITE);

        JMenuItem edit =
                createMenuItem("✎  Edit Member");

        JMenuItem history =
                createMenuItem("◷  View Task History");

        JMenuItem performance =
                createMenuItem("▥  View Performance");

        JMenuItem status =
                createMenuItem("●  Change Status");

        JMenuItem remove =
                createMenuItem("▣  Remove Member");

        remove.setForeground(RED);

        popup.add(edit);
        popup.add(history);
        popup.add(performance);
        popup.add(status);
        popup.addSeparator();
        popup.add(remove);

        edit.addActionListener(
                e -> editMember(row)
        );

        history.addActionListener(
                e -> showTaskHistory(row)
        );

        performance.addActionListener(
                e -> showPerformance(row)
        );

        status.addActionListener(
                e -> changeStatus(row)
        );

        remove.addActionListener(
                e -> removeMember(row)
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
        item.setBackground(Color.WHITE);

        item.setPreferredSize(
                new Dimension(190, 38)
        );

        return item;
    }

    // =========================================================
    // ADD MEMBER BOTTOM SECTION
    // =========================================================

    private JPanel createAddMemberSection() {

        RoundedPanel panel =
                new RoundedPanel(
                        10,
                        new Color(240, 248, 255),
                        new Color(120, 190, 255)
                );

        panel.setLayout(
                new BorderLayout(15, 0)
        );

        panel.setBorder(
                new EmptyBorder(
                        10, 18, 10, 18
                )
        );

        JPanel left =
                new JPanel(
                        new BorderLayout(15, 0)
                );

        left.setOpaque(false);

        JPanel iconPanel =
                new JPanel(new BorderLayout());

        iconPanel.setBackground(
                new Color(218, 239, 255)
        );

        iconPanel.setPreferredSize(
                new Dimension(54, 54)
        );

        JLabel icon =
                new JLabel("♟+");

        icon.setForeground(BLUE);

        icon.setFont(
                new Font("Arial", Font.BOLD, 23)
        );

        icon.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        iconPanel.add(
                icon,
                BorderLayout.CENTER
        );

        JPanel text = new JPanel();

        text.setOpaque(false);

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel("Add Member");

        title.setForeground(TEXT);

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        JLabel description =
                new JLabel(
                        "Quickly add a new member to the maintenance team with all necessary details."
                );

        description.setForeground(
                new Color(55, 91, 135)
        );

        description.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        text.add(title);
        text.add(Box.createVerticalStrut(3));
        text.add(description);

        left.add(
                iconPanel,
                BorderLayout.WEST
        );

        left.add(
                text,
                BorderLayout.CENTER
        );

        RoundedButton addButton =
                new RoundedButton(
                        " Add Member  ›",
                        BLUE,
                        Color.WHITE
                );

        addButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        addButton.setPreferredSize(
                new Dimension(160, 42)
        );

        addButton.addActionListener(
                e -> showAddMemberDialog()
        );

        panel.add(left, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.EAST);

        return panel;
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
                                    8
                            )
                    );

            panel.setBackground(
                    selected
                            ? new Color(235, 243, 255)
                            : Color.WHITE
            );

            RoundedButton view =
                    new RoundedButton(
                            "◉  View",
                            Color.WHITE,
                            BLUE
                    );

            view.setBorderColor(
                    new Color(123, 181, 255)
            );

            view.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            11
                    )
            );

            view.setPreferredSize(
                    new Dimension(90, 30)
            );

            RoundedButton assign =
                    new RoundedButton(
                            "♟  Assign",
                            Color.WHITE,
                            BLUE
                    );

            assign.setBorderColor(
                    new Color(123, 181, 255)
            );

            assign.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            11
                    )
            );

            assign.setPreferredSize(
                    new Dimension(100, 30)
            );

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
                    new Dimension(40, 30)
            );

            panel.add(view);
            panel.add(assign);
            panel.add(more);

            return panel;
        }
    }

    // =========================================================
    // REAL ACTION EDITOR
    // =========================================================

    private class ActionEditor
            extends AbstractCellEditor
            implements TableCellEditor {

        private JPanel panel;
        private int currentRow;

        @Override
        public Component getTableCellEditorComponent(
                JTable table,
                Object value,
                boolean selected,
                int row,
                int column) {

            currentRow = row;

            panel =
                    new JPanel(
                            new FlowLayout(
                                    FlowLayout.LEFT,
                                    7,
                                    8
                            )
                    );

            panel.setBackground(
                    new Color(235, 243, 255)
            );

            RoundedButton view =
                    new RoundedButton(
                            "◉  View",
                            Color.WHITE,
                            BLUE
                    );

            view.setBorderColor(
                    new Color(123, 181, 255)
            );

            view.setPreferredSize(
                    new Dimension(90, 30)
            );

            RoundedButton assign =
                    new RoundedButton(
                            "♟  Assign",
                            Color.WHITE,
                            BLUE
                    );

            assign.setBorderColor(
                    new Color(123, 181, 255)
            );

            assign.setPreferredSize(
                    new Dimension(100, 30)
            );

            RoundedButton more =
                    new RoundedButton(
                            "⋯",
                            Color.WHITE,
                            NAVY
                    );

            more.setBorderColor(BORDER);

            more.setPreferredSize(
                    new Dimension(40, 30)
            );

            view.addActionListener(e -> {

                fireEditingStopped();

                showMemberDetails(currentRow);
            });

            assign.addActionListener(e -> {

                fireEditingStopped();

                showAssignDialog(currentRow);
            });

            more.addActionListener(e -> {

                fireEditingStopped();

                JPopupMenu menu =
                        createMoreMenu(currentRow);

                SwingUtilities.invokeLater(() -> {
                    if (more.isShowing()) {
                        menu.show(
                                more,
                                0,
                                more.getHeight()
                        );
                    }
                });
            });

            panel.add(view);
            panel.add(assign);
            panel.add(more);

            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }

        @Override
        public boolean isCellEditable(
                EventObject e) {

            return true;
        }
    }

    // =========================================================
    // STATUS RENDERER
    // =========================================================

    private class TeamStatusRenderer
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

            JLabel label =
                    new JLabel(
                            String.valueOf(value)
                    );

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
                            5, 10, 5, 10
                    )
            );

            if ("Active".equals(
                    String.valueOf(value))) {

                label.setForeground(GREEN);
                label.setBackground(GREEN_BG);

            }
            else {

                label.setForeground(RED);
                label.setBackground(RED_BG);
            }

            JPanel wrapper =
                    new JPanel(
                            new GridBagLayout()
                    );

            wrapper.setBackground(
                    selected
                            ? new Color(235, 243, 255)
                            : Color.WHITE
            );

            wrapper.add(label);

            return wrapper;
        }
    }

    // =========================================================
    // AVAILABILITY RENDERER
    // =========================================================

    private class AvailabilityRenderer
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

            JLabel label =
                    new JLabel(
                            String.valueOf(value)
                    );

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
                            5, 10, 5, 10
                    )
            );

            if ("On Duty".equals(
                    String.valueOf(value))) {

                label.setForeground(GREEN);
                label.setBackground(GREEN_BG);

            }
            else {

                label.setForeground(RED);
                label.setBackground(RED_BG);
            }

            JPanel wrapper =
                    new JPanel(
                            new GridBagLayout()
                    );

            wrapper.setBackground(
                    selected
                            ? new Color(235, 243, 255)
                            : Color.WHITE
            );

            wrapper.add(label);

            return wrapper;
        }
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

        button.setBorderColor(BORDER);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        button.setPreferredSize(
                new Dimension(35, 35)
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
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(background);

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radius,
                    radius
            );

            g2.setColor(borderColor);

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

            this.backgroundColor = background;

            setForeground(foreground);
            setBackground(background);
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

            this.borderColor = color;
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            Color fill = backgroundColor;

            if (getModel().isRollover()) {

                if (backgroundColor.equals(BLUE)) {

                    fill =
                            new Color(
                                    15, 92, 205
                            );

                }
                else {

                    fill =
                            new Color(
                                    244, 248, 253
                            );
                }
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
                    (Graphics2D) g.create();

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
                    8, 10, 8, 10
            );
        }
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                        UIManager
                                .getSystemLookAndFeelClassName()
                );

            }
            catch (Exception ignored) {
            }

            new maintenance("", "");
        });
    }
}