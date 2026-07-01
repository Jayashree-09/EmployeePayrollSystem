package ui;

import dao.SalaryDAO;
import model.Salary;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SalaryForm extends JFrame {

    JTextField txtEmpId;
    JTextField txtBasicSalary;
    JTextField txtHra;
    JTextField txtDa;
    JTextField txtBonus;
    JTextField txtPf;
    JTextField txtTax;

    JButton btnCalculate;
    JButton btnSave;
    JButton btnSlip;
    JButton btnClear;

    double netSalary = 0;

    // ── Design Tokens (same palette as EmployeeForm) ───────────────
    private static final Color BG_DARK      = new Color(18, 24, 38);
    private static final Color PANEL_BG     = new Color(26, 34, 52);
    private static final Color ACCENT       = new Color(79, 140, 255);
    private static final Color SUCCESS      = new Color(50, 190, 130);
    private static final Color WARN         = new Color(240, 165, 50);
    private static final Color NEUTRAL      = new Color(100, 115, 145);
    private static final Color TEXT_PRIMARY = new Color(230, 235, 248);
    private static final Color TEXT_MUTED   = new Color(130, 145, 175);
    private static final Color BORDER_COLOR = new Color(45, 58, 85);
    private static final Color FIELD_BG     = new Color(32, 42, 64);
    private static final Color EARNINGS_BG  = new Color(22, 42, 34);   // subtle green tint
    private static final Color DEDUCT_BG    = new Color(42, 22, 26);   // subtle red tint

    private static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD, 17);
    private static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD, 11);
    private static final Font FONT_LABEL   = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_FIELD   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BUTTON  = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_SUB     = new Font("Segoe UI", Font.PLAIN, 11);

    public SalaryForm() {

        setTitle("Salary Management");
        setSize(700, 590);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_DARK);

        // ── Header ────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PANEL_BG);
        header.setBorder(new EmptyBorder(18, 28, 18, 28));

        JLabel lblTitle = new JLabel("Salary Management");
        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(TEXT_PRIMARY);

        JLabel lblSub = new JLabel("Calculate, save & generate payroll slips  ·  HR Portal");
        lblSub.setFont(FONT_SUB);
        lblSub.setForeground(TEXT_MUTED);

        JPanel titleStack = new JPanel();
        titleStack.setLayout(new BoxLayout(titleStack, BoxLayout.Y_AXIS));
        titleStack.setOpaque(false);
        titleStack.add(lblTitle);
        titleStack.add(Box.createVerticalStrut(3));
        titleStack.add(lblSub);

        JPanel accentBar = new JPanel();
        accentBar.setPreferredSize(new Dimension(4, 0));
        accentBar.setBackground(SUCCESS);   // green accent for salary
        header.add(accentBar, BorderLayout.WEST);
        header.add(titleStack, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // ── Main card ─────────────────────────────────────────────
        JPanel card = new JPanel(null);
        card.setBackground(PANEL_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(20, 24, 20, 24),
                new LineBorder(BORDER_COLOR, 1, true)
        ));

        // layout constants
        int lx = 30, rx = 360, fw = 250, startY = 20, gap = 44;

        // ── Row 0: Employee ID (full width) ───────────────────────
        addLabel(card, "Employee ID", lx, startY);
        txtEmpId = addField(card, lx, startY + 20, fw * 2 + (rx - lx - fw));

        // ── Section: Earnings ─────────────────────────────────────
        int secY = startY + gap + 10;
        addSectionBadge(card, "EARNINGS", lx, secY, EARNINGS_BG, new Color(50, 190, 130));

        addLabel(card, "Basic Salary  (₹)", lx, secY + 22);
        addLabel(card, "HRA  (₹)",           rx, secY + 22);
        txtBasicSalary = addField(card, lx, secY + 40, fw);
        txtHra         = addField(card, rx, secY + 40, fw);

        addLabel(card, "DA  (₹)",    lx, secY + 82);
        addLabel(card, "Bonus  (₹)", rx, secY + 82);
        txtDa    = addField(card, lx, secY + 100, fw);
        txtBonus = addField(card, rx, secY + 100, fw);

        // ── Section: Deductions ───────────────────────────────────
        int sec2Y = secY + 148;
        addSectionBadge(card, "DEDUCTIONS", lx, sec2Y, DEDUCT_BG, new Color(220, 70, 80));

        addLabel(card, "PF  (₹)",  lx, sec2Y + 22);
        addLabel(card, "Tax  (₹)", rx, sec2Y + 22);
        txtPf  = addField(card, lx, sec2Y + 40, fw);
        txtTax = addField(card, rx, sec2Y + 40, fw);

        // ── Divider ───────────────────────────────────────────────
        int divY = sec2Y + 82;
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_COLOR);
        sep.setBounds(lx, divY, fw * 2 + (rx - lx - fw), 1);
        card.add(sep);

        // ── Buttons ───────────────────────────────────────────────
        int btnY = divY + 12;
        int btnW = 130, btnH = 36, btnGap = 12;

        btnCalculate = makeButton("⊞  Calculate", ACCENT,   btnW,       btnH);
        btnSave      = makeButton("✔  Save",       SUCCESS,  btnW,       btnH);
        btnSlip      = makeButton("⎙  Slip",       WARN,     btnW,       btnH);
        btnClear     = makeButton("↺  Clear",       NEUTRAL,  90,         btnH);

        int bx = lx;
        btnCalculate.setBounds(bx, btnY, btnW, btnH); bx += btnW + btnGap;
        btnSave.setBounds(bx,      btnY, btnW, btnH); bx += btnW + btnGap;
        btnSlip.setBounds(bx,      btnY, btnW, btnH); bx += btnW + btnGap;
        btnClear.setBounds(bx,     btnY, 90,   btnH);

        card.add(btnCalculate);
        card.add(btnSave);
        card.add(btnSlip);
        card.add(btnClear);

        JLabel hint = new JLabel("Calculate before saving  ·  Enter Employee ID to generate slip");
        hint.setFont(FONT_SUB);
        hint.setForeground(TEXT_MUTED);
        hint.setBounds(lx, btnY + btnH + 10, 520, 18);
        card.add(hint);

        card.setPreferredSize(new Dimension(652, btnY + btnH + 40));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG_DARK);
        wrapper.setBorder(new EmptyBorder(16, 16, 16, 16));
        wrapper.add(card, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);

        // ══════════════════════════════════════════════════════════
        //   ALL ACTION LISTENERS — unchanged from original
        // ══════════════════════════════════════════════════════════

        btnCalculate.addActionListener(e -> {
            try {
                double basic = Double.parseDouble(txtBasicSalary.getText());
                double hra   = Double.parseDouble(txtHra.getText());
                double da    = Double.parseDouble(txtDa.getText());
                double bonus = Double.parseDouble(txtBonus.getText());
                double pf    = Double.parseDouble(txtPf.getText());
                double tax   = Double.parseDouble(txtTax.getText());

                netSalary = basic + hra + da + bonus - pf - tax;

                JOptionPane.showMessageDialog(this, "Net Salary : ₹ " + netSalary);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values.");
            }
        });

        btnSave.addActionListener(e -> {
            try {
                Salary salary = new Salary();
                salary.setEmpId(Integer.parseInt(txtEmpId.getText()));
                salary.setBasicSalary(Double.parseDouble(txtBasicSalary.getText()));
                salary.setHra(Double.parseDouble(txtHra.getText()));
                salary.setDa(Double.parseDouble(txtDa.getText()));
                salary.setBonus(Double.parseDouble(txtBonus.getText()));
                salary.setPf(Double.parseDouble(txtPf.getText()));
                salary.setTax(Double.parseDouble(txtTax.getText()));
                salary.setNetSalary(netSalary);

                SalaryDAO dao = new SalaryDAO();
                if (dao.saveSalary(salary)) {
                    JOptionPane.showMessageDialog(this, "Payroll Saved Successfully");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to Save Payroll");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please calculate salary first.");
            }
        });

        btnSlip.addActionListener(e -> {
            try {
                int empId = Integer.parseInt(txtEmpId.getText());
                SalaryDAO dao = new SalaryDAO();
                java.sql.ResultSet rs = dao.getSalarySlip(empId);

                if (rs != null && rs.next()) {
                    String slip =
                            "=========================================\n"
                          + "     EMPLOYEE PAYROLL MANAGEMENT\n"
                          + "=========================================\n\n"
                          + "            SALARY SLIP\n\n"
                          + "Employee ID   : " + rs.getInt("emp_id")
                          + "\nEmployee Name : " + rs.getString("emp_name")
                          + "\nDepartment    : " + rs.getString("department")
                          + "\nDesignation   : " + rs.getString("designation")
                          + "\n\n-----------------------------------------"
                          + "\nBasic Salary  : ₹" + rs.getDouble("basic_salary")
                          + "\nHRA           : ₹" + rs.getDouble("hra")
                          + "\nDA            : ₹" + rs.getDouble("da")
                          + "\nBonus         : ₹" + rs.getDouble("bonus")
                          + "\nPF            : ₹" + rs.getDouble("pf")
                          + "\nTax           : ₹" + rs.getDouble("tax")
                          + "\n-----------------------------------------"
                          + "\nNet Salary    : ₹" + rs.getDouble("net_salary")
                          + "\n-----------------------------------------"
                          + "\n\nGenerated On : " + java.time.LocalDate.now()
                          + "\n\nEmployee Signature      Authorized Signature";

                    JTextArea area = new JTextArea(slip);
                    area.setEditable(false);
                    area.setFont(new Font("Monospaced", Font.PLAIN, 14));

                    JScrollPane scroll = new JScrollPane(area);
                    scroll.setPreferredSize(new java.awt.Dimension(520, 500));

                    JOptionPane.showMessageDialog(this, scroll, "Salary Slip", JOptionPane.INFORMATION_MESSAGE);

                    int option = JOptionPane.showConfirmDialog(
                            this, "Do you want to print the Salary Slip?",
                            "Print", JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.YES_OPTION) {
                        area.print();
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Salary record not found.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid Employee ID.");
            }
        });

        btnClear.addActionListener(e -> {
            txtEmpId.setText("");
            txtBasicSalary.setText("");
            txtHra.setText("");
            txtDa.setText("");
            txtBonus.setText("");
            txtPf.setText("");
            txtTax.setText("");
            netSalary = 0;
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ── UI helper methods ──────────────────────────────────────────

    private void addLabel(JPanel p, String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_MUTED);
        lbl.setBounds(x, y, 220, 18);
        p.add(lbl);
    }

    private JTextField addField(JPanel p, int x, int y, int w) {
        JTextField f = new JTextField();
        f.setFont(FONT_FIELD);
        f.setForeground(TEXT_PRIMARY);
        f.setBackground(FIELD_BG);
        f.setCaretColor(ACCENT);
        f.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(3, 8, 3, 8)
        ));
        f.setBounds(x, y, w, 30);
        p.add(f);
        return f;
    }

    private void addSectionBadge(JPanel p, String label, int x, int y, Color bg, Color fg) {
        JLabel badge = new JLabel("  " + label + "  ");
        badge.setFont(FONT_SECTION);
        badge.setForeground(fg);
        badge.setBackground(bg);
        badge.setOpaque(true);
        badge.setBorder(new EmptyBorder(2, 6, 2, 6));
        badge.setBounds(x, y, 110, 18);
        p.add(badge);
    }

    private JButton makeButton(String label, Color bg, int w, int h) {
        JButton btn = new JButton(label) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? bg.darker()
                        : getModel().isRollover() ? bg.brighter() : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BUTTON);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setSize(w, h);
        return btn;
    }
}