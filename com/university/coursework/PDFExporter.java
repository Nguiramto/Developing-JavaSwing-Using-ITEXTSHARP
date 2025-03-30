package com.university.coursework;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import javax.swing.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDFExporter {
    public static void exportTableToPDF(JTable table, String filePath, boolean darkMode) throws Exception {
        if (table == null || table.getRowCount() == 0) {
            throw new IllegalArgumentException("Table is empty or null");
        }

        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Set background color for dark mode
        if (darkMode) {
            document.setBackgroundColor(ColorConstants.DARK_GRAY);
        }

        // Add title
        Paragraph title = new Paragraph("FACULTY OF SCIENCE AND TECHNOLOGY\nADVANCED OBJECT-ORIENTED PROGRAMMING CLASS ACTIVITY")
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(darkMode ? ColorConstants.WHITE : ColorConstants.BLACK)
                .setMarginBottom(20f);
        document.add(title);

        // Add timestamp
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        document.add(new Paragraph("Report Timestamp: " + timestamp)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(darkMode ? ColorConstants.WHITE : ColorConstants.BLACK)
                .setMarginBottom(30f));

        // Create PDF table
        Table pdfTable = new Table(UnitValue.createPercentArray(table.getColumnCount()))
                .useAllAvailableWidth()
                .setMarginTop(20f);

        // Header row
        for (int i = 0; i < table.getColumnCount(); i++) {
            pdfTable.addHeaderCell(new Paragraph(table.getColumnName(i))
                    .setBackgroundColor(darkMode ? ColorConstants.GRAY : ColorConstants.LIGHT_GRAY)
                    .setFontColor(darkMode ? ColorConstants.WHITE : ColorConstants.BLACK)
                    .setTextAlignment(TextAlignment.CENTER));
        }

        // Data rows
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                Object value = table.getValueAt(i, j);
                pdfTable.addCell(new Paragraph(value != null ? value.toString() : "")
                        .setBackgroundColor(darkMode ? ColorConstants.DARK_GRAY : ColorConstants.WHITE)
                        .setFontColor(darkMode ? ColorConstants.WHITE : ColorConstants.BLACK)
                        .setTextAlignment(TextAlignment.CENTER));
            }
        }
        document.add(pdfTable);

        // Close document properly
        document.close();
    }
}
