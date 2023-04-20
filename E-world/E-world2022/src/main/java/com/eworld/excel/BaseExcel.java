package com.eworld.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

public interface BaseExcel {
   public void writeHeaderLine();
    public void createCell(Row row, int columnCount, Object value, CellStyle style);
    public void writeDataLines();
}
