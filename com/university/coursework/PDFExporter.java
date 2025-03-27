package com.university.coursework;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.RootElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;  // Fixed import
import com.itextpdf.layout.properties.UnitValue;     // Fixed import

import javax.swing.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDFExporter {
    public static void exportTableToPDF(JTable table, String filePath, boolean darkMode) throws Exception {
        if (table == null || table.getRowCount() == 0) {
            throw new IllegalArgumentException("Table is empty or null");
        }

        PdfWriter writer = null;
        try {
            File file = new File(filePath);
            writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Set theme
            if (darkMode) {
                Document color;
                try (RootElement<Document> document1 = color = document.setBackgroundColor(ColorConstants.DARK_GRAY)) {
                }
                try {
                } finally {
                    color.close();
                }
            }

            // Add title (with proper spacing)
            Paragraph title = new Paragraph("FACULTY OF SCIENCE AND TECHNOLOGY\nADVANCED OBJECT-ORIENTED PROGRAMMING CLASS ACTIVITY")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(darkMode ? ColorConstants.WHITE : ColorConstants.BLACK)
                    .setMarginBottom(20f);  // Added spacing
            document.add(title);

            // Add timestamp first (so watermark appears behind)
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            document.add(new Paragraph("Report Timestamp: " + timestamp)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(darkMode ? ColorConstants.WHITE : ColorConstants.BLACK)
                    .setMarginBottom(30f));

            // Add watermark (behind content)
            Paragraph watermark = new Paragraph("Confidential")
                    .setFontSize(60)  // Larger for better visibility
                    .setTextAlignment(TextAlignment.CENTER)
                    .setOpacity(0.1f)  // More subtle
                    .setRotationAngle(45)
                    .setFixedPosition(50, 300, 500);  // Positioned properly
            document.add(watermark);

            // Create PDF table
            Table pdfTable = new Table(UnitValue.createPercentArray(table.getColumnCount()))
                    .useAllAvailableWidth()
                    .setMarginTop(20f);

            // Header row
            for (int i = 0; i < table.getColumnCount(); i++) {
                pdfTable.addHeaderCell(table.getColumnName(i))
                        .setBackgroundColor(darkMode ? ColorConstants.GRAY : ColorConstants.LIGHT_GRAY)
                        .setFontColor(darkMode ? ColorConstants.WHITE : ColorConstants.BLACK);
            }

            // Data rows
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    Object value = table.getValueAt(i, j);
                    pdfTable.addCell(value != null ? value.toString() : "")
                            .setBackgroundColor(darkMode ? ColorConstants.DARK_GRAY : ColorConstants.WHITE)
                            .setFontColor(darkMode ? ColorConstants.WHITE : ColorConstants.BLACK);
                }
            }
            document.add(pdfTable);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}