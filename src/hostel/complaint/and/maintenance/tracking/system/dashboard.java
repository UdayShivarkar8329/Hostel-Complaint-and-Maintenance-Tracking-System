package hostel.complaint.and.maintenance.tracking.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class dashboard extends JFrame implements ActionListener {

   private String studentName;
   private String studentUsername;
   private String roomNumber;

   private JButton dashboardButton;
   private JButton complaintsButton;
   private JButton newComplaintButton;
   private JButton announcementsButton;
   private JButton notificationsButton;
   private JButton feedbackButton;
   private JButton logoutButton;

   private final Color NAVY = new Color(7, 20, 58);
   private final Color NAVY_2 = new Color(8, 28, 67);

   private final Color BLUE = new Color(45, 86, 235);
   private final Color PURPLE = new Color(112, 54, 238);
   private final Color GREEN = new Color(30, 190, 108);
   private final Color ORANGE = new Color(247, 142, 24);
   private final Color RED = new Color(238, 70, 85);

   private final Color TEXT = new Color(16, 27, 65);
   private final Color MUTED = new Color(88, 105, 145);

   private final Color BACKGROUND = new Color(247, 249, 255);
   private final Color BORDER = new Color(224, 230, 244);

   public dashboard(String name, String username, String room) {

      super("Hostel Complaint & Maintenance Tracking System");

      studentName = name;
      studentUsername = username;
      roomNumber = room;

      if (studentName == null || studentName.trim().isEmpty())
         studentName = "Student";

      if (studentUsername == null)
         studentUsername = "";

      if (roomNumber == null || roomNumber.trim().isEmpty())
         roomNumber = "";

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      setMinimumSize(new Dimension(1200, 750));

      setLayout(new BorderLayout());

      createInterface();

      setExtendedState(JFrame.MAXIMIZED_BOTH);

      setLocationRelativeTo(null);

      setVisible(true);
   }



   private void createInterface() {

      add(createSidebar(), BorderLayout.WEST);

      add(createMainPanel(), BorderLayout.CENTER);
   }


   private JPanel createSidebar() {

      JPanel sidebar = new GradientPanel(
              NAVY,
              NAVY_2
      );

      sidebar.setPreferredSize(
              new Dimension(265, 900)
      );

      sidebar.setLayout(new BorderLayout());

      JPanel logoPanel = new JPanel();

      logoPanel.setOpaque(false);

      logoPanel.setBorder(
              new EmptyBorder(22, 22, 15, 15)
      );

      logoPanel.setLayout(
              new BoxLayout(
                      logoPanel,
                      BoxLayout.X_AXIS
              )
      );

      JLabel homeIcon = new JLabel("⌂");

      homeIcon.setFont(
              new Font("Segoe UI Symbol",
                      Font.BOLD,
                      45)
      );

      homeIcon.setForeground(Color.WHITE);

      JLabel logoText = new JLabel(
              "<html><b style='font-size:18px'>HOSTEL</b>" +
                      "<br><font size='2'>COMPLAINT & MAINTENANCE</font>" +
                      "<br><font size='2'>TRACKING SYSTEM</font></html>"
      );

      logoText.setForeground(Color.WHITE);

      logoPanel.add(homeIcon);

      logoPanel.add(
              Box.createHorizontalStrut(12)
      );

      logoPanel.add(logoText);


      JPanel menu = new JPanel();

      menu.setOpaque(false);

      menu.setBorder(
              new EmptyBorder(15, 14, 10, 14)
      );

      menu.setLayout(
              new BoxLayout(
                      menu,
                      BoxLayout.Y_AXIS
              )
      );

      dashboardButton =
              createMenuButton(
                      "⌂",
                      "Dashboard",
                      true
              );

      complaintsButton =
              createMenuButton(
                      "▣",
                      "My Complaints",
                      false
              );

      newComplaintButton =
              createMenuButton(
                      "✎",
                      "New Complaint",
                      false
              );

      announcementsButton = createMenuButton("⚑", "Announcements", false);

      announcementsButton.addActionListener(ActiveEvent ->{
         dispose();
         new announcements(studentUsername,studentName,roomNumber);
              });

      notificationsButton =
              createMenuButton(
                      "🔔",
                      "Notifications",
                      false
              );

      feedbackButton =
              createMenuButton(
                      "★",
                      "Feedback",
                      false
              );

      logoutButton =
              createMenuButton(
                      "⇥",
                      "Logout",
                      false
              );

      addMenu(menu, dashboardButton);
      addMenu(menu, complaintsButton);
      addMenu(menu, newComplaintButton);
      addMenu(menu, announcementsButton);
      addMenu(menu, notificationsButton);
      addMenu(menu, feedbackButton);
      addMenu(menu, logoutButton);

      JPanel center = new JPanel(
              new BorderLayout()
      );

      center.setOpaque(false);

      center.add(
              menu,
              BorderLayout.NORTH
      );

      // ---------- HELP ----------

      JPanel help = new RoundedPanel(
              new Color(14, 52, 112),
              18
      );

      help.setBorder(
              new EmptyBorder(
                      18,
                      18,
                      18,
                      18
              )
      );

      help.setLayout(
              new BoxLayout(
                      help,
                      BoxLayout.Y_AXIS
              )
      );

      JLabel supportIcon =
              new JLabel("◉");

      supportIcon.setFont(
              new Font("Arial",
                      Font.BOLD,
                      25)
      );

      supportIcon.setForeground(Color.WHITE);

      JLabel supportTitle =
              new JLabel("Need Help?");

      supportTitle.setFont(
              new Font("Arial",
                      Font.BOLD,
                      17)
      );

      supportTitle.setForeground(Color.WHITE);

      JLabel supportText =
              new JLabel(
                      "<html>Our support team is here<br>" +
                              "to assist you 24/7.</html>"
              );

      supportText.setFont(
              new Font("Arial",
                      Font.PLAIN,
                      12)
      );

      supportText.setForeground(
              new Color(215, 228, 250)
      );

      JButton supportButton =
              new JButton("Contact Support");

      supportButton.setForeground(Color.WHITE);

      supportButton.setBackground(
              new Color(70, 70, 245)
      );

      supportButton.setFocusPainted(false);

      supportButton.setBorderPainted(false);

      supportButton.setCursor(
              new Cursor(Cursor.HAND_CURSOR)
      );

      supportButton.addActionListener(
              e -> JOptionPane.showMessageDialog(
                      this,
                      "Hostel support is available 24/7.",
                      "Support",
                      JOptionPane.INFORMATION_MESSAGE
              )
      );

      help.add(supportIcon);

      help.add(
              Box.createVerticalStrut(8)
      );

      help.add(supportTitle);

      help.add(
              Box.createVerticalStrut(7)
      );

      help.add(supportText);

      help.add(
              Box.createVerticalStrut(14)
      );

      help.add(supportButton);

      JPanel bottom =
              new JPanel(new BorderLayout());

      bottom.setOpaque(false);

      bottom.setBorder(
              new EmptyBorder(
                      10,
                      15,
                      25,
                      15
              )
      );

      bottom.add(
              help,
              BorderLayout.CENTER
      );

      sidebar.add(
              logoPanel,
              BorderLayout.NORTH
      );

      sidebar.add(
              center,
              BorderLayout.CENTER
      );

      sidebar.add(
              bottom,
              BorderLayout.SOUTH
      );

      return sidebar;
   }

   private void addMenu(
           JPanel menu,
           JButton button
   ) {

      menu.add(button);

      menu.add(
              Box.createVerticalStrut(7)
      );
   }

   // =========================================================
   // SIDEBAR BUTTON
   // =========================================================

   private JButton createMenuButton(
           String icon,
           String text,
           boolean selected
   ) {

      JButton button = new JButton();

      button.setPreferredSize(
              new Dimension(235, 52)
      );

      button.setMaximumSize(
              new Dimension(
                      Integer.MAX_VALUE,
                      52
              )
      );

      button.setLayout(
              new BorderLayout()
      );

      button.setBorder(
              new EmptyBorder(
                      0,
                      14,
                      0,
                      12
              )
      );

      button.setFocusPainted(false);

      button.setBorderPainted(false);

      button.setOpaque(true);

      button.setBackground(
              selected
                      ? PURPLE
                      : NAVY
      );

      button.setCursor(
              new Cursor(
                      Cursor.HAND_CURSOR
              )
      );

      JLabel iconLabel =
              new JLabel(icon);

      iconLabel.setFont(
              new Font(
                      "Segoe UI Symbol",
                      Font.BOLD,
                      22
              )
      );

      iconLabel.setForeground(Color.WHITE);

      iconLabel.setHorizontalAlignment(
              SwingConstants.CENTER
      );

      iconLabel.setPreferredSize(
              new Dimension(38, 40)
      );

      JLabel textLabel =
              new JLabel(text);

      textLabel.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      14
              )
      );

      textLabel.setForeground(Color.WHITE);

      button.add(
              iconLabel,
              BorderLayout.WEST
      );

      button.add(
              textLabel,
              BorderLayout.CENTER
      );

      button.addActionListener(this);

      return button;
   }

   // =========================================================
   // MAIN PANEL
   // =========================================================

   private JPanel createMainPanel() {

      JPanel main =
              new JPanel(new BorderLayout());

      main.setBackground(BACKGROUND);

      main.add(
              createTopBar(),
              BorderLayout.NORTH
      );

      JPanel content =
              new JPanel();

      content.setBackground(
              BACKGROUND
      );

      content.setBorder(
              new EmptyBorder(
                      20,
                      35,
                      20,
                      28
              )
      );

      content.setLayout(
              new BoxLayout(
                      content,
                      BoxLayout.Y_AXIS
              )
      );

      content.add(
              createPageTitle()
      );

      content.add(
              Box.createVerticalStrut(18)
      );

      content.add(
              createWelcomeArea()
      );

      content.add(
              Box.createVerticalStrut(18)
      );

      content.add(
              createStatistics()
      );

      content.add(
              Box.createVerticalStrut(20)
      );

      content.add(
              createBottomArea()
      );

      content.add(
              Box.createVerticalStrut(15)
      );

      JLabel footer =
              new JLabel(
                      "© 2026 Hostel Complaint & Maintenance Tracking System. All rights reserved."
              );

      footer.setFont(
              new Font(
                      "Arial",
                      Font.PLAIN,
                      11
              )
      );

      footer.setForeground(
              new Color(130, 145, 175)
      );

      footer.setAlignmentX(
              Component.CENTER_ALIGNMENT
      );

      content.add(footer);

      JScrollPane scroll =
              new JScrollPane(content);

      scroll.setBorder(null);

      scroll.getVerticalScrollBar()
              .setUnitIncrement(16);

      main.add(
              scroll,
              BorderLayout.CENTER
      );

      return main;
   }

   // =========================================================
   // TOP BAR
   // =========================================================

   private JPanel createTopBar() {

      JPanel bar =
              new JPanel(
                      new BorderLayout()
              );

      bar.setBackground(Color.WHITE);

      bar.setPreferredSize(
              new Dimension(0, 62)
      );

      JLabel hamburger =
              new JLabel("☰");

      hamburger.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      25
              )
      );

      hamburger.setForeground(
              NAVY
      );

      hamburger.setBorder(
              new EmptyBorder(
                      0,
                      28,
                      0,
                      0
              )
      );

      JPanel right =
              new JPanel(
                      new FlowLayout(
                              FlowLayout.RIGHT,
                              12,
                              10
                      )
              );

      right.setOpaque(false);

      JLabel bell =
              new JLabel("🔔");

      bell.setFont(
              new Font(
                      "Segoe UI Symbol",
                      Font.BOLD,
                      26
              )
      );

      bell.setForeground(NAVY);

      JLabel badge =
              new JLabel("2");

      badge.setHorizontalAlignment(
              SwingConstants.CENTER
      );

      badge.setForeground(Color.WHITE);

      badge.setBackground(RED);

      badge.setOpaque(true);

      badge.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      9
              )
      );

      badge.setPreferredSize(
              new Dimension(17, 17)
      );

      JPanel notification =
              new JPanel(
                      new BorderLayout()
              );

      notification.setOpaque(false);

      notification.add(
              bell,
              BorderLayout.CENTER
      );

      notification.add(
              badge,
              BorderLayout.NORTH
      );

      JLabel avatar =
              new JLabel(
                      getInitial(),
                      SwingConstants.CENTER
              );

      avatar.setPreferredSize(
              new Dimension(42, 42)
      );

      avatar.setForeground(Color.WHITE);

      avatar.setBackground(
              BLUE
      );

      avatar.setOpaque(true);

      avatar.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      18
              )
      );

      JPanel userInfo =
              new JPanel();

      userInfo.setOpaque(false);

      userInfo.setLayout(
              new BoxLayout(
                      userInfo,
                      BoxLayout.Y_AXIS
              )
      );

      JLabel name =
              new JLabel(
                      studentName
              );

      name.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      14
              )
      );

      name.setForeground(TEXT);

      JLabel room =
              new JLabel(
                      "Room No: " + roomNumber
              );

      room.setFont(
              new Font(
                      "Arial",
                      Font.PLAIN,
                      11
              )
      );

      room.setForeground(MUTED);

      userInfo.add(name);

      userInfo.add(room);

      JLabel arrow =
              new JLabel("⌄");

      arrow.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      20
              )
      );

      arrow.setForeground(NAVY);

      right.add(notification);

      right.add(avatar);

      right.add(userInfo);

      right.add(arrow);

      bar.add(
              hamburger,
              BorderLayout.WEST
      );

      bar.add(
              right,
              BorderLayout.EAST
      );

      return bar;
   }

   private JPanel createPageTitle() {

      JPanel panel = new JPanel();

      panel.setOpaque(false);

      panel.setLayout(
              new BoxLayout(
                      panel,
                      BoxLayout.X_AXIS
              )
      );

      JLabel title =
              new JLabel("Dashboard");

      title.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      32
              )
      );

      title.setForeground(TEXT);

      JLabel subtitle =
              new JLabel(
                      "Hostel Complaint & Maintenance Tracking System"
              );

      subtitle.setFont(
              new Font(
                      "Arial",
                      Font.PLAIN,
                      14
              )
      );

      subtitle.setForeground(
              MUTED
      );

      panel.add(title);

      panel.add(
              Box.createVerticalStrut(3)
      );

      panel.add(subtitle);

      return panel;
   }


   private JPanel createWelcomeArea() {

      JPanel container =
              new JPanel(
                      new BorderLayout(18, 0)
              );

      container.setOpaque(false);

      container.setMaximumSize(
              new Dimension(
                      Integer.MAX_VALUE,
                      165
              )
      );

      // ---------- WELCOME ----------

      JPanel welcome =
              new GradientPanel(
                      new Color(74, 45, 240),
                      new Color(85, 142, 245)
              );

      welcome.setBorder(
              new EmptyBorder(
                      22,
                      28,
                      22,
                      28
              )
      );

      welcome.setLayout(
              new BorderLayout()
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

      JLabel heading =
              new JLabel(
                      "👋  Welcome, "
                              + studentName
                              + "!  👋"
              );

      heading.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      26
              )
      );

      heading.setForeground(
              Color.WHITE
      );

      JLabel message =
              new JLabel(
                      "<html>Easily manage your hostel complaints<br>" +
                              "and stay updated in real-time.</html>"
              );

      message.setFont(
              new Font(
                      "Arial",
                      Font.PLAIN,
                      14
              )
      );

      message.setForeground(
              Color.WHITE
      );

      text.add(heading);

      text.add(
              Box.createVerticalStrut(8)
      );

      text.add(message);

      welcome.add(
              text,
              BorderLayout.CENTER
      );

      // ---------- ROOM ----------

      JPanel room =
              new RoundedPanel(
                      Color.WHITE,
                      18
              );

      room.setPreferredSize(
              new Dimension(
                      300,
                      165
              )
      );

      room.setLayout(
              new BoxLayout(
                      room,
                      BoxLayout.Y_AXIS
              )
      );

      JLabel roomTitle =
              new JLabel(
                      "YOUR ROOM"
              );

      roomTitle.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      11
              )
      );

      roomTitle.setForeground(
              BLUE
      );

      JLabel roomNumberLabel =
              new JLabel(
                      "Room " + roomNumber
              );

      roomNumberLabel.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      22
              )
      );

      roomNumberLabel.setForeground(
              PURPLE
      );

      JLabel roomInfo =
              new JLabel(
                      "Your assigned hostel room"
              );

      roomInfo.setFont(
              new Font(
                      "Arial",
                      Font.PLAIN,
                      11
              )
      );

      roomInfo.setForeground(MUTED);

      JLabel roomIcon =
              new JLabel("⌂");

      roomIcon.setFont(
              new Font(
                      "Segoe UI Symbol",
                      Font.BOLD,
                      42
              )
      );

      roomIcon.setForeground(
              PURPLE
      );

      room.add(roomIcon);

      room.add(roomTitle);

      room.add(
              Box.createVerticalStrut(3)
      );

      room.add(roomNumberLabel);

      room.add(
              Box.createVerticalStrut(4)
      );

      room.add(roomInfo);

      container.add(
              welcome,
              BorderLayout.CENTER
      );

      container.add(
              room,
              BorderLayout.EAST
      );

      return container;
   }

   // =========================================================
   // STATISTICS
   // =========================================================

   private JPanel createStatistics() {

      JPanel panel =
              new JPanel(
                      new GridLayout(
                              1,
                              4,
                              17,
                              0
                      )
              );

      panel.setOpaque(false);

      panel.setMaximumSize(
              new Dimension(
                      Integer.MAX_VALUE,
                      145
              )
      );

      panel.add(
              createStatCard(
                      "▣",
                      "My Complaints",
                      "Total Submitted",
                      "1",
                      BLUE,
                      "↗ 100%"
              )
      );

      panel.add(
              createStatCard(
                      "✎",
                      "New Complaint",
                      "Submit a new request",
                      "1",
                      GREEN,
                      "↘ 8%"
              )
      );

      panel.add(
              createStatCard(
                      "⚑",
                      "Announcements",
                      "Important updates",
                      "5",
                      ORANGE,
                      "↗ 20%"
              )
      );

      panel.add(
              createStatCard(
                      "⌁",
                      "Resolved",
                      "This Month",
                      "0",
                      new Color(35, 125, 235),
                      "↗ 18%"
              )
      );

      return panel;
   }

   private JPanel createStatCard(
           String icon,
           String title,
           String subtitle,
           String number,
           Color accent,
           String percentage
   ) {

      RoundedPanel card =
              new RoundedPanel(
                      Color.WHITE,
                      18
              );

      card.setBorder(
              new EmptyBorder(
                      16,
                      16,
                      14,
                      16
              )
      );

      card.setLayout(
              new BorderLayout(
                      13,
                      0
              )
      );

      JPanel iconCircle =
              new RoundedPanel(
                      new Color(
                              accent.getRed(),
                              accent.getGreen(),
                              accent.getBlue(),
                              30
                      ),
                      40
              );

      iconCircle.setPreferredSize(
              new Dimension(
                      60,
                      60
              )
      );

      iconCircle.setLayout(
              new GridBagLayout()
      );

      JLabel iconLabel =
              new JLabel(icon);

      iconLabel.setFont(
              new Font(
                      "Segoe UI Symbol",
                      Font.BOLD,
                      27
              )
      );

      iconLabel.setForeground(
              accent
      );

      iconCircle.add(iconLabel);

      JPanel details =
              new JPanel();

      details.setOpaque(false);

      details.setLayout(
              new BoxLayout(
                      details,
                      BoxLayout.Y_AXIS
              )
      );

      JLabel titleLabel = new JLabel(title);
      titleLabel.setBounds(28, 20, 400, 45);
      titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
      titleLabel.setForeground(TEXT);
      add(titleLabel);

      JLabel subtitleLabel =
              new JLabel(subtitle);
      subtitleLabel.setBounds(28, 58, 500, 25);

      subtitleLabel.setFont(
              new Font(
                      "Arial",
                      Font.PLAIN,
                      11
              )
      );

      subtitleLabel.setForeground(MUTED);

      JLabel numberLabel =
              new JLabel(number);

      numberLabel.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      28
              )
      );

      numberLabel.setForeground(TEXT);

      JLabel percent =
              new JLabel(
                      percentage
              );

      percent.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      10
              )
      );

      percent.setForeground(
              accent
      );

      details.add(titleLabel);

      details.add(
              Box.createVerticalStrut(4)
      );

      details.add(subtitleLabel);

      details.add(
              Box.createVerticalGlue()
      );

      JPanel bottom =
              new JPanel(
                      new BorderLayout()
              );

      bottom.setOpaque(false);

      bottom.add(
              numberLabel,
              BorderLayout.WEST
      );

      bottom.add(
              percent,
              BorderLayout.EAST
      );

      details.add(bottom);

      card.add(
              iconCircle,
              BorderLayout.WEST
      );

      card.add(
              details,
              BorderLayout.CENTER
      );

      return card;
   }

   // =========================================================
   // BOTTOM AREA

   // =========================================================

   private JPanel createBottomArea() {

      JPanel area =
              new JPanel(
                      new GridLayout(
                              1,
                              2,
                              18,
                              0
                      )
              );

      area.setOpaque(false);

      area.setMaximumSize(
              new Dimension(
                      Integer.MAX_VALUE,
                      410
              )
      );

      area.add(
              createRecentActivity()
      );

      area.add(
              createRightColumn()
      );

      return area;
   }

   // =========================================================
   // RECENT ACTIVITY
   // =========================================================

   private JPanel createRecentActivity() {

      RoundedPanel panel =
              new RoundedPanel(
                      Color.WHITE,
                      18
              );

      panel.setBorder(
              new EmptyBorder(
                      18,
                      20,
                      15,
                      20
              )
      );

      panel.setLayout(
              new BorderLayout(
                      0,
                      10
              )
      );

      JLabel title =
              new JLabel(
                      "⌁  Recent Activity"
              );

      title.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      17
              )
      );

      title.setForeground(
              TEXT
      );

      panel.add(
              title,
              BorderLayout.NORTH
      );

      String[] columns = {
              "Date",
              "Complaint",
              "Status",
              "Priority"
      };

