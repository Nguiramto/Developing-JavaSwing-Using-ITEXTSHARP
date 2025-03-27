package com.university.coursework;

import javax.swing.*;
import java.awt.*;

public class PDFExportApp extends JFrame {
    private final JTable table;
    private final TableModel tableModel;

    public PDFExportApp() {
        setTitle("Unique PDF Export Application");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        tableModel = new TableModel();
        table = new JTable(tableModel);
        JButton loadDataButton = new JButton("Load Data");
        JButton exportToPDFButton = new JButton("Export to PDF");
        JButton clearTableButton = new JButton("Clear Table");

        // Layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
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
        // Simulate loading data
        Object[][] data = {
                {1, "John Doe", "Computer Science", "A"},
                {2, "Jane Smith", "Mathematics", "B"},
                {3, "Alice Johnson", "Physics", "A+"}
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
}

