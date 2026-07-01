package ui;

import dao.EmployeeDAO;
import model.Employee;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class EmployeeForm extends JFrame {
    JTextField txtName, txtPhone, txtEmail, txtDepartment, txtDesignation;
    JComboBox<String> cmbGender;
    JButton btnAdd;
    JButton btnView;
    JTextField txtEmpId;
    JButton btnDelete;
    JButton btnUpdate;
    JButton btnSearch;
    JButton btnClear;

    // ── Design tokens ──────────────────────────────────────────────
    private static final Color BG_MAIN      = new Color(0xF0F4F8);
    private static final Color BG_CARD      = Color.WHITE;
    private static final Color ACCENT       = new Color(0x2563EB);  // indigo-600
    private static final Color ACCENT_DARK  = new Color(0x1D4ED8);  // indigo-700
    private static final Color DANGER       = new Color(0xDC2626);  // red-600
    private static final Color NEUTRAL      = new Color(0x64748B);  // slate-500
    private static final Color LABEL_COLOR  = new Color(0x374151);  // gray-700
    private static final Color BORDER_COLOR = new Color(0xCBD5E1);  // slate-300
    private static final Color HEADER_BG    = new Color(0x1E3A5F);  // deep navy
    private static final Color HEADER_TEXT  = Color.WHITE;

    public EmployeeForm() {

        setTitle("Employee Payroll Management System");
        setSize(700, 620);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BG_MAIN);

        // ── Header banner ──────────────────────────────────────────
        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 700, 70);
        header.setBackground(HEADER_BG);
        add(header);

        JLabel lblTitle = new JLabel("Employee Payroll Management");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(HEADER_TEXT);
        lblTitle.setBounds(30, 12, 400, 28);
        header.add(lblTitle);

        JLabel lblSub = new JLabel("Manage your workforce with ease");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(0xBFDBFE));  // indigo-200
        lblSub.setBounds(30, 40, 300, 18);
        header.add(lblSub);

        // ── Card panel ─────────────────────────────────────────────
        JPanel card = new JPanel(null);
        card.setBounds(30, 90, 635, 490);
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1, true));
        add(card);

        // Section label
        JLabel secInfo = new JLabel("EMPLOYEE INFORMATION");
        secInfo.setFont(new Font("Segoe UI", Font.BOLD, 10));
        secInfo.setForeground(NEUTRAL);
        secInfo.setBounds(25, 18, 220, 16);
        card.add(secInfo);

        JSeparator sep1 = new JSeparator();
        sep1.setBounds(25, 36, 585, 1);
        sep1.setForeground(BORDER_COLOR);
        card.add(sep1);

        // ── Field rows (left column) ────────────────────────────────
        int lx = 25, rx = 340, fw = 255, fh = 32, ly = 52, gap = 48;

        card.add(makeLabel("Employee Name", lx, ly));
        txtName = makeField();
        txtName.setBounds(lx, ly + 20, fw, fh);
        card.add(txtName);

        card.add(makeLabel("Gender", rx, ly));
        cmbGender = new JComboBox<>(new String[]{"Male", "Female"});
        styleCombo(cmbGender);
        cmbGender.setBounds(rx, ly + 20, fw, fh);
        card.add(cmbGender);

        ly += gap + 10;
        card.add(makeLabel("Phone", lx, ly));
        txtPhone = makeField();
        txtPhone.setBounds(lx, ly + 20, fw, fh);
        card.add(txtPhone);

        card.add(makeLabel("Email", rx, ly));
        txtEmail = makeField();
        txtEmail.setBounds(rx, ly + 20, fw, fh);
        card.add(txtEmail);

        ly += gap + 10;
        card.add(makeLabel("Department", lx, ly));
        txtDepartment = makeField();
        txtDepartment.setBounds(lx, ly + 20, fw, fh);
        card.add(txtDepartment);

        card.add(makeLabel("Designation", rx, ly));
        txtDesignation = makeField();
        txtDesignation.setBounds(rx, ly + 20, fw, fh);
        card.add(txtDesignation);

        // ── Employee ID row ────────────────────────────────────────
        ly += gap + 10;
        JSeparator sep2 = new JSeparator();
        sep2.setBounds(25, ly - 6, 585, 1);
        sep2.setForeground(BORDER_COLOR);
        card.add(sep2);

        JLabel secId = new JLabel("EMPLOYEE ID  (required for Search / Update / Delete)");
        secId.setFont(new Font("Segoe UI", Font.BOLD, 10));
        secId.setForeground(NEUTRAL);
        secId.setBounds(25, ly, 420, 16);
        card.add(secId);

        ly += 20;
        txtEmpId = makeField();
        txtEmpId.setBounds(25, ly, 200, fh);
        card.add(txtEmpId);

        // ── Action buttons ──────────────────────────────────────────
        ly += gap + 8;
        JSeparator sep3 = new JSeparator();
        sep3.setBounds(25, ly - 8, 585, 1);
        sep3.setForeground(BORDER_COLOR);
        card.add(sep3);

        JLabel secAct = new JLabel("ACTIONS");
        secAct.setFont(new Font("Segoe UI", Font.BOLD, 10));
        secAct.setForeground(NEUTRAL);
        secAct.setBounds(25, ly, 100, 16);
        card.add(secAct);

        ly += 20;
        int bw = 130, bh = 36, bx = 25, bGap = 14;

        btnAdd    = makePrimaryBtn("＋  Add");
        btnAdd.setBounds(bx, ly, bw, bh);
        card.add(btnAdd);

        btnView   = makeOutlineBtn("View All");
        btnView.setBounds(bx + bw + bGap, ly, bw, bh);
        card.add(btnView);

        btnSearch = makeOutlineBtn("Search");
        btnSearch.setBounds(bx + (bw + bGap) * 2, ly, bw, bh);
        card.add(btnSearch);

        btnUpdate = makeOutlineBtn("Update");
        btnUpdate.setBounds(bx + (bw + bGap) * 3, ly, bw, bh);
        card.add(btnUpdate);

        ly += bh + 12;
        btnDelete = makeDangerBtn("Delete");
        btnDelete.setBounds(bx, ly, bw, bh);
        card.add(btnDelete);

        btnClear  = makeOutlineBtn("Clear");
        btnClear.setBounds(bx + bw + bGap, ly, bw, bh);
        card.add(btnClear);

        // ══════════════════════════════════════════════════════════════
        //  ALL ORIGINAL LOGIC BELOW — UNTOUCHED
        // ══════════════════════════════════════════════════════════════

        // Add Employee Button
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Validation
                if (txtName.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter Employee Name");
                    return;
                }
                if (txtPhone.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter Phone Number");
                    return;
                }
                if (txtPhone.getText().length() != 10) {
                    JOptionPane.showMessageDialog(null, "Phone Number Must Be 10 Digits");
                    return;
                }
                if (txtEmail.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter Email");
                    return;
                }
                if (!txtEmail.getText().contains("@")) {
                    JOptionPane.showMessageDialog(null, "Invalid Email Address");
                    return;
                }
                if (txtDepartment.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter Department");
                    return;
                }
                if (txtDesignation.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter Designation");
                    return;
                }

                Employee emp = new Employee();
                emp.setEmpName(txtName.getText());
                emp.setGender(cmbGender.getSelectedItem().toString());
                emp.setPhone(txtPhone.getText());
                emp.setEmail(txtEmail.getText());
                emp.setDepartment(txtDepartment.getText());
                emp.setDesignation(txtDesignation.getText());

                EmployeeDAO dao = new EmployeeDAO();

                if (dao.addEmployee(emp)) {
                    JOptionPane.showMessageDialog(null, "Employee Added Successfully");
                    txtName.setText("");
                    txtPhone.setText("");
                    txtEmail.setText("");
                    txtDepartment.setText("");
                    txtDesignation.setText("");
                    txtEmpId.setText("");
                    cmbGender.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to Add Employee");
                }
            }
        });

        // View Employees Button
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewEmployeeFrame();
            }
        });

        // Delete Employee Button
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int empId = Integer.parseInt(txtEmpId.getText());
                    EmployeeDAO dao = new EmployeeDAO();
                    if (dao.deleteEmployee(empId)) {
                        JOptionPane.showMessageDialog(null, "Employee Deleted Successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Employee Not Found");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Enter Valid Employee ID");
                }
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int empId = Integer.parseInt(txtEmpId.getText());
                    Employee emp = new Employee();
                    emp.setEmpName(txtName.getText());
                    emp.setGender(cmbGender.getSelectedItem().toString());
                    emp.setPhone(txtPhone.getText());
                    emp.setEmail(txtEmail.getText());
                    emp.setDepartment(txtDepartment.getText());
                    emp.setDesignation(txtDesignation.getText());
                    EmployeeDAO dao = new EmployeeDAO();
                    if (dao.updateEmployee(empId, emp)) {
                        JOptionPane.showMessageDialog(null, "Employee Updated Successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Employee Not Found");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Enter Valid Employee ID");
                }
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int empId = Integer.parseInt(txtEmpId.getText());
                    EmployeeDAO dao = new EmployeeDAO();
                    ResultSet rs = dao.searchEmployeeById(empId);
                    if (rs != null && rs.next()) {
                        txtName.setText(rs.getString("emp_name"));
                        cmbGender.setSelectedItem(rs.getString("gender"));
                        txtPhone.setText(rs.getString("phone"));
                        txtEmail.setText(rs.getString("email"));
                        txtDepartment.setText(rs.getString("department"));
                        txtDesignation.setText(rs.getString("designation"));
                        JOptionPane.showMessageDialog(null, "Employee Found");
                    } else {
                        JOptionPane.showMessageDialog(null, "Employee Not Found");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Enter Valid Employee ID");
                }
            }
        });

        btnClear.addActionListener(e -> {
            txtName.setText("");
            txtPhone.setText("");
            txtEmail.setText("");
            txtDepartment.setText("");
            txtDesignation.setText("");
            txtEmpId.setText("");
            cmbGender.setSelectedIndex(0);
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ── UI helper methods ──────────────────────────────────────────

    private JLabel makeLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(LABEL_COLOR);
        lbl.setBounds(x, y, 240, 18);
        return lbl;
    }

    private JTextField makeField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setForeground(new Color(0x111827));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(4, 10, 4, 10)));
        tf.setBackground(Color.WHITE);
        return tf;
    }

    private void styleCombo(JComboBox<String> cb) {
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cb.setBackground(Color.WHITE);
        cb.setForeground(new Color(0x111827));
        cb.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1, true));
    }

    private JButton makePrimaryBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(ACCENT_DARK); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(ACCENT); }
        });
        return btn;
    }

    private JButton makeOutlineBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBackground(Color.WHITE);
        btn.setForeground(ACCENT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(ACCENT, 1, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(new Color(0xEFF6FF)); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(Color.WHITE); }
        });
        return btn;
    }

    private JButton makeDangerBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setBackground(Color.WHITE);
        btn.setForeground(DANGER);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(DANGER, 1, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(new Color(0xFEF2F2)); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(Color.WHITE); }
        });
        return btn;
    }
}