package hostel.complaint.and.maintenance.tracking.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class announcements extends JFrame implements ActionListener {

     String studentName;
     String studentUsername;
    String roomNumber;
    private final Color NAVY = new Color(7, 48, 91);
    private final Color NAVY_LIGHT = new Color(15, 67, 120);
    private final Color BLUE = new Color(25, 105, 220);
    private final Color BLUE_LIGHT = new Color(235, 245, 255);
    private final Color BG = new Color(246, 249, 253);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(25, 48, 80);
    private final Color MUTED = new Color(100, 120, 145);
    private final Color GREEN = new Color(25, 170, 105);
    private final Color ORANGE = new Color(240, 145, 20);
    private final Color PURPLE = new Color(125, 75, 210);
    private final Color RED = new Color(225, 70, 85);

    private JTextField searchField;
    private JComboBox<String> priorityBox;
    private JPanel announcementPanel;

    private final List<Announcement> announcementsList = new ArrayList<>();

    public announcements(String name, String username, String roomNumber) {

        super("Announcements - Hostel Complaint & Maintenance Tracking System");

        this.studentName = name;
        this.studentUsername = username;
        this.roomNumber=roomNumber;

        loadAnnouncements();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG);

        add(createSidebar(), BorderLayout.WEST);
        add(createMainPanel(), BorderLayout.CENTER);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1150, 700));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadAnnouncements() {

        announcementsList.add(
                new Announcement(
                        "Water Supply Shutdown – Maintenance Work",
                        "The water supply will be temporarily shut down in all blocks on 28 Aug 2025 from 10:00 AM to 2:00 PM for maintenance work. Please store enough water.",
                        "Important",
                        "28 Aug 2025",
                        "26 Aug 2025, 09:15 AM",
                        "Admin",
                        RED
                )
        );

        announcementsList.add(
                new Announcement(
                        "Mess Menu Update",
                        "The mess menu for the upcoming week has been updated. Please check the notice board for the complete menu.",
                        "General",
                        "25 Aug 2025",
                        "24 Aug 2025, 04:30 PM",
                        "Mess Committee",
                        GREEN
                )
        );

        announcementsList.add(
                new Announcement(
                        "Room Inspection Schedule",
                        "Room inspection will be conducted on 27 Aug 2025 from 9:00 AM to 4:00 PM. All students are requested to keep their rooms clean and well-organized.",
                        "Academic",
                        "27 Aug 2025",
                        "23 Aug 2025, 11:20 AM",
                        "Warden Office",
                        PURPLE
                )
        );

        announcementsList.add(
                new Announcement(
                        "Hostel Laundry Service",
                        "Laundry service will be available on 26 Aug 2025 from 8:00 AM to 6:00 PM. Please collect your clothes from the laundry room.",
                        "Facility",
                        "26 Aug 2025",
                        "22 Aug 2025, 02:45 PM",
                        "Hostel Office",
                        BLUE
                )
        );

        announcementsList.add(
                new Announcement(
                        "Cultural Fest Registration Open",
                        "Registration for the upcoming cultural fest is now open. Interested students can register at the hostel office by 30 Aug 2025.",
                        "General",
                        "30 Aug 2025",
                        "20 Aug 2025, 10:00 AM",
                        "Student Council",
                        ORANGE
                )
        );
    }

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel(null);
        sidebar.setPreferredSize(new Dimension(265, 900));
        sidebar.setBackground(NAVY);

        JLabel logoIcon = new JLabel("⌂");
        logoIcon.setBounds(25, 22, 45, 45);
        logoIcon.setForeground(Color.WHITE);
        logoIcon.setFont(new Font("Arial", Font.BOLD, 36));
        sidebar.add(logoIcon);

        JLabel hostel = new JLabel("HOSTEL");
        hostel.setBounds(78, 20, 170, 35);
        hostel.setForeground(Color.WHITE);
        hostel.setFont(new Font("Arial", Font.BOLD, 24));
        sidebar.add(hostel);

        JLabel subtitle = new JLabel(
                "<html>COMPLAINT & MAINTENANCE<br>TRACKING SYSTEM</html>"
        );
        subtitle.setBounds(79, 52, 180, 38);
        subtitle.setForeground(new Color(195, 215, 235));
        subtitle.setFont(new Font("Arial", Font.PLAIN, 10));
        sidebar.add(subtitle);

        addMenuButton(
                sidebar,
                "⌂    Dashboard",
                115,
                false,
                e -> openDashboard()
        );

        addMenuButton(
                sidebar,
                "▣    My Complaints",
                170,
                false,
                e -> openMyComplaints()
        );

        addMenuButton(
                sidebar,
                "✎    New Complaint",
                225,
                false,
                e -> openNewComplaint()
        );

        addMenuButton(
                sidebar,
                "⚑    Announcements",
                280,
                true,
                e -> {}
        );

        addMenuButton(
                sidebar,
                "♧    Notifications",
                335,
                false,
                e -> showNotifications()
        );

        addMenuButton(
                sidebar,
                "★    Feedback",
                390,
                false,
                e -> showFeedback()
        );

        addMenuButton(
                sidebar,
                "⇥    Logout",
                445,
                false,
                e -> logout()
        );

        JPanel helpPanel = new JPanel(null);
        helpPanel.setBounds(15, 700, 235, 125);
        helpPanel.setBackground(NAVY_LIGHT);

        JLabel helpIcon = new JLabel("?");
        helpIcon.setBounds(15, 12, 35, 35);
        helpIcon.setForeground(Color.WHITE);
        helpIcon.setFont(new Font("Arial", Font.BOLD, 27));
        helpPanel.add(helpIcon);

        JLabel helpTitle = new JLabel("Need Help?");
        helpTitle.setBounds(15, 45, 180, 25);
        helpTitle.setForeground(Color.WHITE);
        helpTitle.setFont(new Font("Arial", Font.BOLD, 16));
        helpPanel.add(helpTitle);

        JLabel helpText = new JLabel(
                "<html>Contact hostel support<br>for assistance.</html>"
        );
        helpText.setBounds(15, 68, 190, 40);
        helpText.setForeground(new Color(210, 225, 240));
        helpText.setFont(new Font("Arial", Font.PLAIN, 11));
        helpPanel.add(helpText);

        sidebar.add(helpPanel);

        return sidebar;
    }

    private void addMenuButton(
            JPanel sidebar,
            String text,
            int y,
            boolean selected,
            ActionListener action
    ) {

        JButton button = new JButton(text);

        button.setBounds(15, y, 235, 45);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setForeground(Color.WHITE);

        button.setBackground(
                selected ? BLUE : NAVY
        );

        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(action);

        sidebar.add(button);
    }

    private JPanel createMainPanel() {

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);

        JPanel header = createHeader();
        main.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setBackground(BG);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(22, 28, 25, 28));

        JPanel titlePanel = createTitleSection();
        content.add(titlePanel);
        content.add(Box.createVerticalStrut(18));

        JPanel stats = createStatistics();
        content.add(stats);
        content.add(Box.createVerticalStrut(20));

        JPanel searchPanel = createSearchPanel();
        content.add(searchPanel);
        content.add(Box.createVerticalStrut(18));

        JPanel body = new JPanel(new BorderLayout(20, 0));
        body.setBackground(BG);
        body.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.setPreferredSize(new Dimension(1200, 600));
        body.setMaximumSize(new Dimension(Integer.MAX_VALUE, 700));

        announcementPanel = new JPanel();
        announcementPanel.setBackground(BG);
        announcementPanel.setLayout(
                new BoxLayout(announcementPanel, BoxLayout.Y_AXIS)
        );

        JScrollPane scrollPane = new JScrollPane(announcementPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(BG);
        scrollPane.getViewport().setBackground(BG);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        body.add(scrollPane, BorderLayout.CENTER);
        body.add(createImportantPanel(), BorderLayout.EAST);

        content.add(body);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG);
        wrapper.add(content, BorderLayout.NORTH);

        JScrollPane mainScroll = new JScrollPane(wrapper);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(16);
        mainScroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        main.add(mainScroll, BorderLayout.CENTER);

        refreshAnnouncements();

        return main;
    }

    private JPanel createHeader() {

        JPanel header = new JPanel(null);
        header.setPreferredSize(new Dimension(100, 62));
        header.setBackground(Color.WHITE);
        header.setBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 1, 0,
                        new Color(225, 232, 242)
                )
        );

        JLabel notification = new JLabel("🔔");
        notification.setBounds(1020, 10, 45, 40);
        notification.setFont(
                new Font("Segoe UI Emoji", Font.PLAIN, 22)
        );
        notification.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(notification);

        JLabel count = new JLabel("2");
        count.setBounds(1042, 3, 18, 18);
        count.setOpaque(true);
        count.setBackground(RED);
        count.setForeground(Color.WHITE);
        count.setHorizontalAlignment(SwingConstants.CENTER);
        count.setFont(new Font("Arial", Font.BOLD, 10));
        header.add(count);

        JLabel avatar = new JLabel(getInitial());
        avatar.setBounds(1080, 10, 42, 42);
        avatar.setOpaque(true);
        avatar.setBackground(BLUE);
        avatar.setForeground(Color.WHITE);
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setFont(new Font("Arial", Font.BOLD, 18));
        header.add(avatar);

        JLabel name = new JLabel(studentName);
        name.setBounds(1135, 8, 160, 22);
        name.setFont(new Font("Arial", Font.BOLD, 14));
        name.setForeground(TEXT);
        header.add(name);

        JLabel room = new JLabel("Student  •  Room No: " + getRoomNumber());
        room.setBounds(1135, 29, 210, 20);
        room.setFont(new Font("Arial", Font.PLAIN, 11));
        room.setForeground(MUTED);
        header.add(room);

        return header;
    }

    private JPanel createTitleSection() {

        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(BG);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));

        JLabel icon = new JLabel("⚑");
        icon.setPreferredSize(new Dimension(65, 65));
        icon.setOpaque(true);
        icon.setBackground(BLUE_LIGHT);
        icon.setForeground(BLUE);
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setVerticalAlignment(SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Symbol", Font.BOLD, 30));

        panel.add(icon, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setBackground(BG);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Announcements");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(NAVY);

        JLabel subtitle = new JLabel(
                "Stay updated with the latest hostel notices and important information."
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(MUTED);

        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(subtitle);

        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatistics() {

        JPanel panel = new JPanel(new GridLayout(1, 3, 15, 0));
        panel.setBackground(BG);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 105));

        panel.add(
                createStatCard(
                        "⚑",
                        "Total Announcements",
                        String.valueOf(announcementsList.size()),
                        BLUE,
                        BLUE_LIGHT
                )
        );

        panel.add(
                createStatCard(
                        "!",
                        "Important",
                        String.valueOf(countImportant()),
                        RED,
                        new Color(255, 238, 241)
                )
        );

        panel.add(
                createStatCard(
                        "◷",
                        "Recent Updates",
                        "3",
                        GREEN,
                        new Color(233, 250, 242)
                )
        );

        return panel;
    }

    private JPanel createStatCard(
            String iconText,
            String title,
            String value,
            Color color,
            Color iconBackground
    ) {

        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 230, 242)
                )
        );

        JLabel icon = new JLabel(iconText);
        icon.setBounds(20, 20, 58, 58);
        icon.setOpaque(true);
        icon.setBackground(iconBackground);
        icon.setForeground(color);
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setVerticalAlignment(SwingConstants.CENTER);
        icon.setFont(new Font("Arial", Font.BOLD, 25));
        card.add(icon);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setBounds(95, 20, 210, 25);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 13));
        titleLabel.setForeground(TEXT);
        card.add(titleLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setBounds(95, 45, 150, 35);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 27));
        valueLabel.setForeground(color);
        card.add(valueLabel);

        return card;
    }

    private JPanel createSearchPanel() {

        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBackground(BG);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(450, 45));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(210, 222, 238)
                        ),
                        new EmptyBorder(0, 15, 0, 15)
                )
        );

        searchField.setToolTipText("Search announcements");
        searchField.addActionListener(this);

        panel.add(searchField, BorderLayout.CENTER);

        priorityBox = new JComboBox<>(
                new String[]{
                        "All Priority",
                        "Important",
                        "General"
                }
        );

        priorityBox.setPreferredSize(new Dimension(170, 45));
        priorityBox.setFont(new Font("Arial", Font.PLAIN, 13));
        priorityBox.setBackground(Color.WHITE);
        priorityBox.addActionListener(this);

        panel.add(priorityBox, BorderLayout.EAST);

        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 45));
        clearButton.setFont(new Font("Arial", Font.BOLD, 12));
        clearButton.setForeground(BLUE);
        clearButton.setBackground(Color.WHITE);
        clearButton.setBorder(
                BorderFactory.createLineBorder(BLUE)
        );
        clearButton.setFocusPainted(false);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(e -> {
            searchField.setText("");
            priorityBox.setSelectedIndex(0);
            refreshAnnouncements();
        });

        panel.add(clearButton, BorderLayout.WEST);

        return panel;
    }

    private JPanel createImportantPanel() {

        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(300, 310));
        panel.setBackground(Color.WHITE);
        panel.setBorder(
                BorderFactory.createLineBorder(
                        new Color(215, 226, 240)
                )
        );

        JLabel titleIcon = new JLabel("⚑");
        titleIcon.setBounds(18, 15, 35, 35);
        titleIcon.setOpaque(true);
        titleIcon.setBackground(new Color(255, 238, 241));
        titleIcon.setForeground(RED);
        titleIcon.setHorizontalAlignment(SwingConstants.CENTER);
        titleIcon.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleIcon);

        JLabel title = new JLabel("Important Notice");
        title.setBounds(65, 15, 210, 35);
        title.setFont(new Font("Arial", Font.BOLD, 17));
        title.setForeground(NAVY);
        panel.add(title);

        JPanel notice = new JPanel(null);
        notice.setBounds(15, 65, 270, 225);
        notice.setBackground(new Color(255, 248, 249));
        notice.setBorder(
                BorderFactory.createLineBorder(
                        new Color(255, 200, 208)
                )
        );

        JLabel noticeTitle = new JLabel(
                "<html>Water Supply<br>Shutdown</html>"
        );
        noticeTitle.setBounds(15, 15, 230, 45);
        noticeTitle.setFont(new Font("Arial", Font.BOLD, 16));
        noticeTitle.setForeground(new Color(190, 45, 65));
        notice.add(noticeTitle);

        JLabel noticeText = new JLabel(
                "<html>The water supply will be temporarily<br>"
                        + "shut down for maintenance work.<br><br>"
                        + "Please store enough water in advance.</html>"
        );
        noticeText.setBounds(15, 65, 240, 90);
        noticeText.setFont(new Font("Arial", Font.PLAIN, 12));
        noticeText.setForeground(MUTED);
        notice.add(noticeText);

        JLabel date = new JLabel("📅  28 Aug 2025");
        date.setBounds(15, 175, 150, 25);
        date.setFont(new Font("Arial", Font.BOLD, 11));
        date.setForeground(TEXT);
        notice.add(date);

        JLabel priority = new JLabel("HIGH PRIORITY");
        priority.setBounds(160, 175, 95, 25);
        priority.setOpaque(true);
        priority.setBackground(new Color(255, 225, 230));
        priority.setForeground(RED);
        priority.setHorizontalAlignment(SwingConstants.CENTER);
        priority.setFont(new Font("Arial", Font.BOLD, 9));
        notice.add(priority);

        panel.add(notice);

        return panel;
    }

    private void refreshAnnouncements() {

        if (announcementPanel == null) {
            return;
        }

        announcementPanel.removeAll();

        String search = searchField == null
                ? ""
                : searchField.getText().trim().toLowerCase();

        String priority = priorityBox == null
                ? "All Priority"
                : (String) priorityBox.getSelectedItem();

        int visible = 0;

        for (Announcement announcement : announcementsList) {

            boolean matchesSearch =
                    search.isEmpty()
                            || announcement.title.toLowerCase().contains(search)
                            || announcement.description.toLowerCase().contains(search);

            boolean matchesPriority =
                    priority.equals("All Priority")
                            || announcement.priority.equals(priority);

            if (matchesSearch && matchesPriority) {

                announcementPanel.add(
                        createAnnouncementCard(announcement)
                );

                announcementPanel.add(
                        Box.createVerticalStrut(12)
                );

                visible++;
            }
        }

        if (visible == 0) {

            JPanel empty = new JPanel(new GridBagLayout());
            empty.setPreferredSize(new Dimension(700, 180));
            empty.setBackground(Color.WHITE);
            empty.setBorder(
                    BorderFactory.createLineBorder(
                            new Color(220, 230, 242)
                    )
            );

            JLabel emptyLabel = new JLabel(
                    "No announcements found."
            );
            emptyLabel.setFont(
                    new Font("Arial", Font.BOLD, 16)
            );
            emptyLabel.setForeground(MUTED);

            empty.add(emptyLabel);

            announcementPanel.add(empty);
        }

        announcementPanel.revalidate();
        announcementPanel.repaint();
    }

    private JPanel createAnnouncementCard(
            Announcement announcement
    ) {

        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(750, 135));
        card.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 135)
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(215, 226, 240)
                        ),
                        new EmptyBorder(1, 1, 1, 1)
                )
        );

        JPanel stripe = new JPanel();
        stripe.setBackground(announcement.color);
        stripe.setBounds(0, 0, 5, 135);
        card.add(stripe);

        JLabel icon = new JLabel(
                announcement.priority.equals("Important")
                        ? "⚑"
                        : "●"
        );

        icon.setBounds(20, 25, 55, 55);
        icon.setOpaque(true);
        icon.setBackground(
                new Color(
                        Math.min(255, announcement.color.getRed() + 220),
                        Math.min(255, announcement.color.getGreen() + 220),
                        Math.min(255, announcement.color.getBlue() + 220)
                )
        );
        icon.setForeground(announcement.color);
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setVerticalAlignment(SwingConstants.CENTER);
        icon.setFont(new Font("Arial", Font.BOLD, 24));
        card.add(icon);

        JLabel priority = new JLabel(
                announcement.priority
        );
        priority.setBounds(92, 15, 80, 22);
        priority.setOpaque(true);

        if (announcement.priority.equals("Important")) {
            priority.setBackground(new Color(255, 225, 230));
            priority.setForeground(RED);
        } else {
            priority.setBackground(new Color(232, 247, 239));
            priority.setForeground(GREEN);
        }

        priority.setHorizontalAlignment(SwingConstants.CENTER);
        priority.setFont(new Font("Arial", Font.BOLD, 9));
        card.add(priority);

        JLabel date = new JLabel(
                announcement.date
        );
        date.setBounds(580, 15, 130, 25);
        date.setOpaque(true);
        date.setBackground(
                announcement.priority.equals("Important")
                        ? new Color(255, 238, 241)
                        : new Color(237, 247, 255)
        );
        date.setForeground(announcement.color);
        date.setHorizontalAlignment(SwingConstants.CENTER);
        date.setFont(new Font("Arial", Font.BOLD, 10));
        card.add(date);

        JLabel title = new JLabel(
                announcement.title
        );
        title.setBounds(92, 40, 450, 25);
        title.setFont(
                new Font("Arial", Font.BOLD, 16)
        );
        title.setForeground(NAVY);
        card.add(title);

        JLabel description = new JLabel(
                "<html>"
                        + shortenText(
                        announcement.description,
                        115
                )
                        + "</html>"
        );

        description.setBounds(92, 65, 570, 38);
        description.setFont(
                new Font("Arial", Font.PLAIN, 11)
        );
        description.setForeground(MUTED);
        card.add(description);

        JLabel author = new JLabel(
                "♙  " + announcement.author
                        + "     •     📅 "
                        + announcement.postedDate
        );
        author.setBounds(92, 105, 500, 20);
        author.setFont(
                new Font("Arial", Font.PLAIN, 10)
        );
        author.setForeground(MUTED);
        card.add(author);

        JLabel arrow = new JLabel("›");
        arrow.setBounds(690, 50, 30, 40);
        arrow.setFont(
                new Font("Arial", Font.BOLD, 28)
        );
        arrow.setForeground(announcement.color);
        arrow.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(arrow);

        card.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        card.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        card.setBorder(
                                BorderFactory.createLineBorder(
                                        announcement.color,
                                        2
                                )
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        card.setBorder(
                                BorderFactory.createLineBorder(
                                        new Color(215, 226, 240)
                                )
                        );
                    }

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e
                    ) {

                        showAnnouncementDetails(
                                announcement
                        );
                    }
                }
        );

        return card;
    }

    private void showAnnouncementDetails(
            Announcement announcement
    ) {

        JPanel panel = new JPanel();
        panel.setLayout(
                new BoxLayout(panel, BoxLayout.Y_AXIS)
        );
        panel.setBorder(
                new EmptyBorder(10, 10, 10, 10)
        );

        JLabel title = new JLabel(
                announcement.title
        );
        title.setFont(
                new Font("Arial", Font.BOLD, 19)
        );
        title.setForeground(NAVY);

        JLabel priority = new JLabel(
                "Priority: " + announcement.priority
        );
        priority.setFont(
                new Font("Arial", Font.BOLD, 12)
        );
        priority.setForeground(
                announcement.color
        );

        JTextArea description = new JTextArea(
                announcement.description
        );
        description.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );
        description.setForeground(TEXT);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setBackground(
                new Color(248, 250, 253)
        );
        description.setBorder(
                new EmptyBorder(12, 12, 12, 12)
        );

        JLabel information = new JLabel(
                "Posted by: "
                        + announcement.author
                        + "     |     "
                        + announcement.postedDate
                        + "     |     Event date: "
                        + announcement.date
        );

        information.setFont(
                new Font("Arial", Font.PLAIN, 11)
        );
        information.setForeground(MUTED);

        panel.add(title);
        panel.add(Box.createVerticalStrut(8));
        panel.add(priority);
        panel.add(Box.createVerticalStrut(12));
        panel.add(description);
        panel.add(Box.createVerticalStrut(12));
        panel.add(information);

        JOptionPane.showMessageDialog(
                this,
                panel,
                "Announcement Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private int countImportant() {

        int count = 0;

        for (Announcement announcement : announcementsList) {

            if (announcement.priority.equals("Important")) {
                count++;
            }
        }

        return count;
    }

    private String shortenText(
            String text,
            int max
    ) {

        if (text.length() <= max) {
            return text;
        }

        return text.substring(0, max) + "...";
    }

    private String getInitial() {

        if (studentName == null
                || studentName.trim().isEmpty()) {

            return "?";
        }

        return studentName
                .trim()
                .substring(0, 1)
                .toUpperCase();
    }

    private String getRoomNumber() {

        return "184";
    }

    private void openDashboard() {

        dispose();

        new dashboard(
                studentName,
                studentUsername,
                getRoomNumber()
        );
    }

    private void openMyComplaints() {

        try {

            dispose();

            new mycomplaints(
                    studentName,
                    studentUsername,roomNumber
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to open My Complaints page.\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void openNewComplaint() {

        try {

            dispose();

            new complaints(
                    studentName,
                    studentUsername,roomNumber
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to open New Complaint page.\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void showNotifications() {

        JOptionPane.showMessageDialog(
                this,
                "You have 2 new notifications.",
                "Notifications",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showFeedback() {

        dispose();
        new feedback("","");
    }

    private void logout() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            dispose();

            new login("", "");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        refreshAnnouncements();
    }

    private static class Announcement {

        String title;
        String description;
        String priority;
        String date;
        String postedDate;
        String author;
        Color color;

        Announcement(
                String title,
                String description,
                String priority,
                String date,
                String postedDate,
                String author,
                Color color
        ) {

            this.title = title;
            this.description = description;
            this.priority = priority;
            this.date = date;
            this.postedDate = postedDate;
            this.author = author;
            this.color = color;
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new announcements(
                        "",
                        "",""
                )
        );
    }
}