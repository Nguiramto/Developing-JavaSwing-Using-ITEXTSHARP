package com.university.coursework;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
    private Object[][] data;
    private String[] columns;

    public void setData(Object[][] data, String[] columns) {
        this.data = data;
        this.columns = columns;
        fireTableStructureChanged();
    }

    @Override
    public int getRowCount() {
        return data == null ? 0 : data.length;
    }

    @Override
    public int getColumnCount() {
        return columns == null ? 0 : columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
}

