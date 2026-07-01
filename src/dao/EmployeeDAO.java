package dao;

import db.DBConnection;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
// import java.sql.ResultSet;
import java.sql.Statement;

public class EmployeeDAO {

    public boolean addEmployee(Employee emp) {

        try {

            Connection con = DBConnection.getConnection();

            String query =
                    "INSERT INTO payroll_employee(emp_name,gender,phone,email,department,designation) VALUES(?,?,?,?,?,?)";

            PreparedStatement pst =
                    con.prepareStatement(query);

            pst.setString(1, emp.getEmpName());
            pst.setString(2, emp.getGender());
            pst.setString(3, emp.getPhone());
            pst.setString(4, emp.getEmail());
            pst.setString(5, emp.getDepartment());
            pst.setString(6, emp.getDesignation());

            int rows = pst.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }
public ResultSet getAllEmployees() {

        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            return st.executeQuery("SELECT * FROM payroll_employee");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    public boolean deleteEmployee(int empId) {

    try {

        Connection con = DBConnection.getConnection();

        String query =
                "DELETE FROM payroll_employee WHERE emp_id=?";

        PreparedStatement pst =
                con.prepareStatement(query);

        pst.setInt(1, empId);

        int rows = pst.executeUpdate();

        return rows > 0;

    } catch (Exception e) {

        e.printStackTrace();

    }

    return false;
}

public boolean updateEmployee(int empId, Employee emp) {

    try {

        Connection con = DBConnection.getConnection();

        String query =
                "UPDATE payroll_employee SET emp_name=?, gender=?, phone=?, email=?, department=?, designation=? WHERE emp_id=?";

        PreparedStatement pst =
                con.prepareStatement(query);

        pst.setString(1, emp.getEmpName());
        pst.setString(2, emp.getGender());
        pst.setString(3, emp.getPhone());
        pst.setString(4, emp.getEmail());
        pst.setString(5, emp.getDepartment());
        pst.setString(6, emp.getDesignation());
        pst.setInt(7, empId);

        int rows = pst.executeUpdate();

        return rows > 0;

    } catch (Exception e) {

        e.printStackTrace();

    }

    return false;
}
public ResultSet searchEmployeeById(int empId) {

    try {

        Connection con = DBConnection.getConnection();

        String query =
                "SELECT * FROM payroll_employee WHERE emp_id=?";

        PreparedStatement pst =
                con.prepareStatement(query);

        pst.setInt(1, empId);

        return pst.executeQuery();

    } catch (Exception e) {

        e.printStackTrace();

    }

    return null;
}
public int getEmployeeCount() {

    try {

        Connection con = DBConnection.getConnection();

        Statement st = con.createStatement();

        ResultSet rs =
                st.executeQuery(
                        "SELECT COUNT(*) FROM payroll_employee");

        if (rs.next()) {

            return rs.getInt(1);

        }

    } catch (Exception e) {

        e.printStackTrace();

    }

    return 0;
}
public String getDepartmentStatistics() {

    StringBuilder result = new StringBuilder();

    try {

        Connection con = DBConnection.getConnection();

        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery(
                "SELECT department, COUNT(*) total " +
                "FROM payroll_employee " +
                "GROUP BY department");

        while (rs.next()) {

            result.append(
                    rs.getString("department"))
                    .append(" : ")
                    .append(rs.getInt("total"))
                    .append("\n");

        }

    } catch (Exception e) {

        e.printStackTrace();

    }

    return result.toString();
}
}
