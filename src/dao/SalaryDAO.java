package dao;

import db.DBConnection;
import model.Salary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SalaryDAO {

    // Save Payroll
    public boolean saveSalary(Salary salary) {

        try {

            Connection con = DBConnection.getConnection();

            String query =
                    "INSERT INTO employee_payroll(emp_id,basic_salary,hra,da,bonus,pf,tax,net_salary) VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement pst =
                    con.prepareStatement(query);

            pst.setInt(1, salary.getEmpId());
            pst.setDouble(2, salary.getBasicSalary());
            pst.setDouble(3, salary.getHra());
            pst.setDouble(4, salary.getDa());
            pst.setDouble(5, salary.getBonus());
            pst.setDouble(6, salary.getPf());
            pst.setDouble(7, salary.getTax());
            pst.setDouble(8, salary.getNetSalary());

            int rows = pst.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    // ===========================
    // Get Salary Slip
    // ===========================
    public ResultSet getSalarySlip(int empId) {

        try {

            Connection con = DBConnection.getConnection();

            String query =
                    "SELECT p.emp_id, e.emp_name, e.department, e.designation, "
                  + "p.basic_salary, p.hra, p.da, p.bonus, p.pf, p.tax, p.net_salary "
                  + "FROM employee_payroll p "
                  + "JOIN payroll_employee e "
                  + "ON p.emp_id = e.emp_id "
                  + "WHERE p.emp_id = ? "
                  + "ORDER BY p.payroll_id DESC "
                  + "LIMIT 1";

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