//      Object[][] data = {
//
//              {
//                      "30 Aug 2025",
//                      "Fan not working (Room 204)",
//                      "Completed",
//                      "Medium"
//              },
//
//              {
//                      "28 Aug 2025",
//                      "Water leakage in bathroom",
//                      "Pending",
//                      "High"
//              },
//
//              {
//                      "25 Aug 2025",
//                      "Tube light not working (Room 204)",
//                      "Scheduled",
//                      "Low"
//              },
//
//              {
//                      "27 Aug 2025",
//                      "AC not cooling (Room 302)",
//                      "Pending",
//                      "High"
//              },
//
//              {
//                      "26 Aug 2025",
//                      "Door lock issue (Room 101)",
//                      "Completed",
//                      "Medium"
//              }
//      };

      JTable table =
              new JTable();

      table.setRowHeight(37);

      table.setFont(
              new Font(
                      "Arial",
                      Font.PLAIN,
                      11
              )
      );

      table.setForeground(
              MUTED
      );

      table.setShowGrid(false);

      table.setIntercellSpacing(
              new Dimension(
                      0,
                      1
              )
      );

      table.setSelectionBackground(
              new Color(
                      240,
                      245,
                      255
              )
      );

      table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11)
              );

      table.getTableHeader().setForeground(TEXT);

      table.getTableHeader().setBackground(new Color(240, 246, 255)
              );

      table.getTableHeader().setPreferredSize(new Dimension(0, 34)
              );

      JScrollPane scroll =
              new JScrollPane(table);

      scroll.setBorder(
              BorderFactory.createLineBorder(
                      new Color(
                              229,
                              235,
                              245
                      )
              )
      );

      panel.add(
              scroll,
              BorderLayout.CENTER
      );

      JButton view =
              new JButton(
                      "View All Complaints  →"
              );

      view.setForeground(
              BLUE
      );

      view.setBackground(
              Color.WHITE
      );

      view.setBorderPainted(false);

      view.setFocusPainted(false);

      view.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      12
              )
      );

      view.setHorizontalAlignment(
              SwingConstants.LEFT
      );

      view.setCursor(
              new Cursor(
                      Cursor.HAND_CURSOR
              )
      );

      view.addActionListener(
              e -> openMyComplaints()
      );

      panel.add(
              view,
              BorderLayout.SOUTH
      );

      return panel;
   }

   // =========================================================
   // RIGHT COLUMN
   // =========================================================

   private JPanel createRightColumn() {

      JPanel right =
              new JPanel();

      right.setOpaque(false);

      right.setLayout(
              new BoxLayout(
                      right,
                      BoxLayout.Y_AXIS
              )
      );

      right.add(
              createQuickActions()
      );

      right.add(
              Box.createVerticalStrut(15)
      );

      right.add(
              createFeedbackCard()
      );

      return right;
   }

   // =========================================================
   // QUICK ACTIONS
   // =========================================================

   private JPanel createQuickActions() {

      RoundedPanel panel =
              new RoundedPanel(
                      Color.WHITE,
                      18
              );

      panel.setBorder(
              new EmptyBorder(
                      15,
                      18,
                      15,
                      18
              )
      );

      panel.setLayout(
              new BorderLayout(
                      0,
                      8
              )
      );

      JLabel title =
              new JLabel(
                      "⚡  Quick Actions"
              );

      title.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      16
              )
      );

      title.setForeground(
              TEXT
      );

      panel.add(
              title,
              BorderLayout.NORTH
      );

      JPanel actions =
              new JPanel();

      actions.setOpaque(false);

      actions.setLayout(
              new BoxLayout(
                      actions,
                      BoxLayout.Y_AXIS
              )
      );

      actions.add(
              createAction(
                      "▣",
                      "My Complaints",
                      BLUE,
                      e -> openMyComplaints()
              )
      );

      actions.add(
              createAction(
                      "✎",
                      "New Complaint",
                      GREEN,
                      e -> openNewComplaint()
              )
      );

      actions.add(
              createAction(
                      "⚑",
                      "Announcements",
                      ORANGE,
                      e -> openAnnouncements()
              )
      );

      actions.add(
              createAction(
                      "🔔",
                      "Notifications",
                      BLUE,
                      e -> openNotifications())
      );

      actions.add(
              createAction(
                      "★",
                      "Feedback",
                      new Color(225, 65, 125),
                      e -> openFeedback()
              )
              );

      actions.add(
              createAction(
                      "⇥",
                      "Logout",
                      RED,
                      e -> logout()
              )
              );

      panel.add(
              actions,
              BorderLayout.CENTER
      );

      return panel;
   }

   private JPanel createAction(
           String icon,
           String text,
           Color color,
           ActionListener listener
   ) {

      JPanel row =
              new JPanel(
                      new BorderLayout()
              );

      row.setOpaque(false);

      row.setPreferredSize(
              new Dimension(
                      0,
                      48
              )
      );

      row.setMaximumSize(
              new Dimension(
                      Integer.MAX_VALUE,
                      48
              )
      );

      JLabel iconLabel =
              new JLabel(icon);

      iconLabel.setFont(
              new Font(
                      "Segoe UI Symbol",
                      Font.BOLD,
                      20
              )
      );

      iconLabel.setForeground(
              color
      );

      iconLabel.setPreferredSize(
              new Dimension(
                      42,
                      40
              )
      );

      JLabel label =
              new JLabel(text);

      label.setFont(
              new Font(
                      "Arial",
                      Font.PLAIN,
                      12
              )
      );

      label.setForeground(
              MUTED
      );

      JButton arrow =
              new JButton("›");

      arrow.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      21
              )
      );

      arrow.setForeground(
              BLUE
      );

      arrow.setBackground(
              Color.WHITE
      );

      arrow.setBorderPainted(false);

      arrow.setFocusPainted(false);

      arrow.addActionListener(
              listener
      );

      row.add(
              iconLabel,
              BorderLayout.WEST
      );

      row.add(
              label,
              BorderLayout.CENTER
      );

      row.add(
              arrow,
              BorderLayout.EAST
      );

      row.setCursor(
              new Cursor(
                      Cursor.HAND_CURSOR
              )
      );

      return row;
   }



   private JPanel createFeedbackCard() {

      JPanel panel = new GradientPanel(new Color(86, 58, 230), new Color(139, 68, 236));
      panel.setBorder(new EmptyBorder(18, 20, 18, 20));
      panel.setLayout(new BorderLayout(10, 0));

      JPanel text = new JPanel();
      text.setOpaque(false);
      text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));


      JLabel title =
              new JLabel(
                      "We Value Your Feedback!"
              );

      title.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      17
              )
      );

      title.setForeground(
              Color.WHITE
      );

      JLabel message =
              new JLabel(
                      "<html>Help us improve<br>" +
                              "our services by<br>" +
                              "sharing your thoughts.</html>"
              );

      message.setFont(
              new Font(
                      "Arial",
                      Font.PLAIN,
                      11
              )
      );

      message.setForeground(
              Color.WHITE
      );

      JButton button =
              new JButton(
                      "Give Feedback  →"
              );

      button.setForeground(
              PURPLE
      );

      button.setBackground(
              Color.WHITE
      );

      button.setBorderPainted(false);

      button.setFocusPainted(false);

      button.setCursor(
              new Cursor(
                      Cursor.HAND_CURSOR
              )
      );

      button.addActionListener(
              e -> openFeedback()
      );

      text.add(title);

      text.add(
              Box.createVerticalStrut(8)
      );

      text.add(message);

      text.add(
              Box.createVerticalStrut(12)
      );

      text.add(button);

      JLabel star =
              new JLabel("★");

      star.setFont(
              new Font(
                      "Arial",
                      Font.BOLD,
                      60
              )
      );

      star.setForeground(
              new Color(
                      255,
                      210,
                      55
              )
      );

      panel.add(
              text,
              BorderLayout.CENTER
      );

      panel.add(
              star,
              BorderLayout.EAST
      );

      panel.setMaximumSize(
              new Dimension(
                      Integer.MAX_VALUE,
                      150
              )
      );

      return panel;
   }


   private String getInitial() {

      if (studentName == null ||
              studentName.trim().isEmpty()) {

         return "?";
      }

      return studentName
              .trim()
              .substring(0, 1)
              .toUpperCase();
   }

   private void openMyComplaints() {

      try {

         dispose();

         new mycomplaints(
                 studentName,
                 studentUsername,
                 roomNumber
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
                 studentUsername,
                 roomNumber
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

   private void openAnnouncements() {

//      JOptionPane.showMessageDialog(
//              this,
//              "Announcements page.",
//              "Announcements",
//              JOptionPane.INFORMATION_MESSAGE
//      );
   }

   private void openNotifications() {

//      JOptionPane.showMessageDialog(
//              this,
//              "You have 2 new notifications.",
//              "Notifications",
//              JOptionPane.INFORMATION_MESSAGE
//      );
   }

   private void openFeedback() {

      dispose();
      new feedback(studentName,studentUsername);
   }

   private void logout() {

      int choice =
              JOptionPane.showConfirmDialog(
                      this,
                      "Are you sure you want to logout?",
                      "Logout",
                      JOptionPane.YES_NO_OPTION
              );

      if (choice ==
              JOptionPane.YES_OPTION) {

         dispose();

         new login("", "");
      }
   }


   @Override
   public void actionPerformed(
           ActionEvent e
   ) {

      Object source =
              e.getSource();

      if (source ==
              dashboardButton) {

         return;

      } else if (source ==
              complaintsButton) {

         openMyComplaints();

      } else if (source ==
              newComplaintButton) {

         openNewComplaint();

      } else if (source ==
              announcementsButton) {

//         openAnnouncements();

      } else if (source ==
              notificationsButton) {

         openNotifications();

      } else if (source ==
              feedbackButton) {

         openFeedback();

      } else if (source ==
              logoutButton) {

         logout();
      }
   }

   // =========================================================
   // CUSTOM ROUNDED PANEL
   // =========================================================

   static class RoundedPanel
           extends JPanel {

      private final Color color;
      private final int radius;

      public RoundedPanel(
              Color color,
              int radius
      ) {

         this.color = color;
         this.radius = radius;

         setOpaque(false);
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

         g2.setColor(color);

         g2.fillRoundRect(
                 0,
                 0,
                 getWidth(),
                 getHeight(),
                 radius,
                 radius
         );

         g2.dispose();

         super.paintComponent(g);
      }
   }

   // =========================================================
   // GRADIENT PANEL
   // =========================================================

   static class GradientPanel
           extends JPanel {

      private final Color color1;
      private final Color color2;

      public GradientPanel(
              Color color1,
              Color color2
      ) {

         this.color1 = color1;
         this.color2 = color2;

         setOpaque(false);
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

         GradientPaint gradient =
                 new GradientPaint(
                         0,
                         0,
                         color1,
                         getWidth(),
                         getHeight(),
                         color2
                 );

         g2.setPaint(gradient);

         g2.fillRoundRect(
                 0,
                 0,
                 getWidth(),
                 getHeight(),
                 18,
                 18
         );

         g2.dispose();

         super.paintComponent(g);
      }
   }

   // =========================================================
   // MAIN
   // =========================================================

   public static void main(
           String[] args
   ) {

      SwingUtilities.invokeLater(
              () -> new dashboard(
                      "",
                      "",
                      ""
              )
      );
   }
}