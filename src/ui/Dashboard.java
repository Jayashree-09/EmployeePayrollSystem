// package ui;

// import dao.EmployeeDAO;

// import javax.swing.*;


// public class Dashboard extends JFrame {

//     public Dashboard() {

//         setTitle("Employee Payroll Management System");
//         setSize(500, 650);
//         setLayout(null);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         JLabel title =
//                 new JLabel("Employee Payroll Management System");

//         title.setBounds(100, 30, 300, 30);
//         add(title);

//         JButton btnEmployee =
//                 new JButton("Employee Management");

//         btnEmployee.setBounds(130, 90, 220, 40);
//         add(btnEmployee);

//         JButton btnSalary =
//                 new JButton("Salary Management");

//         btnSalary.setBounds(130, 150, 220, 40);
//         add(btnSalary);

//         JButton btnAttendance =
//                 new JButton("Attendance Management");

//         btnAttendance.setBounds(130, 210, 220, 40);
//         add(btnAttendance);

//         JButton btnDatabase =
//                 new JButton("Database Tables");

//         btnDatabase.setBounds(130, 270, 220, 40);
//         add(btnDatabase);

//         JButton btnCount =
//                 new JButton("Total Employees");

//         btnCount.setBounds(130, 330, 220, 40);
//         add(btnCount);

//         JButton btnStats =
//         new JButton("Employee Statistics");

// btnStats.setBounds(130, 390, 220, 40);
// add(btnStats);

//         JButton btnAbout =
//                 new JButton("About Project");

//         btnAbout.setBounds(130, 450, 220, 40);
//         add(btnAbout);

//         JButton btnExit =
//                 new JButton("Exit");

//         btnExit.setBounds(130, 510, 220, 40);
//         add(btnExit);

//         btnEmployee.addActionListener(e ->
//                 new EmployeeForm());

//         btnSalary.addActionListener(e ->
//                 new SalaryForm());

//         btnAttendance.addActionListener(e ->
//                 new AttendanceForm());

//         btnDatabase.addActionListener(e ->
//                 new DatabaseViewer());

//         btnCount.addActionListener(e -> {

//             EmployeeDAO dao =
//                     new EmployeeDAO();

//             int count =
//                     dao.getEmployeeCount();

//             JOptionPane.showMessageDialog(
//                     null,
//                     "Total Employees : " + count);

//         });
         
//         btnStats.addActionListener(e -> {

//     EmployeeDAO dao =
//             new EmployeeDAO();

//     String stats =
//             dao.getDepartmentStatistics();

//     JOptionPane.showMessageDialog(
//             null,
//             "Department Statistics\n\n" + stats
//     );

// });
//         btnAbout.addActionListener(e ->
//                 JOptionPane.showMessageDialog(
//                         null,
//                         "Employee Payroll Management System\n\n" +
//                                 "Technologies Used:\n" +
//                                 "- Java Swing\n" +
//                                 "- JDBC\n" +
//                                 "- MySQL\n\n" +
//                                 "Modules:\n" +
//                                 "- Employee Management\n" +
//                                 "- Payroll Management\n" +
//                                 "- Attendance Management"
//                 ));

//         btnExit.addActionListener(e ->
//                 System.exit(0));

//         setLocationRelativeTo(null);
//         setVisible(true);
//     }
// }



package ui;

