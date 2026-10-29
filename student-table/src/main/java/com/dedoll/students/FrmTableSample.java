package com.dedoll.students;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import com.formdev.flatlaf.FlatDarkLaf;


public class FrmTableSample extends JFrame {

    // ── Warm dark palette ───────────────────────────────────────────
    private static final Color BG_DEEP      = new Color(30, 24, 20);     // deep brown-black
    private static final Color BG_CARD      = new Color(42, 34, 28);     // card / panel
    private static final Color BG_FIELD     = new Color(52, 42, 35);     // text field bg
    private static final Color ACCENT       = new Color(217, 152, 78);   // warm amber
    private static final Color ACCENT_DIM   = new Color(180, 120, 55);   // muted amber
    private static final Color TEXT_PRIMARY  = new Color(235, 225, 210);  // warm off-white
    private static final Color TEXT_MUTED    = new Color(170, 155, 135);  // warm gray
    private static final Color TABLE_ROW_ALT = new Color(38, 30, 25);    // subtle stripe
    private static final Color TABLE_GRID   = new Color(60, 50, 42);     // grid lines

    private final DefaultTableModel tableModel;
    private final TableRowSorter<DefaultTableModel> sorter;

    public FrmTableSample() {
        super("data integration - table - De Roxas & Dollentes");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1050, 550);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DEEP);

        // ── Header ──────────────────────────────────────────────────
        JLabel headerLabel = new JLabel("Data Integration - Table - De Roxas - Dollentes");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        headerLabel.setForeground(ACCENT);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_DEEP);
        headerPanel.setBorder(new EmptyBorder(22, 16, 10, 16));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

    
        JPanel accentLine = new JPanel();
        accentLine.setPreferredSize(new Dimension(0, 3));
        accentLine.setBackground(ACCENT_DIM);
        headerPanel.add(accentLine, BorderLayout.SOUTH);

        String[] columns = {"student_Id", "name", "course", "year_level", "contact_number", "gmail"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

      
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setRowHeight(34);
        table.setBackground(BG_CARD);
        table.setForeground(TEXT_PRIMARY);
        table.setSelectionBackground(ACCENT_DIM);
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setGridColor(TABLE_GRID);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));

    
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                setBorder(new EmptyBorder(4, 10, 4, 10));
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? BG_CARD : TABLE_ROW_ALT);
                    setForeground(TEXT_PRIMARY);
                }
                return this;
            }
        });

        // Table header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 16));
        header.setBackground(BG_FIELD);
        header.setForeground(ACCENT);
        header.setPreferredSize(new Dimension(0, 40));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT_DIM));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(BG_CARD);
        scrollPane.setBorder(BorderFactory.createLineBorder(TABLE_GRID));

        // ── Search bar ──────────────────────────────────────────────
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        searchLabel.setForeground(TEXT_MUTED);

        JTextField txtSearch = new JTextField();
        txtSearch.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtSearch.setBackground(BG_FIELD);
        txtSearch.setForeground(TEXT_PRIMARY);
        txtSearch.setCaretColor(ACCENT);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_DIM, 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        txtSearch.putClientProperty("JTextField.placeholderText", "Type to filter…");
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e)  { applyFilter(txtSearch); }
            @Override public void removeUpdate(DocumentEvent e)  { applyFilter(txtSearch); }
            @Override public void changedUpdate(DocumentEvent e) { applyFilter(txtSearch); }
        });

        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(BG_DEEP);
        searchPanel.setBorder(new EmptyBorder(10, 16, 10, 16));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(txtSearch, BorderLayout.CENTER);

   
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BG_DEEP);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

    
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BG_DEEP);
        contentPanel.setBorder(new EmptyBorder(0, 16, 16, 16));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

     
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Load data in background
        loadData();
    }


    private void applyFilter(JTextField txtSearch) {
        String text = txtSearch.getText().trim();
        if (text.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(text)));
        }
    }


    private void loadData() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                String sql = "SELECT student_Id, name, course, year_level, contact_number, gmail FROM tbl_student";
                try (Connection conn = Procedure.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        Object[] row = {
                            rs.getString("student_Id"),
                            rs.getString("name"),
                            rs.getString("course"),
                            rs.getInt("year_level"),
                            rs.getString("contact_number"),
                            rs.getString("gmail")
                        };
                        SwingUtilities.invokeLater(() -> tableModel.addRow(row));
                    }
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(
                            FrmTableSample.this,
                            ex.getMessage(),
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE
                        )
                    );
                }
                return null;
            }
        }.execute();
    }

  
    public static void main(String[] args) {
        FlatDarkLaf.setup();

        // Global warm-dark overrides
        UIManager.put("defaultFont", new Font("SansSerif", Font.PLAIN, 15));

        SwingUtilities.invokeLater(() -> new FrmTableSample().setVisible(true));
    }
}
