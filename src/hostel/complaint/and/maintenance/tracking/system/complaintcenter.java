package hostel.complaint.and.maintenance.tracking.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class complaintcenter extends JFrame {

    private final Color NAVY = new Color(8, 39, 83);
    private final Color BLUE = new Color(25, 112, 232);
    private final Color LIGHT_BLUE = new Color(245, 249, 254);
    private final Color TEXT = new Color(18, 48, 96);
    private final Color BORDER = new Color(215, 226, 240);

    private JTextField searchField;
    private JComboBox<String> categoryBox;
    private JComboBox<String> statusBox;
    private JComboBox<String> priorityBox;
    private JTable complaintTable;

    public complaintcenter() {

        setTitle("Complaint Center - Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1200, 750));
        setLayout(new BorderLayout());
        getContentPane().setBackground(LIGHT_BLUE);

        createSidebar();
        createMainArea();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(new Dimension(237, 0));
        sidebar.setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST);

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(null);
        top.setPreferredSize(new Dimension(237, 450));

        JLabel homeIcon = new JLabel("⌂");
        homeIcon.setForeground(Color.WHITE);
        homeIcon.setFont(new Font("Arial", Font.PLAIN, 42));
        homeIcon.setBounds(20, 20, 40, 45);
        top.add(homeIcon);

        JLabel title = new JLabel("HOSTEL");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(69, 18, 150, 28);
        top.add(title);

        JLabel subtitle1 = new JLabel("Complaint & Maintenance");
        subtitle1.setForeground(Color.WHITE);
        subtitle1.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitle1.setBounds(69, 43, 165, 20);
        top.add(subtitle1);

        JLabel subtitle2 = new JLabel("Tracking System");
        subtitle2.setForeground(Color.WHITE);
        subtitle2.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitle2.setBounds(69, 61, 165, 20);
        top.add(subtitle2);

        JLabel adminPanel = new JLabel("ADMIN PANEL");
        adminPanel.setForeground(Color.WHITE);
        adminPanel.setFont(new Font("Arial", Font.BOLD, 13));
        adminPanel.setBounds(20, 128, 150, 25);
        top.add(adminPanel);

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(62, 91, 128));
        separator.setBounds(20, 153, 197, 1);
        top.add(separator);

        createSidebarButton(top, "▣", "Admin Home", 165, false);
        createSidebarButton(top, "▤", "Complaint Center", 213, true);
        createSidebarButton(top, "♟", "Maintenance Team", 261, false);
        createSidebarButton(top, "♜", "Hostel Records", 309, false);
        createSidebarButton(top, "⚑", "Communication", 357, false);

        JSeparator separator2 = new JSeparator();
        separator2.setForeground(new Color(62, 91, 128));
        separator2.setBounds(20, 425, 197, 1);
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
        bottom.add(Box.createVerticalStrut(100));

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
        button.setBounds(10, y, 217, 44);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setFont(new Font("Arial", Font.BOLD, 22));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setBounds(12, 4, 30, 34);
        button.add(iconLabel);

        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        textLabel.setBounds(50, 4, 160, 34);
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
                if (text.equals("Logout")) {
                    dispose();
                    new login("","");
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
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(20, 18, 20, 18));

        JPanel headingPanel = new JPanel(new BorderLayout());
        headingPanel.setOpaque(false);
        headingPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Complaint Center");
        heading.setForeground(TEXT);
        heading.setFont(new Font("Arial", Font.BOLD, 31));

        JLabel description = new JLabel(
                "View, search and manage all hostel complaints.");
        description.setForeground(new Color(40, 72, 123));
        description.setFont(new Font("Arial", Font.PLAIN, 15));

        headingText.add(heading);
        headingText.add(Box.createVerticalStrut(2));
        headingText.add(description);

        RoundedButton addComplaint = new RoundedButton(
                "⊕  Add New Complaint", BLUE, Color.WHITE);
        addComplaint.setFont(new Font("Arial", Font.BOLD, 13));
        addComplaint.setPreferredSize(new Dimension(180, 50));

        headingPanel.add(headingText, BorderLayout.WEST);
        headingPanel.add(addComplaint, BorderLayout.EAST);

        content.add(headingPanel);
        content.add(Box.createVerticalStrut(10));

        JPanel filterPanel = createFilterPanel();
        filterPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 98));
        content.add(filterPanel);

        content.add(Box.createVerticalStrut(16));

        JPanel cards = createSummaryCards();
        cards.setMaximumSize(new Dimension(Integer.MAX_VALUE, 124));
        content.add(cards);

        content.add(Box.createVerticalStrut(16));

        JPanel tablePanel = createComplaintTable();
        tablePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        content.add(tablePanel);

        mainArea.add(new JScrollPane(
                content,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
    }

    private void createTopBar(JPanel mainArea) {

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(0, 64));
        topBar.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, new Color(224, 232, 243)));

        JLabel menu = new JLabel("☰");
        menu.setForeground(NAVY);
        menu.setFont(new Font("Arial", Font.PLAIN, 28));
        menu.setBorder(new EmptyBorder(0, 27, 0, 0));

        JPanel right = new JPanel(new FlowLayout(
                FlowLayout.RIGHT, 15, 12));
        right.setOpaque(false);

        JLabel notification = new JLabel("♧");
        notification.setForeground(NAVY);
        notification.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel count = new JLabel("3");
        count.setForeground(Color.WHITE);
        count.setBackground(new Color(235, 55, 55));
        count.setOpaque(true);
        count.setHorizontalAlignment(SwingConstants.CENTER);
        count.setFont(new Font("Arial", Font.BOLD, 10));
        count.setPreferredSize(new Dimension(16, 16));

        JPanel notificationPanel = new JPanel(new BorderLayout());
        notificationPanel.setOpaque(false);
        notificationPanel.add(notification, BorderLayout.CENTER);
        notificationPanel.add(count, BorderLayout.NORTH);

        JPanel profile = new JPanel(new BorderLayout());
        profile.setBackground(new Color(232, 240, 252));
        profile.setPreferredSize(new Dimension(40, 40));

        JLabel person = new JLabel("●");
        person.setForeground(NAVY);
        person.setFont(new Font("Arial", Font.BOLD, 25));
        person.setHorizontalAlignment(SwingConstants.CENTER);
        profile.add(person);

        JLabel admin = new JLabel("Admin");
        admin.setForeground(NAVY);
        admin.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel arrow = new JLabel("⌄");
        arrow.setForeground(NAVY);
        arrow.setFont(new Font("Arial", Font.BOLD, 18));

        right.add(notificationPanel);
        right.add(profile);
        right.add(admin);
        right.add(arrow);
        right.add(Box.createHorizontalStrut(15));

        topBar.add(menu, BorderLayout.WEST);
        topBar.add(right, BorderLayout.EAST);

        mainArea.add(topBar, BorderLayout.NORTH);
    }

    private JPanel createFilterPanel() {

        RoundedPanel panel = new RoundedPanel(10, Color.WHITE, BORDER);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(12, 10, 12, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 13));
        searchField.setForeground(new Color(86, 112, 155));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(BORDER, 8),
                new EmptyBorder(0, 38, 0, 8)));
        searchField.setText(
                "Search by student name, complaint ID or title...");

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);

        JLabel searchIcon = new JLabel("⌕");
        searchIcon.setForeground(NAVY);
        searchIcon.setFont(new Font("Arial", Font.BOLD, 24));
        searchIcon.setBorder(new EmptyBorder(0, 10, 0, 5));

        searchPanel.add(searchIcon, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2.8;
        panel.add(searchPanel, gbc);

        categoryBox = createComboBox(new String[]{
                "All Categories", "Electrical", "Plumbing",
                "Furniture", "Cleaning", "Internet-WiFi"
        });

        statusBox = createComboBox(new String[]{
                "All Status", "Pending", "In Progress", "Resolved"
        });

        priorityBox = createComboBox(new String[]{
                "All Priority", "High", "Medium", "Low"
        });

        gbc.weightx = 1.2;
        gbc.gridx = 1;
        panel.add(createFilterGroup("Category", categoryBox), gbc);

        gbc.gridx = 2;
        panel.add(createFilterGroup("Status", statusBox), gbc);

        gbc.gridx = 3;
        panel.add(createFilterGroup("Priority", priorityBox), gbc);

        RoundedButton searchButton = new RoundedButton(
                "⌕  Search", BLUE, Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 13));

        gbc.gridx = 4;
        gbc.weightx = 0.7;
        panel.add(searchButton, gbc);

        RoundedButton resetButton = new RoundedButton(
                "⟳  Reset", Color.WHITE, NAVY);
        resetButton.setBorderColor(BORDER);
        resetButton.setFont(new Font("Arial", Font.BOLD, 13));

        gbc.gridx = 5;
        gbc.weightx = 0.6;
        panel.add(resetButton, gbc);

        resetButton.addActionListener(e -> {
            searchField.setText("");
            categoryBox.setSelectedIndex(0);
            statusBox.setSelectedIndex(0);
            priorityBox.setSelectedIndex(0);
        });

        return panel;
    }

    private JPanel createFilterGroup(
            String title,
            JComboBox<String> comboBox) {

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout(0, 5));

        JLabel label = new JLabel(title);
        label.setForeground(TEXT);
        label.setFont(new Font("Arial", Font.PLAIN, 12));

        panel.add(label, BorderLayout.NORTH);
        panel.add(comboBox, BorderLayout.CENTER);

        return panel;
    }

    private JComboBox<String> createComboBox(String[] items) {

        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox.setForeground(TEXT);
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(new RoundedBorder(BORDER, 8));
        comboBox.setPreferredSize(new Dimension(140, 40));

        return comboBox;
    }

    private JPanel createSummaryCards() {

        JPanel cards = new JPanel(new GridLayout(1, 4, 16, 0));
        cards.setOpaque(false);

        cards.add(createSummaryCard(
                "▤", "Total Complaints", "48",
                "↑ 12% from last month",
                new Color(231, 243, 255),
                new Color(71, 145, 232),
                new Color(0, 139, 91)));

        cards.add(createSummaryCard(
                "◷", "Pending Complaints", "15",
                "↑ 5% from last month",
                new Color(255, 247, 237),
                new Color(244, 174, 82),
                new Color(222, 112, 0)));

        cards.add(createSummaryCard(
                "⚙", "In Progress Complaints", "18",
                "↑ 8% from last month",
                new Color(246, 241, 255),
                new Color(153, 121, 226),
                new Color(0, 139, 91)));

        cards.add(createSummaryCard(
                "✓", "Resolved Complaints", "15",
                "↑ 20% from last month",
                new Color(237, 250, 246),
                new Color(63, 184, 153),
                new Color(0, 139, 91)));

        return cards;
    }

    private JPanel createSummaryCard(
            String icon,
            String title,
            String value,
            String growth,
            Color background,
            Color iconColor,
            Color growthColor) {

        RoundedPanel card = new RoundedPanel(
                10, background, iconColor);
        card.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 0, 10);

        JPanel iconPanel = new JPanel(new BorderLayout());
        iconPanel.setBackground(iconColor);
        iconPanel.setPreferredSize(new Dimension(48, 48));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setFont(new Font("Arial", Font.BOLD, 25));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconPanel.add(iconLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(iconPanel, gbc);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(TEXT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(TEXT);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 29));

        JLabel growthLabel = new JLabel(growth);
        growthLabel.setForeground(growthColor);
        growthLabel.setFont(new Font("Arial", Font.PLAIN, 11));

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(valueLabel);
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(growthLabel);

        gbc.gridx = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        card.add(textPanel, gbc);

        return card;
    }

    private JPanel createComplaintTable() {

        RoundedPanel tablePanel = new RoundedPanel(
                10, Color.WHITE, BORDER);
        tablePanel.setLayout(new BorderLayout(0, 8));
        tablePanel.setBorder(new EmptyBorder(14, 10, 10, 10));

        JLabel heading = new JLabel("All Complaints");
        heading.setForeground(TEXT);
        heading.setFont(new Font("Arial", Font.BOLD, 18));

        tablePanel.add(heading, BorderLayout.NORTH);

        String[] columns = {
                "ID", "Student Name", "Room No.", "Category",
                "Title", "Date", "Priority", "Status", "Action"
        };

        Object[][] data = {
                {"#C048", "Rohit Sharma", "B-101", "Electrical",
                        "Fan not working", "Apr 26, 2025", "High", "Pending", ""},
                {"#C047", "Priya Singh", "A-203", "Plumbing",
                        "Water leakage", "Apr 25, 2025", "Medium", "In Progress", ""},
                {"#C046", "Amit Kumar", "C-302", "Furniture",
                        "Broken chair", "Apr 24, 2025", "Low", "Resolved", ""},
                {"#C045", "Neha Patel", "A-104", "Cleaning",
                        "Room cleaning", "Apr 23, 2025", "Medium", "In Progress", ""},
                {"#C044", "Sahil Verma", "B-205", "Electrical",
                        "Tube light not working", "Apr 22, 2025", "High", "Pending", ""},
                {"#C043", "Isha Patil", "C-108", "Internet-WiFi",
                        "Wi-Fi not working", "Apr 21, 2025", "Medium", "In Progress", ""},
                {"#C042", "Karan Mehta", "D-201", "Plumbing",
                        "Tap leakage", "Apr 20, 2025", "Low", "Resolved", ""},
                {"#C041", "Sneha Yadav", "B-304", "Room Cleaning",
                        "Dirty corridor", "Apr 19, 2025", "Medium", "Pending", ""},
                {"#C040", "Rohit Patel", "A-102", "Water Supply",
                        "No water supply", "Apr 18, 2025", "High", "In Progress", ""}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        complaintTable = new JTable(model);
        complaintTable.setFont(new Font("Arial", Font.PLAIN, 12));
        complaintTable.setForeground(TEXT);
        complaintTable.setRowHeight(43);
        complaintTable.setShowGrid(true);
        complaintTable.setGridColor(new Color(232, 238, 247));
        complaintTable.setIntercellSpacing(new Dimension(0, 0));
        complaintTable.setSelectionBackground(new Color(235, 243, 255));
        complaintTable.getTableHeader().setReorderingAllowed(false);

        complaintTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 12));
        complaintTable.getTableHeader().setForeground(TEXT);
        complaintTable.getTableHeader().setBackground(
                new Color(245, 248, 253));
        complaintTable.getTableHeader().setPreferredSize(
                new Dimension(0, 40));

        int[] widths = {65, 125, 90, 105, 170, 115, 90, 115, 190};

        for (int i = 0; i < widths.length; i++) {
            complaintTable.getColumnModel()
                    .getColumn(i).setPreferredWidth(widths[i]);
        }

        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean selected,
                            boolean focused,
                            int row,
                            int column) {

                        Component c = super.getTableCellRendererComponent(
                                table, value, selected, focused,
                                row, column);

                        setBorder(new EmptyBorder(0, 10, 0, 5));
                        setHorizontalAlignment(SwingConstants.LEFT);

                        if (!selected) {
                            c.setBackground(Color.WHITE);
                        }

                        return c;
                    }
                };

        for (int i = 0; i < 8; i++) {
            complaintTable.getColumnModel()
                    .getColumn(i).setCellRenderer(renderer);
        }

        complaintTable.getColumnModel().getColumn(6)
                .setCellRenderer(new PriorityRenderer());

        complaintTable.getColumnModel().getColumn(7)
                .setCellRenderer(new StatusRenderer());

        complaintTable.getColumnModel().getColumn(8)
                .setCellRenderer(new ActionRenderer());

        JScrollPane scrollPane = new JScrollPane(complaintTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(8, 5, 0, 0));

        JLabel showing = new JLabel("Showing 1 - 9 of 48 complaints");
        showing.setForeground(new Color(54, 82, 126));
        showing.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel pagination = new JPanel(new FlowLayout(
                FlowLayout.RIGHT, 7, 0));
        pagination.setOpaque(false);

        RoundedButton previous = createPageButton("‹");
        RoundedButton page1 = createPageButton("1");
        RoundedButton page2 = createPageButton("2");
        RoundedButton page3 = createPageButton("3");
        RoundedButton page4 = createPageButton("4");
        RoundedButton page5 = createPageButton("5");
        RoundedButton next = createPageButton("›");

        page1.setBackground(BLUE);
        page1.setForeground(Color.WHITE);

        pagination.add(previous);
        pagination.add(page1);
        pagination.add(page2);
        pagination.add(page3);
        pagination.add(page4);
        pagination.add(page5);
        pagination.add(next);

        bottom.add(showing, BorderLayout.WEST);
        bottom.add(pagination, BorderLayout.EAST);

        tablePanel.add(bottom, BorderLayout.SOUTH);

        return tablePanel;
    }

    private RoundedButton createPageButton(String text) {

        RoundedButton button = new RoundedButton(
                text, Color.WHITE, NAVY);
        button.setBorderColor(BORDER);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(34, 34));

        return button;
    }

    private class PriorityRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focused,
                int row,
                int column) {

            JLabel label = new JLabel(String.valueOf(value));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 11));
            label.setOpaque(true);
            label.setBorder(new EmptyBorder(5, 8, 5, 8));

            String priority = String.valueOf(value);

            if (priority.equals("High")) {
                label.setForeground(new Color(218, 48, 48));
                label.setBackground(new Color(255, 225, 225));
            } else if (priority.equals("Medium")) {
                label.setForeground(new Color(230, 127, 0));
                label.setBackground(new Color(255, 240, 213));
            } else {
                label.setForeground(new Color(0, 143, 102));
                label.setBackground(new Color(220, 244, 236));
            }

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(Color.WHITE);
            panel.add(label);

            return panel;
        }
    }

    private class StatusRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focused,
                int row,
                int column) {

            JLabel label = new JLabel(String.valueOf(value));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 11));
            label.setOpaque(true);
            label.setBorder(new EmptyBorder(5, 10, 5, 10));

            String status = String.valueOf(value);

            if (status.equals("Pending")) {
                label.setForeground(new Color(230, 127, 0));
                label.setBackground(new Color(255, 240, 213));
            } else if (status.equals("In Progress")) {
                label.setForeground(new Color(0, 102, 210));
                label.setBackground(new Color(222, 237, 255));
            } else {
                label.setForeground(new Color(0, 143, 102));
                label.setBackground(new Color(220, 244, 236));
            }

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(Color.WHITE);
            panel.add(label);

            return panel;
        }
    }

    private class ActionRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focused,
                int row,
                int column) {

            JPanel panel = new JPanel(new FlowLayout(
                    FlowLayout.LEFT, 8, 7));
            panel.setBackground(Color.WHITE);

            RoundedButton view = new RoundedButton(
                    "◉  View", Color.WHITE, BLUE);
            view.setBorderColor(new Color(123, 181, 255));
            view.setFont(new Font("Arial", Font.BOLD, 11));
            view.setPreferredSize(new Dimension(76, 28));

            RoundedButton update = new RoundedButton(
                    "✎  Update", Color.WHITE, BLUE);
            update.setBorderColor(new Color(123, 181, 255));
            update.setFont(new Font("Arial", Font.BOLD, 11));
            update.setPreferredSize(new Dimension(86, 28));

            RoundedButton more = new RoundedButton(
                    "⋯", Color.WHITE, NAVY);
            more.setBorderColor(BORDER);
            more.setFont(new Font("Arial", Font.BOLD, 16));
            more.setPreferredSize(new Dimension(40, 28));

            panel.add(view);
            panel.add(update);
            panel.add(more);

            return panel;
        }
    }

    private class RoundedPanel extends JPanel {

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
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(background);
            g2.fillRoundRect(
                    0, 0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radius, radius);

            g2.setColor(borderColor);
            g2.drawRoundRect(
                    0, 0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radius, radius);

            g2.dispose();

            super.paintComponent(g);
        }
    }

    private class RoundedButton extends JButton {

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
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        public void setBorderColor(Color color) {
            this.borderColor = color;
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            Color fill = backgroundColor;

            if (getModel().isRollover()) {
                fill = backgroundColor.equals(BLUE)
                        ? new Color(15, 92, 205)
                        : new Color(244, 248, 253);
            }

            g2.setColor(fill);
            g2.fillRoundRect(
                    0, 0,
                    getWidth() - 1,
                    getHeight() - 1,
                    8, 8);

            if (borderColor != null) {
                g2.setColor(borderColor);
                g2.drawRoundRect(
                        0, 0,
                        getWidth() - 1,
                        getHeight() - 1,
                        8, 8);
            }

            g2.dispose();

            super.paintComponent(g);
        }
    }

    private class RoundedBorder extends javax.swing.border.AbstractBorder {

        private final Color color;
        private final int radius;

        public RoundedBorder(Color color, int radius) {
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

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(color);
            g2.drawRoundRect(
                    x, y,
                    width - 1,
                    height - 1,
                    radius, radius);

            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(8, 10, 8, 10);
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            new complaintcenter();
        });
    }
}