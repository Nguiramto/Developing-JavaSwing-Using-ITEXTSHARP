package com.university.coursework;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class PDFExportApp extends JFrame {
    private final JTable table;
    private final TableModel tableModel;

    public PDFExportApp() {
        setTitle("Unique PDF Export Application");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Apply modern UI theme
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize components
        tableModel = new TableModel();
        table = new JTable(tableModel);

        // Custom header styling
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setDefaultRenderer(new HeaderRenderer());

        JButton loadDataButton = new JButton("Load Data");
        JButton exportToPDFButton = new JButton("Export to PDF");
        JButton clearTableButton = new JButton("Clear Table");

        // Apply styling
        getContentPane().setBackground(new Color(20, 20, 40)); // Deep blue background
        table.setBackground(new Color(40, 40, 60)); // Dark blue-gray
        table.setForeground(Color.CYAN); // Light cyan text

        // Style buttons
        JButton[] buttons = {loadDataButton, exportToPDFButton, clearTableButton};
        for (JButton button : buttons) {
            button.setBackground(new Color(120, 120, 255)); // Lighter blue
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 14));
        }

        // Layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(20, 20, 40));
        buttonPanel.add(loadDataButton);
        buttonPanel.add(exportToPDFButton);
        buttonPanel.add(clearTableButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        // Event listeners
        loadDataButton.addActionListener(e -> loadData());
        exportToPDFButton.addActionListener(e -> exportToPDF());
        clearTableButton.addActionListener(e -> clearTable());
    }

    private void loadData() {
        Object[][] data = {
                {1, "John Doe", "Computer Science", "A"},
                {2, "Jane Smith", "Mathematics", "B"},
                {3, "Alice Johnson", "Physics", "A+"},
                {4, "Michael Brown", "Engineering", "B+"},
                {5, "Emily Davis", "Biology", "A"},
                {6, "David Wilson", "Economics", "B-"},
                {7, "Sophia Martinez", "Chemistry", "A-"},
                {8, "James Taylor", "History", "C+"},
                {9, "Olivia Anderson", "Philosophy", "A"},
                {10, "Ethan Thomas", "Statistics", "B"},
                {11, "Charlotte White", "Political Science", "A-"},
                {12, "Daniel Harris", "Electrical Engineering", "B+"},
                {13, "Ava Martin", "Psychology", "A"},
                {14, "Benjamin Clark", "Environmental Science", "C"}
        };
        String[] columns = {"ID", "Name", "Department", "Grade"};
        tableModel.setData(data, columns);
    }

    private void exportToPDF() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".pdf";
            try {
                boolean darkMode = JOptionPane.showConfirmDialog(this, "Use Dark Mode for PDF?", "Theme", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                PDFExporter.exportTableToPDF(table, filePath, darkMode);
                JOptionPane.showMessageDialog(this, "PDF exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error exporting PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearTable() {
        tableModel.setData(new Object[0][0], new String[0]);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PDFExportApp().setVisible(true));
    }

    // Custom header renderer
    static class HeaderRenderer extends DefaultTableCellRenderer {
        public HeaderRenderer() {
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Arial", Font.BOLD, 14));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            Color[] headerColors = {new Color(80, 80, 160), new Color(100, 100, 200), new Color(120, 120, 255), new Color(140, 140, 255)};
            setBackground(headerColors[column % headerColors.length]);
            setForeground(Color.WHITE);
            return this;
        }
    }
}
