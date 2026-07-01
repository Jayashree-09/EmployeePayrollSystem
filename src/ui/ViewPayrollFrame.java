package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewPayrollFrame extends JFrame {

    JTable table;
    DefaultTableModel model;

    // ── Design tokens ──────────────────────────────────────────────────────────
    private static final Color BG_DEEP     = new Color(0x0D1B2A);   // near-black navy
    private static final Color BG_CARD     = new Color(0x1B2B3A);   // card surface
    private static final Color BG_HEADER   = new Color(0x0F2237);   // table header bg
    private static final Color BG_ROW_ALT  = new Color(0x162333);   // zebra stripe
    private static final Color ACCENT      = new Color(0x00C9A7);   // teal accent
    private static final Color TEXT_PRIMARY = new Color(0xE8F0F7);  // primary text
    private static final Color TEXT_MUTED  = new Color(0x7A9BB5);   // muted / header label
    private static final Color BORDER      = new Color(0x1E3A52);   // subtle border
    private static final Color GRID_LINE   = new Color(0x1A3040);   // table grid

    private static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_SUB     = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_HEADER  = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_CELL    = new Font("Segoe UI", Font.PLAIN, 13);

    public ViewPayrollFrame() {

        setTitle("View Payroll");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ── Root panel ─────────────────────────────────────────────────────────
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG_DEEP);
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ── Header bar ─────────────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_CARD);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 3, 0, 0, ACCENT),  // left accent stripe
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));

        JLabel titleLabel = new JLabel("Payroll Records");
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(TEXT_PRIMARY);

        JLabel subLabel = new JLabel("Employee Payroll Overview");
        subLabel.setFont(FONT_SUB);
        subLabel.setForeground(TEXT_MUTED);

        JPanel titleGroup = new JPanel(new BorderLayout(0, 2));
        titleGroup.setBackground(BG_CARD);
        titleGroup.add(titleLabel, BorderLayout.NORTH);
        titleGroup.add(subLabel, BorderLayout.SOUTH);

        header.add(titleGroup, BorderLayout.WEST);

        // ── Table model (unchanged logic) ──────────────────────────────────────
        model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        model.addColumn("Payroll ID");
        model.addColumn("Employee ID");
        model.addColumn("Basic Salary");
        model.addColumn("HRA");
        model.addColumn("DA");
        model.addColumn("Bonus");
        model.addColumn("PF");
        model.addColumn("Tax");
        model.addColumn("Net Salary");

        table = new JTable(model);

        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs =
                    st.executeQuery("SELECT * FROM employee_payroll");

            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getInt("payroll_id"),
                        rs.getInt("emp_id"),
                        rs.getDouble("basic_salary"),
                        rs.getDouble("hra"),
                        rs.getDouble("da"),
                        rs.getDouble("bonus"),
                        rs.getDouble("pf"),
                        rs.getDouble("tax"),
                        rs.getDouble("net_salary")
                });
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        // ── Table styling ──────────────────────────────────────────────────────
        table.setBackground(BG_CARD);
        table.setForeground(TEXT_PRIMARY);
        table.setFont(FONT_CELL);
        table.setRowHeight(34);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(GRID_LINE);
        table.setSelectionBackground(new Color(0x00C9A7, false) {{}}); // handled below
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);

        // Selection colors
        table.setSelectionBackground(new Color(0x1A4A6B));
        table.setSelectionForeground(TEXT_PRIMARY);

        // Zebra striping renderer
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                setFont(FONT_CELL);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));

                if (isSelected) {
                    setBackground(new Color(0x1A4A6B));
                    setForeground(TEXT_PRIMARY);
                } else {
                    setBackground(row % 2 == 0 ? BG_CARD : BG_ROW_ALT);
                    // Highlight Net Salary column in accent color
                    setForeground(col == 8 ? ACCENT : TEXT_PRIMARY);
                }
                return this;
            }
        });

        // Table header
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(BG_HEADER);
        tableHeader.setForeground(TEXT_MUTED);
        tableHeader.setFont(FONT_HEADER);
        tableHeader.setPreferredSize(new Dimension(0, 40));
        tableHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT));
        tableHeader.setReorderingAllowed(false);

        ((DefaultTableCellRenderer) tableHeader.getDefaultRenderer())
            .setHorizontalAlignment(SwingConstants.LEFT);

        // ── Scroll pane ────────────────────────────────────────────────────────
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        scroll.getViewport().setBackground(BG_CARD);
        scroll.getVerticalScrollBar().setBackground(BG_DEEP);
        scroll.getHorizontalScrollBar().setBackground(BG_DEEP);

        // ── Card wrapper ───────────────────────────────────────────────────────
        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        card.add(scroll, BorderLayout.CENTER);

        // ── Footer status bar ──────────────────────────────────────────────────
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        footer.setBackground(BG_DEEP);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 2, 0, 0));

        JLabel footerLabel = new JLabel("  ●  " + model.getRowCount() + " records loaded");
        footerLabel.setFont(FONT_SUB);
        footerLabel.setForeground(ACCENT);
        footer.add(footerLabel);

        // ── Assemble ───────────────────────────────────────────────────────────
        root.add(header, BorderLayout.NORTH);
        root.add(Box.createVerticalStrut(12), BorderLayout.CENTER); // spacing trick
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(BG_DEEP);
        center.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        center.add(card, BorderLayout.CENTER);
        center.add(footer, BorderLayout.SOUTH);

        root.add(center, BorderLayout.CENTER);

        setContentPane(root);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
