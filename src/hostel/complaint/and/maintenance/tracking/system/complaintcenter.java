 package hostel.complaint.and.maintenance.tracking.system;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class complaintcenter extends JFrame implements ActionListener {

    private final Color NAVY = new Color(9, 39, 78);
    private final Color BLUE = new Color(25, 113, 232);
    private final Color TEXT = new Color(18, 50, 95);
    private final Color LIGHT_BG = new Color(247, 250, 255);
    private final Color BORDER = new Color(211, 224, 242);

    private JButton addComplaintButton;
    private JButton searchButton;
    private JButton resetButton;

    private JTextField searchField;
    private JComboBox<String> categoryBox;
    private JComboBox<String> statusBox;
    private JComboBox<String> priorityBox;

    private JTable table;
    private DefaultTableModel model;
    private JLabel showingLabel;
    private JLabel totalLabel;
    private JLabel pendingLabel;
    private JLabel progressLabel;
    private JLabel resolvedLabel;
    private JPanel pagesPanel;
    String adminName;
    String adminUsername;

    private final List<Object[]> allComplaints = new ArrayList<>();
    private final List<Object[]> filteredComplaints = new ArrayList<>();

    private int currentPage = 1;
    private final int rowsPerPage = 9;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Hostel_Complaint_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Uday@8888";

    public complaintcenter(String name, String Username) {
        adminName = name;
        adminUsername = Username;

        setTitle("Hostel Complaint & Maintenance Tracking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1200, 750));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        getContentPane().setBackground(LIGHT_BG);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);
        loadComplaints();
        refreshTable();
    }

    private Connection getConnection() throws SQLException {
        return java.sql.DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void loadComplaints() {
        allComplaints.clear();

        String sql = "SELECT complaint_id, name, username, category, location, " +
                "title, description, visit_time, room_no, status, " +
                "complaint_date, priority " +
                "FROM complaints ORDER BY complaint_date DESC";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                allComplaints.add(new Object[]{
                        "#" + rs.getInt("complaint_id"),
                        rs.getString("name"),
                        rs.getString("room_no"),
                        rs.getString("category"),
                        rs.getString("title"),
                        formatDate(rs.getTimestamp("complaint_date")),
                        rs.getString("priority"),
                        rs.getString("status")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load complaints from database.\n\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        filteredComplaints.clear();
        filteredComplaints.addAll(allComplaints);
    }

    private String formatDate(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        }
        return new java.text.SimpleDateFormat("MMM dd, yyyy").format(timestamp);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(264, 0));
        sidebar.setBackground(NAVY);

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(new EmptyBorder(22, 20, 0, 10));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        logoPanel.setOpaque(false);
        logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel homeIcon = new JLabel("⌂");
        homeIcon.setForeground(Color.WHITE);
        homeIcon.setFont(new Font("SansSerif", Font.BOLD, 40));
        homeIcon.setPreferredSize(new Dimension(38, 52));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel hostel = new JLabel("HOSTEL");
        hostel.setForeground(Color.WHITE);
        hostel.setFont(new Font("SansSerif", Font.BOLD, 23));

        JLabel complaint = new JLabel("Complaint & Maintenance");
        complaint.setForeground(Color.WHITE);
        complaint.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JLabel tracking = new JLabel("Tracking System");
        tracking.setForeground(Color.WHITE);
        tracking.setFont(new Font("SansSerif", Font.PLAIN, 12));

        titlePanel.add(hostel);
        titlePanel.add(complaint);
        titlePanel.add(tracking);

        logoPanel.add(homeIcon);
        logoPanel.add(titlePanel);

        top.add(logoPanel);
        top.add(Box.createVerticalStrut(34));

        JLabel adminPanel = new JLabel("ADMIN PANEL");
        adminPanel.setForeground(Color.WHITE);
        adminPanel.setFont(new Font("SansSerif", Font.BOLD, 14));
        adminPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        top.add(adminPanel);
        top.add(Box.createVerticalStrut(10));

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(58, 91, 132));
        separator.setMaximumSize(new Dimension(229, 1));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(separator);
        top.add(Box.createVerticalStrut(12));

        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        menuPanel.add(createMenuButton("⌂", "Admin Home", false, e -> showAdminHome()));
        menuPanel.add(Box.createVerticalStrut(5));
        menuPanel.add(createMenuButton("▣", "Complaint Center", true, e -> showComplaintCenter()));
        menuPanel.add(Box.createVerticalStrut(5));
        menuPanel.add(createMenuButton("●", "Maintenance Team", false, e -> showmaintenanceteam()));
//        menuPanel.add(createMenuButton("▣", "Maintenance Team", true, e -> showMaintenanceteam()));
        menuPanel.add(Box.createVerticalStrut(5));
        menuPanel.add(createMenuButton("♜", "Hostel Records", false, e -> showhostelrecords()));
        menuPanel.add(Box.createVerticalStrut(5));
        menuPanel.add(createMenuButton("⚑", "Communication", false, e -> showCommunication()));

        top.add(menuPanel);
        top.add(Box.createVerticalStrut(16));

        JSeparator separator2 = new JSeparator();
        separator2.setForeground(new Color(58, 91, 132));
        separator2.setMaximumSize(new Dimension(229, 1));
        separator2.setAlignmentX(Component.LEFT_ALIGNMENT);

        top.add(separator2);
        top.add(Box.createVerticalStrut(14));
        top.add(createMenuButton("⇥", "Logout", false, e -> logout()));

        sidebar.add(top, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBorder(new EmptyBorder(0, 0, 85, 0));

        JLabel building = new JLabel("▥");
        building.setAlignmentX(Component.CENTER_ALIGNMENT);
        building.setForeground(new Color(87, 124, 165));
        building.setFont(new Font("SansSerif", Font.PLAIN, 88));

        JLabel line1 = new JLabel("Better Hostel");
        line1.setAlignmentX(Component.CENTER_ALIGNMENT);
        line1.setForeground(new Color(145, 183, 225));
        line1.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel line2 = new JLabel("Happier Students");
        line2.setAlignmentX(Component.CENTER_ALIGNMENT);
        line2.setForeground(new Color(145, 183, 225));
        line2.setFont(new Font("SansSerif", Font.BOLD, 16));

        bottom.add(building);
        bottom.add(Box.createVerticalStrut(6));
        bottom.add(line1);
        bottom.add(line2);

        sidebar.add(bottom, BorderLayout.SOUTH);
        return sidebar;
    }

    private JButton createMenuButton(String icon, String text, boolean selected, ActionListener listener) {
        JButton button = new JButton(icon + "   " + text);
        button.setPreferredSize(new Dimension(229, 48));
        button.setMinimumSize(new Dimension(229, 48));
        button.setMaximumSize(new Dimension(229, 48));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(0, 10, 0, 0));
        button.setFont(new Font("SansSerif", Font.PLAIN, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(selected ? BLUE : NAVY);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(true);
        button.addActionListener(listener);

        if (!selected) {
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(new Color(18, 59, 105));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(NAVY);
                }
            });
        }

        return button;
    }

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(LIGHT_BG);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(0, 64));
        topBar.setBorder(new MatteBorder(0, 0, 1, 0, new Color(225, 233, 244)));

        JButton hamburger = new JButton("☰");
        hamburger.setFont(new Font("SansSerif", Font.PLAIN, 25));
        hamburger.setForeground(NAVY);
        hamburger.setBorder(new EmptyBorder(0, 25, 0, 0));
        hamburger.setContentAreaFilled(false);
        hamburger.setFocusPainted(false);
        hamburger.setCursor(new Cursor(Cursor.HAND_CURSOR));
        hamburger.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Sidebar navigation is available on the left.",
                "Navigation",
                JOptionPane.INFORMATION_MESSAGE
        ));
        topBar.add(hamburger, BorderLayout.WEST);

        JPanel rightTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 10));
        rightTop.setOpaque(false);

        JButton notification = new JButton("🔔");
        notification.setFont(new Font("SansSerif", Font.BOLD, 15));
        notification.setForeground(NAVY);
        notification.setBorderPainted(false);
        notification.setContentAreaFilled(false);
        notification.setFocusPainted(false);
        notification.setCursor(new Cursor(Cursor.HAND_CURSOR));
        notification.addActionListener(e -> showNotifications());

        JLabel profile = new JLabel("●");
        profile.setFont(new Font("SansSerif", Font.BOLD, 36));
        profile.setForeground(new Color(190, 209, 239));

        JLabel admin = new JLabel("Admin");
        admin.setFont(new Font("SansSerif", Font.BOLD, 15));
        admin.setForeground(NAVY);

        JButton profileButton = new JButton("⌄");
        profileButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        profileButton.setForeground(NAVY);
        profileButton.setBorderPainted(false);
        profileButton.setContentAreaFilled(false);
        profileButton.setFocusPainted(false);
        profileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profileButton.addActionListener(e -> showProfileMenu(profileButton));

        rightTop.add(notification);
        rightTop.add(profile);
        rightTop.add(admin);
        rightTop.add(profileButton);

        topBar.add(rightTop, BorderLayout.EAST);
        main.add(topBar, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setBackground(LIGHT_BG);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(18, 20, 18, 20));

        JPanel heading = new JPanel(new BorderLayout());
        heading.setOpaque(false);
        heading.setPreferredSize(new Dimension(0, 68));
        heading.setMaximumSize(new Dimension(Integer.MAX_VALUE, 68));

        JPanel headingText = new JPanel();
        headingText.setOpaque(false);
        headingText.setLayout(new BoxLayout(headingText, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Complaint Center");
        title.setForeground(NAVY);
        title.setFont(new Font("SansSerif", Font.BOLD, 31));

        JLabel subtitle = new JLabel("View, search and manage all hostel complaints.");
        subtitle.setForeground(new Color(48, 82, 130));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 15));

        headingText.add(title);
        headingText.add(Box.createVerticalStrut(2));
        headingText.add(subtitle);

        addComplaintButton = createBlueButton("⊕  Add New Complaint");
        addComplaintButton.setPreferredSize(new Dimension(190, 46));
        addComplaintButton.addActionListener(this);

        heading.add(headingText, BorderLayout.WEST);
        heading.add(addComplaintButton, BorderLayout.EAST);

        content.add(heading);
        content.add(Box.createVerticalStrut(14));

        JPanel filter = new RoundedPanel(10, Color.WHITE, BORDER);
        filter.setLayout(new GridBagLayout());
        filter.setBorder(new EmptyBorder(10, 10, 10, 10));
        filter.setMaximumSize(new Dimension(Integer.MAX_VALUE, 82));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 7, 0, 7);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;

        searchField = new HintTextField("Search by student name, complaint ID or title...");
        searchField.setPreferredSize(new Dimension(330, 40));
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        searchField.setBorder(new CompoundBorder(
                new LineBorder(new Color(198, 216, 239), 1, true),
                new EmptyBorder(0, 14, 0, 10)
        ));

        gbc.gridx = 0;
        gbc.weightx = 1.0;
        filter.add(searchField, gbc);

        categoryBox = createComboBox(new String[]{
                "All Categories", "Electrical", "Plumbing", "Furniture",
                "Cleaning", "Room Cleaning", "Internet-WiFi", "Water Supply"
        });

        statusBox = createComboBox(new String[]{
                "All Status", "Pending", "In Progress", "Resolved", "Complete"
        });

        priorityBox = createComboBox(new String[]{
                "All Priority", "High", "Medium", "Low"
        });

        gbc.gridx = 1;
        gbc.weightx = 0;
        filter.add(createFilterBox("Category", categoryBox, 165), gbc);

        gbc.gridx = 2;
        filter.add(createFilterBox("Status", statusBox, 150), gbc);

        gbc.gridx = 3;
        filter.add(createFilterBox("Priority", priorityBox, 145), gbc);

        searchButton = createBlueButton("⌕  Search");
        searchButton.setPreferredSize(new Dimension(115, 40));
        searchButton.addActionListener(this);

        gbc.gridx = 4;
        filter.add(searchButton, gbc);

        resetButton = createWhiteButton("⟳  Reset");
        resetButton.setPreferredSize(new Dimension(100, 40));
        resetButton.addActionListener(this);

        gbc.gridx = 5;
        filter.add(resetButton, gbc);

        content.add(filter);
        content.add(Box.createVerticalStrut(16));

        JPanel stats = new JPanel(new GridLayout(1, 4, 16, 0));
        stats.setOpaque(false);
        stats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 108));

        JPanel totalCard = createStatCard("▣", "Total Complaints", "0", "Live count",
                new Color(218, 236, 255), new Color(42, 126, 232));
        JPanel pendingCard = createStatCard("◷", "Pending Complaints", "0", "Needs attention",
                new Color(255, 237, 215), new Color(230, 153, 54));
        JPanel progressCard = createStatCard("⚙", "In Progress Complaints", "0", "Being handled",
                new Color(235, 226, 255), new Color(132, 100, 216));
        JPanel resolvedCard = createStatCard("✓", "Resolved Complaints", "0", "Completed",
                new Color(218, 247, 239), new Color(53, 177, 139));

        totalLabel = (JLabel) totalCard.getClientProperty("numberLabel");
        pendingLabel = (JLabel) pendingCard.getClientProperty("numberLabel");
        progressLabel = (JLabel) progressCard.getClientProperty("numberLabel");
        resolvedLabel = (JLabel) resolvedCard.getClientProperty("numberLabel");

        stats.add(totalCard);
        stats.add(pendingCard);
        stats.add(progressCard);
        stats.add(resolvedCard);

        content.add(stats);
        content.add(Box.createVerticalStrut(16));

        JPanel tablePanel = new RoundedPanel(10, Color.WHITE, BORDER);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));

        JLabel tableTitle = new JLabel("All Complaints");
        tableTitle.setFont(new Font("SansSerif", Font.BOLD, 19));
        tableTitle.setForeground(NAVY);
        tableTitle.setBorder(new EmptyBorder(12, 5, 10, 0));
        tablePanel.add(tableTitle, BorderLayout.NORTH);

        String[] columns = {
                "ID", "Student Name", "Room No.", "Category", "Title",
                "Date", "Priority", "Status", "Action"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8;
            }
        };

        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    component.setBackground(Color.WHITE);
                }
                return component;
            }
        };

        table.setRowHeight(43);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setForeground(new Color(30, 62, 108));
        table.setGridColor(new Color(229, 236, 246));
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 42));
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setForeground(NAVY);
        header.setBackground(new Color(246, 249, 253));
        header.setBorder(new MatteBorder(0, 0, 1, 0, new Color(225, 233, 244)));

        int[] widths = {80, 140, 95, 120, 180, 115, 90, 125, 225};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        table.getColumnModel().getColumn(6).setCellRenderer(new BadgeRenderer("priority"));
        table.getColumnModel().getColumn(7).setCellRenderer(new BadgeRenderer("status"));
        table.getColumnModel().getColumn(8).setCellRenderer(new ActionRenderer());
        table.getColumnModel().getColumn(8).setCellEditor(new ActionEditor());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setPreferredSize(new Dimension(0, 65));

        showingLabel = new JLabel("Showing 0 complaints");
        showingLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        showingLabel.setForeground(new Color(49, 83, 130));

        pagesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 13));
        pagesPanel.setOpaque(false);

        bottom.add(showingLabel, BorderLayout.WEST);
        bottom.add(pagesPanel, BorderLayout.EAST);

        tablePanel.add(bottom, BorderLayout.SOUTH);
        content.add(tablePanel);

        main.add(content, BorderLayout.CENTER);
        updateStats();

        return main;
    }

    private void showComplaintCenter() {
        JOptionPane.showMessageDialog(this,
                "You are already in the Complaint Center.",
                "Complaint Center",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAdminHome() {
        dispose();
        new adminDash("", "").setVisible(true);
    }

    private void showmaintenanceteam(){
        dispose();
        new maintenance("","").setVisible(true);
    }
    private void showhostelrecords(){
        dispose();
        new hostelRecords("","").setVisible(true);
    }


    private void showCommunication() {
        String message = JOptionPane.showInputDialog(this,
                "Enter a message for hostel students:",
                "Communication",
                JOptionPane.QUESTION_MESSAGE);

        if (message != null && !message.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Message sent successfully.",
                    "Communication",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showNotifications() {
        JOptionPane.showMessageDialog(this,
                "3 notifications\n\n• New complaint received\n• Complaint status updated\n• Maintenance team response received",
                "Notifications",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showProfileMenu(Component parent) {
        JPopupMenu menu = new JPopupMenu();

        JMenuItem profileItem = new JMenuItem("View Profile");
        JMenuItem settingsItem = new JMenuItem("Settings");
        JMenuItem logoutItem = new JMenuItem("Logout");

        profileItem.addActionListener(e -> JOptionPane.showMessageDialog(
                this, "Administrator Profile", "Profile", JOptionPane.INFORMATION_MESSAGE));

        settingsItem.addActionListener(e -> JOptionPane.showMessageDialog(
                this, "Settings panel", "Settings", JOptionPane.INFORMATION_MESSAGE));

        logoutItem.addActionListener(e -> logout());

        menu.add(profileItem);
        menu.add(settingsItem);
        menu.addSeparator();
        menu.add(logoutItem);
        menu.show(parent, 0, parent.getHeight());


    }

    private void logout() {
        dispose();
        new login("", "").setVisible(true);
    }

    private void showInfo(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(150, 36));
        combo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        combo.addActionListener(e -> applyFilters());
        return combo;
    }

    private JPanel createFilterBox(String label, JComboBox<String> combo, int width) {
        JPanel wrapper = new JPanel(new BorderLayout(0, 4));
        wrapper.setOpaque(false);
        wrapper.setPreferredSize(new Dimension(width, 58));

        JLabel top = new JLabel(label);
        top.setFont(new Font("SansSerif", Font.BOLD, 12));
        top.setForeground(NAVY);

        combo.setPreferredSize(new Dimension(width, 36));

        wrapper.add(top, BorderLayout.NORTH);
        wrapper.add(combo, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createStatCard(String icon, String title, String number, String percentage,
                                  Color iconBackground, Color iconColor) {
        JPanel card = new RoundedPanel(10, Color.WHITE, new Color(207, 222, 241));
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(13, 16, 12, 16));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        iconLabel.setForeground(iconColor);
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 26));

        JPanel iconBox = new JPanel(new BorderLayout());
        iconBox.setBackground(iconBackground);
        iconBox.setPreferredSize(new Dimension(48, 48));
        iconBox.add(iconLabel);

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setBorder(new EmptyBorder(0, 14, 0, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        titleLabel.setForeground(NAVY);

        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        numberLabel.setForeground(NAVY);

        JLabel percent = new JLabel(percentage);
        percent.setFont(new Font("SansSerif", Font.PLAIN, 11));
        percent.setForeground(new Color(0, 151, 91));

        text.add(titleLabel);
        text.add(Box.createVerticalStrut(1));
        text.add(numberLabel);
        text.add(Box.createVerticalStrut(1));
        text.add(percent);

        card.add(iconBox, BorderLayout.WEST);
        card.add(text, BorderLayout.CENTER);
        card.putClientProperty("numberLabel", numberLabel);

        return card;
    }

    private JButton createBlueButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(BLUE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(17, 92, 198));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BLUE);
            }
        });

        return button;
    }

    private JButton createWhiteButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(NAVY);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(new Color(202, 218, 238), 1, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createPageButton(String text, boolean selected) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(34, 34));
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (selected) {
            button.setBackground(BLUE);
            button.setForeground(Color.WHITE);
            button.setBorder(new LineBorder(BLUE, 1, true));
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(NAVY);
            button.setBorder(new LineBorder(new Color(207, 221, 239), 1, true));
        }

        return button;
    }

    private JButton createSmallButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 10));
        button.setForeground(BLUE);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(new Color(147, 195, 255), 1, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (text.equals("...")) {
            button.setPreferredSize(new Dimension(42, 27));
        } else if (text.contains("Update")) {
            button.setPreferredSize(new Dimension(82, 27));
        } else {
            button.setPreferredSize(new Dimension(70, 27));
        }

        return button;
    }

    private void applyFilters() {
        String search = searchField.getText().trim();
        String hint = "Search by student name, complaint ID or title...";

        if (search.equalsIgnoreCase(hint)) {
            search = "";
        }

        String category = String.valueOf(categoryBox.getSelectedItem());
        String status = String.valueOf(statusBox.getSelectedItem());
        String priority = String.valueOf(priorityBox.getSelectedItem());

        filteredComplaints.clear();

        for (Object[] complaint : allComplaints) {
            String id = String.valueOf(complaint[0]);
            String student = String.valueOf(complaint[1]);
            String title = String.valueOf(complaint[4]);

            boolean searchMatch = search.isEmpty()
                    || id.toLowerCase().contains(search.toLowerCase())
                    || student.toLowerCase().contains(search.toLowerCase())
                    || title.toLowerCase().contains(search.toLowerCase());

            boolean categoryMatch = category.equals("All Categories")
                    || complaint[3].toString().equals(category);

            boolean statusMatch = status.equals("All Status")
                    || complaint[7].toString().equals(status)
                    || (status.equals("Complete")
                    && complaint[7].toString().equals("Resolved"));

            boolean priorityMatch = priority.equals("All Priority")
                    || complaint[6].toString().equals(priority);

            if (searchMatch && categoryMatch && statusMatch && priorityMatch) {
                filteredComplaints.add(complaint);
            }
        }

        currentPage = 1;
        refreshTable();
    }

    private void refreshTable() {
        if (model == null) {
            return;
        }

        model.setRowCount(0);

        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, filteredComplaints.size());

        for (int i = start; i < end; i++) {
            Object[] complaint = filteredComplaints.get(i);
            model.addRow(new Object[]{
                    complaint[0], complaint[1], complaint[2], complaint[3],
                    complaint[4], complaint[5], complaint[6], complaint[7], ""
            });
        }

        int shownStart = filteredComplaints.isEmpty() ? 0 : start + 1;
        int shownEnd = end;

        showingLabel.setText("Showing " + shownStart + " - " + shownEnd
                + " of " + filteredComplaints.size() + " complaints");

        updatePagination();
        updateStats();
    }

    private void updatePagination() {
        pagesPanel.removeAll();

        int totalPages = Math.max(1, (int) Math.ceil(filteredComplaints.size() / (double) rowsPerPage));

        JButton previous = createPageButton("←", false);
        previous.setEnabled(currentPage > 1);
        previous.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                refreshTable();
            }
        });
        pagesPanel.add(previous);

        for (int i = 1; i <= totalPages; i++) {
            final int page = i;
            JButton pageButton = createPageButton(String.valueOf(i), i == currentPage);
            pageButton.addActionListener(e -> {
                currentPage = page;
                refreshTable();
            });
            pagesPanel.add(pageButton);
        }

        JButton next = createPageButton("→", false);
        next.setEnabled(currentPage < totalPages);
        next.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                refreshTable();
            }
        });
        pagesPanel.add(next);

        pagesPanel.revalidate();
        pagesPanel.repaint();
    }

    private void updateStats() {
        int total = allComplaints.size();
        int pending = 0;
        int progress = 0;
        int resolved = 0;

        for (Object[] complaint : allComplaints) {
            String status = complaint[7].toString();
            if (status.equals("Pending")) {
                pending++;
            } else if (status.equals("In Progress")) {
                progress++;
            } else if (status.equals("Resolved")) {
                resolved++;
            }
        }

        if (totalLabel != null) totalLabel.setText(String.valueOf(total));
        if (pendingLabel != null) pendingLabel.setText(String.valueOf(pending));
        if (progressLabel != null) progressLabel.setText(String.valueOf(progress));
        if (resolvedLabel != null) resolvedLabel.setText(String.valueOf(resolved));
    }

    private void openAddComplaintDialog() {
        JTextField studentField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField roomField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField visitTimeField = new JTextField();

        JComboBox<String> category = new JComboBox<>(new String[]{
                "Electrical", "Plumbing", "Furniture", "Cleaning",
                "Room Cleaning", "Internet-WiFi", "Water Supply"
        });

        JComboBox<String> priority = new JComboBox<>(new String[]{
                "High", "Medium", "Low"
        });

        JTextArea descriptionArea = new JTextArea(4, 25);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Student Name:"));
        panel.add(studentField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Room No.:"));
        panel.add(roomField);
        panel.add(new JLabel("Location:"));
        panel.add(locationField);
        panel.add(new JLabel("Category:"));
        panel.add(category);
        panel.add(new JLabel("Complaint Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Visit Time:"));
        panel.add(visitTimeField);
        panel.add(new JLabel("Priority:"));
        panel.add(priority);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Add New Complaint",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (studentField.getText().trim().isEmpty()
                    || usernameField.getText().trim().isEmpty()
                    || roomField.getText().trim().isEmpty()
                    || titleField.getText().trim().isEmpty()
                    || descriptionArea.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Please fill all required fields.",
                        "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String sql = "INSERT INTO complaints " +
                    "(name, username, category, location, title, description, " +
                    "visit_time, room_no, status, priority) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Pending', ?)";

            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, studentField.getText().trim());
                ps.setString(2, usernameField.getText().trim());
                ps.setString(3, String.valueOf(category.getSelectedItem()));
                ps.setString(4, locationField.getText().trim());
                ps.setString(5, titleField.getText().trim());
                ps.setString(6, descriptionArea.getText().trim());
                ps.setString(7, visitTimeField.getText().trim());
                ps.setString(8, roomField.getText().trim());
                ps.setString(9, String.valueOf(priority.getSelectedItem()));

                ps.executeUpdate();

                loadComplaints();
                applyFilters();

                JOptionPane.showMessageDialog(
                        this, "Complaint added successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                        this, "Unable to add complaint:\n" + e.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewComplaint(int row) {
        if (row < 0 || row >= table.getRowCount()) {
            return;
        }

        String idText = table.getValueAt(row, 0).toString().replace("#", "");

        try {
            int complaintId = Integer.parseInt(idText);
            showComplaintDetails(complaintId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid complaint ID.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void showComplaintDetails(int complaintId) {
        String sql = "SELECT complaint_id, name, username, category, location, " +
                "title, description, visit_time, room_no, status, " +
                "complaint_date, priority FROM complaints WHERE complaint_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, complaintId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    showReplyWindow(
                            rs.getInt("complaint_id"),
                            rs.getString("name"),
                            rs.getString("username"),
                            rs.getString("category"),
                            rs.getString("location"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("visit_time"),
                            rs.getString("room_no"),
                            rs.getString("priority"),
                            rs.getString("status"),
                            formatDate(rs.getTimestamp("complaint_date")));
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Complaint not found.", "Complaint",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Unable to open complaint:\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showReplyWindow(
            int complaintId, String name, String username,
            String category, String location, String title,
            String description, String visitTime, String roomNo,
            String priority, String status, String complaintDate) {

        JDialog dialog = new JDialog(this, "Complaint Details", true);
        dialog.setSize(650, 720);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(18, 18, 18, 18));

        JTextArea details = new JTextArea();
        details.setEditable(false);
        details.setLineWrap(true);
        details.setWrapStyleWord(true);
        details.setFont(new Font("SansSerif", Font.PLAIN, 13));

        details.setText(
                "Complaint ID: #C" + complaintId +
                        "\nStudent Name: " + name +
                        "\nUsername: " + username +
                        "\nRoom No.: " + roomNo +
                        "\nCategory: " + category +
                        "\nLocation: " + location +
                        "\nTitle: " + title +
                        "\nVisit Time: " + visitTime +
                        "\nPriority: " + priority +
                        "\nStatus: " + status +
                        "\nDate: " + complaintDate +
                        "\n\nComplaint Description:\n" + description);

        panel.add(new JScrollPane(details), BorderLayout.CENTER);

        JTextArea replyArea = new JTextArea(5, 30);
        replyArea.setLineWrap(true);
        replyArea.setWrapStyleWord(true);
        replyArea.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JButton sendButton = createBlueButton("Send Reply");
        sendButton.addActionListener(e -> {
            String reply = replyArea.getText().trim();

            if (reply.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                        "Please enter a reply.", "Reply",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (saveAdminReply(complaintId, username, reply)) {
                dialog.dispose();
            }
        });

        JPanel replyPanel = new JPanel(new BorderLayout(8, 8));
        replyPanel.add(new JLabel("Admin Reply:"), BorderLayout.NORTH);
        replyPanel.add(new JScrollPane(replyArea), BorderLayout.CENTER);
        replyPanel.add(sendButton, BorderLayout.SOUTH);

        panel.add(replyPanel, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void updateComplaint(int row) {
        if (row < 0 || row >= table.getRowCount()) return;

        String complaintId = table.getValueAt(row, 0).toString();
        String studentName = table.getValueAt(row, 1).toString();
        String currentStatus = table.getValueAt(row, 7).toString();

        JPopupMenu popup = new JPopupMenu();

        JMenuItem pendingItem = new JMenuItem("1. Pending");
        JMenuItem completeItem = new JMenuItem("2. Complete");
        JMenuItem messageItem = new JMenuItem("3. Message Personally");

        pendingItem.addActionListener(e -> changeStatus(complaintId, "Pending"));
        completeItem.addActionListener(e -> changeStatus(complaintId, "Resolved"));
        messageItem.addActionListener(e -> messageStudent(complaintId, studentName));

        popup.add(pendingItem);
        popup.add(completeItem);
        popup.addSeparator();
        popup.add(messageItem);

        if (currentStatus.equals("Resolved") || currentStatus.equals("Complete")) {
            completeItem.setEnabled(false);
        }

        popup.show(table,
                Math.max(0, table.getColumnModel().getColumn(8).getWidth() - 170),
                table.getRowHeight() * row + 5);
    }

    private void changeStatus(String complaintId, String newStatus) {
        int id;

        try {
            id = Integer.parseInt(complaintId.replace("#", ""));
        } catch (NumberFormatException e) {
            return;
        }

        String sql = "UPDATE complaints SET status = ? WHERE complaint_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, id);

            int updated = ps.executeUpdate();

            if (updated > 0) {
                loadComplaints();
                applyFilters();

                JOptionPane.showMessageDialog(
                        this,
                        "Complaint " + complaintId +
                                " status changed to " +
                                (newStatus.equals("Resolved") ? "Complete" : newStatus) + ".",
                        "Status Updated",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update status:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void messageStudent(String complaintId, String studentName) {
        JTextArea messageArea = new JTextArea(6, 30);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        messageArea.setText("Hello " + studentName + ",\n\n");

        JScrollPane scroll = new JScrollPane(messageArea);
        scroll.setBorder(new LineBorder(BORDER));

        int result = JOptionPane.showConfirmDialog(
                this, scroll,
                "Message " + studentName + " (" + complaintId + ")",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String message = messageArea.getText().trim();

            if (message.isEmpty()) return;

            int id;
            try {
                id = Integer.parseInt(complaintId.replace("#C", ""));
            } catch (NumberFormatException e) {
                return;
            }

            String username = getUsernameForComplaint(id);

            if (username != null && saveAdminReply(id, username, message)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Message sent to " + studentName + ".",
                        "Message Sent",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private String getUsernameForComplaint(int complaintId) {
        String sql = "SELECT username FROM complaints WHERE complaint_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, complaintId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("username");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to find student username:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    private boolean saveAdminReply(int complaintId, String username, String reply) {
        String sql = "INSERT INTO complaint_message " +
                "(complaint_id, username, sender, message) VALUES (?, ?, 'ADMIN', ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, complaintId);
            ps.setString(2, username);
            ps.setString(3, reply);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Reply sent successfully.",
                    "Reply",
                    JOptionPane.INFORMATION_MESSAGE);

            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to send reply:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void moreOptions(int row) {
        if (row < 0 || row >= table.getRowCount()) return;

        String complaintId = table.getValueAt(row, 0).toString();

        JPopupMenu menu = new JPopupMenu();

        JMenuItem view = new JMenuItem("View Details");
        JMenuItem update = new JMenuItem("Update Status");
        JMenuItem message = new JMenuItem("Message Student");

        view.addActionListener(e -> viewComplaint(row));
        update.addActionListener(e -> updateComplaint(row));
        message.addActionListener(e -> messageStudent(
                complaintId,
                table.getValueAt(row, 1).toString()
        ));

        menu.add(view);
        menu.add(update);
        menu.addSeparator();
        menu.add(message);

        menu.show(table, table.getWidth() - 190, table.getRowHeight() * row + 5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addComplaintButton) {
            openAddComplaintDialog();

        } else if (e.getSource() == searchButton) {
            applyFilters();

            if (filteredComplaints.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "No complaints found for the selected search and filters.",
                        "Search Result",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } else if (e.getSource() == resetButton) {
            searchField.setText("Search by student name, complaint ID or title...");
            searchField.setForeground(new Color(90, 116, 150));

            categoryBox.setSelectedIndex(0);
            statusBox.setSelectedIndex(0);
            priorityBox.setSelectedIndex(0);

            loadComplaints();
            filteredComplaints.clear();
            filteredComplaints.addAll(allComplaints);
            currentPage = 1;
            refreshTable();
        }
    }

    class BadgeRenderer extends JLabel implements TableCellRenderer {
        private final String type;

        BadgeRenderer(String type) {
            this.type = type;
            setHorizontalAlignment(SwingConstants.CENTER);
            setOpaque(true);
            setFont(new Font("SansSerif", Font.BOLD, 11));
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean selected,
                boolean focused, int row, int column) {

            String text = String.valueOf(value);
            setText(text);

            if (type.equals("priority")) {
                if (text.equals("High")) {
                    setBackground(new Color(255, 218, 218));
                    setForeground(new Color(230, 60, 60));
                } else if (text.equals("Medium")) {
                    setBackground(new Color(255, 237, 211));
                    setForeground(new Color(221, 125, 0));
                } else {
                    setBackground(new Color(215, 241, 232));
                    setForeground(new Color(20, 145, 105));
                }
            } else {
                if (text.equals("Pending")) {
                    setBackground(new Color(255, 239, 213));
                    setForeground(new Color(224, 130, 0));
                } else if (text.equals("In Progress")) {
                    setBackground(new Color(216, 234, 255));
                    setForeground(new Color(20, 102, 205));
                } else {
                    setBackground(new Color(215, 242, 233));
                    setForeground(new Color(20, 145, 105));
                }
            }

            setBorder(new EmptyBorder(5, 8, 5, 8));
            return this;
        }
    }

    class ActionRenderer extends JPanel implements TableCellRenderer {
        ActionRenderer() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 5, 6));
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean selected,
                boolean focused, int row, int column) {

            removeAll();
            add(createSmallButton("◉  View"));
            add(createSmallButton("✎  Update"));
            add(createSmallButton("..."));
            setBackground(Color.WHITE);
            return this;
        }
    }

    class ActionEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private int editingRow;

        ActionEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 6));
            panel.setBackground(Color.WHITE);

            JButton view = createSmallButton("◉  View");
            JButton update = createSmallButton("✎  Update");
            JButton more = createSmallButton("...");

            view.addActionListener(e -> {
                viewComplaint(editingRow);
                fireEditingStopped();
            });

            update.addActionListener(e -> {
                fireEditingStopped();
                SwingUtilities.invokeLater(() -> updateComplaint(editingRow));
            });

            more.addActionListener(e -> {
                moreOptions(editingRow);
                fireEditingStopped();
            });

            panel.add(view);
            panel.add(update);
            panel.add(more);
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value, boolean selected,
                int row, int column) {
            editingRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    class RoundedPanel extends JPanel {
        private final int radius;
        private final Color background;
        private final Color borderColor;

        RoundedPanel(int radius, Color background, Color borderColor) {
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
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(background);
            g2.fill(new RoundRectangle2D.Float(
                    0, 0, getWidth() - 1, getHeight() - 1,
                    radius, radius
            ));

            g2.setColor(borderColor);
            g2.draw(new RoundRectangle2D.Float(
                    0.5f, 0.5f, getWidth() - 2, getHeight() - 2,
                    radius, radius
            ));

            g2.dispose();
            super.paintComponent(g);
        }
    }

    class HintTextField extends JTextField {
        private final String hint;

        HintTextField(String hint) {
            this.hint = hint;
            setForeground(new Color(90, 116, 150));

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (getText().equals(hint)) {
                        setText("");
                        setForeground(TEXT);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (getText().trim().isEmpty()) {
                        setText(hint);
                        setForeground(new Color(90, 116, 150));
                    }
                }
            });

            setText(hint);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            complaintcenter frame = new complaintcenter("", "");
            frame.setVisible(true);
        });
    }
}

