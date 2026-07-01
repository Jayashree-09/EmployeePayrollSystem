package dao;

import db.DBConnection;
import model.Attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AttendanceDAO {

    public boolean saveAttendance(Attendance attendance) {

        try {

            Connection con = DBConnection.getConnection();

            String query =
                "INSERT INTO employee_attendance(emp_id, attendance_date, status) VALUES(?,?,?)";

            PreparedStatement pst =
                con.prepareStatement(query);

            pst.setInt(1, attendance.getEmpId());
            pst.setString(2, attendance.getAttendanceDate());
            pst.setString(3, attendance.getStatus());

            int rows = pst.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
    public ResultSet searchAttendance(int empId) {

    try {

        Connection con =
                DBConnection.getConnection();

        String query =
                "SELECT * FROM employee_attendance WHERE emp_id=?";

        PreparedStatement pst =
                con.prepareStatement(query);

        pst.setInt(1, empId);

        return pst.executeQuery();

    } catch (Exception e) {

        e.printStackTrace();

    }

    return null;
}
}