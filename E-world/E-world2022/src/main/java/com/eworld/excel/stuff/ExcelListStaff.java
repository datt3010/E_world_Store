package com.eworld.excel.stuff;

import com.eworld.entity.Account;
import com.eworld.excel.BaseExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
public class ExcelListStaff implements BaseExcel {
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet sheet;

    private List<Account> accountList;

    public ExcelListStaff(List<Account> accountList){
        this.accountList =accountList;
        xssfWorkbook = new XSSFWorkbook();
    }
    @Override
    public void writeHeaderLine() {
        sheet = xssfWorkbook.createSheet("Stuffs");

        Row row = sheet.createRow(0);

        CellStyle style = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "User ID", style);
        createCell(row, 1, "E-mail", style);
        createCell(row, 2, "Full Name", style);
        createCell(row, 3, "Phone", style);
        createCell(row, 4, "Address", style);

    }
    @Override
    public void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    @Override
    public void writeDataLines() {
        int rowCount = 1;

        CellStyle style = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Account user : accountList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getId(), style);
            createCell(row, columnCount++, user.getAccountProfile().getEmail(), style);
            createCell(row, columnCount++, user.getAccountProfile().getFirstName(), style);
            createCell(row, columnCount++, user.getAccountProfile().getPhone().toString(), style);
            createCell(row, columnCount++, user.getAccountProfile().getAddress(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        xssfWorkbook.write(outputStream);
        xssfWorkbook.close();

        outputStream.close();

    }

}
