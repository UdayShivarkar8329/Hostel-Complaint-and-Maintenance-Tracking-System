package hostel.complaint.and.maintenance.tracking.system;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class mycomplaints extends JFrame {

    String studentname;
    String studentusername;
    String roomNumber;

    JTable complaintTable;
    DefaultTableModel model;

    JLabel totalLabel;
    JLabel pendingLabel;
    JLabel progressLabel;
    JLabel resolvedLabel;

    JTextField searchField;
    JComboBox<String> categoryBox;
    JComboBox<String> statusBox;

    Color darkBlue = new Color(8, 48, 88);
    Color blue = new Color(40, 105, 230);

    public mycomplaints(String name, String username,String roomNumber) {

        this.studentname = name;
        this.studentusername = username;
        this.roomNumber=roomNumber;

        setTitle("My Complaints");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 248, 253));

        createSidebar();
        createTopBar();
        createMainContent();

        loadComplaints();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setBounds(0, 0, 260, 1080);
        sidebar.setBackground(darkBlue);
        sidebar.setLayout(null);
        add(sidebar);

        JLabel homeIcon = new JLabel("■");
        homeIcon.setBounds(28, 30, 35, 35);
        homeIcon.setFont(new Font("Arial", Font.BOLD, 22));
        homeIcon.setForeground(Color.WHITE);
        sidebar.add(homeIcon);

        JLabel logo = new JLabel("Hostel");
        logo.setBounds(80, 25, 150, 40);
        logo.setFont(new Font("Arial", Font.BOLD, 30));
        logo.setForeground(Color.WHITE);
        sidebar.add(logo);

        JLabel logoText = new JLabel(
                "<html>Complaint & Maintenance<br>Tracking System</html>"
        );
        logoText.setBounds(80, 62, 170, 50);
        logoText.setFont(new Font("Arial", Font.PLAIN, 13));
        logoText.setForeground(Color.WHITE);
        sidebar.add(logoText);

        JButton dashboardBtn =
                menuButton(sidebar, "Dashboard", 135);

        JButton newComplaintBtn =
                menuButton(sidebar, "New Complaint", 195);

        JButton myComplaintBtn =
                menuButton(sidebar, "My Complaints", 255);

        JButton notificationBtn =
                menuButton(sidebar, "Notification", 315);

        JButton announcementBtn =
                menuButton(sidebar, "Announcements", 375);

        JButton logoutBtn =
                menuButton(sidebar, "Logout", 475);

        myComplaintBtn.setBackground(blue);

        dashboardBtn.addActionListener(e -> {
            dispose();
            new dashboard(studentname, studentusername,roomNumber);
        });

        newComplaintBtn.addActionListener(e -> {
            dispose();
            new complaints(studentname, studentusername,roomNumber);
        });

        announcementBtn.addActionListener(ActiveEvent ->{
            dispose();
            new announcements(studentname,studentusername,roomNumber);
        });
