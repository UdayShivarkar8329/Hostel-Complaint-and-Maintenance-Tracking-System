package hostel.complaint.and.maintenance.tracking.system;

import java.sql.Connection;
import java.sql.DriverManager;

public class Con {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Hostel_Complaint_system",
                    "root",
                    "Uday@8888"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

