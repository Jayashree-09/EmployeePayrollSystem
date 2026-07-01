package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class DatabaseViewer extends JFrame {

    // ── Design tokens (matches ViewPayrollFrame system) ────────────────────────
    private static final Color BG_DEEP      = new Color(0x0D1B2A);
    private static final Color BG_CARD      = new Color(0x1B2B3A);
    private static final Color ACCENT       = new Color(0x00C9A7);
    private static final Color ACCENT_HOVER = new Color(0x00A688);
    private static final Color TEXT_PRIMARY = new Color(0xE8F0F7);
    private static final Color TEXT_MUTED   = new Color(0x7A9BB5);
    private static final Color BORDER       = new Color(0x1E3A52);

    private static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_SUB    = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_BTN    = new Font("Segoe UI", Font.BOLD, 13);

    public DatabaseViewer() {

        setTitle("Database Viewer");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ── Root panel ─────────────────────────────────────────────────────────
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG_DEEP);
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ── Header ─────────────────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_CARD);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 0, 0, ACCENT),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));

        JLabel titleLabel = new JLabel("Database Viewer");
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(TEXT_PRIMARY);

        JLabel subLabel = new JLabel("Select a table to explore");
        subLabel.setFont(FONT_SUB);
        subLabel.setForeground(TEXT_MUTED);

        JPanel titleGroup = new JPanel(new BorderLayout(0, 2));
        titleGroup.setBackground(BG_CARD);
        titleGroup.add(titleLabel, BorderLayout.NORTH);
        titleGroup.add(subLabel, BorderLayout.SOUTH);
        header.add(titleGroup, BorderLayout.WEST);

        // ── Button panel ───────────────────────────────────────────────────────
        JPanel btnPanel = new JPanel(new GridLayout(3, 1, 0, 12));
        btnPanel.setBackground(BG_DEEP);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));

        JButton btnEmployees  = makeNavButton("View Employees",  "👤");
        JButton btnPayroll    = makeNavButton("View Payroll",    "💰");
        JButton btnAttendance = makeNavButton("View Attendance", "📅");

        btnPanel.add(btnEmployees);
        btnPanel.add(btnPayroll);
        btnPanel.add(btnAttendance);

        // ── Action listeners (unchanged) ───────────────────────────────────────
        btnEmployees.addActionListener(e ->
                new ViewEmployeeFrame());

        btnPayroll.addActionListener(e ->
                new ViewPayrollFrame());

        btnAttendance.addActionListener(e ->
                new ViewAttendanceFrame());

        // ── Assemble ───────────────────────────────────────────────────────────
        root.add(header, BorderLayout.NORTH);
        root.add(btnPanel, BorderLayout.CENTER);

        setContentPane(root);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ── Factory: styled nav button ─────────────────────────────────────────────
    private JButton makeNavButton(String label, String icon) {
        JButton btn = new JButton(icon + "   " + label) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                boolean hovered = getModel().isRollover();
                boolean pressed = getModel().isPressed();

                // Background
                g2.setColor(pressed ? ACCENT_HOVER : hovered ? new Color(0x223E55) : BG_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));

                // Left accent stripe
                g2.setColor(ACCENT);
                g2.fill(new RoundRectangle2D.Float(0, 0, 4, getHeight(), 4, 4));

                // Border
                g2.setColor(hovered ? ACCENT : BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f,
                        getWidth() - 1, getHeight() - 1, 10, 10));

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(FONT_BTN);
        btn.setForeground(TEXT_PRIMARY);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 18));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return btn;
    }
}