package ui;

import dao.AttendanceDAO;
import model.Attendance;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.ResultSet;

public class AttendanceForm extends JFrame {

    JTextField txtEmpId;
    JTextField txtDate;
    JComboBox<String> cmbStatus;

    JButton btnSave;
    JButton btnSearch;
    JButton btnClear;
    JButton btnView;

    // ── Design tokens ──────────────────────────────────────────────────────────
    private static final Color BG_DEEP      = new Color(0x0D1B2A);
    private static final Color BG_CARD      = new Color(0x1B2B3A);
    private static final Color BG_INPUT     = new Color(0x12212F);
    private static final Color ACCENT       = new Color(0x00C9A7);
    private static final Color ACCENT_HOVER = new Color(0x00A688);
    private static final Color BTN_DANGER   = new Color(0x1E3A52);
    private static final Color TEXT_PRIMARY = new Color(0xE8F0F7);
    private static final Color TEXT_MUTED   = new Color(0x7A9BB5);
    private static final Color BORDER       = new Color(0x1E3A52);
    private static final Color BORDER_FOCUS = new Color(0x00C9A7);

    private static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_SUB    = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_LABEL  = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_INPUT  = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BTN    = new Font("Segoe UI", Font.BOLD, 12);

    public AttendanceForm() {

        setTitle("Attendance Management");
        setSize(620, 420);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ── Root panel ─────────────────────────────────────────────────────────
        JPanel root = new JPanel(null);
        root.setBackground(BG_DEEP);
        setContentPane(root);

        // ── Header card ────────────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_CARD);
        header.setBounds(20, 16, 576, 64);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 0, 0, ACCENT),
            BorderFactory.createEmptyBorder(10, 16, 10, 16)
        ));

        JLabel titleLabel = new JLabel("Attendance Management");
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(TEXT_PRIMARY);

        JLabel subLabel = new JLabel("Record and search employee attendance");
        subLabel.setFont(FONT_SUB);
        subLabel.setForeground(TEXT_MUTED);

        JPanel titleGroup = new JPanel(new BorderLayout(0, 2));
        titleGroup.setBackground(BG_CARD);
        titleGroup.add(titleLabel, BorderLayout.NORTH);
        titleGroup.add(subLabel, BorderLayout.SOUTH);
        header.add(titleGroup, BorderLayout.WEST);
        root.add(header);

        // ── Form card ──────────────────────────────────────────────────────────
        JPanel formCard = new JPanel(null);
        formCard.setBackground(BG_CARD);
        formCard.setBounds(20, 96, 576, 220);
        formCard.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        root.add(formCard);

        int labelX = 30, fieldX = 190, fieldW = 220, rowH = 38, startY = 20;

        // Employee ID
        JLabel lblEmpId = makeLabel("Employee ID");
        lblEmpId.setBounds(labelX, startY + 4, 150, 25);
        formCard.add(lblEmpId);

        txtEmpId = makeTextField();
        txtEmpId.setBounds(fieldX, startY, fieldW, rowH);
        formCard.add(txtEmpId);

        // Date
        JLabel lblDate = makeLabel("Date (YYYY-MM-DD)");
        lblDate.setBounds(labelX, startY + 60 + 4, 150, 25);
        formCard.add(lblDate);

        txtDate = makeTextField();
        txtDate.setBounds(fieldX, startY + 60, fieldW, rowH);
        formCard.add(txtDate);

        // Status
        JLabel lblStatus = makeLabel("Status");
        lblStatus.setBounds(labelX, startY + 120 + 4, 150, 25);
        formCard.add(lblStatus);

        cmbStatus = new JComboBox<>(new String[]{"Present", "Absent"});
        styleComboBox(cmbStatus);
        cmbStatus.setBounds(fieldX, startY + 120, fieldW, rowH);
        formCard.add(cmbStatus);

        // ── Button row ─────────────────────────────────────────────────────────
        JPanel btnRow = new JPanel(new GridLayout(1, 4, 10, 0));
        btnRow.setBackground(BG_DEEP);
        btnRow.setBounds(20, 330, 576, 44);
        root.add(btnRow);

        btnSave   = makeButton("Save",   true,  false);
        btnSearch = makeButton("Search", false, false);
        btnView   = makeButton("View",   false, false);
        btnClear  = makeButton("Clear",  false, true);

        btnRow.add(btnSave);
        btnRow.add(btnSearch);
        btnRow.add(btnView);
        btnRow.add(btnClear);

        // ── Action listeners (unchanged) ───────────────────────────────────────

        // Save Attendance
        btnSave.addActionListener(e -> {

            try {

                Attendance attendance = new Attendance();

                attendance.setEmpId(
                        Integer.parseInt(txtEmpId.getText()));

                attendance.setAttendanceDate(
                        txtDate.getText());

                attendance.setStatus(
                        cmbStatus.getSelectedItem().toString());

                AttendanceDAO dao =
                        new AttendanceDAO();

                if (dao.saveAttendance(attendance)) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Attendance Saved Successfully");

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Failed To Save Attendance");

                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter valid details.");

            }

        });

        // Search Attendance
        btnSearch.addActionListener(e -> {

            try {

                int empId =
                        Integer.parseInt(txtEmpId.getText());

                AttendanceDAO dao =
                        new AttendanceDAO();

                ResultSet rs =
                        dao.searchAttendance(empId);

                if (rs != null && rs.next()) {

                    txtDate.setText(
                            rs.getString("attendance_date"));

                    cmbStatus.setSelectedItem(
                            rs.getString("status"));

                    JOptionPane.showMessageDialog(
                            this,
                            "Attendance Found");

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Attendance Not Found");

                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter Valid Employee ID");

            }

        });

        // View Attendance
        btnView.addActionListener(e -> {

            new ViewAttendanceFrame();

        });

        // Clear
        btnClear.addActionListener(e -> {

            txtEmpId.setText("");
            txtDate.setText("");
            cmbStatus.setSelectedIndex(0);

        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ── Helpers ────────────────────────────────────────────────────────────────

    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_MUTED);
        return lbl;
    }

    private JTextField makeTextField() {
        JTextField tf = new JTextField();
        tf.setFont(FONT_INPUT);
        tf.setForeground(TEXT_PRIMARY);
        tf.setBackground(BG_INPUT);
        tf.setCaretColor(ACCENT);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));

        // Focus border highlight
        tf.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_FOCUS, 1),
                    BorderFactory.createEmptyBorder(4, 10, 4, 10)
                ));
            }
            @Override public void focusLost(java.awt.event.FocusEvent e) {
                tf.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER, 1),
                    BorderFactory.createEmptyBorder(4, 10, 4, 10)
                ));
            }
        });
        return tf;
    }

    private void styleComboBox(JComboBox<String> cmb) {
        cmb.setFont(FONT_INPUT);
        cmb.setForeground(TEXT_PRIMARY);
        cmb.setBackground(BG_INPUT);
        cmb.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        cmb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? new Color(0x1A4A6B) : BG_INPUT);
                setForeground(TEXT_PRIMARY);
                setFont(FONT_INPUT);
                setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
                return this;
            }
        });
    }

    private JButton makeButton(String label, boolean primary, boolean ghost) {
        JButton btn = new JButton(label) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                boolean hovered = getModel().isRollover();
                boolean pressed = getModel().isPressed();

                Color bg;
                if (primary) {
                    bg = pressed ? ACCENT_HOVER : hovered ? ACCENT_HOVER : ACCENT;
                } else if (ghost) {
                    bg = pressed ? BTN_DANGER : hovered ? new Color(0x1E3A52) : new Color(0x162333);
                } else {
                    bg = pressed ? ACCENT_HOVER : hovered ? new Color(0x223E55) : BG_CARD;
                }

                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));

                if (!primary) {
                    g2.setColor(hovered ? ACCENT : BORDER);
                    g2.setStroke(new BasicStroke(1f));
                    g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f,
                            getWidth() - 1, getHeight() - 1, 8, 8));
                }

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(FONT_BTN);
        btn.setForeground(primary ? new Color(0x0D1B2A) : TEXT_PRIMARY);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}