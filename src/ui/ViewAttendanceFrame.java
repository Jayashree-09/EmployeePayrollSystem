package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewAttendanceFrame extends JFrame {

    JTable table;
    DefaultTableModel model;

    // ── Design tokens ──────────────────────────────────────────────
    private static final Color BG_MAIN      = new Color(0xF0F4F8);
    private static final Color BG_CARD      = Color.WHITE;
    private static final Color HEADER_BG    = new Color(0x1E3A5F);   // deep navy
    private static final Color HEADER_TEXT  = Color.WHITE;
    private static final Color TBL_HEADER   = new Color(0x1E3A5F);   // navy
    private static final Color TBL_HDR_FG   = Color.WHITE;
    private static final Color ROW_ODD      = Color.WHITE;
    private static final Color ROW_EVEN     = new Color(0xF1F5F9);   // slate-100
    private static final Color ROW_SEL      = new Color(0xDCEDFB);   // indigo-100
    private static final Color BORDER_COLOR = new Color(0xCBD5E1);   // slate-300
    private static final Color NEUTRAL      = new Color(0x64748B);
    private static final Color PRESENT_FG   = new Color(0x16A34A);   // green-600
    private static final Color ABSENT_FG    = new Color(0xDC2626);   // red-600
    private static final Color OTHER_FG     = new Color(0xD97706);   // amber-600

    public ViewAttendanceFrame() {

        setTitle("View Attendance");
        setSize(700, 470);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_MAIN);

        // ── Header banner ──────────────────────────────────────────
        JPanel header = new JPanel(null);
        header.setPreferredSize(new Dimension(700, 65));
        header.setBackground(HEADER_BG);

        JLabel lblTitle = new JLabel("Attendance Records");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(HEADER_TEXT);
        lblTitle.setBounds(25, 10, 350, 28);
        header.add(lblTitle);

        JLabel lblSub = new JLabel("Full log of employee attendance");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(0xBFDBFE));  // indigo-200
        lblSub.setBounds(25, 38, 300, 18);
        header.add(lblSub);

        add(header, BorderLayout.NORTH);

        // ── Card wrapper ────────────────────────────────────────────
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(18, 20, 18, 20),
                BorderFactory.createLineBorder(BORDER_COLOR, 1, true)
        ));

        // ── Section label ──────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG_CARD);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 14, 8, 14));

        JLabel secLabel = new JLabel("ATTENDANCE LOG");
        secLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        secLabel.setForeground(NEUTRAL);
        topBar.add(secLabel, BorderLayout.WEST);
        card.add(topBar, BorderLayout.NORTH);

        // ── Table setup ────────────────────────────────────────────
        model = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        model.addColumn("Attendance ID");
        model.addColumn("Employee ID");
        model.addColumn("Date");
        model.addColumn("Status");

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(34);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(0xE2E8F0));
        table.setSelectionBackground(ROW_SEL);
        table.setSelectionForeground(new Color(0x1E3A5F));
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);

        // Alternating row colors + status coloring
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
                if (isSelected) {
                    setBackground(ROW_SEL);
                    setForeground(new Color(0x1E3A5F));
                } else {
                    setBackground(row % 2 == 0 ? ROW_ODD : ROW_EVEN);
                    // Color-code Status column
                    if (col == 3 && value != null) {
                        String s = value.toString().toLowerCase();
                        if (s.equals("present"))      setForeground(PRESENT_FG);
                        else if (s.equals("absent"))  setForeground(ABSENT_FG);
                        else                           setForeground(OTHER_FG);
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else {
                        setForeground(new Color(0x374151));
                        setFont(new Font("Segoe UI", Font.PLAIN, 13));
                    }
                }
                return this;
            }
        });

        // Table header styling
        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 12));
        th.setBackground(TBL_HEADER);
        th.setForeground(TBL_HDR_FG);
        th.setPreferredSize(new Dimension(0, 38));
        th.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) th.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(110);
        table.getColumnModel().getColumn(1).setPreferredWidth(110);
        table.getColumnModel().getColumn(2).setPreferredWidth(160);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);

        // ── Scroll pane ─────────────────────────────────────────────
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(BG_CARD);
        card.add(scroll, BorderLayout.CENTER);

        // ── Footer row count label ──────────────────────────────────
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 6));
        footer.setBackground(BG_CARD);
        JLabel rowCountLabel = new JLabel();
        rowCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        rowCountLabel.setForeground(NEUTRAL);
        footer.add(rowCountLabel);
        card.add(footer, BorderLayout.SOUTH);

        add(card, BorderLayout.CENTER);

        // ══════════════════════════════════════════════════════════════
        //  ALL ORIGINAL LOGIC BELOW — UNTOUCHED
        // ══════════════════════════════════════════════════════════════

        try {

            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs =
                    st.executeQuery("SELECT * FROM employee_attendance");

            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getInt("attendance_id"),
                        rs.getInt("emp_id"),
                        rs.getDate("attendance_date"),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        // Update row count after data loads
        rowCountLabel.setText(model.getRowCount() + " records found");

        setLocationRelativeTo(null);
        setVisible(true);
    }
}