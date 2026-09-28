package hostel.complaint.and.maintenance.tracking.system;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class hostelRecords extends JFrame {

    private final Color NAVY = new Color(8, 39, 83);
    private final Color BLUE = new Color(25, 112, 232);
    private final Color LIGHT_BG = new Color(245, 249, 254);
    private final Color TEXT = new Color(18, 48, 96);
    private final Color BORDER = new Color(215, 226, 240);

    private final Color GREEN = new Color(0, 145, 105);
    private final Color GREEN_BG = new Color(220, 244, 236);

    private final Color ORANGE = new Color(225, 125, 20);
    private final Color ORANGE_BG = new Color(255, 239, 215);

    private JTextField searchField;
    private JComboBox<String> hostelBox;
    private JComboBox<String> roomTypeBox;
    private JComboBox<String> statusBox;
    private JTable recordsTable;

    private String adminName;
    private String adminUsername;

    public hostelRecords(String name, String username) {

        adminName = name;
        adminUsername = username;

        setTitle("Hostel Records - Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(1200, 750));
        setLayout(new BorderLayout());

        getContentPane().setBackground(LIGHT_BG);

        createSidebar();
        createMainArea();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(264, 0));
        sidebar.setLayout(new BorderLayout());

        add(sidebar, BorderLayout.WEST);

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(null);
        top.setPreferredSize(new Dimension(264, 500));

        JLabel homeIcon = new JLabel("⌂");
        homeIcon.setForeground(Color.WHITE);
        homeIcon.setFont(new Font("Arial", Font.PLAIN, 43));
        homeIcon.setBounds(20, 18, 45, 45);
        top.add(homeIcon);

        JLabel hostel = new JLabel("HOSTEL");
        hostel.setForeground(Color.WHITE);
        hostel.setFont(new Font("Arial", Font.BOLD, 21));
        hostel.setBounds(78, 18, 150, 28);
        top.add(hostel);

        JLabel line1 = new JLabel("Complaint & Maintenance");
        line1.setForeground(Color.WHITE);
        line1.setFont(new Font("Arial", Font.PLAIN, 12));
        line1.setBounds(78, 44, 180, 20);
        top.add(line1);

        JLabel line2 = new JLabel("Tracking System");
        line2.setForeground(Color.WHITE);
        line2.setFont(new Font("Arial", Font.PLAIN, 12));
        line2.setBounds(78, 62, 160, 20);
        top.add(line2);

        JLabel adminPanel = new JLabel("ADMIN PANEL");
        adminPanel.setForeground(Color.WHITE);
        adminPanel.setFont(new Font("Arial", Font.BOLD, 13));
        adminPanel.setBounds(22, 116, 150, 25);
        top.add(adminPanel);

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(62, 91, 128));
        separator.setBounds(22, 143, 218, 1);
        top.add(separator);

        createSidebarButton(top, "⌂", "Admin Home", 155, false);
        createSidebarButton(top, "▤", "Complaint Center", 203, false);
        createSidebarButton(top, "⚒", "Maintenance Team", 251, false);
        createSidebarButton(top, "♜", "Hostel Records", 299, true);
        createSidebarButton(top, "⚑", "Communication", 347, false);

        JSeparator separator2 = new JSeparator();
        separator2.setForeground(new Color(62, 91, 128));
        separator2.setBounds(22, 417, 218, 1);
        top.add(separator2);

        createSidebarButton(top, "⇥", "Logout", 437, false);

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
        bottom.add(Box.createVerticalStrut(85));

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
        button.setBounds(10, y, 240, 44);

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

                if (!selected) {
                    button.setBackground(new Color(17, 61, 116));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

                if (!selected) {
                    button.setBackground(NAVY);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {

                if (text.equals("Admin Home")) {

                    dispose();

                    new adminDash(
                            adminName,
                            adminUsername
                    );

                } else if (text.equals("Complaint Center")) {

                    dispose();

                    complaintcenter frame =
                            new complaintcenter(
                                    adminName,
                                    adminUsername
                            );

                    frame.setVisible(true);

                } else if (text.equals("Maintenance Team")) {

                    dispose();

                    new maintenance(
                            adminName,
                            adminUsername
                    );

                } else if (text.equals("Logout")) {

                    int result =
                            JOptionPane.showConfirmDialog(
                                    hostelRecords.this,
                                    "Are you sure you want to logout?",
                                    "Logout",
                                    JOptionPane.YES_NO_OPTION
                            );

                    if (result == JOptionPane.YES_OPTION) {

                        dispose();

                        new login(
                                "",
                                ""
                        );
                    }
                }
            }
        });

        parent.add(button);
    }

    private void createMainArea() {

        JPanel mainArea =
                new JPanel(new BorderLayout());

        mainArea.setBackground(LIGHT_BG);

        add(mainArea, BorderLayout.CENTER);

        createTopBar(mainArea);

        JPanel content = new JPanel();

        content.setBackground(LIGHT_BG);

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        content.setBorder(
                new EmptyBorder(
                        18,
                        20,
                        20,
                        20
                )
        );

        JPanel headingPanel =
                new JPanel(new BorderLayout());

        headingPanel.setOpaque(false);

        headingPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        72
                )
        );

        JPanel headingText = new JPanel();

        headingText.setOpaque(false);

        headingText.setLayout(
                new BoxLayout(
                        headingText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel heading =
                new JLabel("Hostel Records");

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

        content.add(headingPanel);

        content.add(
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

        content.add(filter);

        content.add(
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

        content.add(cards);

        content.add(
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

        content.add(table);

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

    private void createTopBar(JPanel mainArea) {

        JPanel topBar =
                new JPanel(new BorderLayout());

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

        JLabel menu =
                new JLabel("☰");

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

        JPanel right =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                14,
                                12
                        )
                );

        right.setOpaque(false);

        JLabel notification =
                new JLabel("♧");

        notification.setForeground(NAVY);

        notification.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        27
                )
        );

        JLabel count =
                new JLabel("3");

        count.setForeground(Color.WHITE);
        count.setBackground(
                new Color(
                        235,
                        55,
                        55
                )
        );

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

        JPanel profile =
                new JPanel(
                        new BorderLayout()
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

        JLabel person =
                new JLabel("●");

        person.setForeground(NAVY);

        person.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        25
                )
        );

        person.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        profile.add(
                person,
                BorderLayout.CENTER
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
                                "Active",
                                "Pending",
                                "Inactive"
                        }
                );

        gbc.gridx = 1;
        gbc.weightx = 1.0;

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

                    resetTable();
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

        cards.add(
                createSummaryCard(
                        "♟",
                        "Total Students",
                        "240",
                        "↑ 12% from last month",
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
                )
        );

        cards.add(
                createSummaryCard(
                        "▣",
                        "Occupied Rooms",
                        "118",
                        "↑ 5% from last month",
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
                )
        );

        cards.add(
                createSummaryCard(
                        "⌂",
                        "Available Rooms",
                        "22",
                        "↑ 8% from last month",
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
                )
        );

        cards.add(
                createSummaryCard(
                        "◷",
                        "Pending Allocations",
                        "8",
                        "↑ 3% from last month",
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
                )
        );

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

        iconPanel.setBackground(iconColor);

        iconPanel.setPreferredSize(
                new Dimension(
                        50,
                        50
                )
        );

        JLabel iconLabel =
                new JLabel(icon);

        iconLabel.setForeground(Color.WHITE);

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

        growthLabel.setForeground(
                growth.contains("3%")
                        ? ORANGE
                        : GREEN
        );

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
        gbc.fill = GridBagConstraints.HORIZONTAL;

        card.add(
                text,
                gbc
        );

        return card;
    }

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

        Object[][] data = {

                {
                        "ST001",
                        "Rohit Sharma",
                        "CSE24001",
                        "B-101",
                        "Block B",
                        "2 Sharing",
                        "98765 43210",
                        "Active",
                        ""
                },

                {
                        "ST002",
                        "Priya Singh",
                        "CSE24002",
                        "A-203",
                        "Block A",
                        "3 Sharing",
                        "87654 32109",
                        "Active",
                        ""
                },

                {
                        "ST003",
                        "Amit Kumar",
                        "CSE24003",
                        "C-302",
                        "Block C",
                        "2 Sharing",
                        "91234 56789",
                        "Active",
                        ""
                },

                {
                        "ST004",
                        "Neha Patel",
                        "CSE24004",
                        "A-104",
                        "Block A",
                        "4 Sharing",
                        "99887 76655",
                        "Pending",
                        ""
                },

                {
                        "ST005",
                        "Sahil Verma",
                        "CSE24005",
                        "B-205",
                        "Block B",
                        "2 Sharing",
                        "88776 55443",
                        "Active",
                        ""
                },

                {
                        "ST006",
                        "Isha Patil",
                        "CSE24006",
                        "C-108",
                        "Block C",
                        "3 Sharing",
                        "77665 44332",
                        "Active",
                        ""
                },

                {
                        "ST007",
                        "Karan Mehta",
                        "CSE24007",
                        "D-201",
                        "Block D",
                        "2 Sharing",
                        "66554 33221",
                        "Active",
                        ""
                },

                {
                        "ST008",
                        "Sneha Yadav",
                        "CSE24008",
                        "B-304",
                        "Block B",
                        "4 Sharing",
                        "55443 22110",
                        "Pending",
                        ""
                }
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        data,
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
                new Dimension(
                        0,
                        0
                )
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

        int[] widths = {
                60,
                145,
                110,
                85,
                90,
                105,
                125,
                90,
                215
        };

        for (int i = 0; i < widths.length; i++) {

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

                        if (selected) {

                            component.setBackground(
                                    new Color(
                                            235,
                                            243,
                                            255
                                    )
                            );

                            component.setForeground(TEXT);

                        } else {

                            component.setBackground(
                                    Color.WHITE
                            );

                            component.setForeground(TEXT);
                        }

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

        JScrollPane scrollPane =
                new JScrollPane(
                        recordsTable
                );

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

        JLabel showing =
                new JLabel(
                        "Showing 1 - 8 of 48 records"
                );

        showing.setForeground(
                new Color(
                        54,
                        82,
                        126
                )
        );

        showing.setFont(
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

        pagination.add(
                createPageButton("‹")
        );

        RoundedButton page1 =
                createPageButton("1");

        page1.setBackground(BLUE);
        page1.setForeground(Color.WHITE);

        pagination.add(page1);
        pagination.add(createPageButton("2"));
        pagination.add(createPageButton("3"));
        pagination.add(createPageButton("4"));
        pagination.add(createPageButton("5"));
        pagination.add(createPageButton("›"));

        bottom.add(
                showing,
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
                            5,
                            10,
                            5,
                            10
                    )
            );

            if (String.valueOf(value)
                    .equals("Active")) {

                label.setForeground(GREEN);
                label.setBackground(GREEN_BG);

            } else {

                label.setForeground(ORANGE);
                label.setBackground(ORANGE_BG);
            }

            JPanel wrapper =
                    new JPanel(
                            new GridBagLayout()
                    );

            wrapper.setBackground(Color.WHITE);
            wrapper.add(label);

            return wrapper;
        }
    }

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

            panel.setBackground(Color.WHITE);

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
                            75,
                            30
                    )
            );

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
                            75,
                            30
                    )
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
                    new Dimension(
                            40,
                            30
                    )
            );

            view.addActionListener(
                    e -> {

                        int rowIndex =
                                recordsTable
                                        .getSelectedRow();

                        if (rowIndex < 0) {
                            rowIndex = row;
                        }

                        showStudentDetails(rowIndex);
                    }
            );

            edit.addActionListener(
                    e -> {

                        int rowIndex =
                                recordsTable
                                        .getSelectedRow();

                        if (rowIndex < 0) {
                            rowIndex = row;
                        }

                        showEditStudentDialog(rowIndex);
                    }
            );

            more.addActionListener(
                    e -> {

                        JPopupMenu popup =
                                createMoreMenu(row);

                        popup.show(
                                more,
                                0,
                                more.getHeight()
                        );
                    }
            );

            panel.add(view);
            panel.add(edit);
            panel.add(more);

            return panel;
        }
    }

    private JPopupMenu createMoreMenu(int row) {

        JPopupMenu popup =
                new JPopupMenu();

        popup.setBackground(Color.WHITE);

        popup.setBorder(
                BorderFactory.createCompoundBorder(
                        new RoundedBorder(
                                BORDER,
                                8
                        ),
                        new EmptyBorder(
                                5,
                                5,
                                5,
                                5
                        )
                )
        );

        JMenuItem view =
                createMenuItem(
                        "◉  View Details"
                );

        JMenuItem edit =
                createMenuItem(
                        "✎  Edit Student"
                );

        JMenuItem room =
                createMenuItem(
                        "▣  Room Details"
                );

        JMenuItem remove =
                createMenuItem(
                        "▣  Remove Student"
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
                e -> showEditStudentDialog(row)
        );

        room.addActionListener(
                e -> JOptionPane.showMessageDialog(
                        this,
                        "Room: " +
                                recordsTable
                                        .getValueAt(row, 3)
                                + "\nBlock: " +
                                recordsTable
                                        .getValueAt(row, 4)
                                + "\nRoom Type: " +
                                recordsTable
                                        .getValueAt(row, 5),
                        "Room Details",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        remove.addActionListener(
                e -> {

                    int result =
                            JOptionPane.showConfirmDialog(
                                    this,
                                    "Are you sure you want to remove this student?",
                                    "Remove Student",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE
                            );

                    if (result ==
                            JOptionPane.YES_OPTION) {

                        ((DefaultTableModel)
                                recordsTable.getModel())
                                .removeRow(row);

                        JOptionPane.showMessageDialog(
                                this,
                                "Student removed successfully.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
        );

        return popup;
    }

    private JMenuItem createMenuItem(String text) {

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
                new Dimension(
                        180,
                        36
                )
        );

        return item;
    }

    private void showStudentDetails(int row) {

        if (row < 0 ||
                row >= recordsTable.getRowCount()) {
            return;
        }

        String studentName =
                recordsTable
                        .getValueAt(row, 1)
                        .toString();

        String studentId =
                recordsTable
                        .getValueAt(row, 2)
                        .toString();

        String room =
                recordsTable
                        .getValueAt(row, 3)
                        .toString();

        String block =
                recordsTable
                        .getValueAt(row, 4)
                        .toString();

        String roomType =
                recordsTable
                        .getValueAt(row, 5)
                        .toString();

        String contact =
                recordsTable
                        .getValueAt(row, 6)
                        .toString();

        String status =
                recordsTable
                        .getValueAt(row, 7)
                        .toString();

        JDialog dialog =
                new JDialog(
                        this,
                        "Student Details",
                        true
                );

        dialog.setSize(
                640,
                300
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

        main.setBackground(Color.WHITE);

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
                        200
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

        avatar.setAlignmentX(
                Component.LEFT_ALIGNMENT
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
                        studentName
                );

        name.setForeground(TEXT);

        name.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        name.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel id =
                new JLabel(
                        studentId
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

        id.setAlignmentX(
                Component.LEFT_ALIGNMENT
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

        statusLabel.setForeground(
                status.equals("Active")
                        ? GREEN
                        : ORANGE
        );

        statusLabel.setBackground(
                status.equals("Active")
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

        statusLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
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
                                5,
                                2,
                                12,
                                9
                        )
                );

        details.setOpaque(false);

        addDetail(details, "Student Name", studentName);
        addDetail(details, "Student ID", studentId);
        addDetail(details, "Block", block);
        addDetail(details, "Room Type", roomType);
        addDetail(details, "Room No.", room);
        addDetail(details, "Contact", contact);
        addDetail(details, "Course", "Computer Science");
        addDetail(details, "Year", "3rd Year");
        addDetail(details, "Email", "rohit@example.com");
        addDetail(details, "Admission Date", "15 July 2026");

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

        bottom.setBackground(Color.WHITE);

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
                new JLabel(value);

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

    private void showEditStudentDialog(int row) {

        if (row < 0 ||
                row >= recordsTable.getRowCount()) {
            return;
        }

        JTextField name =
                new JTextField(
                        recordsTable
                                .getValueAt(row, 1)
                                .toString()
                );

        JTextField studentId =
                new JTextField(
                        recordsTable
                                .getValueAt(row, 2)
                                .toString()
                );

        JTextField room =
                new JTextField(
                        recordsTable
                                .getValueAt(row, 3)
                                .toString()
                );

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                3,
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

        panel.add(new JLabel("Student Name:"));
        panel.add(name);

        panel.add(new JLabel("Student ID:"));
        panel.add(studentId);

        panel.add(new JLabel("Room No.:"));
        panel.add(room);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Edit Student",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result ==
                JOptionPane.OK_OPTION) {

            DefaultTableModel model =
                    (DefaultTableModel)
                            recordsTable.getModel();

            model.setValueAt(
                    name.getText(),
                    row,
                    1
            );

            model.setValueAt(
                    studentId.getText(),
                    row,
                    2
            );

            model.setValueAt(
                    room.getText(),
                    row,
                    3
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Student details updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void showAddStudentDialog() {

        JTextField name =
                new JTextField();

        JTextField studentId =
                new JTextField();

        JTextField room =
                new JTextField();

        JTextField contact =
                new JTextField();

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                4,
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

        panel.add(new JLabel("Student Name:"));
        panel.add(name);

        panel.add(new JLabel("Student ID:"));
        panel.add(studentId);

        panel.add(new JLabel("Room No.:"));
        panel.add(room);

        panel.add(new JLabel("Contact:"));
        panel.add(contact);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add Student",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result ==
                JOptionPane.OK_OPTION) {

            if (name.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter student name.",
                        "Validation",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            DefaultTableModel model =
                    (DefaultTableModel)
                            recordsTable.getModel();

            model.addRow(
                    new Object[]{
                            "ST009",
                            name.getText(),
                            studentId.getText(),
                            room.getText(),
                            "Block B",
                            "2 Sharing",
                            contact.getText(),
                            "Pending",
                            ""
                    }
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Student added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void filterRecords() {

        String search =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();

        String block =
                hostelBox
                        .getSelectedItem()
                        .toString();

        String roomType =
                roomTypeBox
                        .getSelectedItem()
                        .toString();

        String status =
                statusBox
                        .getSelectedItem()
                        .toString();

        DefaultTableModel model =
                (DefaultTableModel)
                        recordsTable.getModel();

        model.setRowCount(0);

        Object[][] allData = getAllData();

        for (Object[] row : allData) {

            String name =
                    row[1].toString()
                            .toLowerCase();

            String id =
                    row[2].toString()
                            .toLowerCase();

            String room =
                    row[3].toString()
                            .toLowerCase();

            boolean searchMatch =
                    search.isEmpty()
                            || name.contains(search)
                            || id.contains(search)
                            || room.contains(search);

            boolean blockMatch =
                    block.equals("All Hostels")
                            || row[4].toString()
                            .equals(block);

            boolean roomMatch =
                    roomType.equals("All Room Types")
                            || row[5].toString()
                            .equals(roomType);

            boolean statusMatch =
                    status.equals("All Status")
                            || row[7].toString()
                            .equals(status);

            if (searchMatch &&
                    blockMatch &&
                    roomMatch &&
                    statusMatch) {

                model.addRow(row);
            }
        }
    }

    private void resetTable() {

        DefaultTableModel model =
                (DefaultTableModel)
                        recordsTable.getModel();

        model.setRowCount(0);

        for (Object[] row : getAllData()) {
            model.addRow(row);
        }
    }

    private Object[][] getAllData() {

        return new Object[][]{

                {
                        "ST001",
                        "Rohit Sharma",
                        "CSE24001",
                        "B-101",
                        "Block B",
                        "2 Sharing",
                        "98765 43210",
                        "Active",
                        ""
                },

                {
                        "ST002",
                        "Priya Singh",
                        "CSE24002",
                        "A-203",
                        "Block A",
                        "3 Sharing",
                        "87654 32109",
                        "Active",
                        ""
                },

                {
                        "ST003",
                        "Amit Kumar",
                        "CSE24003",
                        "C-302",
                        "Block C",
                        "2 Sharing",
                        "91234 56789",
                        "Active",
                        ""
                },

                {
                        "ST004",
                        "Neha Patel",
                        "CSE24004",
                        "A-104",
                        "Block A",
                        "4 Sharing",
                        "99887 76655",
                        "Pending",
                        ""
                },

                {
                        "ST005",
                        "Sahil Verma",
                        "CSE24005",
                        "B-205",
                        "Block B",
                        "2 Sharing",
                        "88776 55443",
                        "Active",
                        ""
                },

                {
                        "ST006",
                        "Isha Patil",
                        "CSE24006",
                        "C-108",
                        "Block C",
                        "3 Sharing",
                        "77665 44332",
                        "Active",
                        ""
                },

                {
                        "ST007",
                        "Karan Mehta",
                        "CSE24007",
                        "D-201",
                        "Block D",
                        "2 Sharing",
                        "66554 33221",
                        "Active",
                        ""
                },

                {
                        "ST008",
                        "Sneha Yadav",
                        "CSE24008",
                        "B-304",
                        "Block B",
                        "4 Sharing",
                        "55443 22110",
                        "Pending",
                        ""
                }
        };
    }

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

    private class RoundedButton
            extends JButton {

        private Color backgroundColor;
        private Color borderColor;

        public RoundedButton(
                String text,
                Color background,
                Color foreground) {

            super(text);

            backgroundColor = background;

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

            borderColor = color;
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

            Color fill =
                    backgroundColor;

            if (getModel().isRollover()) {

                if (backgroundColor.equals(BLUE)) {

                    fill =
                            new Color(
                                    15,
                                    92,
                                    205
                            );

                } else {

                    fill =
                            new Color(
                                    244,
                                    248,
                                    253
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

                g2.setColor(borderColor);

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
                    8,
                    10,
                    8,
                    10
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    try {

                        UIManager.setLookAndFeel(
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