//        notificationBtn.addActionListener(ActiveEvent -> {
//            dispose();
//            new notification(studentname,studentusername,roomNumber);
//                }
//        );

        logoutBtn.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                new login("", "");
            }
        });
    }

    private JButton menuButton(
            JPanel panel,
            String text,
            int y
    ) {

        JButton button = new JButton(text);

        button.setBounds(10, y, 240, 48);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setForeground(Color.WHITE);
        button.setBackground(darkBlue);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);

        panel.add(button);

        return button;
    }

    private void createTopBar() {

        JPanel topBar = new JPanel();
        topBar.setBounds(260, 0, 1490, 80);
        topBar.setBackground(Color.WHITE);
        topBar.setLayout(null);
        add(topBar);

        JLabel menu = new JLabel("☰");
        menu.setBounds(30, 22, 40, 35);
        menu.setFont(new Font("Arial", Font.BOLD, 25));
        menu.setForeground(darkBlue);
        topBar.add(menu);

        JLabel heading = new JLabel("My Complaints");
        heading.setBounds(85, 18, 400, 45);
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        heading.setForeground(new Color(15, 55, 100));
        topBar.add(heading);

        JLabel notification = new JLabel("🔔");
        notification.setBounds(1000, 18, 45, 40);
        notification.setFont(
                new Font("Segoe UI Emoji", Font.PLAIN, 28)
        );
        topBar.add(notification);

        JLabel userCircle = new JLabel("●");
        userCircle.setBounds(1060, 20, 35, 35);
        userCircle.setFont(new Font("Arial", Font.BOLD, 25));
        userCircle.setForeground(darkBlue);
        topBar.add(userCircle);

        JLabel user = new JLabel(studentname);
        user.setBounds(1105, 20, 200, 35);
        user.setFont(new Font("Arial", Font.BOLD, 16));
        user.setForeground(new Color(25, 45, 75));
        topBar.add(user);

        JLabel arrow = new JLabel("⌄");
        arrow.setBounds(1300, 20, 30, 35);
        arrow.setFont(new Font("Arial", Font.BOLD, 20));
        arrow.setForeground(darkBlue);
        topBar.add(arrow);
    }

    private void createMainContent() {

        JLabel icon = new JLabel("▣");
        icon.setBounds(300, 105, 75, 75);
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setFont(new Font("Arial", Font.BOLD, 38));
        icon.setForeground(blue);
        icon.setOpaque(true);
        icon.setBackground(Color.WHITE);
        icon.setBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 235, 250)
                )
        );
        add(icon);

        JLabel title = new JLabel("My Complaints");
        title.setBounds(395, 115, 400, 45);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(new Color(15, 55, 100));
        add(title);

        JLabel subtitle = new JLabel(
                "Track and manage all your submitted complaints."
        );
        subtitle.setBounds(395, 155, 500, 30);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitle.setForeground(new Color(80, 100, 125));
        add(subtitle);

        createCard(
                300,
                220,
                "Total Complaints",
                "▣",
                new Color(45, 115, 235)
        );

        createCard(
                605,
                220,
                "Pending",
                "◷",
                new Color(245, 160, 30)
        );

        createCard(
                910,
                220,
                "In Progress",
                "▶",
                new Color(45, 115, 235)
        );

        createCard(
                1215,
                220,
                "Resolved",
                "✓",
                new Color(30, 175, 110)
        );

        JPanel filterPanel = new JPanel();
        filterPanel.setBounds(300, 335, 1210, 75);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setLayout(null);
        filterPanel.setBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 232, 242)
                )
        );
        add(filterPanel);

        searchField = new JTextField();
        searchField.setBounds(15, 15, 500, 45);
        searchField.setFont(new Font("Arial", Font.PLAIN, 15));
        searchField.setBorder(
                BorderFactory.createLineBorder(
                        new Color(210, 220, 235)
                )
        );
        filterPanel.add(searchField);

        categoryBox = new JComboBox<>();

        categoryBox.addItem("All Categories");
        categoryBox.addItem("Plumbing");
        categoryBox.addItem("Electrical");
        categoryBox.addItem("Internet / Wi-Fi");
        categoryBox.addItem("Maintenance");
        categoryBox.addItem("Cleaning");
        categoryBox.addItem("Other");

        categoryBox.setBounds(530, 15, 220, 45);
        categoryBox.setFont(new Font("Arial", Font.PLAIN, 14));
        filterPanel.add(categoryBox);

        statusBox = new JComboBox<>();

        statusBox.addItem("All Status");
        statusBox.addItem("Pending");
        statusBox.addItem("In Progress");
        statusBox.addItem("Resolved");

        statusBox.setBounds(765, 15, 200, 45);
        statusBox.setFont(new Font("Arial", Font.PLAIN, 14));
        filterPanel.add(statusBox);

        JButton clearButton = new JButton("Clear Filters");
        clearButton.setBounds(980, 15, 210, 45);
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setForeground(blue);
        clearButton.setBackground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorder(
                BorderFactory.createLineBorder(blue)
        );
        filterPanel.add(clearButton);

        clearButton.addActionListener(e -> {
            searchField.setText("");
            categoryBox.setSelectedIndex(0);
            statusBox.setSelectedIndex(0);
            loadComplaints();
        });

        searchField.addActionListener(e -> loadComplaints());
        categoryBox.addActionListener(e -> loadComplaints());
        statusBox.addActionListener(e -> loadComplaints());

        createTable();
    }

    private void createCard(
            int x,
            int y,
            String title,
            String iconText,
            Color iconColor
    ) {

        JPanel card = new JPanel();
        card.setBounds(x, y, 280, 90);
        card.setBackground(Color.WHITE);
        card.setLayout(null);
        card.setBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 232, 242)
                )
        );
        add(card);

        JLabel icon = new JLabel(iconText);
        icon.setBounds(20, 18, 55, 55);
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setFont(new Font("Arial", Font.BOLD, 28));
        icon.setForeground(iconColor);
        icon.setOpaque(true);
        icon.setBackground(new Color(242, 247, 255));
        card.add(icon);

        JLabel value = new JLabel("0");
        value.setBounds(90, 13, 70, 35);
        value.setFont(new Font("Arial", Font.BOLD, 26));
        value.setForeground(new Color(20, 50, 85));
        card.add(value);

        JLabel label = new JLabel(title);
        label.setBounds(90, 48, 170, 25);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(new Color(60, 80, 105));
        card.add(label);

        if (title.equals("Total Complaints")) {
            totalLabel = value;
        } else if (title.equals("Pending")) {
            pendingLabel = value;
        } else if (title.equals("In Progress")) {
            progressLabel = value;
        } else if (title.equals("Resolved")) {
            resolvedLabel = value;
        }
    }

    private void createTable() {

        String[] columns = {
                "Complaint",
                "Category",
                "Location",
                "Visit Time",
                "Status",
                "Date",
                "Action"
        };

        model = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return column == 6;
            }
        };

        complaintTable = new JTable(model);

        complaintTable.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        complaintTable.setRowHeight(65);
        complaintTable.setGridColor(
                new Color(235, 238, 244)
        );
        complaintTable.setShowVerticalLines(false);

        complaintTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        complaintTable.getTableHeader().setForeground(
                new Color(30, 55, 90)
        );

        complaintTable.getTableHeader().setBackground(
                new Color(239, 245, 253)
        );

        complaintTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(260);

        complaintTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(150);

        complaintTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(150);

        complaintTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(160);

        complaintTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(150);

        complaintTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(150);

        complaintTable.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(130);

        complaintTable.getColumnModel()
                .getColumn(6)
                .setCellRenderer(new ButtonRenderer());

        complaintTable.getColumnModel()
                .getColumn(6)
                .setCellEditor(
                        new ButtonEditor(new JCheckBox())
                );

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        for (int i = 1; i <= 5; i++) {

            complaintTable.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane =
                new JScrollPane(complaintTable);

        scrollPane.setBounds(300, 430, 1210, 400);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 232, 242)
                )
        );

        add(scrollPane);

        JLabel bottomText = new JLabel(
                "Showing complaints submitted by " + studentname
        );

        bottomText.setBounds(305, 840, 500, 30);
        bottomText.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        bottomText.setForeground(
                new Color(80, 100, 125)
        );

        add(bottomText);
    }

    private void loadComplaints() {

        if (model == null) {
            return;
        }

        model.setRowCount(0);

        int total = 0;
        int pending = 0;
        int progress = 0;
        int resolved = 0;

        String search = searchField == null
                ? ""
                : searchField.getText().trim();

        String category = categoryBox == null
                ? "All Categories"
                : categoryBox.getSelectedItem().toString();

        String status = statusBox == null
                ? "All Status"
                : statusBox.getSelectedItem().toString();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            con = Con.getConnection();

            if (con == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database connection failed!",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            String query =
                    "SELECT complaint_id, title, category, location, " +
                            "visit_time, status, complaint_date " +
                            "FROM complaints " +
                            "WHERE username = ? " +
                            "AND (title LIKE ? OR category LIKE ? OR location LIKE ?) ";

            if (!category.equals("All Categories")) {
                query += "AND category = ? ";
            }

            if (!status.equals("All Status")) {
                query += "AND status = ? ";
            }

            query += "ORDER BY complaint_date DESC";

            ps = con.prepareStatement(query);

            int index = 1;

            String searchValue = "%" + search + "%";

            ps.setString(index++, studentusername);
            ps.setString(index++, searchValue);
            ps.setString(index++, searchValue);
            ps.setString(index++, searchValue);

            if (!category.equals("All Categories")) {
                ps.setString(index++, category);
            }

            if (!status.equals("All Status")) {
                ps.setString(index++, status);
            }

            rs = ps.executeQuery();

            while (rs.next()) {

                String title =
                        rs.getString("title");

                String complaintCategory =
                        rs.getString("category");

                String location =
                        rs.getString("location");

                String visitTime =
                        rs.getString("visit_time");

                String complaintStatus =
                        rs.getString("status");

                String date =
                        rs.getString("complaint_date");

                model.addRow(
                        new Object[]{
                                title,
                                complaintCategory,
                                location,
                                visitTime,
                                complaintStatus,
                                date,
                                "View Details"
                        }
                );

                total++;

                if (complaintStatus != null &&
                        complaintStatus.equalsIgnoreCase("Pending")) {

                    pending++;
                }

                if (complaintStatus != null &&
                        complaintStatus.equalsIgnoreCase("In Progress")) {

                    progress++;
                }

                if (complaintStatus != null &&
                        complaintStatus.equalsIgnoreCase("Resolved")) {

                    resolved++;
                }
            }

            totalLabel.setText(String.valueOf(total));
            pendingLabel.setText(String.valueOf(pending));
            progressLabel.setText(String.valueOf(progress));
            resolvedLabel.setText(String.valueOf(resolved));

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
    }

    private void showDetails(int row) {

        if (row < 0 || row >= model.getRowCount()) {
            return;
        }

        String title =
                String.valueOf(model.getValueAt(row, 0));

        String category =
                String.valueOf(model.getValueAt(row, 1));

        String location =
                String.valueOf(model.getValueAt(row, 2));

        String visitTime =
                String.valueOf(model.getValueAt(row, 3));

        String status =
                String.valueOf(model.getValueAt(row, 4));

        String date =
                String.valueOf(model.getValueAt(row, 5));

        JOptionPane.showMessageDialog(
                this,
                "Complaint: " + title +
                        "\nCategory: " + category +
                        "\nLocation: " + location +
                        "\nVisit Time: " + visitTime +
                        "\nStatus: " + status +
                        "\nDate: " + date,
                "Complaint Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    class ButtonRenderer extends JButton
            implements javax.swing.table.TableCellRenderer {

        ButtonRenderer() {

            setOpaque(true);
            setText("View Details");
            setFont(
                    new Font("Arial", Font.BOLD, 12)
            );
            setForeground(blue);
            setBackground(Color.WHITE);
            setFocusPainted(false);
            setBorder(
                    BorderFactory.createLineBorder(blue)
            );
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            setText("View Details");

            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        JButton button;
        int row;

        ButtonEditor(JCheckBox checkBox) {

            super(checkBox);

            button = new JButton("View Details");

            button.setFont(
                    new Font("Arial", Font.BOLD, 12)
            );

            button.setForeground(blue);
            button.setBackground(Color.WHITE);
            button.setFocusPainted(false);

            button.setBorder(
                    BorderFactory.createLineBorder(blue)
            );

            button.addActionListener(e -> {

                fireEditingStopped();

                showDetails(row);
            });
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column
        ) {

            this.row = row;

            return button;
        }

        @Override
        public Object getCellEditorValue() {

            return "View Details";
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new mycomplaints("Test Student", "test","");
        });
    }
}