import dao.EmployeeDAO;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {

        setTitle("Employee Payroll Management System");
        setSize(750, 700);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color background = new Color(240,248,255);
        Color buttonColor = new Color(52,152,219);
        Color textColor = Color.WHITE;

        getContentPane().setBackground(background);

        //---------------- Title ----------------//

        JLabel title = new JLabel("EMPLOYEE PAYROLL MANAGEMENT SYSTEM");

        title.setBounds(70,25,620,40);

        title.setFont(new Font("Segoe UI",Font.BOLD,28));

        title.setForeground(new Color(33,37,41));

        add(title);

        JLabel subtitle = new JLabel("Payroll | Attendance | Database | Statistics");

        subtitle.setBounds(170,65,400,25);

        subtitle.setFont(new Font("Segoe UI",Font.PLAIN,15));

        subtitle.setForeground(Color.GRAY);

        add(subtitle);

        //---------------- Buttons ----------------//

        JButton btnEmployee = createButton("👤 Employee Management",buttonColor,textColor);
        btnEmployee.setBounds(220,120,300,45);
        add(btnEmployee);

        JButton btnSalary = createButton("💰 Salary Management",buttonColor,textColor);
        btnSalary.setBounds(220,180,300,45);
        add(btnSalary);

        JButton btnAttendance = createButton("📅 Attendance Management",buttonColor,textColor);
        btnAttendance.setBounds(220,240,300,45);
        add(btnAttendance);

        JButton btnDatabase = createButton("🗄 Database Tables",buttonColor,textColor);
        btnDatabase.setBounds(220,300,300,45);
        add(btnDatabase);

        JButton btnCount = createButton("👥 Total Employees",buttonColor,textColor);
        btnCount.setBounds(220,360,300,45);
        add(btnCount);

        JButton btnStats = createButton("📊 Employee Statistics",buttonColor,textColor);
        btnStats.setBounds(220,420,300,45);
        add(btnStats);

        JButton btnAbout = createButton("ℹ About Project",buttonColor,textColor);
        btnAbout.setBounds(220,480,300,45);
        add(btnAbout);

        JButton btnExit = createButton("❌ Exit",new Color(220,53,69),Color.WHITE);
        btnExit.setBounds(220,540,300,45);
        add(btnExit);

        //---------------- Footer ----------------//

        JLabel footer = new JLabel("Java Swing • JDBC • MySQL");

        footer.setBounds(250,610,250,20);

        footer.setForeground(Color.GRAY);

        footer.setFont(new Font("Segoe UI",Font.PLAIN,14));

        add(footer);

        //---------------- Button Actions ----------------//

        btnEmployee.addActionListener(e -> new EmployeeForm());

        btnSalary.addActionListener(e -> new SalaryForm());

        btnAttendance.addActionListener(e -> new AttendanceForm());

        btnDatabase.addActionListener(e -> new DatabaseViewer());

        btnCount.addActionListener(e -> {

            EmployeeDAO dao = new EmployeeDAO();

            JOptionPane.showMessageDialog(

                    this,

                    "Total Employees : " + dao.getEmployeeCount(),

                    "Employee Count",

                    JOptionPane.INFORMATION_MESSAGE);

        });

        btnStats.addActionListener(e -> {

            EmployeeDAO dao = new EmployeeDAO();

            JOptionPane.showMessageDialog(

                    this,

                    dao.getDepartmentStatistics(),

                    "Department Statistics",

                    JOptionPane.INFORMATION_MESSAGE);

        });

        btnAbout.addActionListener(e -> {

            JOptionPane.showMessageDialog(

                    this,

                    "EMPLOYEE PAYROLL MANAGEMENT SYSTEM\n\n"

                    + "Version : 1.0\n\n"

                    + "Technologies Used\n"

                    + "• Java Swing\n"

                    + "• JDBC\n"

                    + "• MySQL\n\n"

                    + "Modules\n"

                    + "• Employee Management\n"

                    + "• Salary Management\n"

                    + "• Attendance Management\n"

                    + "• Database Viewer\n\n"

                    + "Features\n"

                    + "• Add Employee\n"

                    + "• Update Employee\n"

                    + "• Delete Employee\n"

                    + "• Search Employee\n"

                    + "• Salary Calculation\n"

                    + "• Attendance Management\n"

                    + "• Department Statistics\n"

                    + "• Database Viewer",

                    "About Project",

                    JOptionPane.INFORMATION_MESSAGE);

        });

        btnExit.addActionListener(e -> {

            int option = JOptionPane.showConfirmDialog(

                    this,

                    "Do you really want to exit?",

                    "Exit",

                    JOptionPane.YES_NO_OPTION);

            if(option==JOptionPane.YES_OPTION)

                System.exit(0);

        });

        setLocationRelativeTo(null);

        setVisible(true);
    }

    //---------------- Button Style ----------------//

    private JButton createButton(String text, Color bg, Color fg){

        JButton button = new JButton(text);

        button.setBackground(bg);

        button.setForeground(fg);

        button.setFont(new Font("Segoe UI",Font.BOLD,15));

        button.setFocusPainted(false);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setBorder(BorderFactory.createEmptyBorder());

        return button;
    }

}